package com.sicknasty.objects;


public class Password {

    private String hash;
    private String salt; //a new "salt" will be created for each user, just makes things more secure

    private int maxPassLength = 30;
    public Password(String password) {
        salt = generateSalt(); //generate the salt for this obj
        hash = hash(password, salt);

    }


    //this combines the password string and the salt and creates a hash
    private static String hash(String password, String salt) {
        return "";
    }//end of hash function

    public void changePassword(String newPass) {
        if(newPass.length() < maxPassLength) { //make sure the password isn't rediculously long
            hash = hash(newPass, salt); //hash the password and set it as the new password
        }
    }

    //todo
    private static String generateSalt(){

    }


    //if the stored hash equals the hash of the input, the password is correct
    public boolean checkPass(String input) {
        return hash.equals(hash(input, salt));
    }
}//end of class
