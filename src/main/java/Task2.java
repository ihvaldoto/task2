import java.util.ArrayList;
import java.util.Random;

public class Task2 {
    public static void run(int size) {
        Random random = new Random();
        ArrayList<Double> numbers = new ArrayList<>(size);
        for (int index = 0; index < size; index++) {
            numbers.add(random.nextDouble(-100.0, 100.0));
        }

        System.out.println("Исходный список: " + numbers);
        selectionSort(numbers);
        System.out.println("Отсортированный список: " + numbers);
    }

    static void selectionSort(ArrayList<Double> numbers) {
        for (int index = 0; index < numbers.size() - 1; index++) {
            int minIndex = index;
            for (int next = index + 1; next < numbers.size(); next++) {
                if (Double.compare(numbers.get(next), numbers.get(minIndex)) < 0) {
                    minIndex = next;
                }
            }

            Double current = numbers.get(index);
            numbers.set(index, numbers.get(minIndex));
            numbers.set(minIndex, current);
        }
    }
}
