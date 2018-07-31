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
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import hacker.l.emergency_help.R;
import hacker.l.emergency_help.activity.DashBoardActivity;
import hacker.l.emergency_help.adapter.SocialContactsAdapter;
import hacker.l.emergency_help.adapter.SubCategoryAdapter;
import hacker.l.emergency_help.models.MyPojo;
import hacker.l.emergency_help.models.Result;

public class SubCategoryFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String response;
    private String mParam2;

    public SubCategoryFragment() {
        // Required empty public constructor
    }

    public static SubCategoryFragment newInstance(String response, String param2) {
        SubCategoryFragment fragment = new SubCategoryFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, response);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            response = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    RecyclerView recycleView;
    View view;
    Context context;
    LinearLayoutManager linearLayoutManager;
    TextView tv_type, tv_District;
    SearchView search_barUser;
    SubCategoryAdapter subCategoryAdapter;
    ProgressDialog pd;
    String suCate, social, dist;
    List<Result> resultList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        context = getActivity();
        view = inflater.inflate(R.layout.fragment_sub_category, container, false);
        init();
        return view;
    }

    private void init() {
        recycleView = view.findViewById(R.id.recycleView);
        tv_type = view.findViewById(R.id.type);
        tv_District = view.findViewById(R.id.tv_District);
        linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        recycleView.setLayoutManager(linearLayoutManager);
        MyPojo myPojo = new Gson().fromJson(response, MyPojo.class);
        if (myPojo != null) {
            List<Result> results = new ArrayList<>();
            for (Result result : myPojo.getResult()) {
                Result resultModel = new Result();
                suCate = result.getSubCategory();
                social = result.getSocialName();
                dist = result.getDistrict();
                resultModel.setSubCategory(suCate);
                resultModel.setSocialName(social);
                resultModel.setDistrict(dist);
                results.add(resultModel);

            }
            DashBoardActivity dashBoardActivity = (DashBoardActivity) context;
            dashBoardActivity.setTitle(social);
            tv_type.setText(social);

            if (dist != null) {
                tv_District.setText(dist);
            } else {
                tv_District.setText("Ranchi");
            }
            SubCategoryAdapter subCategoryAdapter = new SubCategoryAdapter(context, results);
            recycleView.setAdapter(subCategoryAdapter);
        }

    }
}