package ru.qpoto.view;

import ru.qpoto.controller.LabelController;
import ru.qpoto.model.Label;

import java.util.List;
import java.util.Scanner;

public class LabelView {
    public final LabelController labelController = new LabelController();
    private final List<String> commands = List.of("Создать", "Найти", "Показать всех", "Обновить", "Удалить", "Возврат к предыдущему меню");
    private final Scanner scanner = new Scanner(System.in);

    private void showMenu() {
        System.out.println();
        System.out.println("======================");
        System.out.println("Меню Labels:");
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
            case "Создать" -> createLabel();
            case "Найти" -> getById();
            case "Показать всех" -> showAllLabels();
            case "Обновить" -> update();
            case "Удалить" -> deleteLabel();
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

    public void createLabel() {
        Label newLabel = new Label();
        System.out.println("Укажите name");
        String name = scanner.next();
        newLabel.setName(name);
        labelController.save(newLabel);
    }

    private void getById() {
        System.out.println("Укажите id");
        Long id = scanner.nextLong();
        System.out.println(labelController.findById(id));
    }

    private void showAllLabels() {
        labelController.findAll().forEach(System.out::println);
    }

    private void update() {
        showAllLabels();
        System.out.println("Укажите id");
        Long id = scanner.nextLong();
        Label newLabel = labelController.findById(id);
        System.out.println("Укажите name");
        String name = scanner.next();
        newLabel.setName(name);
        labelController.update(newLabel);
    }

    private void deleteLabel() {
        System.out.println("Укажите id");
        showAllLabels();
        Long id = scanner.nextLong();
        labelController.delete(id);
    }
}
