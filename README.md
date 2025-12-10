# 🤖 MIO - Assistente Pessoal Inteligente

MIO é um projeto de assistente pessoal multiplataforma que combina uma aplicação mobile Android (Kotlin) e um web app moderno (React) para ajudar você a gerenciar tarefas, lembretes e atividades diárias com inteligência.

## 📱 Plataformas Suportadas

- **Android** (`/app`) - Aplicativo mobile nativo com Kotlin e Android Studio
- **Web** (`/web`) - Aplicação web moderna com React 18 e Vite

## 🎯 Objetivo

Criar um assistente pessoal acessível, simples e funcional que rode em múltiplos dispositivos, permitindo que o usuário:

- Crie e gerencie tarefas e lembretes
- Visualize um resumo diário das atividades
- Acesse suas informações em qualquer dispositivo
- Aproveite uma interface limpa e intuitiva

## 🚀 Começando

### Web App

```bash
cd web
npm install
npm run dev
```

O aplicativo web estará disponível em `http://localhost:3000`.

### Android App

1. Abra o projeto no Android Studio
2. Sincronize o Gradle
3. Execute no emulador ou dispositivo

## 📦 Estrutura do Projeto

```
MIO/
├── app/              # Aplicativo Android (Kotlin)
├── web/              # Aplicação Web (React + Vite)
│   ├── src/
│   │   ├── components/   # Componentes React
│   │   ├── App.jsx       # Componente raiz
│   │   └── index.css     # Estilos globais
│   ├── index.html        # HTML raiz
│   ├── package.json      # Dependências npm
│   └── vite.config.js    # Configuração Vite
├── gradle/           # Gradle wrapper
└── README.md         # Este arquivo
```

## ✨ Funcionalidades (MVP)

- ✅ Criar nova tarefa
- ✅ Marcar tarefa como concluída
- ✅ Deletar tarefa
- ✅ Persistência de dados via localStorage (web)
- 🔄 Sincronização entre dispositivos (em desenvolvimento)
- 🤖 Inteligência artificial para sugerir tarefas (roadmap)

## 🛠️ Stack Tecnológico

### Web
- React 18
- Vite 4
- Vanilla CSS (com CSS variables)

### Mobile
- Kotlin
- Android SDK
- Gradle

## 📝 Convenções de Commit

Mantemos um histórico limpo de commits com mensagens descritivas:

- `feat: adicionar nova funcionalidade`
- `fix: corrigir bug`
- `docs: atualizar documentação`
- `refactor: reorganizar código`
- `chore: tarefas de manutenção`

## 🌱 Roadmap

### v0.2
- [ ] API backend Node/Express
- [ ] Autenticação de usuário
- [ ] Sincronização entre dispositivos

### v0.3
- [ ] Integração com calendário
- [ ] Notificações push
- [ ] Análise de produtividade

### v1.0
- [ ] IA para sugestões de tarefas
- [ ] Modo offline avançado
- [ ] Temas personalizáveis

## 🤝 Como Contribuir

1. Faça um fork do repositório
2. Crie uma branch para sua feature (`git checkout -b feature/AmazingFeature`)
3. Commit suas mudanças (`git commit -m 'Add some AmazingFeature'`)
4. Push para a branch (`git push origin feature/AmazingFeature`)
5. Abra um Pull Request

## 📄 Licença

Este projeto está sob a licença MIT. Veja o arquivo LICENSE para mais detalhes.

## 👨‍💻 Autor

**SnDann** - Desenvolvedor e entusiasta de tecnologia

---

✨ Feito com dedicação para simplificar o gerenciamento de tarefas diárias.
