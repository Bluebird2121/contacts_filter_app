package com.bluebird.contacts.services;

import com.bluebird.contacts.dtos.ContactsDto;
import java.util.regex.Pattern;

public interface ContactsService {
    ContactsDto filterNameNotMatch(int page, Pattern nameFilter);
    void populateContactsData();
}
