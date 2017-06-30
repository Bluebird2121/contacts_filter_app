package com.bluebird.contacts.services;

import com.bluebird.contacts.dtos.Contacts;

import java.util.regex.Pattern;

public interface ContactsService {

    //TODO Come up with good name for this something like reverse filter
    Contacts filter(int page, Pattern nameFilter);

    void populateContactsData();
}
