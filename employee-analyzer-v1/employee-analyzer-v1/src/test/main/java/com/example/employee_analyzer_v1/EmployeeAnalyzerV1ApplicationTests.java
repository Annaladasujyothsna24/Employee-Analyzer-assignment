package com.example.employee_analyzer_v1;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class EmployeeAnalyzerV1ApplicationTests {

	@package com.company;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

class EmployeeTest {

    
    static class Employee {
        int id;
        double salary;
        Integer managerId;
        List<Employee> subordinates = new ArrayList<>();

        Employee(int id, double salary, Integer managerId) {
            this.id = id;
            this.salary = salary;
            this.managerId = managerId;
        }
    }

    // Method to test salary logic
    String checkSalary(Employee manager) {

        if (manager.subordinates.isEmpty()) return "NO_SUBS";

        double total = 0;
        for (Employee e : manager.subordinates) {
            total += e.salary;
        }

        double avg = total / manager.subordinates.size();

        double min = avg * 1.2;
        double max = avg * 1.5;

        if (manager.salary < min) return "LESS";
        if (manager.salary > max) return "MORE";

        return "OK";
    }

    // Method to test depth logic
    int getDepth(Employee emp, Map<Integer, Employee> map) {

        int depth = 0;

        while (emp.managerId != null) {
            emp = map.get(emp.managerId);
            depth++;
        }
        return depth;
    }

    @Test
    void testSalaryAndDepth() {

        // Create employees
        Employee ceo = new Employee(1, 100000, null);
        Employee m1 = new Employee(2, 30000, 1);
        Employee e1 = new Employee(3, 30000, 2);
        Employee e2 = new Employee(4, 30000, 2);

        // Build hierarchy
        m1.subordinates.add(e1);
        m1.subordinates.add(e2);

        // Salary Test
        String salaryResult = checkSalary(m1);
        assertEquals("LESS", salaryResult);

        // Depth Test
        Map<Integer, Employee> map = new HashMap<>();
        map.put(1, ceo);
        map.put(2, m1);
        map.put(3, e1);
        map.put(4, e2);

        int depth = getDepth(e1, map);
        assertEquals(2, depth);
    }
}