package com.sicknasty.objects;


import java.io.IOException;
import java.security.MessageDigest;
import java.util.Base64;
import java.security.SecureRandom;

public class Password {

    private String hash;
    private String salt; //a new "salt" will be created for each user, just makes things more secure

    private final int MAX_PASS_LENGTH = 40;
    private final int MIN_PASS_LENGTH = 7;


    public Password(String password){
        if(isValidNewPass(password)) { //is this password valid
            salt = generateSalt(); //generate the salt for this obj
            hash = hash(password, salt);
        } else {
            //throw new PasswordErrorException("Password is not valid"); //for iteration 2
        }
    }


    //this combines the password string and the salt and creates a hash
    private static String hash(String password, String salt) {
        try {
            // 64 byte hash, same length as the salt
            byte hash[] = new byte[64];

            // get the byte encoding of concat password and salt
            MessageDigest msg = MessageDigest.getInstance("SHA-512");
            msg.update((password + salt).getBytes());

            // digest the password + salt and put bytes into the array
            hash = msg.digest();

            // just like in generateSalt() we return text
            return Base64.getEncoder().withoutPadding().encodeToString(hash);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }//end of hash function

    public boolean changePassword(String newPass) {
        boolean success = false;
        if(newPass.length() < MAX_PASS_LENGTH) { //make sure the password isn't rediculously long
            hash = hash(newPass, salt); //hash the password and set it as the new password
            success = true;
        }
        return success;
    }

    private boolean isValidNewPass(String input) {
        boolean success = false;
        if(!input.contains(" ")){
            if((input.length() <= MAX_PASS_LENGTH) &&
                    (input.length() >= MIN_PASS_LENGTH)){
                success = true;
            }
        }
        return success;
    }

    private static String generateSalt(){
        try {
            // create a 64 byte salt
            byte salt[] = new byte[64];

            // start a new instance of the generator and populate the salt array with random bytes
            SecureRandom saltGenerator = SecureRandom.getInstance("SHA1PRNG");
            saltGenerator.nextBytes(salt);

            // encode the bytes as UTF-8 encoded text so that it can go into the database
            return Base64.getEncoder().withoutPadding().encodeToString(salt);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }

    //if the stored hash equals the hash of the input, the password is correct
    public boolean checkPass(String input) {
        return hash.equals(hash(input, salt));
    }
}//end of class

class PasswordErrorException extends Exception {

    public PasswordErrorException(String message) {
        super(message);
    }
}