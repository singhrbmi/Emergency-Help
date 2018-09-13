package hacker.l.emergency_help.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import hacker.l.emergency_help.R;
import hacker.l.emergency_help.adapter.SocialContactsAdapter;
import hacker.l.emergency_help.models.MyPojo;
import hacker.l.emergency_help.models.Result;
import hacker.l.emergency_help.utility.Contants;
import hacker.l.emergency_help.utility.Utility;


public class SearchSurakshaNoFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public SearchSurakshaNoFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static SearchSurakshaNoFragment newInstance(String param1, String param2) {
        SearchSurakshaNoFragment fragment = new SearchSurakshaNoFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    TextView tv_emergency, tv_phone1, tv_emergency2, tv_phone2, tv_emergency3, tv_phone3;
    Context context;
    View view;
    EditText id_et_phone;
    Button id_bt_forget;
    ProgressDialog pd;
    String phone1, phone2, phone3;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        context = getActivity();
        view = inflater.inflate(R.layout.fragment_search_suraksha_no, container, false);
        init();
        return view;
    }

    private void init() {
        tv_emergency = view.findViewById(R.id.tv_emergency);
        tv_phone1 = view.findViewById(R.id.tv_phone1);
        tv_emergency2 = view.findViewById(R.id.tv_emergency2);
        tv_phone2 = view.findViewById(R.id.tv_phone2);
        tv_emergency3 = view.findViewById(R.id.tv_emergency3);
        tv_phone3 = view.findViewById(R.id.tv_phone3);
        id_et_phone = view.findViewById(R.id.id_et_phone);
        id_bt_forget = view.findViewById(R.id.id_bt_forget);

        tv_phone1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeCall(phone1);
            }
        });
        tv_phone2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeCall(phone2);
            }
        });
        tv_emergency3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeCall(phone3);
            }
        });
        tv_phone3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        id_bt_forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchData();
            }
        });
    }

    void makeCall(String no) {
        if (no != null) {
            Intent intent = new Intent(Intent.ACTION_CALL);
            intent.setData(Uri.parse("tel:" + no));
            startActivity(intent);
        }
    }

    private void searchData() {
        final String sPhone = id_et_phone.getText().toString();
        if (sPhone.length() == 10) {
            if (Utility.isOnline(context)) {
                pd = new ProgressDialog(context);
                pd.setMessage("Searching  Wait Please...");
                pd.show();
                pd.setCancelable(false);
                StringRequest stringRequest = new StringRequest(Request.Method.POST, Contants.SERVICE_BASE_URL + Contants.SearchsurakshaNo,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                try {
                                    if (pd.isShowing()) {
                                        pd.dismiss();
                                    }
                                }catch (Exception e){
                                    e.printStackTrace();
                                }
                                if (!response.equalsIgnoreCase("no")) {
                                    MyPojo myPojo = new Gson().fromJson(response, MyPojo.class);
                                    if (myPojo != null) {
                                        for (Result result : myPojo.getResult()) {
                                            phone1 = result.getEmergencyOne();
                                            phone2 = result.getEmergencyTwo();
                                            phone3 = result.getEmergencyThree();
                                            if (phone1 != null && phone2 != null && phone3 != null) {
                                                tv_emergency.setText(phone1);
                                                tv_emergency2.setText(phone2);
                                                tv_emergency3.setText(phone3);
                                            }

                                        }
                                    } else {
                                        Toast.makeText(context, "No Data Found", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    Toast.makeText(context, "No Data Found", Toast.LENGTH_SHORT).show();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                try {
                                    if (pd.isShowing()) {
                                        pd.dismiss();
                                    }
                                }catch (Exception e){
                                    e.printStackTrace();
                                }
                            }
                        }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("EmergencyOne", sPhone);
                        params.put("EmergencyTwo", sPhone);
                        params.put("EmergencyThree", sPhone);
                        return params;
                    }
                };
                RequestQueue requestQueue = Volley.newRequestQueue(context);
                requestQueue.add(stringRequest);
            } else {
                Toast.makeText(context, "You are Offline. Please check your Internet Connection.", Toast.LENGTH_SHORT).show();
            }
        } else {
            id_et_phone.setError("Enter Valid Phone");
        }
    }
}
