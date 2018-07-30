package hacker.l.emergency_help.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hacker.l.emergency_help.R;
import hacker.l.emergency_help.activity.DashBoardActivity;
import hacker.l.emergency_help.adapter.SocialAdapter;
import hacker.l.emergency_help.database.DbHelper;
import hacker.l.emergency_help.models.MyPojo;
import hacker.l.emergency_help.models.Result;
import hacker.l.emergency_help.utility.Contants;
import hacker.l.emergency_help.utility.Utility;

public class UtilityFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public static UtilityFragment newInstance(String param1, String param2) {
        UtilityFragment fragment = new UtilityFragment();
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

    View view;
    Context context;
    //    Button btn_police, btn_sakticomd, btn_tiger, btn_pcr, btn_policeNo, btn_highway;
    RecyclerView recycleView;
    List<String> districtList;
    List<Result> resultList = null;
    Spinner spinnerDist;
    String district;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        context = getActivity();
        view = inflater.inflate(R.layout.fragment_utility, container, false);
        init();
        return view;
    }

    private void init() {
        DashBoardActivity dashBoardActivity = (DashBoardActivity) context;
        dashBoardActivity.setTitle("Utility Contacts");
        if (!Utility.isOnline(context)) {
            Toast.makeText(context, "Connect Internet connection", Toast.LENGTH_SHORT).show();
        }
//        btn_police = view.findViewById(R.id.btn_police);
//        btn_sakticomd = view.findViewById(R.id.btn_sakticomd);
//        btn_tiger = view.findViewById(R.id.btn_tiger);
//        btn_pcr = view.findViewById(R.id.btn_pcr);
//        btn_highway = view.findViewById(R.id.btn_highway);
//        btn_policeNo = view.findViewById(R.id.btn_policeNo);
//        btn_police.setOnClickListener(this);
//        btn_sakticomd.setOnClickListener(this);
//        btn_tiger.setOnClickListener(this);
//        btn_pcr.setOnClickListener(this);
//        btn_highway.setOnClickListener(this);
//        btn_policeNo.setOnClickListener(this);
        resultList = new ArrayList<>();
        districtList = new ArrayList<>();
        spinnerDist = view.findViewById(R.id.spinnerDist);
        recycleView = view.findViewById(R.id.recycleView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        recycleView.setLayoutManager(linearLayoutManager);
        setSpinnerDistAdapter();
//        getDataFromServer();
//        setAdapter();
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
                                    getDataFromServer();
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

    private void getDataFromServer() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Contants.SERVICE_BASE_URL + Contants.getAllCategory,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        MyPojo myPojo = new Gson().fromJson(response, MyPojo.class);
                        resultList.clear();
                        if (myPojo != null) {
                            for (Result result : myPojo.getResult()) {
                                resultList.addAll(Arrays.asList(result));
                            }
                            SocialAdapter socialAdapter = new SocialAdapter(context, resultList);
                            recycleView.setAdapter(socialAdapter);
//                            if (response.equalsIgnoreCase("no")) {
//                                Toast.makeText(context, "Any category not Found", Toast.LENGTH_SHORT).show();
//                            }
                        }
//                        progressDialog.dismiss();
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

    private void setAdapter() {
        DbHelper dbHelper = new DbHelper(context);
        List<Result> resultList = dbHelper.getAllSocialData();
        if (resultList != null) {


        } else {
            Toast.makeText(context, "Add Social Numbers", Toast.LENGTH_SHORT).show();
        }
    }


    private void moveFragment(Fragment fragment) {
        FragmentManager fragmentManager = ((FragmentActivity) context).getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, fragment)
                .addToBackStack(null)
                .commit();
    }
}
