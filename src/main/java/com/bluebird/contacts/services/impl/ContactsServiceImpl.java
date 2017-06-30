package com.bluebird.contacts.services.impl;

import com.bluebird.contacts.domain.entity.Contact;
import com.bluebird.contacts.domain.repository.ContactRepository;
import com.bluebird.contacts.dtos.Contacts;
import com.bluebird.contacts.services.ContactsService;
import com.bluebird.contacts.util.FullNameGenerator;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
        Iterable<Contact> allContacts = null;// contactRepository.findAll();

        List<Contact> resultContacts = StreamSupport.stream(allContacts.spliterator(), IS_PARALLELED)
                .filter(x -> ! nameFilter.matcher(x.getName()).find())
                .skip(page * CONTACTS_PER_PAGE)
                .limit(CONTACTS_PER_PAGE + 1)
                .collect(Collectors.toList());

        if (resultContacts.isEmpty()) {
            return Contacts.empty();
        }
        /*boolean isNextPagePresent = resultContacts.size() > CONTACTS_PER_PAGE;
        Contacts contacts = new Contacts(isNextPagePresent ? resultContacts.subList(0, resultContacts.size() - 1) : resultContacts);
        PaginationInfo paginationInfo = new PaginationInfo();
        if (page != 0) {
            paginationInfo.setPrev("http://localhost:8080/hello/contacts?page="+(page-1)+"&nameFilter="+nameFilter.pattern());
        }
        if (isNextPagePresent) {
            paginationInfo.setNext("http://localhost:8080/hello/contacts?page="+(page+1)+"&nameFilter="+nameFilter.pattern());
        }
        contacts.setPagination(paginationInfo);*/
        return null;
    }

    @Override
    public void populateContactsData() {
        int contactsSize = 1_000_000;
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
