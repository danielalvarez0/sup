package org.server;

import java.io.PrintWriter;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 
 * @author sjarvis
 * 
 * Universally accessible contacts list (safe to edit from any thread of execution)
 *
 */
public class Contacts {
    
    private static Contacts instance = null;
    
    // map user names to their client sockets
    private ConcurrentHashMap<String, PrintWriter> userList = new ConcurrentHashMap<String, PrintWriter>();
    
    protected Contacts() {
        // Don't allow instantiation. Do nothing here.
    }
       
    // Use this to get an instance of this contacts object.
    public static Contacts getInstance() {
        if(instance == null) { 
            instance = new Contacts();
        }
        return instance;
    }
    
    // determine whether a certain contact name is currently logged on
    public Boolean hasContact(String name) {
        return userList.containsKey(name);
    }
    
    // return true if successfully added name, false otherwise
    public Boolean addContact(String name, PrintWriter pw) {
        if(hasContact(name)) {
            // cannot add duplicate
            return false;
        }
        userList.put(name, pw);
        return true;
    }
    
    // get the output stream
    public PrintWriter getContact(String name) throws Exception {
        if(! hasContact(name)) {
            // cannot get that contact
            throw new Exception();
        }
        return userList.get(name);
    }
    
    // remove a contact
    public void removeContact(String name) {
        userList.remove(name);
    }
}
