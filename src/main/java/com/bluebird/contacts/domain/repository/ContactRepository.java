package com.bluebird.contacts.domain.repository;

import com.bluebird.contacts.domain.entity.Contact;

import java.util.List;
import java.util.function.Predicate;

public interface ContactRepository {
    void save(List<Contact> contactsToSave);
    List<Contact> findFilteredPaginated(Predicate<Contact> predicate, int skipAmount, int limitAmount);
    boolean isEmpty();
}
