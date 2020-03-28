package com.sicknasty.objects;

import androidx.annotation.NonNull;

import com.sicknasty.objects.Exceptions.ChangeNameException;
import com.sicknasty.objects.Exceptions.SetChatNameException;

import java.util.ArrayList;


public abstract class Chat {

    private String chatName;            //name for the chat, default to other users name if private message
    private ArrayList<Message> messages;        //arraylist for messages
    private final int MAX_CHATNAME = 15;
    private final int MIN_CHATNAME = 1;


    public Chat(){                      //starts new chat
        messages = new ArrayList<>();
    }

    public ArrayList<Message> getMessages() {
        return messages;
    }

    public String getChatName() {
        return chatName;
    }

    public void setChatName(String chatName) throws SetChatNameException {

        if(chatName.length() < MAX_CHATNAME ){
            if(chatName.length() > MIN_CHATNAME){
                this.chatName = chatName;
            }else{
                throw new SetChatNameException("The chat name must be larger than" + MIN_CHATNAME);
            }
        }else{
            throw new SetChatNameException("The chat name must be shorter than " + MAX_CHATNAME);
        }
    }







}
