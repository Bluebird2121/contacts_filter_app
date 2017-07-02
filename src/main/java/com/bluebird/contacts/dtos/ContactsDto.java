package com.bluebird.contacts.dtos;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

import com.bluebird.contacts.domain.entity.Contact;

import com.fasterxml.jackson.annotation.JsonInclude;

public class ContactsDto {

    private static final ContactsDto EMPTY = new ContactsDto(Collections.emptyList());

    private Collection<Contact> contacts;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private PaginationInfo pagination;

    private ContactsDto(List<Contact> contacts) {
        this.contacts = contacts;
    }

    private ContactsDto(Builder builder) {
        contacts = builder.resultContacts;
        PaginationInfo paginationInfo = new PaginationInfo();
        if (builder.page > 0) {
            paginationInfo.setPrev(builder.appBaseLink + "contacts?page=" + (builder.page - 1) + "&nameFilter=" + builder.namePattern.pattern());
        }
        if (builder.isMoreResults) {
            paginationInfo.setNext(builder.appBaseLink + "contacts?page=" + (builder.page + 1) + "&nameFilter=" + builder.namePattern.pattern());
        }
        pagination = paginationInfo;
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

    public static ContactsDto empty() {
        return EMPTY;
    }

    public static class Builder {

        private List<Contact> resultContacts;
        private boolean isMoreResults;
        private int page;
        private Pattern namePattern;
        private String appBaseLink;

        public Builder resultContacts(List<Contact> resultContacts) {
            this.resultContacts = resultContacts;
            return this;
        }

        public Builder isMoreResults(boolean isMoreResults) {
            this.isMoreResults = isMoreResults;
            return this;
        }

        public Builder page(int page) {
            this.page = page;
            return this;
        }

        public Builder pattern(Pattern namePattern) {
            this.namePattern = namePattern;
            return this;
        }

        public Builder appBaseLink(String appBaseLink) {
            this.appBaseLink = appBaseLink;
            return this;
        }

        public ContactsDto build() {
            return new ContactsDto(this);
        }
    }

}
