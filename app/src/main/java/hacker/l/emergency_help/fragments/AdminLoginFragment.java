package hacker.l.emergency_help.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
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

import hacker.l.emergency_help.R;
import hacker.l.emergency_help.activity.DashBoardActivity;
import hacker.l.emergency_help.activity.LoginActivity;
import hacker.l.emergency_help.database.DbHelper;
import hacker.l.emergency_help.models.MyPojo;
import hacker.l.emergency_help.models.Result;
import hacker.l.emergency_help.utility.Contants;
import hacker.l.emergency_help.utility.Utility;


public class AdminLoginFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    // TODO: Rename and change types and number of parameters
    public static AdminLoginFragment newInstance(String param1, String param2) {
        AdminLoginFragment fragment = new AdminLoginFragment();
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
    Button id_bt_login;
    EditText id_et_username, id_et_password;
    CheckBox showCheck;
    ProgressDialog pd;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        context = getActivity();
        view = inflater.inflate(R.layout.fragment_admin_login, container, false);
        init();
        return view;
    }

    private void init() {
        id_bt_login = view.findViewById(R.id.id_bt_login);
        id_et_username = view.findViewById(R.id.id_et_username);
        id_et_password = view.findViewById(R.id.id_et_password);
        showCheck = view.findViewById(R.id.show_password);
        id_bt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AdminDasboardFragment adminDasboardFragment = AdminDasboardFragment.newInstance("", "");
                moveFragment(adminDasboardFragment);
//                loginFunction();
            }
        });
        showCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (showCheck.isChecked()) {
                    id_et_password.setInputType(InputType.TYPE_CLASS_TEXT);
                    id_et_password.setSelection(id_et_password.length());
                } else {
                    id_et_password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    id_et_password.setSelection(id_et_password.length());
                }
            }
        });
    }

    private void loginFunction() {
        final String userPhone = id_et_username.getText().toString();
        final String userPass = id_et_password.getText().toString();
        if (userPhone.length() == 0) {
            id_et_username.setError("Enter  Phone Number ");
        } else if (userPhone.length() != 10) {
            id_et_username.setError("Enter  Valid Phone");
        } else if (userPass.length() == 0) {
            id_et_password.setError("Enter password");
        } else {
            if (Utility.isOnline(context)) {
                pd = new ProgressDialog(context);
                pd.setMessage("Verify wait...");
                pd.show();
                pd.setCancelable(false);
                StringRequest stringRequest = new StringRequest(Request.Method.POST, Contants.SERVICE_BASE_URL + Contants.adminLogin,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                pd.dismiss();
                                if (!response.equalsIgnoreCase("no")) {
                                    AdminDasboardFragment adminDasboardFragment = AdminDasboardFragment.newInstance("", "");
                                    moveFragment(adminDasboardFragment);
                                } else {
                                    Toast.makeText(context, "Invalid Information", Toast.LENGTH_SHORT).show();
                                    id_et_username.setError("Invalid Information");
                                }
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
                        params.put("adminPhone", userPhone);
                        params.put("adminPass", userPass);
                        return params;
                    }
                };
                RequestQueue requestQueue = Volley.newRequestQueue(context);
                requestQueue.add(stringRequest);
            } else

            {

                Toast.makeText(context, "You are Offline. Please check your Internet Connection.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void moveFragment(Fragment fragment) {
        FragmentManager fragmentManager = ((FragmentActivity) context).getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, fragment)
//                .addToBackStack(null)
                .commit();
    }

}
