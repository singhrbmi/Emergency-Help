package hacker.l.emergency_help.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import hacker.l.emergency_help.R;

public class AdviceViewerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advice_viewer);
        Intent intent = getIntent();
        TextView textView = findViewById(R.id.tv_advice);
        TextView tv_date = findViewById(R.id.tv_date);
        ImageView image = findViewById(R.id.tv_image);
        textView.setText(intent.getStringExtra("advise"));
        tv_date.setText(intent.getStringExtra("date"));
        String url = intent.getStringExtra("image");
        if (image != null) {
            if (url != null) {
                Picasso.with(AdviceViewerActivity.this).load(url).into(image);

            } else {
                Picasso.with(AdviceViewerActivity.this).load(R.drawable.logo).into(image);
            }
        }

    }
}
