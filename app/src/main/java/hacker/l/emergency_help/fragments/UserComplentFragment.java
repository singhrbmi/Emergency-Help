package hacker.l.emergency_help.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import hacker.l.emergency_help.R;
import hacker.l.emergency_help.database.DbHelper;
import hacker.l.emergency_help.models.Result;
import hacker.l.emergency_help.utility.Contants;
import hacker.l.emergency_help.utility.Utility;

public class UserComplentFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    // TODO: Rename and change types and number of parameters
    public static UserComplentFragment newInstance(String param1, String param2) {
        UserComplentFragment fragment = new UserComplentFragment();
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

    Context context;
    View view;
    EditText edt_message;
    TextView tv_submit;
    ProgressDialog pd;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        context = getActivity();
        view = inflater.inflate(R.layout.fragment_user_complent, container, false);
        init();
        return view;
    }

    private void init() {
        edt_message = view.findViewById(R.id.edt_message);
        tv_submit = view.findViewById(R.id.tv_submit);
        tv_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addComplent();
            }
        });
    }


    private void addComplent() {
        final String message = edt_message.getText().toString();
        if (message.length() != 0) {
            if (Utility.isOnline(context)) {
                pd = new ProgressDialog(context);
                pd.setMessage("Submit Your Complete Please wait...");
                pd.show();
                pd.setCancelable(false);
                StringRequest stringRequest = new StringRequest(Request.Method.POST, Contants.SERVICE_BASE_URL + Contants.AddComplents,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                pd.dismiss();
                                Toast.makeText(context, "Send Successfully", Toast.LENGTH_SHORT).show();
                                edt_message.setText("");
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
                        params.put("complent", message);
                        DbHelper dbHelper = new DbHelper(context);
                        Result result = dbHelper.getUserData();
                        params.put("name", result.getUsername());
                        params.put("phone", result.getUserPhone());
                        return params;
                    }
                };
                RequestQueue requestQueue = Volley.newRequestQueue(context);
                requestQueue.add(stringRequest);
            } else {
                Toast.makeText(context, "You are Offline. Please check your Internet Connection.", Toast.LENGTH_SHORT).show();
            }
        } else {
            edt_message.setError("Enter Advise Please");
        }
    }
}
