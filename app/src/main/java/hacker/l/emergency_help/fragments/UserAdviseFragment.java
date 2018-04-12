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
import hacker.l.emergency_help.adapter.AdviseAdapter;
import hacker.l.emergency_help.models.MyPojo;
import hacker.l.emergency_help.models.Result;
import hacker.l.emergency_help.utility.Contants;

public class UserAdviseFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    // TODO: Rename and change types and number of parameters
    public static UserAdviseFragment newInstance(String param1, String param2) {
        UserAdviseFragment fragment = new UserAdviseFragment();
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
    RecyclerView recycleView;
    ProgressDialog pd;
    List<Result> resultList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        context = getActivity();
        view = inflater.inflate(R.layout.fragment_user_advise, container, false);
        init();
        return view;

    }

    private void init() {
        resultList = new ArrayList<>();
        recycleView = view.findViewById(R.id.recycleView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        recycleView.setLayoutManager(linearLayoutManager);
        pd = new ProgressDialog(context);
        pd.setMessage("Getting  wait...");
        pd.show();
        pd.setCancelable(false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Contants.SERVICE_BASE_URL + Contants.getAdvise,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        pd.dismiss();
                        MyPojo myPojo = new Gson().fromJson(response, MyPojo.class);
                        resultList.clear();
                        resultList.addAll(Arrays.asList(myPojo.getResult()));
                        Collections.reverse(resultList);
                        AdviseAdapter adapter = new AdviseAdapter(context, resultList, true);
                        recycleView.setAdapter(adapter);
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
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }
}
