package com.bluebird.contacts.services.impl;

import com.bluebird.contacts.domain.entity.Contact;
import com.bluebird.contacts.domain.repository.ContactRepository;
import com.bluebird.contacts.dtos.Contacts;
import com.bluebird.contacts.services.ContactsService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.regex.Pattern;

@Service
public class ContactsServiceImpl implements ContactsService {

    private final ContactRepository contactRepository;

    public ContactsServiceImpl(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    @Override
    public Contacts filter(int page, Pattern nameFilter) {
        Iterable<Contact> allContacts = contactRepository.findAll();
        Contacts result = new Contacts();

        Collection<Contact> contacts = new ArrayList<>();
        CollectionUtils.addAll(contacts, allContacts);
        result.setContacts(contacts);
        return result;
    }
}
