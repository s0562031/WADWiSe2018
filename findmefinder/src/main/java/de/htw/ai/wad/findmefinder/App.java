package de.htw.ai.wad.findmefinder;

/**
 * Klasse mit Dummy Methoden und Attributen zum finden fuer Finder
 * @author Dustin
 */
public class App {
	
	@FindMe public static java.lang.String publicStaticField;
	@FindMe private final int privatesFinalField = 0;
	@FindMe protected java.math.BigDecimal accountBalance;
	
	// Testattribut nicht annotiert
	public int testfield = 0;
    
    @FindMe
    public static void method1() {
    	
    }
    
    @FindMe
    private int method2() {
		return 0;
    }
    
    @FindMe
    protected final java.lang.String method3() {
		return null;
    }
    
    // Testmethode nicht annotiert
    public int testmethode() {
    	return 1;
    }
    

    
}
