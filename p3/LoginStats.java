package project3;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.Date;

/**
 * This class is the program performing the login and logout actions
 * The program is interactive
 * referred to the ColorConverter class for the overall structure of the class
 * 
 * @author Thales Gao
 *
 */
public class LoginStats {
     /**
      * the main() method of this program
      * 
      * @param args array of Strings input on the command line by the user
      */
     public static void main(String[] args) {
          //verify that the user has input a command line argument
          if (args.length == 0 ) {
               System.err.println("Usage Error: the program expects file name as an argument.\n");
               System.exit(1);
          }

          //verify that command line argument contains an existing file and can be opened
          File testFile = new File(args[0]);
          if (!testFile.exists()) {
               System.err.println("Error: the file " + testFile.getAbsolutePath() + " does not exist.\n");
               System.exit(1);
          }
          if (!testFile.canRead()) {
               System.err.println("Error: the file " + testFile.getAbsolutePath() + " cannot be opened.\n");
               System.exit(1);
          }

          //open the file and start reading
          Scanner fileScanner = null;

          try {
               fileScanner = new Scanner (testFile) ;
          } catch (FileNotFoundException e) {
               System.err.println("Error: the file " + testFile.getAbsolutePath() + " cannot be found.\n");
               System.exit(1);
          }

          //read the content of the file and save the data in a list of Records
          RecordList list = new RecordList();
          String line = null;
          //Scanner parseLine = null;
          //each term passed in order
          int term = 0;
          boolean loginLogout = false;
          String username = null;
          long inputDate = 0;
          Record current = null;
          while (fileScanner.hasNextLine()) {
               //Splitting each line of the file to three component 
               String [] inputSplit = fileScanner.nextLine().split(" ");
               //terminal number
               term = Integer.parseInt(inputSplit[0]);
               //assigning terminal numbers and isLogin values
               if (term > 0) {
                    loginLogout = true;
               } else {
                    term = Math.abs(term);
                    loginLogout = false;
               }
               //assigning date values
               Date dateValue = new Date(Long.parseLong(inputSplit[1]));
               //assigning username values
               username = inputSplit[2];
               //creating new Record objects and adding to the list
               current = new Record(term, loginLogout, username, dateValue);
               list.add(current);
          }
          


          //interactive mode
          System.out.println("Welcome to Login Stats!\n");
          System.out.println("Available commands:");
          System.out.println("  first USERNAME" + "   -   " + "retrieves first login session for the USER");
          System.out.println("  last USERNAME " + "   -   " + "retrieves last login session for the USER");
          System.out.println("  all USERNAME  " + "   -   " + "retrieves all login sessions for the USER");
          System.out.println("  total USERNAME" + "   -   " + "retrieves total login duration for the USER");
          System.out.println("  quit          " + "   -   " + "terminates this program\n");

          //scanner to store user input
          Scanner userInput  = new Scanner (System.in);
          String userInputLine = "";

          do {
               //get value of from the user
               userInputLine = userInput.nextLine().trim();
               //splitting into an array of String, with the first component being first/last, second being the username
               String[] userString = userInputLine.split(" ");
               String outString = "";
               if (!userString[0].equalsIgnoreCase("quit")) {
                    if (userString.length != 2){
                         //to ensure there's no runtime error in the following codes
                         System.out.println("Error: this is not a valid command. Try again.\n");
                    } else if (userString[0].equalsIgnoreCase("first")){
                         //search for first session, handling exceptions
                         try {
                              outString = list.getFirstSession(userString[1]).toString();
                              System.out.println(outString);
                         } catch (NoSuchElementException ex) {
                              System.out.println("No user matching " + userString[1] + " found\n");
                              continue;
                         } catch (IllegalArgumentException exx) {
                              System.out.println("Invalid username; username cannot be null or empty.\n");
                              continue;
                         }
                    } else if (userString[0].equalsIgnoreCase("last")) {
                         //search for last session
                         try {
                              outString = list.getLastSession(userString[1]).toString();
                              System.out.println(outString);
                         } catch (NoSuchElementException ex) {
                              System.out.println("No user matching " + userString[1] + " found\n");
                              continue;
                         } catch (IllegalArgumentException exx) {
                              System.out.println("Invalid username; username cannot be null or empty.\n");
                              continue;
                         }
                    } else if (userString[0].equalsIgnoreCase("all")) {
                         try {
                              SortedLinkedList<Session> outList = list.getAllSessions(userString[1]);
                              for (Session s : outList) {
                                   System.out.println(s.toString());
                                   System.out.println();
                              }
                         } catch (NoSuchElementException ex) {
                              System.out.println("No user matching " + userString[1] + " found\n");
                              continue;
                         } catch (IllegalArgumentException exx) {
                              System.out.println("Invalid username; username cannot be null or empty.\n");
                              continue;
                         }
                    } else if (userString[0].equalsIgnoreCase("total")) {
                         try {
                              outString = userString[1] + " , total duration " + list.getTotalTimeString(userString[1]);
                              System.out.println(outString);
                         } catch (NoSuchElementException ex) {
                              System.out.println("No user matching " + userString[1] + " found\n");
                              continue;
                         } catch (IllegalArgumentException exx) {
                              System.out.println("Invalid username; username cannot be null or empty.\n");
                              continue;
                         }
                    } else {
                              //error otherwise
                              System.out.println("Error: this is not a valid command. Try again.\n");
                         }

               }
          } while (!userInputLine.equalsIgnoreCase("quit"));

          userInput.close();
     }
}
