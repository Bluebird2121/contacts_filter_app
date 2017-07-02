package com.bluebird.contacts.domain.repository;

import java.util.List;
import java.util.function.Predicate;

import com.bluebird.contacts.domain.entity.Contact;

public interface ContactRepository {
    void save(List<Contact> contactsToSave);
    List<Contact> findFilteredPaginated(Predicate<Contact> predicate, int skipAmount, int limitAmount);
    boolean isEmpty();
}
