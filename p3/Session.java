package project3;

import java.util.Date;

/**
 * The Session class represent a single login session, with the corresponding login and logout record
 * 
 * @author Thales Gao
 */
 public class Session implements Comparable<Session>{
     //instance variables 
     private long duration;
     private Record login;
     private Record logout;

     /**
      * Constructor initializes a new session with two Record object parameters
      * one for login and one for logout each
      * @param login The Record object representing the login
      * @param logout The Record object representing the logout
      * @throws IllegalArgumentException if the login or logout parameters are invalid
      */
     public Session (Record login, Record logout) throws IllegalArgumentException {
          if (login == null){
               //login cannot be null
               throw new IllegalArgumentException ("Login cannot be null");
          } else if (!login.isLogin()){
               //login must be login
               throw new IllegalArgumentException ("Login is not an instance of login");
          } else {
               this.login = login;
               this.logout = logout;
               if (logout == null) {
                    //set duration to -1 as required 
                    duration = -1;
               } else if (!logout.isLogout()) {
                    //logout must be logout
                    throw new IllegalArgumentException ("Logout is not an instance of logout");
               } else {
                    //other exceptions
                    duration = logout.getTime().getTime() - login.getTime().getTime();
                    if (!login.getUsername().equals(logout.getUsername())) {
                         throw new IllegalArgumentException ("Login and logout are from different users");
                    } else if (duration <= 0) {
                         throw new IllegalArgumentException ("Login cannot be after logout");
                    } else if (login.getTerminal() != logout.getTerminal()) {
                         throw new IllegalArgumentException ("Login terminal does not match logout terminal");
                    }
               }
          }
     }

     /**
      * Returns the terminal number with which the user used to login
      * @return The terminal number
      */
     public int getTerminal() {
          return login.getTerminal();
     }

     /**
      * Returns the time of login
      * @return The time of login
      */
     public Date getLoginTime() {
          return login.getTime();
     }

     /**
      * Returns the time of logout
      * Returns null if still in session
      * @return The time of logout
      */
     public Date getLogoutTime() {
          if (logout == null)
               return null;

          return logout.getTime();
     }

     /**
      * Returns the login username as a String
      * @return The username
      */
     public String getUsername() {
          return login.getUsername();
     }

     /**
      * Returns the number of milliseconds elapsed
      * between the login time and logout time
      * Returns -1 if the session is still active
      * @return duration the duration of the session
      */
     public long getDuration() {
          return duration;
     }

     /**
      * Helper methods converts duration to a formatted String
      * consulted with tutor for ideas
      * @return a string representation of days, hours, and seconds
      */
     public String durationStr() {
          long seconds = duration / 1000;
          long minutes = seconds / 60;
          //if less than 60, the seconds should stay the same
          seconds %= 60;
          long hours = minutes / 60;
          //similar as above
          minutes %= 60;
          long days = hours / 24;
          hours %= 24;
          return days + " days, " + hours + " hours, " + minutes + " minutes, " + seconds + " seconds";
     }

    /**
     * Compares this session with the specified session for order
     * @param s The session object to be compared
     * @return a negative integer, zero,
     * or a positive integer as this session object is less than, equal to,
     * or greater than the specified session object
     */
    public int compareTo(Session s) {
        return this.login.compareTo(s.login);
    }
     /**
      * Returns a formatted string including the username, terminal number, duration
      * as well as login and logout times
      * Returns that the session is active if the user has not logged out
      * @return a formatted string of relevant information
      */
     @Override
     public String toString() {
          if (logout == null)
               return this.getUsername() + ", terminal " + this.getTerminal() + ", duration " + "active session" + "\n logged in: " + this.getLoginTime().toString() + "\n logged out: " + "still logged in";
          return this.getUsername() + ", terminal " + this.getTerminal() + ", duration " + this.durationStr() + "\n logged in: " + this.getLoginTime().toString() + "\n logged out: " + this.getLogoutTime().toString();
     }

    /**
     * Compares two session objects for equality
     * @param s The session to be compared
     * @return if the sessions have the same login object
     */
     public boolean equals(Session s) {
         if (s == null) {
             return false;
         }
         if (s == this) {
             return true;
         }
         return this.login.equals(s.login) && this.logout.equals(s.logout);
     }
 }
