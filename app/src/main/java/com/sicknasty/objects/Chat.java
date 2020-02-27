package com.sicknasty.objects;

import androidx.annotation.NonNull;

import java.util.ArrayList;


public abstract class Chat {

    private String chatName;            //name for the chat, default to other users name if private message
    private ArrayList<Message> messages;        //arraylist for messages


    public Chat(){
        messages = new ArrayList<>();
    }

    public ArrayList<Message> getMessages() {
        return messages;
    }

    public String getChatName() {
        return chatName;
    }

    public void setChatName(String chatName) {
        this.chatName = chatName;
    }
}
