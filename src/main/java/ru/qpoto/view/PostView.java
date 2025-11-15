package ru.qpoto.view;


import ru.qpoto.controller.LabelController;
import ru.qpoto.controller.PostController;
import ru.qpoto.model.Label;
import ru.qpoto.model.Post;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PostView {
    private final PostController postController;
    private final LabelController labelController;
    private final LabelView labelView;
    private final List<String> commands = List.of("Создать", "Найти", "Показать всех", "Обновить", "Удалить", "Возврат к предыдущему меню");
    private final Scanner scanner;
    private final String YES = "y";

    public PostView(PostController postController, LabelController labelController, LabelView labelView, Scanner scanner) {
        this.postController = postController;
        this.labelController = labelController;
        this.labelView = labelView;
        this.scanner = scanner;
    }

    private void showMenu() {
        System.out.println();
        System.out.println("======================");
        System.out.println("Меню Posts:");
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
            case "Создать" -> createPost();
            case "Найти" -> getById();
            case "Показать всех" -> showAllPosts();
            case "Обновить" -> update();
            case "Удалить" -> deletePost();
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

    public void createPost() {
        Post newPost = new Post();
        System.out.println("Укажите title");
        String title = scanner.next();
        System.out.println("Укажите content");
        String content = scanner.next();
        System.out.println("Добавить labels? нажмите Y или N");
        String needLabels = scanner.next();
        List<Label> labels = new ArrayList<>();
        if (needLabels.equalsIgnoreCase(YES)) {
            if (labelController.findAll().isEmpty()) {
                System.out.println("Нет созданных Label, создать? нажмите Y или N");
                needLabels = scanner.next();
                if (needLabels.equalsIgnoreCase(YES)) {
                    labelView.createLabel();
                    labelController.findAll().forEach(System.out::println);
                    System.out.println("Выберете Label: ");
                    Long needLabel = scanner.nextLong();
                    Label label = labelController.findById(needLabel);
                    labels.add(label);
                }
            } else {
                postController.findAll().forEach(System.out::println);
                System.out.println("Выберете Post");
                Long needPost = scanner.nextLong();
                Label label = labelController.findById(needPost);
                labels.add(label);
            }
        }
        newPost.setTitle(title);
        newPost.setContent(content);
        newPost.setLabels(labels);
        postController.save(newPost);
    }

    private void deletePost() {
        System.out.println("Укажите id");
        showAllPosts();
        Long id = scanner.nextLong();
        postController.delete(id);
    }

    private void showAllPosts() {
        postController.findAll().forEach(System.out::println);
    }

    private void getById() {
        System.out.println("Укажите id");
        Long id = scanner.nextLong();
        System.out.println(postController.findById(id));
    }

    private void update() {
        showAllPosts();
        System.out.println("Укажите id");
        Long id = scanner.nextLong();
        Post newPost = postController.findById(id);
        System.out.println("Обновить title?");
        String title = scanner.next();
        if (title.equalsIgnoreCase(YES)) {
            System.out.println("Введите firstName");
            newPost.setTitle(scanner.next());
        }
        System.out.println("Обновить content");
        String content = scanner.next();
        if (content.equalsIgnoreCase(YES)) {
            System.out.println("Введите lastName");
            newPost.setContent(scanner.next());
        }
        postController.update(newPost);
    }
}
