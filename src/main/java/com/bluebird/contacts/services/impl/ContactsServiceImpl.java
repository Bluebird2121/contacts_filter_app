package com.bluebird.contacts.services.impl;

import com.bluebird.contacts.domain.entity.Contact;
import com.bluebird.contacts.domain.repository.ContactRepository;
import com.bluebird.contacts.dtos.ContactsDto;
import com.bluebird.contacts.services.ContactsService;
import com.bluebird.contacts.utils.FullNameGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@Service
public class ContactsServiceImpl implements ContactsService {

    private final ContactRepository contactRepository;

    @Value("${app.baseLink}")
    private String appBaseLink;

    @Value("${app.contactsPerPage}")
    private int contactsPerPage;

    @Value("${app.populateContactsAmount}")
    private int populateContactsAmount;

    public ContactsServiceImpl(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    @Override
    public ContactsDto filterNameNotMatch(int page, String regexp) {
        Pattern namePattern = Pattern.compile(regexp);
        int skipAmount = page * contactsPerPage;
        int limitAmount = contactsPerPage + 1;
        List<Contact> filteredContacts
                = contactRepository.findFilteredPaginated(c -> !namePattern.matcher(c.getName()).find(), skipAmount, limitAmount);
        if (filteredContacts == null || filteredContacts.isEmpty()) {
            return ContactsDto.empty();
        }
        boolean isMoreResults = filteredContacts.size() > contactsPerPage;
        List<Contact> resultContacts = isMoreResults ? filteredContacts.subList(0, contactsPerPage - 1) : filteredContacts;
        return new ContactsDto.Builder()
                .resultContacts(resultContacts)
                .isMoreResults(isMoreResults)
                .page(page)
                .pattern(namePattern)
                .appBaseLink(appBaseLink)
                .build();
    }

    @Override
    public void populateContactsData() {
        if (!contactRepository.isEmpty()) {
            throw new IllegalStateException("Can't populate not empty database.");
        }
        List<Contact> contactsToSave = new ArrayList<>(populateContactsAmount);
        for (int i = 0; i < populateContactsAmount; i++) {
            Contact contactToAdd = new Contact();
            contactToAdd.setId(i);
            contactToAdd.setName(FullNameGenerator.generate());
            contactsToSave.add(contactToAdd);
        }
        contactRepository.save(contactsToSave);
    }
}
