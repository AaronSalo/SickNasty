package com.sicknasty.objects;

//@project sicknasty
//@author aaron salo
//this password object handles all password related stuff
//All hashing functions and password validation is done here
public class Password {

    private String hash; //a hashed version of the password
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

    //todo -- lucas has a good idea on how to implement this so im leaving it to him
    private static String generateSalt(){
        return "";
    }


    //if the stored hash equals the hash of the input, the password is correct
    public boolean checkPass(String inputPassword) {
        return hash.equals(hash(inputPassword, salt));
    }
}//end of class
