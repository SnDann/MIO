# Guia de Desenvolvimento - MIO

## Objetivo: Manter a Autenticidade do Projeto

Este documento estabelece as praticas para garantir que o MIO mantenha um desenvolvimento genuino e autentico, sem deixar vestigios obvios de ferramentas automatizadas.

## Sinais de IA (O que EVITAR)

### 1. Mensagens de Commit Genericas

ERRADO:
- "Implement feature X for task management"
- "Add component for displaying tasks"
- "Add base CSS styles and variables"

CERTO:
- "Adicionar sistema de tags para organizar tarefas"
- "Refatorar TaskList - mover logica de filtro para App"
- "Ajustar padding do input pra alinhar com o design"

Por que? Mensagens genericas em ingles sao sinais de automacao. Use linguagem natural e descreva POR QUE mudou.

### 2. Estrutura de Codigo Mecanica

Codigo bom tem comentarios que explicam DECISOES DE DESIGN, nao o obvio.

ERRADO:
// Componente Home - Task management
function Home({ tasks, response, onAddTask }) { ... }

CERTO:
// Onde o usuario digita e gerencia tudo (estado centralizado aqui)
function Home({ tasks, response, onAddTask }) { ... }

### 3. README de Template

ERRADO:
# Project Name
Description of the project.
## Getting Started
1. Clone the repository
2. Install dependencies
3. Run the project

CERTO:
# MIO - Assistente Pessoal
Surgiu da necessidade de acompanhar tarefas de forma simples.
## Rodando
```bash
cd web && npm install && npm run dev
```
Abre em localhost:3000 com reload automatico.

### 4. Nomes Genericos

ERRADO:
const handleAddItem = () => { ... }
const component1 = () => { ... }

CERTO:
const adicionarTarefa = () => { ... }
const ListaDeTarefas = () => { ... }

### 5. Pastas Perfeitas Mas Vazias

Crie apenas as pastas que voce REALMENTE precisa. Estrutura perfeita vazia parece gerada.

## Boas Praticas Autenticas

### 1. Commits com Historia Real

Feat: Persistencia com localStorage (nao precisa de BD ainda)
Fix: Input aceitava tarefas vazias
Refactor: Mover estado para App.jsx (foi mais facil de debugar)
Docs: Atualizar README com instrucoes reais

### 2. Comentarios Uteis

// Salvamos no localStorage pra nao perder entre reloads
useEffect(() => {
  localStorage.setItem('mio_tasks', JSON.stringify(tasks))
}, [tasks])

### 3. Documentacao Conversacional

Em vez de: "This project is a personal assistant application."
Escreva: "Surgiu da vontade de ter um assistente simples."

### 4. Historico de Commits Natural

Em vez de:
Commit 1: Create project structure
Commit 2: Add components
Commit 3: Add styling
Commit 4: Add README

Faca:
Commit 1: Setup inicial com Vite + React
Commit 2: Componentes Home e TaskList funcionando
Commit 3: Estilos da interface (ainda ta meio quadrado mas funciona)
Commit 4: Persistencia com localStorage
Commit 5: Melhorar CSS dos checkboxes

## Checklist Antes de Commitar

- [ ] A mensagem descreve REALMENTE o que mudou?
- [ ] Usei a linguagem do projeto?
- [ ] Os comentarios explicam decisoes, nao obvios?
- [ ] Removi mensagens genericas do Copilot?
- [ ] A estrutura de pastas eh necessaria?

## Red Flags

Se ver isto no repo, refatore:
- Commits com "Add feature X" generico
- README copiado de template
- Funcao chamada `component1` ou `handleItem`
- Mensagens de commit em ingles quando projeto eh PT
- Codigo perfeito demais sem nenhuma imperfeicao humana

## Tom do Projeto

O MIO deve se sentir como um projeto pessoal de alguem que:
- Quis resolver um problema real
- Pensou nas decisoes (nao foi gerado)
- Explica o porque das escolhas
- Usa linguagem natural
- Tem opinioes e tira suas proprias decisoes

Nao deve parecer:
- Scaffold/template
- Gerado em massa
- Seguindo checklist de IA
- Copypasta de documentacao
- Generico e sem contexto

---

Resumo: Desenvolva como um humano de verdade. Commite como quem esta documentando seus experimentos. Documente pensando em quem vai ler depois e querer entender sua intencao.
