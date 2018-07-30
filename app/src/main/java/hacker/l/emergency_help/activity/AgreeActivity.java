package hacker.l.emergency_help.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RadioGroup;

import hacker.l.emergency_help.R;

public class AgreeActivity extends AppCompatActivity {
    RadioGroup radioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sharedPreferences = getSharedPreferences("token", MODE_PRIVATE);
        String toen = sharedPreferences.getString("token", null);
        if (toen != null) {
            startActivity(new Intent(AgreeActivity.this, SplashActivity.class));
            finish();
        } else {
            setContentView(R.layout.activity_agree);
            init();
        }

    }

    private void init() {
        final String token = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
        radioGroup = findViewById(R.id.radipGroup);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.rb_agree) {
                    SharedPreferences sharedPreferences = getSharedPreferences("token", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("token", token);
                    editor.apply();
                    startActivity(new Intent(AgreeActivity.this, SplashActivity.class));
                    finish();
                } else {
                    finish();
                }
            }
        });
    }
}
