package de.htw.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import de.htw.model.Contacts;
import de.htw.repository.IContactsDAO;


@Service("contactservice")
public class ContactsService implements IContactsService{
	
	@Autowired
    private IContactsDAO repository;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	/**
	 * adds a Contact to the DB
	 */
	@Override
	public Contacts addContact(Contacts contact) {
		String sql = "INSERT INTO contacts (lastname, firstname, address, city, postcode, country, privat) VALUES (?, ?, ? ,? ,?,?, ?)";
		jdbcTemplate.update( sql, contact.getLastname(), contact.getFirstname(), contact.getAddress(), contact.getCity(), contact.getPostcode(), contact.getCountry(), contact.isPrivat());
		
		String sqlget = "SELECT * FROM contacts WHERE id=(SELECT max(id) FROM contacts)";
		RowMapper<Contacts> rowMapper = new BeanPropertyRowMapper<Contacts>(Contacts.class);
		return  jdbcTemplate.queryForObject(sqlget, rowMapper);
	}
	
	/**
	 * deletes a Contact from the DB
	 */
	@Override
	public void deleteContact(int contactId) {
		String sql = "DELETE FROM contacts WHERE id = ?";
		jdbcTemplate.update(sql,contactId);
	}
	
	/**
	 * updates Contact information by performing delete and add
	 * primary key not changeable
	 */
	@Override
	public void updateContact(Contacts contact) {
		String sql = "UPDATE contacts SET  lastname=?, firstname=?, address=?, city=?, postcode=?, country=? WHERE id = ?";
		jdbcTemplate.update(sql, contact.getLastname(), contact.getFirstname(), contact.getAddress(), contact.getCity(), contact.getPostcode(), contact.getCountry(), contact.getId());
	}
	
	/**
	 * returns List of Contacts in DB
	 * @return
	 */
	@Override
	public List<Contacts> getAllContacts() {
		String sql = "SELECT * FROM contacts ORDER BY id";
		return jdbcTemplate.query(sql, new BeanPropertyRowMapper(Contacts.class));
	} 
	
	/**
	 * returns List of Contacts in DB
	 * @return
	 */
	@Override
	public List<Contacts> getAllPublicContacts() {
		String sql = "SELECT * FROM contacts WHERE privat = false ORDER BY id";
		return jdbcTemplate.query(sql, new BeanPropertyRowMapper(Contacts.class));
	} 
	
	/**
	 * gets Contact by its Id
	 */
	@Override
	public Contacts getContactById(int contactID) {
		String sql = "SELECT * FROM contacts WHERE id = ?";
		RowMapper<Contacts> rowMapper = new BeanPropertyRowMapper<Contacts>(Contacts.class);
		Contacts contact = null;
		
		try {
			contact = jdbcTemplate.queryForObject(sql, rowMapper, contactID);
		} catch(EmptyResultDataAccessException e) {
			return null;
		}
		return contact;
	}

}
