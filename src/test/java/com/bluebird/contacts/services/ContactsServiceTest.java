package com.bluebird.contacts.services;

import com.bluebird.contacts.configs.AppConfig;
import com.bluebird.contacts.domain.entity.Contact;
import com.bluebird.contacts.domain.repository.ContactRepository;
import com.bluebird.contacts.dtos.ContactsDto;
import com.bluebird.contacts.dtos.PaginationInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.regex.PatternSyntaxException;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = AppConfig.class)
public class ContactsServiceTest {

    @MockBean
    private ContactRepository repository;

    @Autowired
    private ContactsService contactsService;

    @Value("${app.contactsPerPage}")
    private int contactsPerPage;

    @Value("${app.populateContactsAmount}")
    private int populateContactsAmount;

    @Test(expected = IllegalStateException.class)
    public void testPopulateWhenDbIsNotEmpty() throws Exception {
        when(repository.isEmpty()).thenReturn(false);
        contactsService.populateContactsData();
    }

    @Test
    public void testPopulateWhenDbIsEmpty() throws Exception {
        when(repository.isEmpty()).thenReturn(true);

        contactsService.populateContactsData();

        ArgumentCaptor<List<Contact>> argument = ArgumentCaptor.forClass((Class) List.class);

        verify(repository, times(1)).isEmpty();
        verify(repository, times(1)).save(argument.capture());
        assertEquals(populateContactsAmount, argument.getValue().size());
        verifyNoMoreInteractions(repository);
    }

    @Test(expected = PatternSyntaxException.class)
    public void testFilterWithIncorrectRegexp() throws Exception {
        contactsService.filterNameNotMatch(0, "(");
    }

    @Test
    public void testFilterWhenNullReturnedFromDb() throws Exception {
        when(repository.findFilteredPaginated(any(), anyInt(), anyInt()))
                .thenReturn(null);

        ContactsDto result = contactsService.filterNameNotMatch(0, "(?!A)");

        assertNotNull(result);
        assertEquals(result, ContactsDto.empty());
    }

    @Test
    public void testFilterWhenEmptyReturnedFromDb() throws Exception {
        when(repository.findFilteredPaginated(any(), anyInt(), anyInt()))
                .thenReturn(Collections.emptyList());

        ContactsDto result = contactsService.filterNameNotMatch(0, "(?!A)");

        assertNotNull(result);
        assertEquals(result, ContactsDto.empty());
    }

    @Test
    public void testWithNoNextResults() throws Exception {
        List<Contact> listToReturn = generateContactsList(contactsPerPage - 1);
        when(repository.findFilteredPaginated(any(), anyInt(), anyInt()))
                .thenReturn(listToReturn);

        ContactsDto result = contactsService.filterNameNotMatch(0, "^(?!A)");

        assertNotNull(result);
        Collection<Contact> resultContacts = result.getContacts();
        assertNotNull(resultContacts);
        assertEquals(resultContacts.size(), contactsPerPage - 1);
        PaginationInfo resultPagination = result.getPagination();
        assertNotNull(resultPagination);
        assertNull(resultPagination.getPrev());
        assertNull(resultPagination.getNext());
    }

    @Test
    public void testWithNextResults() throws Exception {
        List<Contact> listToReturn = generateContactsList(contactsPerPage + 1);
        when(repository.findFilteredPaginated(any(), anyInt(), anyInt()))
                .thenReturn(listToReturn);

        ContactsDto result = contactsService.filterNameNotMatch(0, "^(?!A)");

        assertNotNull(result);
        Collection<Contact> resultContacts = result.getContacts();
        assertNotNull(resultContacts);
        assertEquals(resultContacts.size(), contactsPerPage);
        PaginationInfo resultPagination = result.getPagination();
        assertNotNull(resultPagination);
        assertNull(resultPagination.getPrev());
        assertNotNull(resultPagination.getNext());
    }

    @Test
    public void testFilterWithPrevResults() throws Exception {
        List<Contact> listToReturn = generateContactsList(contactsPerPage + 1);
        when(repository.findFilteredPaginated(any(), anyInt(), anyInt()))
                .thenReturn(listToReturn);

        ContactsDto result = contactsService.filterNameNotMatch(1, "^(?!A)");

        assertNotNull(result);
        Collection<Contact> resultContacts = result.getContacts();
        assertNotNull(resultContacts);
        assertEquals(resultContacts.size(), contactsPerPage);
        PaginationInfo resultPagination = result.getPagination();
        assertNotNull(resultPagination);
        assertNotNull(resultPagination.getPrev());
        assertNotNull(resultPagination.getNext());
    }

    private List<Contact> generateContactsList(int amountOfContacts) {
        List<Contact> result = new ArrayList<>(amountOfContacts);
        for (int i = 0; i < amountOfContacts; i++) {
            result.add(new Contact(i, i+" A"));
        }
        return result;
    }

    class IsListOfTwoElements extends ArgumentMatcher<List> {
        public boolean matches(Object list) {
            return ((List) list).size() == 2;
        }
    }
}