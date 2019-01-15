package de.htw.service;

import java.util.List;

import de.htw.model.Contacts;

public interface IContactsService {

	void deleteContact(int contactId);

	void addContact(Contacts contact);

	void updateContact(Contacts contact);

	List<Contacts> getAllContacts();

	Contacts getContactById(int userId);

}
