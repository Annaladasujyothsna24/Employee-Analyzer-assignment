package com.example.employee_analyzer_v1.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.example.employee_analyzer_v1.model.Employee;

@Service
public class EmployeeService {

    public Map<String, Object> analyze(List<Employee> employees) {

        Map<Integer, List<Employee>> hierarchy = buildHierarchy(employees);

        List<String> salaryIssues = analyzeSalaries(employees, hierarchy);
        List<String> reportingIssues = checkReportingLines(employees);

        Map<String, Object> result = new HashMap<>();
        result.put("salaryIssues", salaryIssues);
        result.put("reportingIssues", reportingIssues);

        return result;
    }

    private Map<Integer, List<Employee>> buildHierarchy(List<Employee> employees) {
        Map<Integer, List<Employee>> map = new HashMap<>();
        for (Employee e : employees) {
            if (e.getManagerId() != null) {
                map.computeIfAbsent(e.getManagerId(), k -> new ArrayList<>()).add(e);
            }
        }
        return map;
    }

    private List<String> analyzeSalaries(List<Employee> employees, Map<Integer, List<Employee>> map) {
        List<String> issues = new ArrayList<>();

        for (Employee m : employees) {
            List<Employee> subs = map.get(m.getId());
            if (subs == null) continue;

            double avg = subs.stream().mapToDouble(Employee::getSalary).average().orElse(0);

            double min = avg * 1.2;
            double max = avg * 1.5;

            if (m.getSalary() < min) {
                issues.add(m.getFirstName() + " earns LESS by " + (min - m.getSalary()));
            } else if (m.getSalary() > max) {
                issues.add(m.getFirstName() + " earns MORE by " + (m.getSalary() - max));
            }
        }
        return issues;
    }
    private List<String> checkReportingLines(List<Employee> employees) {
        Map<Integer, Employee> map = new HashMap<>();
        Map<Integer, Integer> memo = new HashMap<>();

        for (Employee e : employees) map.put(e.getId(), e);

        List<String> issues = new ArrayList<>();

        for (Employee e : employees) {
            int depth = getDepth(e, map, memo);
            if (depth > 4) {
                issues.add(e.getFirstName() + " exceeds by " + (depth - 4));
            }
        }
        return issues;
    }

    private int getDepth(Employee e, Map<Integer, Employee> map, Map<Integer, Integer> memo) {
        if (e.getManagerId() == null) return 0;

        if (memo.containsKey(e.getId())) return memo.get(e.getId());

        int depth = 1 + getDepth(map.get(e.getManagerId()), map, memo);
        memo.put(e.getId(), depth);
        return depth;
    }
}

