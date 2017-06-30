package com.bluebird.contacts.domain.repository.impl;

import com.bluebird.contacts.domain.entity.Contact;
import com.bluebird.contacts.domain.repository.ContactRepository;
import org.hibernate.SessionFactory;
import org.hibernate.StatelessSession;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class ContactRepositoryImpl implements ContactRepository {

    private SessionFactory sessionFactory;

    public ContactRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
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
}
