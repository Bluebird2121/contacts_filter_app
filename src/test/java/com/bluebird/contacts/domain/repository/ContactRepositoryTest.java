package com.bluebird.contacts.domain.repository;

import com.bluebird.contacts.configs.AppConfig;
import com.bluebird.contacts.domain.entity.Contact;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = AppConfig.class)
@DataJpaTest
public class ContactRepositoryTest {

    @Autowired
    private ContactRepository repository;

    @Test
    public void name() throws Exception {
        assertNotNull(repository);
        List<Contact> contactsToSave = new ArrayList<>();
        Contact contactw = new Contact();
        contactw.setId(1);
        contactw.setName("Abc");
        contactsToSave.add(contactw);
        repository.save(contactsToSave);

        List<Contact> filteredPaginated = repository.findFilteredPaginated(contact -> contact.getId() == 1, 0, 1);
        assertNotNull(filteredPaginated);

    }
}