package com.sicknasty.presentation;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.sicknasty.R;
import com.sicknasty.business.AccessUsers;
import com.sicknasty.objects.User;

public class UserAccountActivity extends AppCompatActivity {
    AccessUsers users=new AccessUsers();
    SharedPreferences preferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_account);

        preferences = getSharedPreferences("MY_PREFS",MODE_PRIVATE);

        final EditText username=findViewById(R.id.updateUsername);
        final EditText password=findViewById(R.id.updatePassword);
        Button update = findViewById(R.id.updateInfo);
        CheckBox showPass = findViewById(R.id.passwordShow);

        final String oldUsername = preferences.getString("username",null);      //retrieve old data
        final String oldPassword = preferences.getString("password",null);

        username.setText(oldUsername);      //this allows user to see their oldUsername
        password.setText(oldPassword);      //and oldPassword

        Button logout=findViewById(R.id.logout);

        //this is used to show user their password
        showPass.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
                else {
                    password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String newPass = username.getText().toString();
                String newUsername = password.getText().toString();
                String message="An unexpected error has occurred";
                try {
                    User user = users.getUser(oldUsername);     //find user with oldUsername
                    String updatedUsername = oldUsername;       //if it gets updates
                    if(!newUsername.equals(oldUsername)) {      //check if they have entered a different username than their currUsername
                        users.updateUsername(user, newUsername);    //if yes,try to update it

                        //update shared preferences data
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.remove("username");
                        editor.putString("username", newUsername);
                        editor.apply();
                        updatedUsername = newUsername;          //update it with new username
                    }
                    users.updateUserPassword(updatedUsername,newPass);      //try to update password
                    message="Username and Password updated successfully";
                } catch (Exception e) {
                    message = e.getMessage();
                } finally {
                    Toast.makeText(UserAccountActivity.this, message, Toast.LENGTH_SHORT).show();
                }
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences.Editor editor = preferences.edit();
                editor.remove("username");
                editor.remove("password");
                editor.remove("isLogin");
                editor.apply();

                Intent newIntent = new Intent(UserAccountActivity.this,LoginActivity.class);
                startActivity(newIntent);
                finish();
            }
        });
    }
    @Override
    public void onBackPressed() {
        goToHome();
    }

    private void goToHome(){
        Intent intent = new Intent(UserAccountActivity.this, LoggedUserPageActivity.class);
        startActivity(intent);
        finish();
    }
}
