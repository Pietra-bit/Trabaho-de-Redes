# Chat de Turma - Sala de Bate-Papo Única

**Tema 6** – Chat Cliente/Servidor em Java (TCP + Threads)

---

## Funcionalidades

- Servidor com IP e Porta configuráveis
- Múltiplos clientes simultâneos (Threads)
- Broadcast de mensagens (`Nome: Texto`)
- Notificação de entrada e saída
- Comando `/quem` (lista usuários online)
- Comando `sair`
- Histórico gravado em `historico_chat.txt`

---

## Estrutura

```
src/
├── servidor/
│   ├── ChatServer.java
│   └── ClientHandler.java
└── cliente/
    └── ChatClient.java
```

---

## Como Executar

**Servidor:**
```bash
java -cp src servidor.ChatServer
```

**Cliente:**
```bash
java -cp src cliente.ChatClient
```

---

## Comandos

| Comando | Descrição              |
|---------|------------------------|
| `/quem` | Lista usuários online  |
| `sair`  | Desconecta do chat     |
```
