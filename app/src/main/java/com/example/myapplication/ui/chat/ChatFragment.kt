package com.example.myapplication.ui.chat

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.ai.OpenAIService
import org.json.JSONArray
import okhttp3.OkHttpClient
import okhttp3.Request
import java.util.Locale

class ChatFragment : Fragment() {
    // Substitua pela sua chave real
    private val openAIService = OpenAIService(BuildConfig.OPENAI_API_KEY)
    private val chatHistory = mutableListOf<Pair<String, String>>() // (pergunta, resposta)
    private lateinit var adapter: ChatAdapter
    private val comandosParaUrls = mapOf(
        "ligue a luz" to "http://192.168.1.100/ligar",
        "desligue a luz" to "http://192.168.1.100/desligar"
        // Adicione mais comandos e URLs conforme necessário
    )
    private val REQUEST_CODE_VOICE = 1001

    private fun saveChatHistory(context: Context) {
        val prefs = context.getSharedPreferences("chat_prefs", Context.MODE_PRIVATE)
        val arr = JSONArray()
        chatHistory.forEach { (q, a) ->
            val obj = org.json.JSONObject()
            obj.put("q", q)
            obj.put("a", a)
            arr.put(obj)
        }
        prefs.edit().putString("history", arr.toString()).apply()
    }

    private fun loadChatHistory(context: Context) {
        val prefs = context.getSharedPreferences("chat_prefs", Context.MODE_PRIVATE)
        val arrStr = prefs.getString("history", null)
        if (arrStr != null) {
            val arr = JSONArray(arrStr)
            for (i in 0 until arr.length()) {
                val obj = arr.getJSONObject(i)
                chatHistory.add(Pair(obj.getString("q"), obj.getString("a")))
            }
        }
    }

    private fun acionarDispositivo(url: String, onResult: (Boolean) -> Unit) {
        val client = OkHttpClient()
        val request = Request.Builder().url(url).build()
        Thread {
            try {
                val response = client.newCall(request).execute()
                onResult(response.isSuccessful)
            } catch (e: Exception) {
                onResult(false)
            }
        }.start()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_chat, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerViewChat)
        val editTextInput = view.findViewById<EditText>(R.id.editTextChatInput)
        val buttonSend = view.findViewById<Button>(R.id.buttonSendChat)
        val progressBar = view.findViewById<ProgressBar>(R.id.progressBarLoading)
        val layoutQuickPrompts = view.findViewById<LinearLayout>(R.id.layoutQuickPrompts)
        val buttonMic = view.findViewById<Button>(R.id.buttonMicChat)

        adapter = ChatAdapter(chatHistory)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        loadChatHistory(requireContext())
        adapter.notifyDataSetChanged()

        buttonSend.setOnClickListener {
            val question = editTextInput.text.toString().trim().lowercase()
            if (question.isEmpty()) return@setOnClickListener
            editTextInput.setText("")
            progressBar.visibility = View.VISIBLE
            val url = comandosParaUrls[question]
            if (url != null) {
                acionarDispositivo(url) { sucesso ->
                    requireActivity().runOnUiThread {
                        progressBar.visibility = View.GONE
                        val resposta = if (sucesso) "Comando executado com sucesso!" else "Falha ao executar comando."
                        chatHistory.add(Pair(question, resposta))
                        adapter.notifyItemInserted(chatHistory.size - 1)
                        recyclerView.scrollToPosition(chatHistory.size - 1)
                        saveChatHistory(requireContext())
                    }
                }
            } else {
                openAIService.getCompletion(question) { resposta ->
                    requireActivity().runOnUiThread {
                        progressBar.visibility = View.GONE
                        chatHistory.add(Pair(question, resposta ?: "Erro ao obter resposta."))
                        adapter.notifyItemInserted(chatHistory.size - 1)
                        recyclerView.scrollToPosition(chatHistory.size - 1)
                        saveChatHistory(requireContext())
                    }
                }
            }
        }

        buttonMic.setOnClickListener {
            val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
            try {
                startActivityForResult(intent, REQUEST_CODE_VOICE)
            } catch (e: Exception) {
                Toast.makeText(requireContext(), "Reconhecimento de voz não suportado.", Toast.LENGTH_SHORT).show()
            }
        }

        val quickPrompts = listOf(
            "Sugira o melhor horário para minha próxima tarefa.",
            "Há conflitos na minha agenda hoje?",
            "Como posso otimizar meu tempo amanhã?",
            "Quais tarefas posso adiar sem prejuízo?",
            "Me lembre de beber água a cada 2 horas."
        )
        quickPrompts.forEach { prompt ->
            val btn = Button(requireContext())
            btn.text = prompt
            btn.setOnClickListener {
                progressBar.visibility = View.VISIBLE
                val url = comandosParaUrls[prompt.lowercase()]
                if (url != null) {
                    acionarDispositivo(url) { sucesso ->
                        requireActivity().runOnUiThread {
                            progressBar.visibility = View.GONE
                            val resposta = if (sucesso) "Comando executado com sucesso!" else "Falha ao executar comando."
                            chatHistory.add(Pair(prompt, resposta))
                            adapter.notifyItemInserted(chatHistory.size - 1)
                            recyclerView.scrollToPosition(chatHistory.size - 1)
                            saveChatHistory(requireContext())
                        }
                    }
                } else {
                    openAIService.getCompletion(prompt) { resposta ->
                        requireActivity().runOnUiThread {
                            progressBar.visibility = View.GONE
                            chatHistory.add(Pair(prompt, resposta ?: "Erro ao obter resposta."))
                            adapter.notifyItemInserted(chatHistory.size - 1)
                            recyclerView.scrollToPosition(chatHistory.size - 1)
                            saveChatHistory(requireContext())
                        }
                    }
                }
            }
            layoutQuickPrompts.addView(btn)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_VOICE && resultCode == Activity.RESULT_OK) {
            val result = data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
            val recognizedText = result?.get(0) ?: ""
            if (recognizedText.isNotEmpty()) {
                val editTextInput = view?.findViewById<EditText>(R.id.editTextChatInput)
                editTextInput?.setText(recognizedText)
            }
        }
    }
} 