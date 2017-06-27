package com.bluebird.contacts.dtos;

import com.bluebird.contacts.domain.entity.Contact;

import java.util.Collection;
import java.util.List;

public class Contacts {

    private Collection<Contact> contacts;

    public Contacts(List<Contact> contacts) {
        this.contacts = contacts;
    }

    public Collection<Contact> getContacts() {
        return contacts;
    }

    public void setContacts(Collection<Contact> contacts) {
        this.contacts = contacts;
    }
}
