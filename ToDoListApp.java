import java.io.*;
import java.util.*;

class Task {
    String description;
    boolean isCompleted;

    Task(String description) {
        this.description = description;
        this.isCompleted = false;
    }

    void markCompleted() {
        this.isCompleted = true;
    }

    @Override
    public String toString() {
        return (isCompleted ? "[✔] " : "[ ] ") + description;
    }
}

public class ToDoListApp {
    private static final String FILE_NAME = "tasks.txt";
    private static List<Task> tasks = new ArrayList<>();

    public static void main(String[] args) {
        loadTasks();
        Scanner scanner = new Scanner(System.in);
        
        while (true) {
            System.out.println("\nTo-Do List Menu:");
            System.out.println("1. Add Task");
            System.out.println("2. View Tasks");
            System.out.println("3. Remove Task");
            System.out.println("4. Mark Task as Completed");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");
            
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            
            switch (choice) {
                case 1 -> addTask(scanner);
                case 2 -> viewTasks();
                case 3 -> removeTask(scanner);
                case 4 -> completeTask(scanner);
                case 5 -> {
                    saveTasks();
                    System.out.println("Goodbye!");
                    scanner.close();
                    return;
                }
                default -> System.out.println("Invalid option. Try again!");
            }
        }
    }

    private static void addTask(Scanner scanner) {
        System.out.print("Enter task description: ");
        String description = scanner.nextLine();
        tasks.add(new Task(description));
        System.out.println("Task added successfully!");
    }

    private static void viewTasks() {
        if (tasks.isEmpty()) {
            System.out.println("No tasks found!");
            return;
        }
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println((i + 1) + ". " + tasks.get(i));
        }
    }

    private static void removeTask(Scanner scanner) {
        viewTasks();
        System.out.print("Enter task number to remove: ");
        int index = scanner.nextInt() - 1;
        if (index >= 0 && index < tasks.size()) {
            tasks.remove(index);
            System.out.println("Task removed successfully!");
        } else {
            System.out.println("Invalid task number!");
        }
    }

    private static void completeTask(Scanner scanner) {
        viewTasks();
        System.out.print("Enter task number to mark as completed: ");
        int index = scanner.nextInt() - 1;
        if (index >= 0 && index < tasks.size()) {
            tasks.get(index).markCompleted();
            System.out.println("Task marked as completed!");
        } else {
            System.out.println("Invalid task number!");
        }
    }

    private static void saveTasks() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_NAME))) {
            for (Task task : tasks) {
                writer.println(task.description + "," + task.isCompleted);
            }
        } catch (IOException e) {
            System.out.println("Error saving tasks: " + e.getMessage());
        }
    }

    private static void loadTasks() {
        File file = new File(FILE_NAME);
        if (!file.exists()) return;
        
        try (Scanner fileScanner = new Scanner(file)) {
            while (fileScanner.hasNextLine()) {
                String[] data = fileScanner.nextLine().split(",");
                Task task = new Task(data[0]);
                task.isCompleted = Boolean.parseBoolean(data[1]);
                tasks.add(task);
            }
        } catch (IOException e) {
            System.out.println("Error loading tasks: " + e.getMessage());
        }
    }
}
