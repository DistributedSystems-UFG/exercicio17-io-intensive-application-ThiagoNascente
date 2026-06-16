import java.io.*;
import java.net.*;
import java.nio.file.*;
import java.util.concurrent.*;

public class ServidorThreadPool {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(8081);
        // Cria um pool com 100 threads reutilizáveis
        ExecutorService pool = Executors.newFixedThreadPool(100); 
        System.out.println("Servidor Thread-Pool rodando na porta 8081...");

        while (true) {
            Socket clientSocket = serverSocket.accept();
            // Delega a conexão para uma thread existente no pool
            pool.submit(() -> processarRequisicao(clientSocket));
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