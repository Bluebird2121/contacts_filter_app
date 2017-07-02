package com.bluebird.contacts.services;

import com.bluebird.contacts.configs.AppConfig;
import com.bluebird.contacts.domain.entity.Contact;
import com.bluebird.contacts.domain.repository.ContactRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = AppConfig.class)
public class ContactsServiceTest {

    @MockBean
    private ContactRepository repository;

    @Autowired
    private ContactsService contactsService;

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

    class IsListOfTwoElements extends ArgumentMatcher<List> {
        public boolean matches(Object list) {
            return ((List) list).size() == 2;
        }
    }
}