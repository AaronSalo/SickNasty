/*
    chat between 2 people, stores arraylist of messages to display
 */
package com.sicknasty.objects;

public class PrivateChat extends Chat {

        private User messenger;     //users involved in this private chat
        private User receiver;


        public PrivateChat(User user1, User user2){

            this.messenger = user1;
            this.receiver = user2;

        }


        /*
            returns opposite users name to display above the messenger, other persons name dpending on whos calling it
            over rides method from super class

         */
        public String getChatName(){        //what if the users dont call get name

            if(this.equals(messenger)){

                return receiver.getName();

            }else{
                return messenger.getName();
            }
        }

        public void addMessage(Message msg){        //msg might be null

            getMessages().add(msg);

        }

}
