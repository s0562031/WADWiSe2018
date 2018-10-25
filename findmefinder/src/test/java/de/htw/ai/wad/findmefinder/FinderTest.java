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
}
