package com.bluebird.contacts.domain.repository.impl;

import com.bluebird.contacts.domain.entity.Contact;
import com.bluebird.contacts.domain.repository.ContactRepository;
import com.bluebird.contacts.domain.util.ScrollableResultsConverter;
import org.springframework.stereotype.Repository;
import org.hibernate.*;

import javax.persistence.EntityManagerFactory;
import javax.transaction.Transactional;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Repository
public class ContactRepositoryImpl implements ContactRepository {

    private SessionFactory  sessionFactory;

    public ContactRepositoryImpl(EntityManagerFactory sessionFactory) {
        this.sessionFactory = sessionFactory.unwrap(SessionFactory.class);
    }

    @Override
    public void save(List<Contact> contactsToSave) {
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
        try (StatelessSession session = sessionFactory.openStatelessSession();
             ScrollableResults scrollableResults = session.createQuery("SELECT c FROM Contact c").scroll(ScrollMode.FORWARD_ONLY)) {

            Stream<Contact> stream = ScrollableResultsConverter.toStream(scrollableResults, Contact.class);
            return stream
                    .filter(predicate)
                    .skip(skipAmount)
                    .limit(limitAmount)
                    .collect(Collectors.toList());
        }
    }
}
