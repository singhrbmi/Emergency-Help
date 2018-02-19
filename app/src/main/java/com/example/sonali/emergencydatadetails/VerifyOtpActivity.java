package com.example.sonali.emergencydatadetails;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class VerifyOtpActivity extends AppCompatActivity {
    TextView id_et_submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_otp);
        id_et_submit=findViewById(R.id.id_et_submit);
        id_et_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(VerifyOtpActivity.this,DashBoardActivity.class);
                startActivity(intent);
            }
        });
    }
}
