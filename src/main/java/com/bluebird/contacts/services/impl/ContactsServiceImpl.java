package com.bluebird.contacts.services.impl;

import com.bluebird.contacts.domain.entity.Contact;
import com.bluebird.contacts.domain.repository.ContactRepository;
import com.bluebird.contacts.dtos.Contacts;
import com.bluebird.contacts.dtos.PaginationInfo;
import com.bluebird.contacts.services.ContactsService;
import com.bluebird.contacts.utils.FullNameGenerator;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class ContactsServiceImpl implements ContactsService {

    private static final int CONTACTS_PER_PAGE = 1000;

    private final ContactRepository contactRepository;

    public ContactsServiceImpl(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    @Override
    public Contacts filterNameNotMatch(int page, Pattern nameFilter) {
        List<Contact> resultContacts = contactRepository.findAll(
            stream -> stream
                .filter(x -> !nameFilter.matcher(x.getName()).find())
                .skip(page * CONTACTS_PER_PAGE)
                .limit(CONTACTS_PER_PAGE + 1)
                .collect(Collectors.toList())
        );
        if (resultContacts.isEmpty()) {
            return Contacts.empty();
        }
        boolean isNextPagePresent = resultContacts.size() > CONTACTS_PER_PAGE;
        Contacts contacts = new Contacts(isNextPagePresent ? resultContacts.subList(0, resultContacts.size() - 1) : resultContacts);
        PaginationInfo paginationInfo = new PaginationInfo();
        if (page != 0) {
            paginationInfo.setPrev("http://localhost:8080/hello/contacts?page="+(page-1)+"&nameFilter="+nameFilter.pattern());
        }
        if (isNextPagePresent) {
            paginationInfo.setNext("http://localhost:8080/hello/contacts?page="+(page+1)+"&nameFilter="+nameFilter.pattern());
        }
        contacts.setPagination(paginationInfo);
        return contacts;
    }

    @Override
    public void populateContactsData() {
        int contactsSize = 100_000;
        List<Contact> contactsToSave = new ArrayList<>(contactsSize);
        for (int i = 0; i < contactsSize; i++) {
            Contact contactToAdd = new Contact();
            contactToAdd.setId(i);
            contactToAdd.setName(FullNameGenerator.generate());
            contactsToSave.add(contactToAdd);
        }
        contactRepository.save(contactsToSave);
    }
}
