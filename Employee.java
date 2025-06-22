// Employee.java
public class Employee {
    private int employeeId;
    private String name;
    private String position;
    private double salary;

    public Employee(int employeeId, String name, String position, double salary) {
        this.employeeId = employeeId;
        this.name = name;
        this.position = position;
        this.salary = salary;
    }

    // Getters
    public int getEmployeeId() {
        return employeeId;
    }

    public String getName() {
        return name;
    }

    public String getPosition() {
        return position;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) { this.salary = salary; }

    @Override
    public String toString() {
        return "ID: " + employeeId + ", Name: " + name + ", Position: " + position + String.format(", Salary: $%.2f", salary);
    }
}

// EmployeeManagementSystem.java
public class EmployeeManagementSystem {
    private Employee[] employees;
    private int currentSize; // Keeps track of the number of actual employees in the array

    public EmployeeManagementSystem(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("Capacity must be greater than 0.");
        }
        this.employees = new Employee[capacity]; // Initialize the array with the given capacity
        this.currentSize = 0; // Initially, no employees
    }

    /**
     * Adds an employee to the array.
     * @param employee The Employee object to add.
     * @return true if added successfully, false if the array is full.
     */
    public boolean addEmployee(Employee employee) {
        if (currentSize < employees.length) { // Check if there's space
            employees[currentSize] = employee;
            currentSize++;
            System.out.println("Employee " + employee.getName() + " added successfully.");
            return true;
        } else {
            System.out.println("Array is full. Cannot add more employees.");
            return false;
        }
    }

    public Employee searchEmployee(int employeeId) {
        for (int i = 0; i < currentSize; i++) {
            // Defensive check for null in case array elements are explicitly set to null
            // although with currentSize, this loop only goes over valid employees.
            if (employees[i] != null && employees[i].getEmployeeId() == employeeId) {
                System.out.println("Employee found: " + employees[i]);
                return employees[i];
            }
        }
        System.out.println("Employee with ID " + employeeId + " not found.");
        return null;
    }


    public void traverseEmployees() {
        if (currentSize == 0) {
            System.out.println("No employees to display.");
            return;
        }

        System.out.println("\n--- Employee List ---");
        for (int i = 0; i < currentSize; i++) {
            if (employees[i] != null) { // Ensure the slot isn't null (e.g., after deletion logic)
                System.out.println(employees[i]);
            }
        }
        System.out.println("--------------------");
    }

    
    public boolean deleteEmployee(int employeeId) {
        int indexToDelete = -1;

        // Find the index of the employee to delete
        for (int i = 0; i < currentSize; i++) {
            if (employees[i] != null && employees[i].getEmployeeId() == employeeId) {
                indexToDelete = i;
                break;
            }
        }

        if (indexToDelete != -1) {
            // Shift elements to the left to fill the gap
            for (int i = indexToDelete; i < currentSize - 1; i++) {
                employees[i] = employees[i + 1];
            }
            employees[currentSize - 1] = null; // Clear the last element (important for garbage collection)
            currentSize--; // Decrement the count of actual employees
            System.out.println("Employee with ID " + employeeId + " deleted successfully.");
            return true;
        } else {
            System.out.println("Employee with ID " + employeeId + " not found.");
            return false;
        }
    }

    public static void main(String[] args) {
        // Create an employee management system with a capacity of 5
        EmployeeManagementSystem ems = new EmployeeManagementSystem(5);

        // Add employees
        Employee emp1 = new Employee(101, "Alice Smith", "Software Engineer", 75000);
        Employee emp2 = new Employee(102, "Bob Johnson", "Project Manager", 90000);
        Employee emp3 = new Employee(103, "Charlie Brown", "HR Specialist", 60000);
        Employee emp4 = new Employee(104, "Diana Prince", "Marketing Lead", 80000);

        ems.addEmployee(emp1);
        ems.addEmployee(emp2);
        ems.addEmployee(emp3);
        ems.addEmployee(emp4);

        // Traverse employees
        ems.traverseEmployees();

        // Search for an employee
        ems.searchEmployee(102);
        ems.searchEmployee(999); // Not found

        // Delete an employee
        ems.deleteEmployee(102);
        ems.traverseEmployees();

        // Add more employees (filling up the space after deletion)
        Employee emp5 = new Employee(105, "Eve Adams", "Data Analyst", 70000);
        Employee emp6 = new Employee(106, "Frank White", "UX Designer", 85000);

        ems.addEmployee(emp5); // Should succeed (4 employees now)
        ems.addEmployee(emp6); // Should succeed (5 employees now, array is full)

        ems.traverseEmployees();
    }
}
