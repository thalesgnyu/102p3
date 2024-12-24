package project3;

import java.util.Date;
/**
 * The Record class in which each object corresponds to the record of an instance of login
 * 
 * @author Thales Gao
 */
public class Record implements Comparable<Record> {
    //instance variables for terminals
    private int terminal;
    private Date time;
    private String username;
    private boolean login;

    /**
     * Constructor initializes a new Record with terminal as a positive integer 
     * login as a boolean username as a string
     * and a date object representing the time the user logged in or out
     * @param terminal The terminal number
     * @param login The login Status
     * @param username The username
     * @param time The login date
     * @throws IllegalArgumentException when the constructor is called with an invalid terminal number
     */
    public Record(int terminal, boolean login, String username, Date time) throws IllegalArgumentException {
        if (terminal < 0)
            throw new IllegalArgumentException ("Invalid terminal value: " + "Must be a positive integer.");
            
        this.terminal = terminal;
        this.username = username;
        this.time = time;
        this.login = login;
    }

    /**
     * returns the terminal number
     * @return terminal The terminal number
     */
    public int getTerminal() {
        return terminal;
    }

    /**
     * return the status of login
     * @return login The status of login
     */
    public boolean isLogin() {
        return login;
    }

    /**
     * return the status of logout
     * @return !login The status of logout
     */
    public boolean isLogout() {
        return !login;
    }

    /**
     * return the username as a string
     * @return username The imput username
     */
    public String getUsername() {
        return username;
    }

    /**
     * returns the date 
     * @return time The time The login/logout time
     */
    public Date getTime() {
        return time;
    }

    /**
     * Compares this record with the specified record for order
     * @param r The record object to be compared
     * @return a negative integer, zero,
     * or a positive integer as this record object is less than, equal to,
     * or greater than the specified record object
     */
    public int compareTo(Record r) {
        if (this.equals(r)) {
            return 0;
        }
        return (int) (this.getTime().getTime() - r.getTime().getTime());
    }

    /**
     * Compares two record objects for equality
     * @param r The record object to be compared
     * @return if they have the same username, terminal, record type, and time
     */
    public boolean equals(Record r) {
        if (r == null) {
            return false;
        }
        if (r == this) {
            return true;
        }
        return r.isLogin() == this.isLogin() && r.getTerminal() == this.getTerminal() && r.getTime().equals(this.getTime()) && r.getUsername().equals(this.getUsername());
    }
}
