import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.*;

public class ToDoListAppGUI extends Application {
    private ToDoList toDoList = new ToDoList();
    private ListView<String> listView = new ListView<>();

    @Override
    public void start(Stage primaryStage) {
        TextField taskInput = new TextField();
        taskInput.setPromptText("Enter a new task");
        Button addButton = new Button("Add Task");
        Button removeButton = new Button("Remove Task");
        Button saveButton = new Button("Save Tasks");
        Button loadButton = new Button("Load Tasks");

        addButton.setOnAction(e -> {
            String task = taskInput.getText();
            if (!task.isEmpty()) {
                toDoList.addTask(task);
                updateListView();
                taskInput.clear();
            }
        });

        removeButton.setOnAction(e -> {
            int selectedIndex = listView.getSelectionModel().getSelectedIndex();
            if (selectedIndex >= 0) {
                toDoList.removeTask(selectedIndex);
                updateListView();
            }
        });

        saveButton.setOnAction(e -> {
            saveTasksToFile("tasks.txt");
        });

        loadButton.setOnAction(e -> {
            loadTasksFromFile("tasks.txt");
            updateListView();
        });

        VBox layout = new VBox(10, taskInput, addButton, removeButton, saveButton, loadButton, listView);
        Scene scene = new Scene(layout, 300, 400);

        primaryStage.setTitle("To-Do List App");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void updateListView() {
        listView.getItems().setAll(toDoList.getTasks());
    }

    private void saveTasksToFile(String filename) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            for (String task : toDoList.getTasks()) {
                writer.println(task);
            }
            showAlert(Alert.AlertType.INFORMATION, "Tasks saved to file.");
        } catch (IOException e) {
            showAlert(Alert.AlertType.ERROR, "Error saving tasks to file: " + e.getMessage());
        }
    }

    private void loadTasksFromFile(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String task;
            toDoList.clearTasks();
            while ((task = reader.readLine()) != null) {
                toDoList.addTask(task);
            }
            showAlert(Alert.AlertType.INFORMATION, "Tasks loaded from file.");
        } catch (IOException e) {
            showAlert(Alert.AlertType.ERROR, "Error loading tasks from file: " + e.getMessage());
        }
    }

    private void showAlert(Alert.AlertType alertType, String message) {
        Alert alert = new Alert(alertType);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

class ToDoList {
    private ArrayList<String> tasks = new ArrayList<>();

    public void addTask(String taskDescription) {
        tasks.add(taskDescription);
    }

    public void removeTask(int index) {
        if (index >= 0 && index < tasks.size()) {
            tasks.remove(index);
        }
    }

    public ArrayList<String> getTasks() {
        return tasks;
    }

    public void clearTasks() {
        tasks.clear();
    }
}