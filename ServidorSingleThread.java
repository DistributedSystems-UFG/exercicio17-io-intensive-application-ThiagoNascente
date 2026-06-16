import java.io.*;
import java.net.*;
import java.nio.file.*;

public class ServidorSingleThread {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(8081);
        System.out.println("Servidor Single-Thread rodando na porta 8081...");

        while (true) {
            Socket clientSocket = serverSocket.accept(); // Aceita a conexão
            processarRequisicao(clientSocket);           // Processa na mesma thread
        }
    }

    static void processarRequisicao(Socket socket) {
        try (socket; PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {
            // Simula lentidão de leitura de disco
            Thread.sleep(50); 
            String dados = Files.readString(Paths.get("dados.txt"));
            out.println(dados);
        } catch (Exception e) {
            System.err.println("Erro: " + e.getMessage());
        }
    }
}