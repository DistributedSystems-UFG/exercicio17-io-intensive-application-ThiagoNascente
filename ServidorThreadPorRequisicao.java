import java.io.*;
import java.net.*;
import java.nio.file.*;

public class ServidorThreadPorRequisicao {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(8081);
        System.out.println("Servidor Thread-por-Requisição rodando na porta 8081...");

        while (true) {
            Socket clientSocket = serverSocket.accept();
            // Cria uma NOVA thread para cada conexão
            new Thread(() -> processarRequisicao(clientSocket)).start(); 
        }
    }

    static void processarRequisicao(Socket socket) {
        try (socket; PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {
            Thread.sleep(50); 
            String dados = Files.readString(Paths.get("dados.txt"));
            out.println(dados);
        } catch (Exception e) {
            System.err.println("Erro: " + e.getMessage());
        }
    }
}