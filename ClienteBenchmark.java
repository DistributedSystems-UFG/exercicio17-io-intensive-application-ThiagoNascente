import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicInteger;

public class ClienteBenchmark {
    public static void main(String[] args) throws InterruptedException {
        int numClientesSimultaneos = 200;
        int tempoDeTesteSegundos = 5;
        AtomicInteger requisicoesCompletadas = new AtomicInteger(0);

        long startTime = System.currentTimeMillis();
        long endTime = startTime + (tempoDeTesteSegundos * 1000);

        Runnable tarefaCliente = () -> {
            while (System.currentTimeMillis() < endTime) {
                try (Socket socket = new Socket("localhost", 8081);
                     BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
                    in.readLine(); // Lê a resposta do arquivo
                    requisicoesCompletadas.incrementAndGet();
                } catch (Exception e) {
                    // Ignora falhas de conexão no contador
                }
            }
        };

        // Dispara os clientes
        Thread[] threads = new Thread[numClientesSimultaneos];
        for (int i = 0; i < numClientesSimultaneos; i++) {
            threads[i] = new Thread(tarefaCliente);
            threads[i].start();
        }

        // Aguarda todos terminarem
        for (int i = 0; i < numClientesSimultaneos; i++) {
            threads[i].join();
        }

        int total = requisicoesCompletadas.get();
        System.out.println("Total de requisições: " + total);
        System.out.println("Vazão Média: " + (total / tempoDeTesteSegundos) + " requisições por segundo (RPS)");
    }
}