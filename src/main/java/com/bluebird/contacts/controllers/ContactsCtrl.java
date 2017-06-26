package com.bluebird.contacts.controllers;

import com.bluebird.contacts.dtos.Contacts;
import com.bluebird.contacts.services.ContactsService;
import org.springframework.web.bind.annotation.*;

import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

@RestController
@RequestMapping("/hello")
public class ContactsCtrl {

    @RequestMapping(value = "contacts", method=RequestMethod.GET)
    public @ResponseBody
    Contacts filterContacts(@RequestParam(value="nameFilter") String regexp, ContactsService contactsService) {
        try {
            return contactsService.filter(Pattern.compile(regexp));
        } catch (PatternSyntaxException e) {
            throw new IllegalArgumentException(String.format("Filter value '%s' is invalid.", regexp));
        }
    }

}
