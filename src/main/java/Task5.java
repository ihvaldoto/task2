import java.util.LinkedList;
import java.util.Objects;
import java.util.Queue;
import java.util.Scanner;

public class Task5 implements Task {
    private final Queue<String> queue = new LinkedList<>();
    private Thread worker;

    public synchronized void submit(String data) {
        queue.add(Objects.requireNonNull(data));
        notifyAll();
    }

    @Override
    public synchronized void start() {
        if (worker == null) {
            worker = new Thread(this::processQueue, "queue-worker");
            worker.start();
        }
    }

    @Override
    public synchronized void stop() {
        worker = null;
        notifyAll();
    }

    private void processQueue() {
        Thread current = Thread.currentThread();
        try {
            // Между элементами очереди может выполниться stop().
            while (processNext(current)) {
            }
        } catch (InterruptedException e) {
            current.interrupt();
        } finally {
            synchronized (this) {
                if (worker == current) {
                    worker = null;
                    notifyAll();
                }
            }
        }
    }

    private synchronized boolean processNext(Thread current) throws InterruptedException {
        while (worker == current && queue.isEmpty()) {
            wait();
        }
        if (worker != current) {
            return false;
        }
        System.out.println("Обработано: " + queue.remove());
        notifyAll();
        return true;
    }

    private synchronized void awaitEmpty() throws InterruptedException {
        while (worker != null && !queue.isEmpty()) {
            wait();
        }
    }

    public static void run(Scanner input) throws InterruptedException {
        Task5 task = new Task5();
        System.out.println("Вводите данные по одной строке. Пустая строка завершает задание.");
        task.start();
        try {
            while (input.hasNextLine()) {
                String line = input.nextLine();
                if (line.isBlank()) {
                    break;
                }
                task.submit(line);
            }
            task.awaitEmpty();
        } finally {
            task.stop();
        }
    }
}
