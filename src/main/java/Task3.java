import java.util.ArrayList;
import java.util.List;

public class Task3 {
    public static void run() {
        ArrayList<Employee> employees = new ArrayList<>(List.of(
                new Employee("Анна Иванова", 28, "Разработка", 110_000.0),
                new Employee("Дмитрий Петров", 35, "Тестирование", 95_000.0),
                new Employee("Мария Соколова", 31, "Аналитика", 105_000.0),
                new Employee("Илья Смирнов", 24, "Поддержка", 70_000.0),
                new Employee("Елена Волкова", 42, "Разработка", 130_000.0)
        ));

        List<String> namesAndDepartments = employees.stream()
                .map(employee -> employee.getFullName() + " - " + employee.getDepartment())
                .toList();
        namesAndDepartments.forEach(System.out::println);
    }
}
