package ru.qpoto.view;

import ru.qpoto.controller.PostController;
import ru.qpoto.controller.WriterController;
import ru.qpoto.model.Post;
import ru.qpoto.model.Writer;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class WriterView {
    private final WriterController writerController;
    private final PostController postController;
    private final PostView postView;
    private final List<String> commands = List.of("Создать", "Найти", "Показать всех", "Обновить", "Удалить", "Возврат к предыдущему меню");
    private final Scanner scanner;
    private final String YES = "y";

    public WriterView(WriterController writerController, PostController postController, PostView postView, Scanner scanner) {
        this.writerController = writerController;
        this.postController = postController;
        this.postView = postView;
        this.scanner = scanner;
    }

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
            case "Создать" -> createWriter();
            case "Найти" -> getById();
            case "Показать всех" -> showAllWriters();
            case "Обновить" -> update();
            case "Удалить" -> deleteWriter();
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

    private void showAllWriters() {
        writerController.findAll().forEach(System.out::println);
    }

    private void createWriter() {
        Writer newWriter = new Writer();
        System.out.println("Укажите firstName");
        String firstName = scanner.next();
        System.out.println("Укажите lastName");
        String lastName = scanner.next();
        System.out.println("Добавить posts? Нажмите Y или N");
        String needPosts = scanner.next();
        List<Post> posts = new ArrayList<>();
        if (needPosts.equalsIgnoreCase(YES)) {
            if (postController.findAll().isEmpty()) {
                System.out.println("Нет созданных Post, создать? Нажмите Y или N");
                needPosts = scanner.next();
                if (needPosts.equalsIgnoreCase(YES)) {
                    postView.createPost();
                    postController.findAll().forEach(System.out::println);
                    System.out.println("Выберете Post: ");
                    Long needPost = scanner.nextLong();
                    Post post = postController.findById(needPost);
                    posts.add(post);
                }
            } else {
                postController.findAll().forEach(System.out::println);
                System.out.println("Выберете Post");
                Long needPost = scanner.nextLong();
                Post post = postController.findById(needPost);
                posts.add(post);
            }
        }
        newWriter.setFirstName(firstName);
        newWriter.setLastName(lastName);
        newWriter.setPosts(posts);
        writerController.save(newWriter);
    }

    private void deleteWriter() {
        System.out.println("Укажите id");
        showAllWriters();
        Long id = scanner.nextLong();
        writerController.delete(id);
    }

    private void update() {
        showAllWriters();
        System.out.println("Укажите id");
        Long id = scanner.nextLong();
        Writer newWriter = writerController.findById(id);
        System.out.println("Обновить firstName? Нажмите Y или N");
        String firstName = scanner.next();
        if (firstName.equalsIgnoreCase(YES)) {
            System.out.println("Введите firstName");
            newWriter.setFirstName(scanner.next());
        }
        System.out.println("Обновить lastName? нажмите Y или N");
        String lastName = scanner.next();
        if (lastName.equalsIgnoreCase(YES)) {
            System.out.println("Введите lastName");
            newWriter.setLastName(scanner.next());
        }
        writerController.update(newWriter);
    }

    private void getById() {
        System.out.println("Укажите id");
        Long id = scanner.nextLong();
        System.out.println(writerController.findById(id));
    }
}
