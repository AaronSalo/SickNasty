package com.sicknasty.objects;

import com.sicknasty.objects.Exceptions.GroupChatMembersException;
import com.sicknasty.objects.Exceptions.SetChatNameException;

import java.util.ArrayList;

public class GroupChat extends Chat {


    private final int MAX_MEMBERS = 8;
    private final int MIN_MEMBERS = 3;
    private ArrayList<User> group;



    public GroupChat(String chatName, ArrayList<User> group) throws SetChatNameException, GroupChatMembersException {   //need exception for group being too small

        if(group.size()>= MIN_MEMBERS){
            if(group.size()<= MAX_MEMBERS){
                this.group = group;
                setChatName(chatName);      //sending to super chat method
        }else{
                throw new GroupChatMembersException("The group cannot excede " + MAX_MEMBERS + " members at a time");
            }
        }else{
            throw new GroupChatMembersException("There must be at least " + MIN_MEMBERS +" members in a group");
        }
    }


    public void addMessage(Message msg){            //msg might be null

        getMessages().add(msg);


    }

    public ArrayList<User> getGroup(){      //group might be empty
        return group;
    }


    public void addMember(User user) throws GroupChatMembersException{       //add exception for group being full
        if(group.size() <= MAX_MEMBERS) {
            group.add(user);
        }else{
            throw new GroupChatMembersException("The group cannot excede " + MAX_MEMBERS + " members at a time");
        }
    }











}
