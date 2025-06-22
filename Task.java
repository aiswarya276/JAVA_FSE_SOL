// Task.java
public class Task {
    private int taskId;
    private String taskName;
    private String status; // e.g., "Pending", "In Progress", "Completed"

    public Task(int taskId, String taskName, String status) {
        this.taskId = taskId;
        this.taskName = taskName;
        this.status = status;
    }

    // Getters
    public int getTaskId() {
        return taskId;
    }

    public String getTaskName() {
        return taskName;
    }

    public String getStatus() {
        return status;
    }

    // Setters (optional, but useful for updating status)
    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Task ID: " + taskId + ", Name: " + taskName + ", Status: " + status;
    }
}

class Node {
    Task data;
    Node next;

    public Node(Task data) {
        this.data = data;
        this.next = null; // Initialize next pointer to null
    }
}

// SinglyLinkedListTaskManagement.java
public class SinglyLinkedListTaskManagement {
    private Node head; // Head of the list
    private int size; // Keep track of the number of tasks

    public SinglyLinkedListTaskManagement() {
        this.head = null;
        this.size = 0;
    }

    public void addTask(Task task) {
        Node newNode = new Node(task);
        if (head == null) {
            head = newNode; // If list is empty, new node becomes head
        } else {
            Node current = head;
            while (current.next != null) {
                current = current.next; // Traverse to the last node
            }
            current.next = newNode; // Link the last node to the new node
        }
        size++;
        System.out.println("Task '" + task.getTaskName() + "' added successfully.");
    }

    public Task searchTask(int taskId) {
        Node current = head;
        while (current != null) {
            if (current.data.getTaskId() == taskId) {
                System.out.println("Task found: " + current.data);
                return current.data;
            }
            current = current.next;
        }
        System.out.println("Task with ID " + taskId + " not found.");
        return null;
    }

    public void traverseTasks() {
        if (head == null) {
            System.out.println("No tasks to display.");
            return;
        }

        System.out.println("\n--- Current Tasks ---");
        Node current = head;
        while (current != null) {
            System.out.println(current.data);
            current = current.next;
        }
        System.out.println("---------------------");
    }

    public boolean deleteTask(int taskId) {
        if (head == null) {
            System.out.println("List is empty. Cannot delete task.");
            return false;
        }

        // Case 1: Task to be deleted is the head node
        if (head.data.getTaskId() == taskId) {
            head = head.next; // Move head to the next node
            size--;
            System.out.println("Task with ID " + taskId + " deleted successfully.");
            return true;
        }

        // Case 2: Task to be deleted is in the middle or at the end
        Node current = head;
        Node prev = null;
        while (current != null && current.data.getTaskId() != taskId) {
            prev = current;
            current = current.next;
        }

        if (current != null) { // Task found (current is the node to be deleted)
            prev.next = current.next; // Skip the current node
            size--;
            System.out.println("Task with ID " + taskId + " deleted successfully.");
            return true;
        } else { // Task not found
            System.out.println("Task with ID " + taskId + " not found.");
            return false;
        }
    }

    public int getSize() {
        return size;
    }

    public static void main(String[] args) {
        SinglyLinkedListTaskManagement taskList = new SinglyLinkedListTaskManagement();

        // Add tasks
        taskList.addTask(new Task(1, "Prepare Presentation", "Pending"));
        taskList.addTask(new Task(2, "Review Project Report", "In Progress"));
        taskList.addTask(new Task(3, "Schedule Team Meeting", "Completed"));
        taskList.addTask(new Task(4, "Follow up with Client", "Pending"));

        // Traverse tasks
        taskList.traverseTasks();

        // Search for a task
        taskList.searchTask(2);
        taskList.searchTask(5); // Not found

        // Delete a task (middle)
        taskList.deleteTask(2);
        taskList.traverseTasks();

        // Delete a task (head)
        taskList.deleteTask(1);
        taskList.traverseTasks();

        // Delete a task (non-existent)
        taskList.deleteTask(10);

        // Add more tasks
        taskList.addTask(new Task(5, "Update Documentation", "In Progress"));
        taskList.traverseTasks();
    }
}