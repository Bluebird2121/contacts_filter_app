package com.bluebird.contacts.services;

import com.bluebird.contacts.dtos.ContactsDto;

public interface ContactsService {
    ContactsDto filterNameNotMatch(int page, String regexp);
    void populateContactsData();
}
