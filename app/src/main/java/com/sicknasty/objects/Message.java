/*
    Object to hold 1 single message
    stored in chat in array list
 */


package com.sicknasty.objects;
import com.sicknasty.objects.Exceptions.MessageException;

import java.util.ArrayList;


public class Message {

    private String msg;         //message to be sent

    private long timeSent;      //time that message was sent at

    private User messenger;         //person message sent from
    private User receiver;          //person message sent to

    private ArrayList<User> group;  //if its a group, group messsage sent to

    public Message(String msg, User messenger, User receiver) throws MessageException {     //makes a new messages for a private chat
        if (msg.length() <= Constants.MESSAGE_MAX_CONTENT_LENGTH) {
            if (msg.length() >= Constants.MESSAGE_MIN_CONTENT_LENGTH) {
                this.msg = msg;
                timeSent = System.currentTimeMillis();
                this.messenger = messenger;
                this.receiver = receiver;
            } else {
                throw new MessageException("The message must be larger than "+ Constants.MESSAGE_MIN_CONTENT_LENGTH +" character");
            }
        } else {
            throw new MessageException("The message cannot be larger than "+ Constants.MESSAGE_MAX_CONTENT_LENGTH+ " characters");
        }
    }




    public Message(String msg, User messenger, ArrayList<User> group) throws MessageException{  //creates a new message for a group

        if (msg.length() <= Constants.MESSAGE_MAX_CONTENT_LENGTH) {
            if (msg.length() >= Constants.MESSAGE_MIN_CONTENT_LENGTH) {
                this.msg = msg;
                timeSent = System.currentTimeMillis();
                this.messenger = messenger;
                this.group = group;
            } else {
                throw new MessageException("The message must be larger than " + Constants.MESSAGE_MIN_CONTENT_LENGTH + " character");
            }
        } else {
            throw new MessageException("The message cannot be larger than " + Constants.MESSAGE_MAX_CONTENT_LENGTH + " characters");
        }

    }

    public String getMsg(){
        return msg;
    }

    public User getMessenger(){             //returning person sending message
        return messenger;
    }

    public User getReceiver(){       //returning person receiving message
        return receiver;
    }

    public ArrayList<User> getGroup(){       //returning group receiving message
        return group;
    }

    public long getTimeSent() {             //to be implemented, if we want to show what time the message was sent
        return timeSent;
    }

    public void setTimeSent(long timeSent) {        //need exception for correct time here
        this.timeSent = timeSent;
    }
}
