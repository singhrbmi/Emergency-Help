package hacker.l.emergency_help.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import hacker.l.emergency_help.R;

public class SignupActivity extends AppCompatActivity {
    Button id_bt_signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        id_bt_signup=findViewById(R.id.id_bt_signup);
        id_bt_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUpFunction();
            }
        });
    }

    private void signUpFunction() {


    }
}
