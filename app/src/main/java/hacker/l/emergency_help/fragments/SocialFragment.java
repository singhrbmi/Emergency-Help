package hacker.l.emergency_help.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import java.util.List;
import java.util.Map;

import hacker.l.emergency_help.R;
import hacker.l.emergency_help.adapter.SocialAdapter;
import hacker.l.emergency_help.database.DbHelper;
import hacker.l.emergency_help.models.MyPojo;
import hacker.l.emergency_help.models.Result;
import hacker.l.emergency_help.utility.Contants;

public class SocialFragment extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public static SocialFragment newInstance(String param1, String param2) {
        SocialFragment fragment = new SocialFragment();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        context = getActivity();
        view = inflater.inflate(R.layout.fragment_social, container, false);
        init();
        return view;
    }

    private void init() {
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
        recycleView = view.findViewById(R.id.recycleView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        recycleView.setLayoutManager(linearLayoutManager);
        getDataFromServer();
        setAdapter();
    }

    private void getDataFromServer() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Contants.SERVICE_BASE_URL + Contants.getAllCategory,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        MyPojo myPojo = new Gson().fromJson(response, MyPojo.class);
                        if (myPojo != null) {
                            for (Result result : myPojo.getResult()) {
                                if (result != null) {
                                    new DbHelper(context).upsertSocialData(result);
                                    setAdapter();
                                }
                            }
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
            SocialAdapter socialAdapter = new SocialAdapter(context, resultList);
            recycleView.setAdapter(socialAdapter);

        } else {
            Toast.makeText(context, "Add Social Numbers", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
//            case R.id.btn_police:
//                SocialNoViewFragment fragment = SocialNoViewFragment.newInstance("police", "");
//                moveFragment(fragment);
//                break;
//            case R.id.btn_sakticomd:
//                SocialNoViewFragment fragmentSakti = SocialNoViewFragment.newInstance("sakti", "");
//                moveFragment(fragmentSakti);
//                break;
//            case R.id.btn_tiger:
//                SocialNoViewFragment fragmenttiger = SocialNoViewFragment.newInstance("tiger", "");
//                moveFragment(fragmenttiger);
//                break;
//            case R.id.btn_pcr:
//                SocialNoViewFragment fragmentPcr = SocialNoViewFragment.newInstance("pcr", "");
//                moveFragment(fragmentPcr);
//                break;
//            case R.id.btn_highway:
//                SocialNoViewFragment fragmenthighway = SocialNoViewFragment.newInstance("highway", "");
//                moveFragment(fragmenthighway);
//                break;
//            case R.id.btn_policeNo:
//                SocialNoViewFragment fragmentPolice = SocialNoViewFragment.newInstance("policeNo", "");
//                moveFragment(fragmentPolice);
//                break;
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
