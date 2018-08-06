package hacker.l.emergency_help.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hacker.l.emergency_help.R;
import hacker.l.emergency_help.activity.DashBoardActivity;
import hacker.l.emergency_help.activity.SignupActivity;
import hacker.l.emergency_help.activity.SplashActivity;
import hacker.l.emergency_help.adapter.AdminAdapter;
import hacker.l.emergency_help.models.MyPojo;
import hacker.l.emergency_help.models.Result;
import hacker.l.emergency_help.utility.Contants;
import hacker.l.emergency_help.utility.Utility;


public class AddUpdateAdministiveFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public AddUpdateAdministiveFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static AddUpdateAdministiveFragment newInstance(String param1, String param2) {
        AddUpdateAdministiveFragment fragment = new AddUpdateAdministiveFragment();
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
    List<String> districtList;
    List<Result> resultList;
    Spinner spinnerDist;
    String district, isActive = "UnBlock", adminName, adminPass, adminPhone;
    EditText edt_name, edt_phone, edt_pass;
    RadioGroup radipGroup;
    RadioButton radio_unblock, radio_block;
    Button tv_add;
    RecyclerView recycleView;
    boolean updateFlag = false;
    int adminId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        context = getActivity();
        view = inflater.inflate(R.layout.fragment_add_update_administive, container, false);
        init();
        return view;
    }

    private void init() {
        DashBoardActivity dashBoardActivity = (DashBoardActivity) context;
        dashBoardActivity.setTitle("Add/Update Administrative");
        spinnerDist = view.findViewById(R.id.spinnerDist);
        edt_name = view.findViewById(R.id.edt_name);
        edt_phone = view.findViewById(R.id.edt_phone);
        edt_pass = view.findViewById(R.id.edt_pass);
        radipGroup = view.findViewById(R.id.radipGroup);
        radio_unblock = view.findViewById(R.id.radio_unblock);
        radio_block = view.findViewById(R.id.radio_block);
        tv_add = view.findViewById(R.id.tv_add);
        resultList = new ArrayList<>();
        recycleView = view.findViewById(R.id.recycleView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        recycleView.setLayoutManager(linearLayoutManager);
        radipGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.radio_block) {
                    isActive = "Block";
                } else {
                    isActive = "UnBlock";
                }
            }
        });
        tv_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validation()) {
                    if (updateFlag) {
                        updateAdministrativeData();
                    } else {
                        addAdministrativeData();
                    }
                }
            }
        });
        setSpinnerDistAdapter();
        setAdapter();
    }

    boolean validation() {
        adminName = edt_name.getText().toString();
        adminPass = edt_pass.getText().toString();
        adminPhone = edt_phone.getText().toString();
        if (adminName.length() == 0) {
            edt_name.setError("Enter Name");
            requestFocus(edt_name);
            return false;
        } else if (adminPhone.length() != 10) {
            edt_phone.setError("Enter Valid Phone");
            requestFocus(edt_phone);
            return false;
        } else if (adminPass.length() == 0) {
            edt_pass.setError("Enter Password");
            requestFocus(edt_pass);
            return false;
        }

        return true;
    }

    private void addAdministrativeData() {
        if (Utility.isOnline(context)) {
            final ProgressDialog progressDialog = new ProgressDialog(context);
            progressDialog.setMessage("Adding Wait");
            progressDialog.show();
            progressDialog.setCancelable(false);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, Contants.SERVICE_BASE_URL + Contants.addadmin,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if (!response.equalsIgnoreCase("Phone already exists")) {
                                if (!response.equalsIgnoreCase("District already Used")) {
                                    Toast.makeText(context, "Add Successfully", Toast.LENGTH_SHORT).show();
                                    setAdapter();
                                } else {
                                    Toast.makeText(context, "District already Used", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                edt_phone.setError(response);
                                requestFocus(edt_phone);
                                Toast.makeText(context, response, Toast.LENGTH_SHORT).show();
                            }
                            progressDialog.dismiss();
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            progressDialog.dismiss();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("district", district);
                    params.put("isActive", isActive);
                    params.put("adminName", adminName);
                    params.put("adminPass", adminPass);
                    params.put("adminPhone", adminPhone);

                    return params;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(context);
            requestQueue.add(stringRequest);
        } else {
            Toast.makeText(context, "You are Offline. Please check your Internet Connection.", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateAdministrativeData() {
        if (Utility.isOnline(context)) {
            final ProgressDialog progressDialog = new ProgressDialog(context);
            progressDialog.setMessage("Updating Wait");
            progressDialog.show();
            progressDialog.setCancelable(false);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, Contants.SERVICE_BASE_URL + Contants.updateAdmin,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if (!response.equalsIgnoreCase("Phone already exists")) {
                                if (!response.equalsIgnoreCase("District already Used")) {
                                    Toast.makeText(context, "Update Successfully", Toast.LENGTH_SHORT).show();
                                    tv_add.setText("Add Administrative");
                                    setAdapter();
                                } else {
                                    Toast.makeText(context, "District already Used", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                edt_phone.setError(response);
                                requestFocus(edt_phone);
                                Toast.makeText(context, response, Toast.LENGTH_SHORT).show();
                            }
                            progressDialog.dismiss();
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            progressDialog.dismiss();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
//                    params.put("district", district);
                    params.put("isActive", isActive);
                    params.put("adminName", adminName);
                    params.put("adminPass", adminPass);
//                    params.put("adminPhone", adminPhone);
                    params.put("adminId", String.valueOf(adminId));

                    return params;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(context);
            requestQueue.add(stringRequest);
        } else {
            Toast.makeText(context, "You are Offline. Please check your Internet Connection.", Toast.LENGTH_SHORT).show();
        }
    }

    public void setAdapter() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Contants.SERVICE_BASE_URL + Contants.getAllAdmin,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (!response.equals("no")) {
                            resultList.clear();
                            MyPojo myPojo = new Gson().fromJson(response, MyPojo.class);
                            if (myPojo != null) {
                                for (Result result : myPojo.getResult()) {
                                    if (result != null) {
                                        resultList.addAll(Arrays.asList(result));
                                    }
                                }
                                if (resultList.size() != 0) {
                                    Collections.reverse(resultList);
                                    AdminAdapter adminAdapter = new AdminAdapter(context, resultList, false, AddUpdateAdministiveFragment.this);
                                    recycleView.setAdapter(adminAdapter);
                                    edt_phone.setText("");
                                    edt_pass.setText("");
                                    edt_name.setText("");

                                }
                            }
                        }

//                        progressDialog.dismiss();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
//                        progressDialog.dismiss();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }

    private void setSpinnerDistAdapter() {
        districtList = new ArrayList<>();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Contants.SERVICE_BASE_URL + Contants.getAllDistrict,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        MyPojo myPojo = new Gson().fromJson(response, MyPojo.class);
                        districtList.clear();
                        for (Result result : myPojo.getResult()) {
                            districtList.addAll(Arrays.asList(result.getDistrict()));
                        }
                        if (districtList != null) {
                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, districtList);
                            spinnerDist.setAdapter(adapter);
                            spinnerDist.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    district = parent.getSelectedItem().toString();
//                                    getAdminAdvise();
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    public void updateShowData(boolean updateFlag, int adminId, String name, String phone, String pass, String dist, String stus) {
        this.updateFlag = updateFlag;
        this.adminId = adminId;
        this.adminName = name;
        this.adminPhone = phone;
        this.adminPass = pass;
        edt_name.setText(name);
        edt_pass.setText(pass);
        edt_phone.setText(phone);
        if (stus.equalsIgnoreCase("block")) {
            radio_block.setChecked(true);
        } else {
            radio_unblock.setChecked(true);
        }
        ArrayAdapter myAdap = (ArrayAdapter) spinnerDist.getAdapter(); //cast to an ArrayAdapter

        int spinnerPosition = myAdap.getPosition(dist);

//set the default according to value
        spinnerDist.setSelection(spinnerPosition);
        edt_phone.setEnabled(false);
        edt_phone.setFocusable(false);
        edt_phone.setClickable(false);
        tv_add.setText("Update Administrative");
    }
}
