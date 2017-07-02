package com.bluebird.contacts.domain.repository.impl;

import org.hibernate.*;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.persistence.EntityManagerFactory;

import com.bluebird.contacts.domain.entity.Contact;
import com.bluebird.contacts.domain.repository.ContactRepository;
import com.bluebird.contacts.domain.util.ScrollableResultsConverter;

@Repository
public class ContactRepositoryImpl implements ContactRepository {

    private SessionFactory sessionFactory;

    public ContactRepositoryImpl(EntityManagerFactory sessionFactory) {
        this.sessionFactory = sessionFactory.unwrap(SessionFactory.class);
    }

    @Override
    public void save(List<Contact> contactsToSave) {
        if (contactsToSave == null) {
            throw new IllegalArgumentException("Can't save null contacts list");
        }
        try (StatelessSession session = sessionFactory.openStatelessSession()) {
            Transaction tx = session.getTransaction();
            tx.begin();
            for (Contact c : contactsToSave) {
                session.insert(c);
            }
            tx.commit();
        }
    }

    @Override
    public List<Contact> findFilteredPaginated(Predicate<Contact> predicate, int skipAmount, int limitAmount) {
        if (predicate == null) {
            throw new IllegalArgumentException("Can't filter with null predicate");
        }
        try (StatelessSession session = sessionFactory.openStatelessSession();
             ScrollableResults scrollableResults
                     = session.createQuery("SELECT c FROM Contact c").scroll(ScrollMode.FORWARD_ONLY)) {

            Stream<Contact> stream = ScrollableResultsConverter.toStream(scrollableResults, Contact.class);
            return stream
                    .filter(predicate)
                    .skip(skipAmount)
                    .limit(limitAmount)
                    .collect(Collectors.toList());
        }
    }

    @Override
    public boolean isEmpty() {
        return findFilteredPaginated(e -> true, 0, 1).isEmpty();
    }
}
