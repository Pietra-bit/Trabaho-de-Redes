===========================================
  CHAT DE TURMA - Projeto NetBeans
  Tema 6: Sala de Bate-Papo Única
===========================================

COMO ABRIR NO NETBEANS:
1. Abra o NetBeans
2. File → Open Project
3. Selecione a pasta "ChatTurma"
4. Clique em Open Project

OU (se não abrir automaticamente):
1. File → New Project → Java → Java Application
2. Crie o projeto
3. Clique com botão direito em Source Packages → New → Java Package
   - Crie os pacotes: servidor e cliente
4. Copie os arquivos .java para dentro dos pacotes correspondentes


COMO EXECUTAR:

1. Primeiro execute o SERVIDOR:
   - Clique com botão direito em ChatServer.java → Run File
   - Digite o IP (recomendado: 0.0.0.0 ou 127.0.0.1)
   - Digite a porta (ex: 12345)

2. Depois execute um ou mais CLIENTES:
   - Clique com botão direito em ChatClient.java → Run File
   - Digite o IP do servidor (ex: 127.0.0.1)
   - Digite a mesma porta
   - Digite seu nickname


FUNCIONALIDADES IMPLEMENTADAS:
✓ Configuração de IP e Porta no servidor
✓ Múltiplos clientes simultâneos (threads)
✓ Broadcast de mensagens (Nome: Texto)
✓ Notificação de entrada e saída
✓ Comando /quem (lista usuários online)
✓ Comando sair
✓ Histórico gravado em historico_chat.txt


ESTRUTURA DO PROJETO:
ChatTurma/
├── src/
│   ├── servidor/
│   │   ├── ChatServer.java
│   │   └── ClientHandler.java
│   └── cliente/
│       └── ChatClient.java
└── README.txt
