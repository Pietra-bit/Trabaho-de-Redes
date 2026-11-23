package cliente;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class ChatClient {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("=== CLIENTE - CHAT DE TURMA ===");

        System.out.print("Digite o IP do servidor: ");
        String ip = sc.nextLine().trim();

        System.out.print("Digite a porta do servidor: ");
        int porta = Integer.parseInt(sc.nextLine().trim());

        System.out.print("Digite seu apelido (nickname): ");
        String nickname = sc.nextLine().trim();
        if (nickname.isEmpty()) {
            nickname = "Aluno";
        }

        try (Socket socket = new Socket(ip, porta);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            out.println(nickname);

            Thread receptor = new Thread(() -> {
                try {
                    String msg;
                    while ((msg = in.readLine()) != null) {
                        System.out.println(msg);
                    }
                } catch (IOException e) {
                    System.out.println("\n[Conexão encerrada pelo servidor]");
                }
            });
            receptor.start();

            System.out.println("\nConectado com sucesso!");
            System.out.println("Digite suas mensagens abaixo.");
            System.out.println("Comandos disponíveis: /quem  |  sair\n");

            while (true) {
                String mensagem = sc.nextLine();

                if (mensagem.equalsIgnoreCase("sair")) {
                    out.println("sair");
                    break;
                }

                out.println(mensagem);
            }

            System.out.println("Você saiu do chat. Até logo!");

        } catch (IOException e) {
            System.err.println("Erro de conexão: " + e.getMessage());
            System.err.println("Verifique se o servidor está rodando e se o IP/Porta estão corretos.");
        }
    }
}
