package com.office.repo;

import com.office.entity.Employee;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class EmployeeRepo {
    private final Map<Integer, Employee> employees;

    public EmployeeRepo() {
        employees = new HashMap<>();
    }

    public int getSize() {
        return employees.size();
    }

    public Collection<Employee> getValues() {
        return employees.values();
    }

    public void addEmployee(Employee employee) {
        employees.put(employee.getId(), employee);
    }

    public Employee getEmployeeById(int id) {
        return employees.get(id);
    }

    public Employee getEmployeeByName(String name) {
        for (Employee employee : employees.values()) {
            if (employee.getName().equalsIgnoreCase(name)) {
                return employee;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return "EmployeeRepo{" +
                "employees=" + employees +
                '}';
    }
}