package hacker.l.emergency_help.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.ClipboardManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import hacker.l.emergency_help.R;

public class AfterSacnActivity extends AppCompatActivity implements View.OnClickListener {
    TextView tv_name, tv_phone, tv_email, tv_address, tv_city, tv_pincode, tv_emergency, tv_emergency2, tv_emergency3, tv_show;
    LinearLayout mainLayout;
    ImageView sms, sms1, sms2, sms3, call, call1, call2, call3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_sacn);
        Intent intent = getIntent();
        String data = intent.getStringExtra("data");
        init(data);
    }

    private void init(String data) {
        tv_name = findViewById(R.id.tv_name);
        tv_email = findViewById(R.id.tv_email);
        tv_phone = findViewById(R.id.tv_phone);
        tv_emergency = findViewById(R.id.tv_emergency);
        tv_emergency2 = findViewById(R.id.tv_emergency2);
        tv_emergency3 = findViewById(R.id.tv_emergency3);
        tv_address = findViewById(R.id.tv_address);
        tv_city = findViewById(R.id.tv_city);
        tv_pincode = findViewById(R.id.tv_pincode);
        tv_show = findViewById(R.id.tv_show);
        mainLayout = findViewById(R.id.mainLayout);
        sms = findViewById(R.id.sms);
        sms1 = findViewById(R.id.sms1);
        sms2 = findViewById(R.id.sms2);
        sms3 = findViewById(R.id.sms3);
        call = findViewById(R.id.call);
        call1 = findViewById(R.id.call1);
        call2 = findViewById(R.id.call2);
        call3 = findViewById(R.id.call3);
        sms.setOnClickListener(this);
        sms1.setOnClickListener(this);
        sms2.setOnClickListener(this);
        sms3.setOnClickListener(this);
        call.setOnClickListener(this);
        call1.setOnClickListener(this);
        call2.setOnClickListener(this);
        call3.setOnClickListener(this);
        String[] newStr = data.split(",");
        if (data.contains(",")) {
            tv_name.setText(newStr[0]);
            tv_phone.setText(newStr[1]);
            tv_email.setText(newStr[2]);
            tv_address.setText(newStr[3]);
            tv_city.setText(newStr[4]);
            tv_pincode.setText(newStr[5]);
            tv_emergency.setText(newStr[6]);
            tv_emergency2.setText(newStr[7]);
            tv_emergency3.setText(newStr[8]);
        } else {
            mainLayout.setVisibility(View.GONE);
            tv_show.setVisibility(View.VISIBLE);
            tv_show.setText(data);
            tv_show.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ClipboardManager cm = (ClipboardManager) AfterSacnActivity.this.getSystemService(Context.CLIPBOARD_SERVICE);
                    cm.setText(tv_show.getText().toString());
                    Toast.makeText(AfterSacnActivity.this, "Copied to clipboard", Toast.LENGTH_SHORT).show();
                }
            });
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(AfterSacnActivity.this, DashBoardActivity.class));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sms:
                sendSmsFun(tv_phone.getText().toString());
                break;
            case R.id.sms1:
                sendSmsFun(tv_emergency.getText().toString());
                break;
            case R.id.sms2:
                sendSmsFun(tv_emergency2.getText().toString());
                break;
            case R.id.sms3:
                sendSmsFun(tv_emergency3.getText().toString());
                break;

            case R.id.call:
                sendCallFun(tv_phone.getText().toString());
                break;
            case R.id.call1:
                sendCallFun(tv_emergency.getText().toString());
                break;
            case R.id.call2:
                sendCallFun(tv_emergency2.getText().toString());
                break;
            case R.id.call3:
                sendCallFun(tv_emergency3.getText().toString());
                break;
        }
    }

    private void sendSmsFun(String phone) {
        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phone, null, "Test Message", null, null);
            Toast.makeText(this, "SMS sent.",
                    Toast.LENGTH_LONG).show();

        } catch (
                Exception e)

        {
            Toast.makeText(this,
                    "SMS faild, Please try Again.",
                    Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }

    }

    private void sendCallFun(String phone) {
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:" + phone));//change the number
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        startActivity(callIntent);
    }
}
