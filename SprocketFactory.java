
package CIS484;

import java.util.*;
import java.io.*;

public class SprocketFactory {

    public static void main(String[] args) {
        
//        Sprocket testGear = new Sprocket("Test Sprocket", 1.0);
//        System.out.println(testGear); // Implicit call to the .toString()
        
        Sprocket[] gearArray = new Sprocket[5];
        Sprocket gear001 = new Sprocket("Standard Sprocket", 1.0);
        Sprocket gear002 = new Sprocket("Standard Sprocket", 1.5);
        Sprocket gear003 = new Sprocket("Industrial Sprocket", 3.0);
        
        gearArray[0] = gear001; // This does not copy the object, only the reference to it.
        gearArray[1] = gear002;
        gearArray[2] = gear003;
        
        gearArray[3] = new CeramicSprocket("Painted Sprocket", 2.5, "Purple");
        gearArray[4] = new CeramicSprocket("Painted Sprocket", 3.0, "Gold");
        
        printGearArray(gearArray);
        
        
        /*
        Abstract Class:
        Determine that there should be some methods in your concrete class, but you get to decide
        the code thats implemented for theose methods!
        - will have some reguar code ( like normal class) but can also have abstract methods.
        - Abstract? there is just a method header and No code:
        
        public abstract class GeneralSprocket
        {
            ... regular code...
            public abstract double aMetheod();
            public abstract void anotherMethod();
        }
        
        // Interface
        - has no regular Class within!
        - contains only constants and abstract methods
        public interface InterfaceName
        {
            public final double aConstant=5.0;
            public abstract String someMethod();
        }
        
        public interface Comparable<T>
        {
            public abstract int compareTo(T o);
            // + number if obj is "larger"
            // 0 if both objects are the same
            // - if obj is "smaller"
            // obj1.compareTo(obj2);
        }
        
        */
        
        CeramicSprocket compareSprocket = new CeramicSprocket("Painted Sprocket", 2.0, "Green");
        for(Sprocket s: gearArray)
        {
            System.out.println("Comparing " + compareSprocket.getIDNum() + " with " + s.getIDNum());
            if (compareSprocket.compareTo(s) < 0)
                System.out.println(compareSprocket.getIDNum() + " is lighter!");
            else if (compareSprocket.compareTo(s) > 0)
                System.out.println(compareSprocket.getIDNum() + " is heavier!");
            else
                System.out.println("They are the same!");
        }
        
        // Exception Handling
        // Basic File I/O - "Plain Text"
        
        
        // Method 1 - Handle exceptions thrown by java itself.
        CeramicSprocket csNullPointer = null;
        
//        try
//        {
//           System.out.println(csNullPointer.howToRotate()); 
//        }
//        catch (NullPointerException npex)
//        {
//            // When you chain exceptions, you must start
//            // with subclasses first and work your way up
//            // the class hiearchy
//        }
//        catch(Exception ex)
//        {
//            System.out.println(ex.toString());
//        }
//        
//        
//        // Method 2 - Throw our own method
//        try
//        {
//            System.out.print("Please enter sprocket label and weight: ");
//            String tempLabel = "";
//            double tempWeight = 0.0;
//            Scanner in = new Scanner(System.in);
//            tempLabel = in.next();
//            tempWeight = in.nextDouble();
//            
//            if (tempWeight < 0.0)
//                throw new ArithmeticException("Sprocket Weight must be larger than 0!");
//        }
//        catch(ArithmeticException amex)
//        {
//            System.out.println(amex.toString());
//        }
        
        
        // Exception Handling Situations:
            // User Input
            // File I/O
            // Networking
            // Parallel Processing
            // Database Access
            
        // Read in from a file:
        File inFile = new File("sprocketdata.txt");
        ArrayList<Sprocket> spArray = new ArrayList<>();
        
        try
        {
            Scanner fileIn = new Scanner(inFile);
            while (fileIn.hasNext())
            {
                spArray.add(new Sprocket(fileIn.next(), fileIn.nextDouble()));
            }
            // VERY IMPORTANT!!!!!!
            fileIn.close();
        }
        catch(IOException ioex)
        {
            System.out.println(ioex.toString());
        }
        
        // Write this data back out to a plain text file.
        try
        {
            PrintWriter outFile = new PrintWriter (new File("sprocketDataOut.txt"));
            for (Sprocket spr: spArray)
            {
                outFile.print(spr.getIDNum());
                outFile.print("\t");
                outFile.print(spr.getLabel());
                outFile.print("\t");
                outFile.print(spr.getWeight());
                outFile.print("\n");
            }
            outFile.close();
        }
        catch(IOException ioex)
        {
            System.out.println(ioex.toString());
        }
              
            
        
        // File I/O - Plain Text (readable)
        // Read in from a file - Scanner!
        
        // Write out to file - PrintWriter
        // Wrap these in try... catch statements!
        
        
    } // end of main
    
    public static void printGearArray(Sprocket[] array)
    {
        for (int i=0; i<array.length;i++)
        {
            if(array[i] != null)
                System.out.println(array[i] + ", Rotate?: " + array[i].howToRotate());
            else
                System.out.println("No object found at location: " + i);
            
        }
    }
    
}
