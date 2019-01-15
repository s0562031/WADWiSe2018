package de.htw.repository;

import org.springframework.data.repository.CrudRepository;

import de.htw.model.Contacts;

public interface IContactsDAO extends CrudRepository<Contacts, Integer> {

}
