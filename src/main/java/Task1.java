import java.util.Arrays;
import java.util.OptionalInt;
import java.util.Random;

public class Task1 {
    public static void run(int size) {
        int[] numbers = new Random().ints(size, -100, 101).toArray();
        System.out.println("Массив: " + Arrays.toString(numbers));

        OptionalInt lastPositive = findLastPositive(numbers);
        if (lastPositive.isPresent()) {
            System.out.println("Последний положительный элемент: " + lastPositive.getAsInt());
        } else {
            System.out.println("Положительных элементов нет.");
        }
    }

    static OptionalInt findLastPositive(int[] numbers) {
        for (int index = numbers.length - 1; index >= 0; index--) {
            if (numbers[index] > 0) {
                return OptionalInt.of(numbers[index]);
            }
        }
        return OptionalInt.empty();
    }
}
