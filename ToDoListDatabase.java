import java.sql.*;

public class ToDoListDatabase {
    private Connection connection;

    public ToDoListDatabase() {
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:tasks.db");
            Statement statement = connection.createStatement();
            statement.execute("CREATE TABLE IF NOT EXISTS tasks (id INTEGER PRIMARY KEY, description TEXT)");
            statement.close();
        } catch (SQLException e) {
            System.out.println("Error connecting to the database: " + e.getMessage());
        }
    }

    public void addTask(String taskDescription) {
        String sql = "INSERT INTO tasks (description) VALUES (?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, taskDescription);
            preparedStatement.executeUpdate();
            System.out.println("Task added: " + taskDescription);
        } catch (SQLException e) {
            System.out.println("Error adding task: " + e.getMessage());
        }
    }

    public void removeTask(int id) {
        String sql = "DELETE FROM tasks WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Task removed with ID: " + id);
            } else {
                System.out.println("No task found with ID: " + id);
            }
        } catch (SQLException e) {
            System.out.println("Error removing task: " + e.getMessage());
        }
    }

    public void updateTask(int id, String newDescription) {
        String sql = "UPDATE tasks SET description = ? WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, newDescription);
            preparedStatement.setInt(2, id);
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Task updated with ID: " + id);
            } else {
                System.out.println("No task found with ID: " + id);
            }
        } catch (SQLException e) {
            System.out.println("Error updating task: " + e.getMessage());
        }
    }

    public void displayTasks() {
        String sql = "SELECT * FROM tasks";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            boolean hasTasks = false;
            while (resultSet.next()) {
                hasTasks = true;
                System.out.println(resultSet.getInt("id") + ": " + resultSet.getString("description"));
            }
            if (!hasTasks) {
                System.out.println("No tasks to display.");
            }
        } catch (SQLException e) {
            System.out.println("Error displaying tasks: " + e.getMessage());
        }
    }

    public void close() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Database connection closed.");
            }
        } catch (SQLException e) {
            System.out.println("Error closing the database connection: " + e.getMessage());
        }
    }
}