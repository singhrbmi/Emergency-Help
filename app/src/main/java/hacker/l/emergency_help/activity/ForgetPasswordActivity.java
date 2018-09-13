package hacker.l.emergency_help.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.ClipboardManager;
import android.util.Patterns;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import hacker.l.emergency_help.R;
import hacker.l.emergency_help.database.DbHelper;
import hacker.l.emergency_help.models.MyPojo;
import hacker.l.emergency_help.models.Result;
import hacker.l.emergency_help.utility.Contants;
import hacker.l.emergency_help.utility.Utility;

public class ForgetPasswordActivity extends AppCompatActivity {
    ProgressDialog pd;
    //    TextView forgot_password;
    Button id_bt_forget;
    EditText id_et_phone;
    String userPhone, userEmail, copyText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
//        forgot_password = findViewById(R.id.forgot_password);
        id_bt_forget = findViewById(R.id.id_bt_forget);
        id_et_phone = findViewById(R.id.id_et_phone);
//        id_et_email = findViewById(R.id.id_et_email);
        id_bt_forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (id_bt_forget.getText().toString().equalsIgnoreCase("Copy Password")) {
//                    ClipboardManager _clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
//                    String[] cutString = copyText.split("-");
//                    String aaq = cutString[0];
//                    String aa = cutString[1];
//                    _clipboard.setText(aa);
//                    Toast.makeText(getApplicationContext(), "Copied to Clipboard!", Toast.LENGTH_SHORT).show();
//                } else {
                Pattern pattern = Patterns.EMAIL_ADDRESS;
                userPhone = id_et_phone.getText().toString();
//                userEmail = id_et_email.getText().toString();
//                if (userEmail.length() == 0) {
//                    id_et_email.setError("Enter Email");
//                } else if (!pattern.matcher(userEmail).matches()) {
//                    id_et_email.setError("Enter Valid Email");
                if (userPhone.length() == 0) {
                    id_et_phone.setError("Enter  Phone Number ");
                } else if (userPhone.length() != 10) {
                    id_et_phone.setError("Enter  Valid Phone");
                } else

                {
                    forgetPass();
                    //}
                }
            }
        });

    }

    private void forgetPass() {
        if (Utility.isOnline(this)) {
            pd = new ProgressDialog(ForgetPasswordActivity.this);
            pd.setMessage("Forget Password wait...");
            pd.show();
            pd.setCancelable(false);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, Contants.SERVICE_BASE_URL + Contants.forgetpassword,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            pd.dismiss();
                            Toast.makeText(ForgetPasswordActivity.this, response, Toast.LENGTH_SHORT).show();
//                            forgot_password.setText("Your Password:-" + response);
                            //id_bt_forget.setText("Copy Password");
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            pd.dismiss();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("UserPhone", userPhone);
//                    params.put("EmailId", userEmail);
                    return params;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);
        } else

        {

            Toast.makeText(this, "You are Offline. Please check your Internet Connection.", Toast.LENGTH_SHORT).show();
        }
    }

    //for hid keyboard when tab outside edittext box
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (v instanceof EditText) {
                Rect outRect = new Rect();
                v.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int) event.getRawX(), (int) event.getRawY())) {
                    v.clearFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        }
        return super.dispatchTouchEvent(event);
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }
}
