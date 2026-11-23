package servidor;

import java.io.*;
import java.net.Socket;

public class ClientHandler implements Runnable {

    private final Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private String nickname;

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    public String getNickname() {
        return nickname;
    }

    public void enviar(String mensagem) {
        if (out != null) {
            out.println(mensagem);
        }
    }

    @Override
    public void run() {
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

            nickname = in.readLine();
            if (nickname == null || nickname.trim().isEmpty()) {
                nickname = "Anônimo";
            }

            String entrada = "* " + nickname + " entrou na sala *";
            System.out.println(entrada);
            ChatServer.notificarTodos(entrada);

            String linha;
            while ((linha = in.readLine()) != null) {
                linha = linha.trim();

                if (linha.equalsIgnoreCase("sair")) {
                    break;
                }

                if (linha.equalsIgnoreCase("/quem")) {
                    enviar(ChatServer.listarUsuariosOnline());
                    continue;
                }

                String mensagemFormatada = nickname + ": " + linha;
                System.out.println(mensagemFormatada);
                ChatServer.broadcast(mensagemFormatada, this);
            }

        } catch (IOException e) {
            System.err.println("Erro com cliente " + nickname + ": " + e.getMessage());
        } finally {
            if (nickname != null) {
                String saida = "* " + nickname + " saiu da sala *";
                System.out.println(saida);
                ChatServer.notificarTodos(saida);
            }
            ChatServer.removerCliente(this);
            fecharConexao();
        }
    }

    private void fecharConexao() {
        try {
            if (in != null) in.close();
            if (out != null) out.close();
            if (socket != null && !socket.isClosed()) socket.close();
        } catch (IOException e) {
        }
    }
}
