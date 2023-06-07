package com.office.service;

import com.office.entity.Employee;
import com.office.entity.Phone;
import com.office.repo.EmployeeRepo;
import com.office.repo.PhoneRepo;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class DataBaseInitializationServiceTest {
    private final EmployeeRepo employeeRepo = new EmployeeRepo();
    private final PhoneRepo phoneRepo = new PhoneRepo();

    @Test
    public void testInitializeDatabase() {
        Employee employee1 = new Employee(1, "John");
        Employee employee2 = new Employee(2, "Kenny");
        Employee employee3 = new Employee(3, "Morty");

        employeeRepo.addEmployee(employee1);
        employeeRepo.addEmployee(employee2);
        employeeRepo.addEmployee(employee3);

        assertEquals(employeeRepo.getSize(), 3);

        assertEquals(employeeRepo.getEmployeeById(2), employee2);
        assertEquals(employeeRepo.getEmployeeByName("Morty"), employee3);

        assertNotEquals(employeeRepo.getEmployeeByName("Rick"), employee3);
        assertNotEquals(employeeRepo.getEmployeeById(5), employee3);


        Phone phone1 = new Phone(employee1.getId(), "111-111-1111");
        Phone phone2 = new Phone(employee1.getId(), "222-222-2222");
        Phone phone3 = new Phone(employee2.getId(), "333-333-3333");
        Phone phone4 = new Phone(employee3.getId(), "444-444-4444");
        Phone phone5 = new Phone(employee3.getId(), "555-555-5555");
        Phone phone6 = new Phone(employee3.getId(), "666-666-6666");

        phoneRepo.addPhone(phone1);
        phoneRepo.addPhone(phone2);
        phoneRepo.addPhone(phone3);
        phoneRepo.addPhone(phone4);
        phoneRepo.addPhone(phone5);
        phoneRepo.addPhone(phone6);

        assertEquals(phoneRepo.getPhonesById(3).toString(), "[444-444-4444, 555-555-5555, 666-666-6666]");
        assertEquals(phoneRepo.getPhonesById(4).toString(), "[]");
    }
}
