package de.htw.ai.wad.findmefinder;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Field;

/**
 * Finder, der mit FindMe annotierte Methoden aus per Argument definierter Klasse (App) findet, falls diese existiert
 * @author Dustin
 */
public class Finder {
	
	/**
	 * Objekt der per Argument definierten Klasse
	 */
	private static Object classobj;

	/**
	 * findet per Argument definierte Klasse und gibt deklarierte Klassen und Attribute mit
	 * Namen, Typen und Modifizierern gibt diese auf der Konsole aus
	 * @param args
	 */
	public static void main(String[] args) {

		String classtoload = null;
		
		if(args != null && args.length != 0) {
			classtoload = args[0];
		} else {
			System.out.println("Klasse Finder muss mit Argumenten aufgerufen werden");
			System.exit(1);
		}
		
	
		try {
			classobj = Finder.createObject(classtoload);
		} catch (ReflectiveOperationException e) {
			e.printStackTrace();
		} 
				
		
		Method[] methodsOfApp = classobj.getClass().getDeclaredMethods();
		
		// Methoden aus Klasse, die per Argument uebergeben wurde
		for(Method m : methodsOfApp) {
			if(m.isAnnotationPresent(FindMe.class)) {
				System.out.println("Methodenname: " + m.getName());
				System.out.println("Modifizierer: " + Modifier.toString(m.getModifiers()));
				System.out.println("Returntype: " + m.getReturnType());
				System.out.println("");
			}
		}
		
		Field[] fieldsOfApp = classobj.getClass().getDeclaredFields();
		
		// Attribute aus Klasse, die per Argument uebergeben wurde
		for(Field f : fieldsOfApp) {
			if(f.isAnnotationPresent(FindMe.class)) {
				System.out.println("Attributnname: " + f.getName());
				System.out.println("Modifizierer: " + Modifier.toString(f.getModifiers()));
				System.out.println("Typ: " + f.getType());
				System.out.println("");
			}
		}
	}
	
    /**
     * erzeugt Object der im Argument definierten Klasse
     * @author E.Schueler
     * @param className
     * @return
     * @throws ReflectiveOperationException
     */
	public static Object createObject(String className) throws ReflectiveOperationException {
	
		if(className == null || className.trim().isEmpty()) {
		    className = "de.htw.ai.wad.findmefinder.App"; //setze einen default-Wert
		}
		else className = "de.htw.ai.wad.findmefinder." + className; //per Hand groupID setzen..?
		
		
		System.out.println("in create: " + className);
		Class<?> c = null;
		
        try {
            c = Class.forName(className);
            
        } catch (ClassNotFoundException e) {
            System.out.println("Class '" + className + "' not found, " + "using 'de.htw.ai.wad.findmefinder.App");
            
            try {
                c = Class.forName("de.htw.ai.wad.findmefinder.App");
                
            } catch (ClassNotFoundException e1) {
                e1.printStackTrace();
                return null;
            }
        }
		
        // class.newInstance() depricated
        return c.getDeclaredConstructor().newInstance();
     
	}

}
