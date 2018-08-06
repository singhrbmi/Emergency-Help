package hacker.l.emergency_help.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import hacker.l.emergency_help.R;
import hacker.l.emergency_help.activity.DashBoardActivity;

public class AdministrativeMgmtFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public AdministrativeMgmtFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static AdministrativeMgmtFragment newInstance(String param1, String param2) {
        AdministrativeMgmtFragment fragment = new AdministrativeMgmtFragment();
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
    TextView tv_add, tv_ViewAll, tv_unblock, tv_block;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        context = getActivity();
        view = inflater.inflate(R.layout.fragment_administrative_mgmt, container, false);
        init();
        return view;
    }

    private void init() {
        DashBoardActivity dashBoardActivity = (DashBoardActivity) context;
        dashBoardActivity.setTitle("Administrative Management");
        tv_add = view.findViewById(R.id.tv_add);
        tv_ViewAll = view.findViewById(R.id.tv_ViewAll);
        tv_unblock = view.findViewById(R.id.tv_unblock);
        tv_block = view.findViewById(R.id.tv_block);
        tv_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddUpdateAdministiveFragment fragment = AddUpdateAdministiveFragment.newInstance("", "");
                moveFragment(fragment);
            }
        });
        tv_ViewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewAllAdministiveFragment fragment = ViewAllAdministiveFragment.newInstance("", "");
                moveFragment(fragment);
            }
        });
        tv_unblock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BlockAdministiveFragment fragment = BlockAdministiveFragment.newInstance(false, "");
                moveFragment(fragment);
            }
        });
        tv_block.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BlockAdministiveFragment fragment = BlockAdministiveFragment.newInstance(true, "");
                moveFragment(fragment);
            }
        });
    }

    private void moveFragment(Fragment fragment) {
        FragmentManager fragmentManager = ((FragmentActivity) context).getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, fragment)
                .addToBackStack(null)
                .commit();
    }
}
