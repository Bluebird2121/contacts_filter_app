package com.bluebird.contacts.controllers;

import com.bluebird.contacts.dtos.Contacts;
import com.bluebird.contacts.services.ContactsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("/hello")
public class ContactsCtrl {

    @Autowired
    private ContactsService contactsService;

    @RequestMapping(value = "contacts", method=RequestMethod.GET)
    public @ResponseBody
    Contacts filterContacts(@RequestParam(value="nameFilter") String nameFilter) {
        return contactsService.filter(nameFilter);
    }

}
