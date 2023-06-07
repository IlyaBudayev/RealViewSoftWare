
package com.office.service;

import com.office.entity.Employee;
import com.office.entity.Phone;
import com.office.repo.EmployeeRepo;
import com.office.repo.PhoneRepo;

public class DataBaseInitializationService {
    private final EmployeeRepo employeeRepo;
    private final PhoneRepo phoneRepo;

    public DataBaseInitializationService(EmployeeRepo employeeRepo, PhoneRepo phoneRepo) {
        this.employeeRepo = employeeRepo;
        this.phoneRepo = phoneRepo;
    }

    public void initializeDatabase() {
        Employee employee1 = new Employee(1, "Sanchez");
        phoneRepo.addPhone(new Phone(employee1.getId(), "111-111-1111"));
        phoneRepo.addPhone(new Phone(employee1.getId(), "222-222-2222"));
        employeeRepo.addEmployee(employee1);

        Employee employee2 = new Employee(2, "Smith");
        phoneRepo.addPhone(new Phone(employee2.getId(), "333-333-3333"));
        employeeRepo.addEmployee(employee2);

        Employee employee3 = new Employee(3, "Broflovski");
        phoneRepo.addPhone(new Phone(employee3.getId(), "444-444-4444"));
        phoneRepo.addPhone(new Phone(employee3.getId(), "555-555-5555"));
        phoneRepo.addPhone(new Phone(employee3.getId(), "666-666-6666"));
        employeeRepo.addEmployee(employee3);
    }
}
