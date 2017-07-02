package com.bluebird.contacts.domain.repository;

import com.bluebird.contacts.configs.AppConfig;
import com.bluebird.contacts.domain.entity.Contact;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.contains;
import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = AppConfig.class)
@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
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

        assertDbContainsContact(johnDoe);
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

    @Test
    public void testFilter() throws Exception {
        Contact alexMorozov = new Contact(1, "Alex Morozov");
        Contact johnDeer = new Contact(2, "John Deer");
        Contact alexA = new Contact(3, "Alex A");
        Contact alexB = new Contact(4, "Alex B");
        Contact petyaA = new Contact(5, "Petya A");
        Contact alexC = new Contact(6, "Alex C");
        Contact johnDoe = new Contact(7, "John Doe");
        List<Contact> contactsToSave = new ArrayList<>();
        Collections.addAll(contactsToSave, alexMorozov, johnDeer, alexA, alexB, petyaA, alexC, johnDoe);
        repository.save(contactsToSave);

        List<Contact> resultAS0L1 = repository.findFilteredPaginated(c -> c.getName().startsWith("A"), 0, 1);
        assertNotNull(resultAS0L1);
        Assertions.assertThat(resultAS0L1).contains(alexMorozov);
        Assertions.assertThat(resultAS0L1).doesNotContain(johnDeer, alexA, alexB, petyaA, alexC, johnDoe);

        List<Contact> resultAS1L2 = repository.findFilteredPaginated(c -> c.getName().startsWith("A"), 1, 2);
        assertNotNull(resultAS1L2);
        Assertions.assertThat(resultAS1L2).contains(alexA, alexB);
        Assertions.assertThat(resultAS1L2).doesNotContain(alexMorozov, johnDeer, petyaA, alexC, johnDoe);

        List<Contact> resultAS4L1 = repository.findFilteredPaginated(c -> c.getName().startsWith("A"), 4, 1);
        assertNotNull(resultAS1L2);
        assertTrue(resultAS4L1.isEmpty());
    }

    private void assertResultNotContains(Contact johnDeer, Contact alexA, Contact alexB, Contact petyaA, Contact alexC, Contact johnDoe) {
    }


    private void assertResultContains(Contact... args) {
        for (Contact arg : args) {
            
        }
    }

    private void assertDbContainsContact(Contact johnDoe) {
        List<Contact> filteredPaginated = repository.findFilteredPaginated(contact -> contact.getId() == johnDoe.getId(), 0, 1);
        assertNotNull(filteredPaginated);
        assertEquals(1, filteredPaginated.size());
        Contact contactFromDb = filteredPaginated.get(0);
        assertNotNull(contactFromDb);
        assertEquals(johnDoe, contactFromDb);
    }
}