package com.bluebird.contacts.services;

import com.bluebird.contacts.dtos.Contacts;

public interface ContactsService {

    //TODO Come up with good name for this something like reverse filter
    Contacts filter(String nameFilter);
}
