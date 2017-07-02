package com.bluebird.contacts.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.regex.PatternSyntaxException;

import com.bluebird.contacts.dtos.ContactsDto;
import com.bluebird.contacts.dtos.SuccessDto;
import com.bluebird.contacts.services.ContactsService;

@RestController
@RequestMapping("/hello")
public class ContactsCtrl {

    private static final String INVALID_FILTER_VALUE_MSG = "Filter value '%s' is invalid.";

    private final ContactsService contactsService;

    public ContactsCtrl(ContactsService contactsService) {
        this.contactsService = contactsService;
    }

    @RequestMapping(value = "/contacts/populate_contacts_data", method = RequestMethod.GET)
    public ResponseEntity<SuccessDto> populateContactsData() {
        contactsService.populateContactsData();
        SuccessDto result = new SuccessDto();
        result.setMessage("Db successfully populated");
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @RequestMapping(value = "/contacts", method = RequestMethod.GET)
    public @ResponseBody
    ContactsDto filterContactsNameNotMatch(@RequestParam(value = "page") int page,
                                           @RequestParam(value = "nameFilter") String regexp) {
        try {
            return contactsService.filterNameNotMatch(page, regexp);
        } catch (PatternSyntaxException e) {
            throw new IllegalArgumentException(String.format(INVALID_FILTER_VALUE_MSG, regexp), e);
        }
    }

}
