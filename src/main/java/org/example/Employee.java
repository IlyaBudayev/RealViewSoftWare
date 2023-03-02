package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Employee {

    private final int id;
    private final String name;
    private final List<Phone> phoneNumbers = new ArrayList<>();

    public Employee(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void addPhone(Phone phone) {
        phoneNumbers.add(phone);
    }

    public String getPhoneNumbersAsString() {
        List<String> phoneStrings = phoneNumbers.stream()
                .map(Phone::getPhoneNumber)
                .collect(Collectors.toList());
        return String.join(", ", phoneStrings);
    }
}
