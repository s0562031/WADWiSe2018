package de.htw.ai.wad.findmefinder;

import static org.junit.Assert.*;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class FinderTest extends TestCase {
	
	/**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public FinderTest( String testName ){
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( AppTest.class );
    }

}
