package com.bluebird.contacts.dtos;

import com.bluebird.contacts.domain.entity.Contact;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class Contacts {

    private static final Contacts EMPTY = new Contacts(Collections.emptyList());

    private Collection<Contact> contacts;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private PaginationInfo pagination;


    public Contacts(List<Contact> contacts) {
        this.contacts = contacts;
    }

    public Collection<Contact> getContacts() {
        return contacts;
    }

    public void setContacts(Collection<Contact> contacts) {
        this.contacts = contacts;
    }

    public PaginationInfo getPagination() {
        return pagination;
    }

    public void setPagination(PaginationInfo pagination) {
        this.pagination = pagination;
    }

    public static Contacts empty() {
        return EMPTY;
    }
}
