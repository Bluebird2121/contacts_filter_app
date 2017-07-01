package com.bluebird.contacts.controllers;

import com.bluebird.contacts.dtos.ContactsDto;
import com.bluebird.contacts.services.ContactsService;
import org.springframework.web.bind.annotation.*;

import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

@RestController
@RequestMapping("/hello")
public class ContactsCtrl {

    private final ContactsService contactsService;

    public ContactsCtrl(ContactsService contactsService) {
        this.contactsService = contactsService;
    }

    @RequestMapping(value = "/contacts/populate_contacts_data", method=RequestMethod.GET)
    public void populateContactsData() {
        contactsService.populateContactsData();
    }

    @RequestMapping(value = "/contacts", method=RequestMethod.GET)
    public @ResponseBody
    ContactsDto filterContactsNameNotMatch(@RequestParam(value="page") int page,
                                           @RequestParam(value="nameFilter") String regexp) {
        try {
            return contactsService.filterNameNotMatch(page, Pattern.compile(regexp));
        } catch (PatternSyntaxException e) {
            throw new IllegalArgumentException(String.format("Filter value '%s' is invalid.", regexp));
        }
    }

}
