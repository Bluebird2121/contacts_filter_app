package com.bluebird.contacts.domain.repository;

import com.bluebird.contacts.domain.entity.Contact;

import java.util.List;

public interface ContactRepository {
    void save(List<Contact> contactsToSave);
}
