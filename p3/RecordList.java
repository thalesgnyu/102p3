package project3;

import java.util.NoSuchElementException;
import java.util.ArrayList;

/**
 * This class is used to store all Record objects
 * It inherits the properties from Arraylist<Record> class
 * It allows the user to get the first or last session of a given username
 *
 * @author Thales Gao
 */
public class RecordList extends SortedLinkedList<Record> {

     /**
      * Returns the first login session of a specific user
      * i.e. earliest login time
      * @param user The user whose information is to be accessed
      * @return the first login session of a specific user
      * @throws NoSuchElementException where the specific user does not match any record
      * @throws IllegalArgumentException when the argument string is invalid
      */
     public Session getFirstSession(String user) throws NoSuchElementException, IllegalArgumentException {
          //The username is invalid if it is null or if it is empty
          if (user == null || user.isEmpty()) {
               throw new IllegalArgumentException("Invalid username.");
          }
          Record firstLogin = null;
          Record firstLogout = null;
          int i;
          int j;
          //Go through the list, find the first instance of the given username in a login record
          for (i = 0; i < this.size(); i++) {
               String loginUser = this.get(i).getUsername();
               if (loginUser != null && loginUser.equals(user) && this.get(i).isLogin()) { 
                    firstLogin = this.get(i);
                    break;
               }

          }
          if (firstLogin == null)
               throw new NoSuchElementException("The given user does not have a login record.");

          //Search for the corresponding logout record
          for (j = i; j < this.size(); j++) {
               String logoutUser = this.get(j).getUsername();
               if (logoutUser != null && logoutUser.equals(user) && this.get(j).isLogout() && firstLogin.getTerminal() == this.get(j).getTerminal()) {
                    firstLogout = this.get(j);
                    break;
               }
          }

          //try constructing a corresponding session, exception can only occur if the login is after logout
          try {
              return new Session(firstLogin, firstLogout);
          } catch (IllegalArgumentException ex) {
               throw new NoSuchElementException("list not in order");
          }
     }

     /**
      * Returns the last logout session of a specific user
      * i.e. latest logout time
      * @param user The user whose information is to be accessed
      * @return the last logout session of a specific user
      * @throws NoSuchElementException where the specific user does not match any record
      * @throws IllegalArgumentException when the argument string is invalid
      */
     public Session getLastSession(String user) throws NoSuchElementException, IllegalArgumentException {
          //The username is invalid if it is null or if it is empty
          if (user == null || user.isEmpty())
               throw new IllegalArgumentException("Invalid username");

          Record lastLogin = null;
          Record lastLogout = null;
          int i;
          int j;
          int mark = -1;
          //Go through the list, find the first instance of the given username in a login record
          for (i = 0; i < this.size(); i++) {
               String loginUser = this.get(i).getUsername();
               if (loginUser != null && loginUser.equals(user) && this.get(i).isLogin()) {
                    lastLogin = this.get(i);
                    mark = i;
               }
          }
          if (lastLogin == null)
               throw new NoSuchElementException("The given user does not have a login record.");

          //Search for the corresponding logout record
          for (j = mark; j < this.size(); j++) {
               String logoutUser = this.get(j).getUsername();
               if (logoutUser != null && logoutUser.equals(user) && this.get(j).isLogout() && lastLogin.getTerminal() == this.get(j).getTerminal()) {
                    lastLogout = this.get(j);
                    break;
               }
          }
          //try constructing a corresponding session 
          try {
              return new Session(lastLogin, lastLogout);
          } catch (IllegalArgumentException ex) {
               throw new NoSuchElementException("list not in order");
          }
     }

     /**
      * Provides the total login time of a specified user
      * @param user The user whose information is to be accessed
      * @return the total duration of the user
      * @throws NoSuchElementException where the specific user does not match any record
      * @throws IllegalArgumentException when the argument string is invalid
      */
     public long getTotalTime(String user) throws NoSuchElementException, IllegalArgumentException {
          if (user == null || user.isEmpty())
               throw new IllegalArgumentException("Invalid username");

          try {
               this.getFirstSession(user);
          } catch (NoSuchElementException ex) {
               throw new NoSuchElementException("The given user does not have a login record.");
          }
          long l = 0;
          SortedLinkedList<Session> sll = this.getAllSessions(user);
          for (int i = 0; i < sll.size(); i++) {
               if (sll.get(i).getDuration() > 0) {
                    l += sll.get(i).getDuration();
               }
          }
          return l;
     }

     /**
      * Generate a sorted linked list of all the session of a given user
      * @param user The user whose information is to be accessed
      * @return A sorted linked list of all the sessions of the user
      * @throws NoSuchElementException where the specific user does not match any record
      * @throws IllegalArgumentException when the argument string is invalid
      */
     public SortedLinkedList<Session> getAllSessions(String user) throws NoSuchElementException, IllegalArgumentException {
          if (user == null || user.isEmpty())
               throw new IllegalArgumentException("Invalid username");

          try {
               this.getFirstSession(user);
          } catch (NoSuchElementException ex) {
               throw new NoSuchElementException("The given user does not have a login record.");
          }
          SortedLinkedList<Session> sll = new SortedLinkedList<>();

          Record Login;
          Record Logout = null;
          int i;
          int j;

          for (i = 0; i < this.size(); i++) {
               String loginUser = this.get(i).getUsername();
               if (loginUser != null && loginUser.equals(user) && this.get(i).isLogin()) {
                    Login = this.get(i);
                    for (j = i; j < this.size(); j++) {
                         String logoutUser = this.get(j).getUsername();
                         if (logoutUser != null && logoutUser.equals(user) && this.get(j).isLogout() && Login.getTerminal() == this.get(j).getTerminal()) {
                              Logout = this.get(j);
                              break;
                         }
                         Logout = null;
                    }
                    sll.add(new Session(Login, Logout));
               }
          }
          return sll;
     }

     /**
      * A helper method used to generate duration in string format, borrowed from the one in Session class
      * @param user The user whose information is to be accessed
      * @return A formatted string of the total duration
      * @throws NoSuchElementException where the specific user does not match any record
      * @throws IllegalArgumentException when the argument string is invalid
      */
     public String getTotalTimeString(String user) throws NoSuchElementException, IllegalArgumentException {
          try {
               long seconds = this.getTotalTime(user) / 1000;
               long minutes = seconds / 60;
               //if less than 60, the seconds should stay the same
               seconds %= 60;
               long hours = minutes / 60;
               //similar as above
               minutes %= 60;
               long days = hours / 24;
               hours %= 24;
               return days + " days, " + hours + " hours, " + minutes + " minutes, " + seconds + " seconds";
          } catch (IllegalArgumentException e) {
               throw new IllegalArgumentException("Invalid username");
          } catch (NoSuchElementException e) {
               throw new NoSuchElementException("The given user does not have a login record.");
          }
     }

}
