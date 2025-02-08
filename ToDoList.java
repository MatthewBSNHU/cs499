import java.util.ArrayList;

public class ToDoListApp {
    public static void main(String[] args) {
        ToDoList toDoList = new ToDoList();
        
        // Add some tasks
        toDoList.addTask("Buy groceries");
        toDoList.addTask("Finish homework");

        // Display tasks
        toDoList.displayTasks();

        // Remove a task
        toDoList.removeTask(1);

        // Display tasks again
        toDoList.displayTasks();
    }
}

class ToDoList {
    private ArrayList<String> tasks;

    public ToDoList() {
        tasks = new ArrayList<>();
    }

    public void addTask(String task) {
        tasks.add(task);
    }

    public void removeTask(int index) {
        tasks.remove(index); // No error handling
    }

    public void displayTasks() {
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println(i + ": " + tasks.get(i));
        }
    }
}
