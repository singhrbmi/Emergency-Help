package hacker.l.emergency_help.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
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
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hacker.l.emergency_help.R;
import hacker.l.emergency_help.adapter.AddContactsAdapter;
import hacker.l.emergency_help.adapter.CategoryAdapter;
import hacker.l.emergency_help.adapter.SocialContactsAdapter;
import hacker.l.emergency_help.models.MyPojo;
import hacker.l.emergency_help.models.Result;
import hacker.l.emergency_help.utility.Contants;
import hacker.l.emergency_help.utility.Utility;

public class AddPhoneNoFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    // TODO: Rename and change types and number of parameters
    public static AddPhoneNoFragment newInstance(String param1, String param2) {
        AddPhoneNoFragment fragment = new AddPhoneNoFragment();
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
    Spinner spinner, spinnerDist;
    List<Result> resultList;
    List<String> stringList, districtList;
    String district, category, offNumber, offName, phone, name;
    Button btn_add;
    Boolean aBoolean = false;
    EditText edt_offNo, edt_offName, edt_phone, edt_name;
    LinearLayoutManager linearLayoutManager;
    Result result;
    SearchView search_barUser;
    AddContactsAdapter socialContactsAdapter;
    RecyclerView recycleView;
    ProgressDialog pd;
    int phoneId;

    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        context = getActivity();
        view = inflater.inflate(R.layout.fragment_add_phone_no, container, false);
        init();
        return view;
    }

    private void init() {
        spinner = view.findViewById(R.id.spinner);
        spinnerDist = view.findViewById(R.id.spinnerDist);
        btn_add = view.findViewById(R.id.btn_add);
        edt_offNo = view.findViewById(R.id.edt_offNo);
        edt_offName = view.findViewById(R.id.edt_offName);
        edt_phone = view.findViewById(R.id.edt_phone);
        edt_name = view.findViewById(R.id.edt_name);
        resultList = new ArrayList<>();
        stringList = new ArrayList<>();
        districtList = new ArrayList<>();
        setSpinnerDistAdapter();
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validation()) {
                    if (aBoolean) {
                        updatePhoneData();
                    } else {
                        addPhoneData();
                    }
                }
            }
        });
        recycleView = view.findViewById(R.id.recycleView);
        linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        recycleView.setLayoutManager(linearLayoutManager);
        search_barUser = (SearchView) view.findViewById(R.id.search_barUser);
        search_barUser.setIconified(true);
        search_barUser.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // filter recycler view when query submitted
                socialContactsAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                // filter recycler view when text is changed
                socialContactsAdapter.getFilter().filter(query);
                return false;
            }
        });
        setPhoneAdapter();
    }

    private void setSpinnerDistAdapter() {
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
                                    setSpinnerAdapter();
                                    setPhoneAdapter();
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

    public void setPhoneAdapter() {
        if (Utility.isOnline(context)) {
            pd = new ProgressDialog(context);
            pd.setMessage("Getting  wait...");
            pd.show();
            pd.setCancelable(false);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, Contants.SERVICE_BASE_URL + Contants.getAllPhone,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            pd.dismiss();
                            MyPojo myPojo = new Gson().fromJson(response, MyPojo.class);
                            resultList.clear();
                            resultList.addAll(Arrays.asList(myPojo.getResult()));
                            if (resultList != null) {
                                Collections.reverse(resultList);
                                AddContactsAdapter adapter = new AddContactsAdapter(context, resultList, AddPhoneNoFragment.this);
                                recycleView.setAdapter(adapter);
                                edt_name.setText("");
                                edt_phone.setText("");
                                edt_offName.setText("");
                                edt_offNo.setText("");
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
                    params.put("category", category);
                    params.put("district", district);
                    return params;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(context);
            requestQueue.add(stringRequest);
        } else {
            Toast.makeText(context, "You are Offline. Please check your Internet Connection.", Toast.LENGTH_SHORT).show();
        }
    }

    private void addPhoneData() {
        if (Utility.isOnline(context)) {
            pd = new ProgressDialog(context);
            pd.setMessage("Adding wait...");
            pd.show();
            pd.setCancelable(false);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, Contants.SERVICE_BASE_URL + Contants.addPhone,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            pd.dismiss();
                            Toast.makeText(context, "Add Successfully", Toast.LENGTH_SHORT).show();
                            setPhoneAdapter();
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
                    params.put("name", name);
                    params.put("phone", phone);
                    params.put("offName", offName);
                    params.put("offNumber", offNumber);
                    params.put("category", category);
                    params.put("district", district);
                    return params;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(context);
            requestQueue.add(stringRequest);
        } else {
            Toast.makeText(context, "You are Offline. Please check your Internet Connection.", Toast.LENGTH_SHORT).show();
        }
    }

    private void updatePhoneData() {
        if (Utility.isOnline(context)) {
            pd = new ProgressDialog(context);
            pd.setMessage("Updating wait...");
            pd.show();
            pd.setCancelable(false);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, Contants.SERVICE_BASE_URL + Contants.updatePhone,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            pd.dismiss();
                            Toast.makeText(context, "Update Successfully", Toast.LENGTH_SHORT).show();
                            btn_add.setText("Add");
                            setPhoneAdapter();
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
                    params.put("phoneId", String.valueOf(phoneId));
                    params.put("name", name);
                    params.put("phone", phone);
                    params.put("offName", offName);
                    params.put("offNumber", offNumber);
                    params.put("category", category);
                    params.put("district", district);
                    return params;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(context);
            requestQueue.add(stringRequest);
        } else {
            Toast.makeText(context, "You are Offline. Please check your Internet Connection.", Toast.LENGTH_SHORT).show();
        }

    }

    public void updateShowData(boolean aBoolean, int phoneId, String name, String phone, String offName, String offNumber) {
        this.aBoolean = aBoolean;
        this.phoneId = phoneId;
        edt_name.setText(name);
        edt_phone.setText(phone);
        edt_offName.setText(offName);
        edt_offNo.setText(offNumber);
        btn_add.setText("Update");
    }

    boolean validation() {
        offNumber = edt_offNo.getText().toString();
        offName = edt_offName.getText().toString();
        phone = edt_phone.getText().toString();
        name = edt_name.getText().toString();
        if (name.length() == 0) {
            edt_name.setError("Enter Name");
            return false;
        } else if (phone.length() == 0) {
            edt_phone.setError("Enter Phone");
            return false;
        } else if (phone.length() != 10) {
            edt_phone.setError("Enter Valid Phone");
            return false;
        } else if (offName.length() == 0) {
            edt_offName.setError("Enter Office Name");
            return false;
        } else if (offNumber.length() == 0) {
            edt_offNo.setError("Enter Office No");
            return false;
        } else if (category.length() == 0) {
            Toast.makeText(context, "Select Category", Toast.LENGTH_LONG).show();
        }
        return true;
    }

    private void setSpinnerAdapter() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Contants.SERVICE_BASE_URL + Contants.getAllCategory,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        MyPojo myPojo = new Gson().fromJson(response, MyPojo.class);
                        stringList.clear();
                        for (Result result : myPojo.getResult()) {
                            stringList.addAll(Arrays.asList(result.getSocialName()));
                        }
                        if (stringList != null) {
                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, stringList);
                            spinner.setAdapter(adapter);
                            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    category = parent.getSelectedItem().toString();
                                    setPhoneAdapter();
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
                params.put("district", district);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }
}