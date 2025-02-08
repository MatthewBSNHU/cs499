import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;

public class ToDoListApp {
    public static void main(String[] args) {
        ToDoList toDoList = new ToDoList();
        toDoList.loadTasksFromFile("tasks.txt");
        Scanner scanner = new Scanner(System.in);
        String command;

        System.out.println("Welcome to the To-Do List App!");
        while (true) {
            System.out.println("Commands: add, remove, display, save, exit");
            System.out.print("Enter a command: ");
            command = scanner.nextLine().trim().toLowerCase();

            switch (command) {
                case "add":
                    System.out.print("Enter a task: ");
                    toDoList.addTask(scanner.nextLine());
                    break;
                case "remove":
                    System.out.print("Enter the index of the task to remove: ");
                    try {
                        int index = Integer.parseInt(scanner.nextLine());
                        toDoList.removeTask(index);
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid input. Please enter a valid index.");
                    }
                    break;
                case "display":
                    toDoList.displayTasks();
                    break;
                case "save":
                    toDoList.saveTasksToFile("tasks.txt");
                    System.out.println("Tasks saved to file.");
                    break;
                case "exit":
                    toDoList.saveTasksToFile("tasks.txt");
                    System.out.println("Exiting the app. Goodbye!");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid command. Try again.");
            }
        }
    }
}

class ToDoList {
    private ArrayList<String> tasks;

    public ToDoList() {
        tasks = new ArrayList<>();
    }

    public void addTask(String taskDescription) {
        tasks.add(taskDescription);
        System.out.println("Task added: " + taskDescription);
    }

    public void removeTask(int index) {
        if (index < 0 || index >= tasks.size()) {
            System.out.println("Error: Index out of range.");
            return;
        }
        String removedTask = tasks.remove(index);
        System.out.println("Task removed: " + removedTask);
    }

    public void displayTasks() {
        if (tasks.isEmpty()) {
            System.out.println("No tasks to display.");
        } else {
            System.out.println("Your Tasks:");
            for (int i = 0; i < tasks.size(); i++) {
                System.out.println(i + ": " + tasks.get(i));
            }
        }
    }

    public void saveTasksToFile(String filename) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            for (String task : tasks) {
                writer.println(task);
            }
        } catch (IOException e) {
            System.out.println("Error saving tasks to file: " + e.getMessage());
        }
    }

    public void loadTasksFromFile(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String task;
            while ((task = reader.readLine()) != null) {
                tasks.add(task);
            }
        } catch (IOException e) {
            System.out.println("Error loading tasks from file: " + e.getMessage());
        }
    }
}