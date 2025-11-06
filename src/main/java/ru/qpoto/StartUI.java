package ru.qpoto;

import java.util.List;
import java.util.Scanner;

public class StartUI {
    private final List<String> commands = List.of("Writers", "Labels", "Posts", "Exit");
    private final Scanner scanner = new Scanner(System.in);

    private void showMenu() {
        System.out.println("Menu:");
        commands.forEach((cmd) -> System.out.println(commands.indexOf(cmd) + ". " + cmd));
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
            case "Writers" -> System.out.println("Вы выбрали Writers");
            case "Labels" -> System.out.println("Вы выбрали Labels");
            case "Posts" -> System.out.println("Вы выбрали Posts");
            case "Exit" -> {
                System.out.println("Программа завершена.");
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