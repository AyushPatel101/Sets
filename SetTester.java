
/*
 * Student information for assignment:
 *
 * Number of slip days used: 2
 * Student 1 (Student whose turnin account is being used)
 *  UTEID: ap55837
 *  email address: patayush01@utexas.edu
 *  Grader name: Tony
*/

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

import javax.swing.JFileChooser;
import javax.swing.UIManager;

/*
 * CS 314 Students, put your results to the experiments and answers to questions
 * here: CS314 Students, why is it unwise to implement all three of the
 * intersection, union, and difference methods in the AbstractSet class:
 */
// Why we shouldn't implement all 3 operations (difference, intersection and union) in abstract class:
// The way we implement those 3 functions is my calling a combination of the other functions. For example,
// the intersection is found by taking the difference of other set and calling set and the difference of the calling and the resulting set
// of previous difference. The other operations would also do so combination of functions, leading to a back and forth calling of each other
// (will never compute answer). 
/*
Title	   			 		Size	 Total Words	Distinct Words     SortedSet Time  
Breath of Beelzebug         45 KB   		7532			  2468	   		  .0345 s			
Derelict			 		1.82x   	   1.83x			  4358	   		    1.68x			
The Story Tellers’ Magazine 2.06x  		   1.88x              7253     		    1.04x			  
English Cathedrals          3.47x 		   3.67x             14548     		    1.49x			  

Title	   			 		Size	 Total Words	Distinct Words     UnsortedSet Time  
Breath of Beelzebug         45 KB   		7532			  2468	   		 	.0838 s  	  
Derelict			 		1.82x   	   1.83x			  4358	   		   	  1.45x         
The Story Tellers’ Magazine 2.06x  		   1.88x              7253     		      2.48x         
English Cathedrals          3.47x 		   3.67x             14548     		      4.28x         

Title	   			 		Size	 Total Words	Distinct Words       HashSet Time  
Breath of Beelzebug         45 KB   		7532			  2468	   		   .0107 s	    
Derelict			 		1.82x   	   1.83x			  4358	   		     1.44x      	 
The Story Tellers’ Magazine 2.06x  		   1.88x              7253     		     1.85x         
English Cathedrals          3.47x 		   3.67x             14548     		     2.68x        

Title	   			 		Size	 Total Words	Distinct Words       TreeSet Time
Breath of Beelzebug         45 KB   		7532			  2468	   		  .0147 s	
Derelict			 		1.82x   	   1.83x			  4358	   		    1.40x
The Story Tellers’ Magazine 2.06x  		   1.88x              7253     		    1.57x
English Cathedrals          3.47x 		   3.67x             14548     		    2.79x
*/

// Big(O) for processText SortedSet should be O(N*M) because its iterating through every word in the file(N iterations), and for each iteration, its calling sortedSet add method, which is O(N) as well. 
// Big(O) for processText UnsortedSet should also be O(N*M), because its iterating through every word in the file, and for each iteration, its called unsortedSet's add method, which is O(N) as well.
// Big(O) for processText HashSet should also be O(N) , knowing that its iterating through every word in the file(N iterations), with HashSet add method O(1)
// Big(O) for processText TreeSet seems to be O(NlogM), knowing that its iterating through every word in the file(N iterations), with TreeSet add method being O(logN).

// Big(O) of sortedSet add method is O(N)
// Big(O) of UnsortedSet add method is O(N)
// Big(O) of HashSet add method seems to be O(1).
// Big(O) of TreeSet add method seems to be O(logN).

// Difference between HashSet and TreeSet when printing out contents: TreeSet guarantees printed in order, HashSet does not 
public class SetTester {

    public static void main(String[] args) {
    	int testNumber=0;
        ISet<String> s1 = new UnsortedSet<>();
        ISet<String> s2= new SortedSet<>();
        s1.add("B");
        String expected= "(B)";
        // add methods
        showTestResults(s1.toString().equals(expected), ++testNumber, "add Unsorted");
        s2.add("B");
        s2.add("A");
        s2.add("B");
        expected= "(A, B)";
        showTestResults(s2.toString().equals(expected), ++testNumber, "add Sorted");
        //addAll methods
        s1.addAll(s2);
        expected= "(B, A)";
        showTestResults(s1.toString().equals(expected), ++testNumber, "addAll Unsorted");
        s2.addAll(s1);
        expected= "(A, B)";
        showTestResults(s2.toString().equals(expected), ++testNumber, "addAll Sorted");
        //clear tests
        s1.clear();
        expected= "()";
        showTestResults(s1.toString().equals(expected), ++testNumber, "clear Unsorted");
        s2.clear();
        expected= "()";
        showTestResults(s2.toString().equals(expected), ++testNumber, "clear Sorted");
        //contains tests
        s1.add("P");
        s1.add("O");
        showTestResults(!s1.contains("PO"), ++testNumber, "contains Unsorted");
        s2.add("M");
        s2.add("A");
        s2.add("M");
        s2.add("P");
        showTestResults(s2.contains("P"), ++testNumber, "contains Sorted");
        //containsAll tests
        ISet<String> s3= new UnsortedSet<>();
        s3.add("P");
        showTestResults(s1.containsAll(s3), ++testNumber, "containsAll Unsorted");
        s3.add("Z");
        showTestResults(!s2.containsAll(s3), ++testNumber, "containsAll Sorted");
        
        //constructor SortedSet
        ISet<String> s4= new SortedSet<>(s1);
        expected="(O, P)";
        showTestResults(s4.toString().equals(expected), ++testNumber, "constructor Sorted");
        
        //difference tests
        expected= "(O)";
        s1= s1.difference(s3);
        showTestResults(s1.toString().equals(expected), ++testNumber, "difference Unsorted");
        expected= "(A, M)";
        s2= s2.difference(s3);
        showTestResults(s2.toString().equals(expected), ++testNumber, "difference Sorted");
        //equals test
        s3.clear();
        s1.add("L");
        s3.add("L");
        s3.add("P");
        showTestResults(!s1.equals(s3), ++testNumber, "equals Unsorted");
        s3.clear();
        s3.addAll(s2);
        showTestResults(s2.equals(s3), ++testNumber, "equals Sorted");
        
        //intersection test
        s1= s1.intersection(s2);
        expected= "()";
        showTestResults(s1.toString().equals(expected), ++testNumber, "intersection Unsorted");
        s2= s2.intersection(s1);
        expected= "()";
        showTestResults(s2.toString().equals(expected), ++testNumber, "intersection Sorted");
        
        //min tests
        SortedSet<String> s5= new SortedSet<String>();
        s5.add("O");
        s5.add("A");
        expected= "A";
        showTestResults(s5.min().toString().equals(expected), ++testNumber, "min Sorted");
        expected= "O";
        showTestResults(s5.max().toString().equals(expected), ++testNumber, "max Sorted");
        //max tests
        //iterator tests
        Iterator<String> iter1= s1.iterator();
        int count=0;
        while(iter1.hasNext()) {
        	iter1.next();
        	count++;
        }
        showTestResults(s1.size()==count, ++testNumber, "iterator Unsorted");
        iter1= s2.iterator();
        count=0;
        while(iter1.hasNext()) {
        	iter1.next();
        	count++;
        }
        showTestResults(s2.size()==count, ++testNumber, "iterator Sorted");	
        //remove tests
        s1.clear();
        s1.add("PO");
        s1.add("P");
        s1.add("O");
        s1.remove("P");
        expected= "(PO, O)";
        showTestResults(s1.toString().equals(expected), ++testNumber, "remove AbstractSet");
        
        //size tests
        int expect= 2;
        showTestResults(s1.size()== expect, ++testNumber, "size Unsorted");
        s2.clear();
        s2.add("O");
        s2.add("O");
        s2.add("O");
        s2.add("O");
        expect=1;
        showTestResults(s2.size()== expect, ++testNumber, "size Sorted");
        
        //union tests
        expected=s1.toString();
        s1= s1.union(s2);
      
        showTestResults(s1.toString().equals(expected), ++testNumber, "union UnsortedSet");
        
        s3.clear();
        s3.add("A");
        s3.add("B");
        s3.add("O");
        expected= "(A, B, O)";
        s2= s2.union(s3);
        showTestResults(s2.toString().equals(expected), ++testNumber, "union SortedSet");
        
        
        
        
       
//         CS314 Students. Uncomment this section when ready to
//         run your experiments
         try {
         UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
         }
         catch(Exception e) {
         System.out.println("Unable to change look and feel");
         }
         Scanner sc = new Scanner(System.in);
         String response = "";
         do {
         largeTest();
         System.out.print("Another file? Enter y to do another file: ");
         response = sc.next();
         } while( response != null && response.length() > 0
         && response.substring(0,1).equalsIgnoreCase("y") );
//
    }

    // print out results of test
    private static void showTestResults(boolean passed, int testNumber, String testDescription) {
        if (passed) {
            System.out.print("Passed test ");
        } else {
            System.out.print("Failed test ");
        }
        System.out.println(testNumber + ": " + testDescription);
    }

    /*
     * Method asks user for file and compares run times to add words from file
     * to various sets, including CS314 UnsortedSet and SortedSet and Java's
     * TreeSet and HashSet.
     */
    private static void largeTest() {
        System.out.println();
        System.out
                .println("Opening Window to select file. You may have to minimize other windows.");
        String text = convertFileToString();
        Scanner keyboard = new Scanner(System.in);
        System.out.println();
        System.out.println("***** CS314 SortedSet: *****");
        processTextCS314Sets(new SortedSet<String>(), text, keyboard);
        System.out.println("****** CS314 UnsortedSet: *****");
        processTextCS314Sets(new UnsortedSet<String>(), text, keyboard);
        System.out.println("***** Java HashSet ******");
        processTextJavaSets(new HashSet<String>(), text, keyboard);
        System.out.println("***** Java TreeSet ******");
        processTextJavaSets(new TreeSet<String>(), text, keyboard);
        keyboard.close();
    }

    /*
     * pre: set != null, text != null Method to add all words in text to the
     * given set. Words are delimited by white space. This version for CS314
     * sets.
     */
    private static void processTextCS314Sets(ISet<String> set, String text, Scanner keyboard) {
        Stopwatch s = new Stopwatch();
        Scanner sc = new Scanner(text);
        int totalWords = 0;
        s.start();
        while (sc.hasNext()) {
            totalWords++;
            set.add(sc.next());
        }
        s.stop();

        showResultsAndWords(set, s, totalWords, set.size(), keyboard);
    }

    /*
     * pre: set != null, text != null Method to add all words in text to the
     * given set. Words are delimited by white space. This version for Java
     * Sets.
     */
    private static void processTextJavaSets(Set<String> set, String text,
            Scanner keyboard) {
        Stopwatch s = new Stopwatch();
        Scanner sc = new Scanner(text);
        int totalWords = 0;
        s.start();
        while (sc.hasNext()) {
            totalWords++;
            set.add(sc.next());
        }
        s.stop();
        sc.close();

        showResultsAndWords(set, s, totalWords, set.size(), keyboard);
    }

    /*
     * Show results of add words to given set.
     */
    private static <E> void showResultsAndWords(Iterable<E> set, Stopwatch s, int totalWords,
            int setSize, Scanner keyboard) {

        System.out.println("Time to add the elements in the text to this set: " + s.toString());
        System.out.println("Total number of words in text including duplicates: " + totalWords);
        System.out.println("Number of distinct words in this text " + setSize);

        System.out.print("Enter y to see the contents of this set: ");
        String response = keyboard.next();

        if (response != null && response.length() > 0
                && response.substring(0, 1).equalsIgnoreCase("y")) {
            for (Object o : set)
                System.out.println(o);
        }
        System.out.println();
    }

    /*
     * Ask user to pick a file via a file choosing window and convert that file
     * to a String. Since we are evalutatin the file with many sets convert to
     * string once instead of reading through file multiple times.
     */
    private static String convertFileToString() {
        // create a GUI window to pick the text to evaluate
        JFileChooser chooser = new JFileChooser(".");
        StringBuilder text = new StringBuilder();
        int retval = chooser.showOpenDialog(null);

        chooser.grabFocus();

        // read in the file
        if (retval == JFileChooser.APPROVE_OPTION) {
            File source = chooser.getSelectedFile();
            try {
                Scanner s = new Scanner(new FileReader(source));

                while (s.hasNextLine()) {
                    text.append(s.nextLine());
                    text.append(" ");
                }

                s.close();
            } catch (IOException e) {
                System.out.println("An error occured while trying to read from the file: " + e);
            }
        }

        return text.toString();
    }
}