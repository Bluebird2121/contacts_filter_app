package com.bluebird.contacts.domain.repository.impl;

import com.bluebird.contacts.domain.entity.Contact;
import com.bluebird.contacts.domain.repository.ContactRepository;
import com.bluebird.contacts.domain.repository.util.ScrollableResultsConverter;
import com.bluebird.contacts.domain.util.ScrollableResultsIterator;
import org.hibernate.*;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManagerFactory;
import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.Function;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@Repository
@Transactional
public class ContactRepositoryImpl implements ContactRepository {

    private SessionFactory  sessionFactory;

    public ContactRepositoryImpl(EntityManagerFactory sessionFactory) {
        this.sessionFactory = sessionFactory.unwrap(SessionFactory.class);
    }

    @Override
    public void save(List<Contact> contactsToSave) {
        StatelessSession session = sessionFactory.openStatelessSession();
        Transaction tx = session.beginTransaction();

        for (Contact c : contactsToSave) {
            session.insert(c);
        }
        tx.commit();
        session.close();
    }

    @Override
    public List<Contact> findAll(Function<Stream<Contact>,  List<Contact>> streamContactFunction) {
        StatelessSession session = null;
        ScrollableResults scrollableResults = null;
        List<Contact> result = Collections.emptyList();
        try {
            session = sessionFactory.openStatelessSession();
            Transaction tx = session.beginTransaction();
            tx.begin();
            scrollableResults = session.createQuery("SELECT c FROM Contact c").scroll(ScrollMode.FORWARD_ONLY);
            Stream<Contact> stream = ScrollableResultsConverter.toStream(scrollableResults, Contact.class);
            result = streamContactFunction.apply(stream);
            tx.commit();
        } finally {
            if (scrollableResults != null) {
                scrollableResults.close();
            }
            if (session != null) {
                session.close();
            }
            return result;
        }
    }
}
