package hacker.l.emergency_help.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import hacker.l.emergency_help.R;
import hacker.l.emergency_help.utility.Contants;
import hacker.l.emergency_help.utility.Utility;

public class SignupActivity extends AppCompatActivity {
    Button id_bt_signup;
    ProgressDialog pd;
    EditText id_et_userName, id_phone, id_email, id_password, id_city, id_locality;
    String userName, userPhone, emailId, Password, city, state, locality;
    Spinner spinnerState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        id_bt_signup = findViewById(R.id.id_bt_signup);
        id_et_userName = findViewById(R.id.id_et_userName);
        id_phone = findViewById(R.id.id_phone);
        id_email = findViewById(R.id.id_email);
        id_city = findViewById(R.id.id_city);
        id_locality = findViewById(R.id.id_locality);
        id_password = findViewById(R.id.id_password);
        spinnerState = findViewById(R.id.spinnerState);
        setStateSpinner();
        id_bt_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUpFunction();
            }
        });
    }

    private void setStateSpinner() {
        ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, getState());
        spinnerState.setAdapter(stringArrayAdapter);
        spinnerState.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                state = parent.getSelectedItem().toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });
    }

    private List<String> getState() {
        List<String> strings = new ArrayList<>();
        strings.add("Jharkhand");
        strings.add("Delhi");
        strings.add("Punjab");
        strings.add("Haryana");
        strings.add("Bihar");
        return strings;
    }

    public boolean Validation() {
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        userName = id_et_userName.getText().toString();
        userPhone = id_phone.getText().toString();
        emailId = id_email.getText().toString();
        city = id_city.getText().toString();
        locality = id_locality.getText().toString();
        Password = id_password.getText().toString();
        if (userName.isEmpty()) {
            id_et_userName.setError("Enter Username");
            return false;
        } else if (userPhone.isEmpty()) {
            id_phone.setError("Enter Phone Number");
            return false;
        } else if (userPhone.length() != 10) {
            id_phone.setError("Enter Valid Phone Number");
            return false;
        } else if (emailId.length() == 0) {
            id_email.setError("Enter Email Id");
            return false;
        } else if (!pattern.matcher(emailId).matches()) {
            id_email.setError("Enter Valid Email Id");
            return false;
        } else if (city.length() == 0) {
            id_city.setError("Enter City");
            return false;
        } else if (locality.length() == 0) {
            id_locality.setError("Enter Locality");
            return false;
        } else if (Password.length() == 0) {
            id_password.setError("Enter Password");
            return false;
        } else {
            return true;
        }
    }

    private void signUpFunction() {
        if (Utility.isOnline(this)) {
            if (Validation()) {
                pd = new ProgressDialog(SignupActivity.this);
                pd.setMessage("Sign Up wait...");
                pd.show();
                pd.setCancelable(false);
                StringRequest stringRequest = new StringRequest(Request.Method.POST, Contants.SERVICE_BASE_URL + Contants.usersingup,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                pd.dismiss();
                                startActivity(new Intent(SignupActivity.this, DashBoardActivity.class));
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
                        params.put("Username", userName);
                        params.put("UserPhone", userPhone);
                        params.put("EmailId", emailId);
                        params.put("state", state);
                        params.put("city", city);
                        params.put("locality", locality);
                        params.put("Password", Password);
                        return params;
                    }
                };
                RequestQueue requestQueue = Volley.newRequestQueue(this);
                requestQueue.add(stringRequest);
            }
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

