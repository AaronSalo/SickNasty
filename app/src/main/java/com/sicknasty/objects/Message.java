/*
    Object to hold 1 single message
    stored in chat in array list
 */


package com.sicknasty.objects;
import java.util.ArrayList;


public class Message {

    private String msg;
    private long timeSent;
    private boolean seen = false;
    private User messenger;
    private User receiver;
    private ArrayList<User> group;


    private final int MAX_LENGTH = 255;
    private final int MIN_LENGTH = 1;


    /*
        for private messages constructor, gets current time, message from user, and the users involved
     */
    public Message(String msg, User messenger, User receiver){     //need to add exception for max and min length here

        this.msg = msg;
        timeSent = System.currentTimeMillis();
        this.messenger = messenger;
        this.receiver = receiver;

    }

    /*

     */
    public Message(String msg, User messenger, ArrayList<User> group){     //need to add exception for max and min length here

        this.msg = msg;
        timeSent = System.currentTimeMillis();
        this.messenger = messenger;
        this.group = group;

    }

    public void viewed(){

        seen = true;

    }

    public String getMsg(){
        return msg;
    }

    public User getMessenger(){
        return messenger;
    }

    public User getReceiver(){
        return receiver;
    }

    public long getTimeSent() {
        return timeSent;
    }
}
