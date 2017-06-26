package com.bluebird.contacts.services.impl;

import com.bluebird.contacts.domain.entity.Contact;
import com.bluebird.contacts.dtos.Contacts;
import com.bluebird.contacts.services.ContactsService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
public class ContactsServiceImpl implements ContactsService {

    @Override
    public Contacts filter(String nameFilter) {
        Contacts result = new Contacts();
        Collection<Contact> contacts = new ArrayList<>();
        Contact testContact = new Contact();
        testContact.setId(1);
        testContact.setName("test");
        contacts.add(testContact);
        result.setContacts(contacts);
        return result;
    }
}
