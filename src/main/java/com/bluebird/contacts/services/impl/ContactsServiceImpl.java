package com.bluebird.contacts.services.impl;

import com.bluebird.contacts.domain.entity.Contact;
import com.bluebird.contacts.domain.repository.ContactRepository;
import com.bluebird.contacts.dtos.Contacts;
import com.bluebird.contacts.services.ContactsService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class ContactsServiceImpl implements ContactsService {

    private static final boolean IS_PARALLELED = false;
    private static final int CONTACTS_PER_PAGE = 1;

    private final ContactRepository contactRepository;

    public ContactsServiceImpl(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    @Override
    public Contacts filter(int page, Pattern nameFilter) {
        Iterable<Contact> allContacts = contactRepository.findAll();

        List<Contact> resultContacts = StreamSupport.stream(allContacts.spliterator(), IS_PARALLELED)
                .filter(x -> ! nameFilter.matcher(x.getName()).find())
                .skip(page * CONTACTS_PER_PAGE)
                .limit(CONTACTS_PER_PAGE)
                .collect(Collectors.toList());

        return new Contacts(resultContacts);
    }
}
