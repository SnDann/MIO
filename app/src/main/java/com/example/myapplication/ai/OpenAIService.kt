package com.example.myapplication.ai

import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject

class OpenAIService(private val apiKey: String) {
    private val client = OkHttpClient()
    private val url = "https://api.openai.com/v1/chat/completions"

    fun getCompletion(prompt: String, onResult: (String?) -> Unit) {
        val json = JSONObject()
        json.put("model", "gpt-3.5-turbo")
        json.put("messages", listOf(mapOf("role" to "user", "content" to prompt)))
        val body = json.toString().toRequestBody("application/json".toMediaTypeOrNull())

        val request = Request.Builder()
            .url(url)
            .addHeader("Authorization", "Bearer $apiKey")
            .addHeader("Content-Type", "application/json")
            .post(body)
            .build()

        Thread {
            try {
                val response = client.newCall(request).execute()
                val responseBody = response.body?.string()
                val result = if (response.isSuccessful && responseBody != null) {
                    val obj = JSONObject(responseBody)
                    val choices = obj.getJSONArray("choices")
                    if (choices.length() > 0) {
                        choices.getJSONObject(0).getJSONObject("message").getString("content")
                    } else null
                } else null
                onResult(result)
            } catch (e: Exception) {
                onResult(null)
            }
        }.start()
    }
} 