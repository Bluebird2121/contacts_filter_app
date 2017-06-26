package com.bluebird.contacts.services.impl;

import com.bluebird.contacts.domain.repository.ContactRepository;
import com.bluebird.contacts.dtos.Contacts;
import com.bluebird.contacts.services.ContactsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ContactsServiceImpl implements ContactsService {

    @Autowired
    private ContactRepository contactRepository;

    @Override
    public Contacts filter(String nameFilter) {
        return null;
    }
}
