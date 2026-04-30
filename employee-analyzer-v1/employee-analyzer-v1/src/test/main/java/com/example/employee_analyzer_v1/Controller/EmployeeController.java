package com.example.employee_analyzer_v1.Controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.employee_analyzer_v1.model.Employee;
import com.example.employee_analyzer_v1.service.EmployeeService;
import com.example.employee_analyzer_v1.util.CsvReader;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService service;

    @Autowired
    private CsvReader reader;

    @GetMapping("/analyze")
    public Map<String, Object> analyze() throws Exception {

        String filePath = "data/employees2.csv";
        List<Employee> employees = reader.read(filePath);

        return service.analyze(employees);
    }
}

