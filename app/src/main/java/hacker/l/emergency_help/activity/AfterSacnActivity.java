package hacker.l.emergency_help.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import hacker.l.emergency_help.R;

public class AfterSacnActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_sacn);
        Intent intent = getIntent();
        String data = intent.getStringExtra("data");
        TextView textView = findViewById(R.id.show);
        String[] newStr = data.split(",");
        textView.setText(newStr[0] + "\n" + newStr[1] + "\n" + newStr[2] + "\n" + newStr[3] + "\n" + newStr[4] + "\n" + newStr[5] + "\n" + newStr[6] + "\n" + newStr[7] + "\n" + newStr[8]);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(AfterSacnActivity.this, DashBoardActivity.class));
    }
}
