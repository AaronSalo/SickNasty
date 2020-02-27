package com.sicknasty.objects;

import java.util.ArrayList;

public class GroupChat extends Chat {


    private final int MAX_MEMBERS = 8;
    private final int MIN_MEMBERS = 3;
    private ArrayList<User> group;



    public GroupChat(String chatName, ArrayList<User> group){   //need exception for group being too small

        if(group.size()>3){
            setChatName(chatName);
            this.group = group;
        }
    }


    public void addMessage(Message msg){            //msg might be null

        getMessages().add(msg);


    }

    public ArrayList<User> getGroup(){      //group might be empty
        return group;
    }

    public void addMember(User user){       //add exception for group being full
        if(group.size() <= MAX_MEMBERS) {
            group.add(user);
        }
    }











}
