package de.htw.ai.wad.findmefinder;

import static org.junit.Assert.*;

import org.junit.Test;

public class FinderTest {
	
	@Test
	public void testCreateObjectWithApp() throws ReflectiveOperationException {
		assertTrue(Finder.createObject("App") instanceof App);
	}
	
	@Test
	public void testCreateObjectWithNoExistingClass() throws ReflectiveOperationException {
		assertTrue(Finder.createObject("irgendwas") instanceof App);
	}
	
	@Test
	public void testCreateObjectWithEmptyArgument() throws ReflectiveOperationException {
		assertTrue(Finder.createObject("") instanceof App);
	}
	
	@Test
	public void testCreateObjectWithNull() throws ReflectiveOperationException {
		assertTrue(Finder.createObject(null) instanceof App);
	}
	
	@Test
	public void testcreateObjectWODefaultWithApp() throws ReflectiveOperationException {
		assertTrue(Finder.createObject("App") instanceof App);
	}
	
	@Test(expected=ClassNotFoundException.class)
	public void testcreateObjectWODefaultWithNoExistingClass() throws ReflectiveOperationException {
		Finder.createObjectWODefault("irgendwas");
	}
	
	@Test(expected=NullPointerException.class)
	public void testcreateObjectWODefaultWithNull() throws ReflectiveOperationException {
		Finder.createObjectWODefault(null);
	}
	
	@Test(expected=ClassNotFoundException.class)
	public void testcreateObjectWODefaultWithEmptyArguments() throws ReflectiveOperationException {
		Finder.createObjectWODefault("");
	}
}
