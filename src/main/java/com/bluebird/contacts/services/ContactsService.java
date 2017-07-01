package com.bluebird.contacts.services;

import com.bluebird.contacts.dtos.Contacts;
import java.util.regex.Pattern;

public interface ContactsService {
    Contacts filterNameNotMatch(int page, Pattern nameFilter);
    void populateContactsData();
}
