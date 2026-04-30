package com.example.employee_analyzer_v1.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.example.employee_analyzer_v1.model.Employee;

@Component
public class CsvReader {
	    public List<Employee> read(String filePath) throws IOException {
	        List<Employee> list = new ArrayList<>();

	        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
	            String line = br.readLine();

	            while ((line = br.readLine()) != null) {
	                String[] p = line.split(",");

	                Employee e = new Employee();
	                e.setId(Integer.parseInt(p[0]));
	                e.setFirstName(p[1]);
	                e.setLastName(p[2]);
	                e.setSalary(Double.parseDouble(p[3]));

	                if (p.length > 4 && !p[4].isEmpty()) {
	                    e.setManagerId(Integer.parseInt(p[4]));
	                }

	                list.add(e);
	            }
	        }
	        return list;
}
	    }
