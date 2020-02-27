/*
    chat between 2 people, stores arraylist of messages to display
 */
package com.sicknasty.objects;

public class PrivateChat extends Chat {

        private User user1;     //users involved in this private chat
        private User user2;


        public PrivateChat(User user1, User user2){

            this.user1 = user1;
            this.user2 = user2;

        }


        /*
            returns opposite users name to display above the messenger, other persons name depending on whos calling it
            over rides method from super class

         */
        public String getChatName(){        //what if the users dont call get name

            if(this.equals(user1)){

                return user2.getName();

            }else{
                return user1.getName();
            }
        }

        public void addMessage(Message msg){        //msg might be null

            getMessages().add(msg);

        }

}
