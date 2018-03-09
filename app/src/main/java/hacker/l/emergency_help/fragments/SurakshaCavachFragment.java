package hacker.l.emergency_help.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.zxing.WriterException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import hacker.l.emergency_help.R;
import hacker.l.emergency_help.activity.ForgetPasswordActivity;
import hacker.l.emergency_help.database.DbHelper;
import hacker.l.emergency_help.models.Result;
import hacker.l.emergency_help.utility.Contants;
import hacker.l.emergency_help.utility.Utility;


public class SurakshaCavachFragment extends Fragment {
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public static SurakshaCavachFragment newInstance(String param1, String param2) {
        SurakshaCavachFragment fragment = new SurakshaCavachFragment();
        Bundle args = new Bundle();
        args.putString("", param1);
        args.putString("", param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString("");
            mParam2 = getArguments().getString("");
        }
    }

    View view;
    Context context;
    List<String> spinnerList;
    Spinner spinner;
    EditText id_et_userName, id_phone, id_email, id_address, id_no1, id_no2, id_no3, id_city, id_pincode;
    Button id_btproced;
    ProgressDialog pd;
    int loginID;
    String name, phone, email, city, address, pincode, no1, no2, no3, socialUs;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        context = getActivity();
        view = inflater.inflate(R.layout.fragment_suraksha_cavach, container, false);
        intit();
        return view;
    }

    private void intit() {
        spinnerList = new ArrayList<>();
        spinnerList.add("Sri Maheswari Sabha,Ranchi");
        spinnerList.add("Lions Club of Ranchi Central");
        spinnerList.add("Rotaract Club of Ranchi City");
        spinnerList.add("Marwari Yuwa Manch,Ranchi South");
        spinnerList.add("Lions Club of Ranchi North");
        spinnerList.add("Lions Club of Ranchi Citizen");
        spinnerList.add("Rotaract Club of ICWAI");
        spinnerList.add("Rotaract Club of Social Revolution");
        spinnerList.add("Rotaract Club of NIFFT");
        spinnerList.add("Rotaract Club of St.Xavier's College");
        spinnerList.add("Rotaract Club of Kalimati");
        spinnerList.add("Rotaract Club of Chaibase");
        spinnerList.add("Rotaract Club of BIT MESRA");
        spinnerList.add("FJCCI");

        spinner = view.findViewById(R.id.spinner);
        id_et_userName = view.findViewById(R.id.id_et_userName);
        id_phone = view.findViewById(R.id.id_phone);
        id_email = view.findViewById(R.id.id_email);
        id_address = view.findViewById(R.id.id_address);
        id_no1 = view.findViewById(R.id.id_no1);
        id_no2 = view.findViewById(R.id.id_no2);
        id_no3 = view.findViewById(R.id.id_no3);
        id_city = view.findViewById(R.id.id_city);
        id_pincode = view.findViewById(R.id.id_pincode);
        id_btproced = view.findViewById(R.id.id_btproced);
        DbHelper dbHelper = new DbHelper(context);
        Result result = dbHelper.getUserData();
        loginID = result.getLoginId();
        id_email.setText(result.getEmailId());
        id_et_userName.setText(result.getUsername());
        id_et_userName.setSelection(id_et_userName.getText().length());
        id_phone.setText(result.getUserPhone());
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, spinnerList);
        spinner.setAdapter(adapter);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                socialUs = parent.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinner.setSelection(0, true);
        View v = spinner.getSelectedView();
        ((TextView) v).setTextColor(getResources().getColor(R.color.red));
        ((TextView) v).setTextSize(14);
        id_btproced.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadSurakhshaCavach();
            }
        });
    }

    private boolean validation() {
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        name = id_et_userName.getText().toString();
        phone = id_phone.getText().toString();
        email = id_email.getText().toString();
        pincode = id_pincode.getText().toString();
        city = id_city.getText().toString();
        address = id_address.getText().toString();
        no1 = id_no1.getText().toString();
        no2 = id_no2.getText().toString();
        no3 = id_no3.getText().toString();
        if (name.isEmpty()) {
            id_et_userName.setError("Enter Username");
            return false;
        } else if (phone.isEmpty()) {
            id_phone.setError("Enter Phone Number");
            return false;
        } else if (phone.length() != 10) {
            id_phone.setError("Enter Valid Phone Number");
            return false;
        } else if (email.length() == 0) {
            id_email.setError("Enter Email Id");
            return false;
        } else if (!pattern.matcher(email).matches()) {
            id_email.setError("Enter Valid Email Id");
            return false;
        } else if (address.length() == 0) {
            id_address.setError("Enter Address");
            return false;
        } else if (no1.length() == 0) {
            id_no1.setError("Enter Number");
            return false;
        } else if (no2.length() == 0) {
            id_no2.setError("Enter Number");
            return false;
        } else if (no3.length() == 0) {
            id_no3.setError("Enter Number");
            return false;
        } else if (city.length() == 0) {
            id_city.setError("Enter City");
            return false;
        } else if (pincode.length() == 0) {
            id_pincode.setError("Enter Pincode");
            return false;
        } else {
            return true;
        }
    }

    private void uploadSurakhshaCavach() {
        if (Utility.isOnline(context)) {
            if (validation()) {
                pd = new ProgressDialog(context);
                pd.setMessage("Uploading wait...");
                pd.show();
                pd.setCancelable(false);
                StringRequest stringRequest = new StringRequest(Request.Method.POST, Contants.SERVICE_BASE_URL + Contants.surakshacavach,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                pd.dismiss();
                                Toast.makeText(context, "Done", Toast.LENGTH_SHORT).show();
                                id_et_userName.setText("");
                                id_phone.setText("");
                                id_email.setText("");
                                id_address.setText("");
                                id_no1.setText("");
                                id_no2.setText("");
                                id_no3.setText("");
                                id_city.setText("");
                                id_pincode.setText("");
                                spinner.setSelection(0);
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
                        params.put("loginId", String.valueOf(loginID));
                        params.put("Username", name);
                        params.put("UserPhone", phone);
                        params.put("EmailId", email);
                        params.put("Address", address);
                        params.put("City", city);
                        params.put("PinCode", pincode);
                        params.put("EmergencyOne", no1);
                        params.put("EmergencyTwo", no2);
                        params.put("EmergencyThree", no3);
                        String barCode = name + "," + phone + "," + email + "," + address + "," + city + "," + pincode + "," + no1 + "," + no2 + "," + no3;
                        params.put("barCode", barCode);
                        params.put("socialUs", socialUs);
                        return params;
                    }
                };
                RequestQueue requestQueue = Volley.newRequestQueue(context);
                requestQueue.add(stringRequest);
            }
        } else {
            Toast.makeText(context, "You are Offline. Please check your Internet Connection.", Toast.LENGTH_SHORT).show();
        }
    }

}
