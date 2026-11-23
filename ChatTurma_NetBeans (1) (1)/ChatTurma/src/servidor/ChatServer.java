package servidor;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

public class ChatServer {

    private static final List<ClientHandler> clientes = new CopyOnWriteArrayList<>();
    private static final String ARQUIVO_LOG = "historico_chat.txt";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("=== SERVIDOR - CHAT DE TURMA ===");
        System.out.print("Digite o IP de escuta (ex: 0.0.0.0 ou 127.0.0.1): ");
        String ip = sc.nextLine().trim();
        if (ip.isEmpty()) {
            ip = "0.0.0.0";
        }

        System.out.print("Digite a porta de escuta: ");
        int porta = Integer.parseInt(sc.nextLine().trim());

        try (ServerSocket serverSocket = new ServerSocket()) {
            serverSocket.bind(new InetSocketAddress(ip, porta));
            System.out.println("Servidor iniciado em " + ip + ":" + porta);
            System.out.println("Aguardando conexões de alunos...\n");

            while (true) {
                Socket socket = serverSocket.accept();
                ClientHandler handler = new ClientHandler(socket);
                clientes.add(handler);
                new Thread(handler).start();
            }
        } catch (IOException e) {
            System.err.println("Erro no servidor: " + e.getMessage());
        }
    }

    public static void broadcast(String mensagem, ClientHandler remetente) {
        for (ClientHandler cliente : clientes) {
            if (cliente != remetente) {
                cliente.enviar(mensagem);
            }
        }
        gravarLog(mensagem);
    }

    public static void notificarTodos(String mensagem) {
        for (ClientHandler cliente : clientes) {
            cliente.enviar(mensagem);
        }
        gravarLog(mensagem);
    }

    public static void removerCliente(ClientHandler cliente) {
        clientes.remove(cliente);
    }

    public static String listarUsuariosOnline() {
        if (clientes.isEmpty()) {
            return "Nenhum usuário online no momento.";
        }
        StringBuilder sb = new StringBuilder("Usuários online (" + clientes.size() + "):\n");
        for (ClientHandler c : clientes) {
            sb.append(" - ").append(c.getNickname()).append("\n");
        }
        return sb.toString().trim();
    }

    private static void gravarLog(String mensagem) {
        try (FileWriter fw = new FileWriter(ARQUIVO_LOG, true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {

            out.println("[" + new Date() + "] " + mensagem);

        } catch (IOException e) {
            System.err.println("Erro ao gravar log: " + e.getMessage());
        }
    }
}
