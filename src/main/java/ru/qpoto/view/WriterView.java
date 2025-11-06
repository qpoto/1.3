package ru.qpoto.view;

import ru.qpoto.controller.WriterController;

import java.util.List;
import java.util.Scanner;

public class WriterView {
    private final WriterController writerController = new WriterController();
    private final List<String> commands = List.of("Создать", "Найти", "Обновить", "Удалить", "Возврат к предыдущему меню");
    private final Scanner scanner = new Scanner(System.in);

    private void showMenu() {
        System.out.println();
        System.out.println("======================");
        System.out.println("Меню Writers:");
        commands.forEach((cmd) -> System.out.println(commands.indexOf(cmd) + ". " + cmd));
        System.out.print("Введите цифру от 0 до " + (commands.size() - 1) + ": ");
    }

    public void run() {
        boolean run = true;
        while (run) {
            showMenu();
            if (!validateInput()) {
                continue;
            }
            int select = scanner.nextInt();
            if (validateMenu(select)) {
                continue;
            }
            run = getView(select, run);
        }
    }

    private boolean getView(int select, boolean run) {
        String command = commands.get(select);
        switch (command) {
            case "Создать" -> System.out.println("Вы выбрали Создать Writer");
            case "Найти" -> System.out.println("Вы выбрали Найти Writer");
            case "Обновить" -> System.out.println("Вы выбрали Обновить Writer");
            case "Удалить" -> System.out.println("Вы выбрали Удалить Writer");
            case "Возврат к предыдущему меню" -> {
                run = false;
            }
            default -> System.out.println("Неизвестная команда: " + command);
        }
        return run;
    }

    private boolean validateMenu(int select) {
        if (select < 0 || select >= commands.size()) {
            System.out.println("Выберите из списка доступных команд.");
            return true;
        }
        return false;
    }

    private boolean validateInput() {
        if (!scanner.hasNextInt()) {
            System.out.println("Неверный ввод. Введите число. от 0 до " + (commands.size() - 1));
            scanner.next();
            return false;
        }
        return true;
    }
}
