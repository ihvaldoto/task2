import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try (Scanner input = new Scanner(System.in,
                System.console() == null ? StandardCharsets.UTF_8 : System.console().charset())) {
            run(args, input);
        } catch (IllegalArgumentException | IOException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Выполнение прервано.");
            System.exit(1);
        }
    }

    private static void run(String[] args, Scanner input) throws IOException, InterruptedException {
        if (args.length == 0) {
            System.out.println("""
                    Практическая работа № 2. Вариант 4
                    1. Последний положительный элемент массива
                    2. Сортировка списка выбором
                    3. Список сотрудников и отделов
                    4. Заголовок Accept
                    5. Обработка очереди
                    Выберите номер задания.""");
        }
        String task = args.length == 0 ? readLine(input) : args[0];
        if (args.length > 2 || (args.length == 2 && !task.equals("1") && !task.equals("2"))) {
            throw new IllegalArgumentException("Размер можно указать только для задания 1 или 2.");
        }

        switch (task) {
            case "1" -> Task1.run(readSize(args, input));
            case "2" -> Task2.run(readSize(args, input));
            case "3" -> Task3.run();
            case "4" -> Task4.main(new String[0]);
            case "5" -> Task5.run(input);
            default -> throw new IllegalArgumentException("Номер задания должен быть от 1 до 5.");
        }
    }

    private static int readSize(String[] args, Scanner input) {
        String value;
        if (args.length == 2) {
            value = args[1];
        } else {
            System.out.println("Введите количество элементов.");
            value = readLine(input);
        }

        int size;
        try {
            size = Integer.parseInt(value);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Количество элементов должно быть целым неотрицательным числом.");
        }
        if (size < 0) {
            throw new IllegalArgumentException("Количество элементов не может быть отрицательным.");
        }
        return size;
    }

    private static String readLine(Scanner input) {
        if (!input.hasNextLine()) {
            throw new IllegalArgumentException("Ввод завершён.");
        }
        return input.nextLine().strip();
    }
}
