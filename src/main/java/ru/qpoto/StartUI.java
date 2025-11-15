package ru.qpoto;

import ru.qpoto.view.LabelView;
import ru.qpoto.view.PostView;
import ru.qpoto.view.WriterView;

import java.util.List;
import java.util.Scanner;

public class StartUI {
    private final List<String> commands = List.of("Writers", "Posts", "Labels", "Exit");

    private final Scanner scanner;
    private final WriterView writerView;
    private final LabelView labelView;
    private final PostView postView;

    public StartUI(Scanner scanner, WriterView writerView, LabelView labelView, PostView postView) {
        this.scanner = scanner;
        this.writerView = writerView;
        this.labelView = labelView;
        this.postView = postView;
    }

    private void showMenu() {
        System.out.println();
        System.out.println("======================");
        System.out.println("Главное меню:");
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
            case "Writers" -> writerView.run();
            case "Posts" -> postView.run();
            case "Labels" -> labelView.run();
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