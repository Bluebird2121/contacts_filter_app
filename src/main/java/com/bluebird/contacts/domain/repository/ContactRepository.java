package com.bluebird.contacts.domain.repository;

import com.bluebird.contacts.domain.entity.Contact;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

public interface ContactRepository {
    void save(List<Contact> contactsToSave);

    List<Contact> findAll(Function<Stream<Contact>,  List<Contact>> streamContactFunction);
}
