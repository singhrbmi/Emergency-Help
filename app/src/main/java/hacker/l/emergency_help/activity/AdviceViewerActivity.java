package hacker.l.emergency_help.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import hacker.l.emergency_help.R;

public class AdviceViewerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advice_viewer);
        Intent intent = getIntent();
        TextView textView = findViewById(R.id.tv_advice);
        TextView tv_date = findViewById(R.id.tv_date);
        ImageView image = findViewById(R.id.image);
        textView.setText(intent.getStringExtra("advise"));
        tv_date.setText(intent.getStringExtra("date"));
        String url = intent.getStringExtra("image");
        if (url != null) {
            image.setImageURI(Uri.parse(url));
        } else {
            if (image != null) {
                image.setImageResource(R.drawable.logo);
            }
        }

    }
}
