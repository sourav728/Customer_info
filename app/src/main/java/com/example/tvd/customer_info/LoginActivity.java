package com.example.tvd.customer_info;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tvd.customer_info.values.UserSession;

public class LoginActivity extends AppCompatActivity {
    Button login;
    TextView signup;
    EditText email,password;
    private static final String PREFER_NAME = "Reg";
    UserSession session;
    private SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow(); // in Activity's onCreate() for instance
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }

        login = (Button) findViewById(R.id.login_btn);
        email = (EditText) findViewById(R.id.edit_email);
        password = (EditText) findViewById(R.id.edit_password);
        signup = (TextView) findViewById(R.id.signup);

        session = new UserSession(getApplicationContext());
        Toast.makeText(this, "UserLoginStatus"+session.isUserLoggedIn(), Toast.LENGTH_SHORT).show();

        sharedPreferences = getSharedPreferences(PREFER_NAME, Context.MODE_PRIVATE);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String get_email = email.getText().toString();
                String get_password = password.getText().toString();

                if (get_email.trim().length() > 0 && get_password.trim().length()>0) {
                    String uEmail = null;
                    String uPassword = null;
                    if (sharedPreferences.contains("Email")) {
                        uEmail = sharedPreferences.getString("Email", "");
                    }
                    if (sharedPreferences.contains("Password")) {
                        uPassword = sharedPreferences.getString("Password", "");
                    }
                    // Object uName = null;
                    // Object uEmail = null;
                    if (get_email.equals(uEmail) && get_password.equals(uPassword)) {
                        session.createUserLoginSession(uEmail, uPassword);
                        // Starting MainActivity
                        Intent i = new Intent(getApplicationContext(), MainActivity.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                        // Add new Flag to start new Activity
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(i);

                        //finish();
                    } else {
                        // username / password doesn't match&
                        Toast.makeText(getApplicationContext(),
                                "Username/Password is incorrect",
                                Toast.LENGTH_LONG).show();
                    }
                }else {
                    Toast.makeText(LoginActivity.this, "Please Enter Username and Password!!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,SignUpActivity.class);
                startActivity(intent);
            }
        });
    }
}
