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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = AppConfig.class)
@DataJpaTest
public class ContactRepositoryTest {

    @Autowired
    private ContactRepository repository;

    @Test(expected = IllegalArgumentException.class)
    public void testSaveWithNullInput() throws Exception {
        repository.save(null);
    }

    @Test
    public void testSave() throws Exception {
        List<Contact> contactsToSave = new ArrayList<>();
        Contact johnDoe = new Contact(1, "John Doe");
        contactsToSave.add(johnDoe);
        repository.save(contactsToSave);

        assertContainsContact(johnDoe);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFilterWithNullPredicate() throws Exception {
        repository.findFilteredPaginated(null, 0, 1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFilterWithNegativeSkipAmount() throws Exception {
        repository.findFilteredPaginated(c -> true, -1, 1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFilterWithNegativeLimitAmount() throws Exception {
        repository.findFilteredPaginated(c -> true, 0, -1);
    }

    private void assertContainsContact(Contact johnDoe) {
        List<Contact> filteredPaginated = repository.findFilteredPaginated(contact -> contact.getId() == johnDoe.getId(), 0, 1);
        assertNotNull(filteredPaginated);
        assertEquals(filteredPaginated.size(), 1);
        Contact contactFromDb = filteredPaginated.get(0);
        assertNotNull(contactFromDb);
        assertEquals(johnDoe, contactFromDb);
    }
}