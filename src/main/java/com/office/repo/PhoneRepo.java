package com.office.repo;

import com.office.entity.Phone;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PhoneRepo {
    private final Map<Integer, List<Phone>> phones;

    public PhoneRepo() {
        phones = new HashMap<>();
    }

    public void addPhone(Phone phone) {
        int id = phone.getId();
        if (!phones.containsKey(id)) {
            phones.put(id, new ArrayList<>());
        }
        phones.get(id).add(phone);
    }

    public List<Phone> getPhonesById(int id) {
        if (phones.containsKey(id)) {
            return phones.get(id);
        }
        return new ArrayList<>();
    }
}