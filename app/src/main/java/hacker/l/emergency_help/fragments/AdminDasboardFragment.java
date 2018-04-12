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

public class AdminDasboardFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    // TODO: Rename and change types and number of parameters
    public static AdminDasboardFragment newInstance(String param1, String param2) {
        AdminDasboardFragment fragment = new AdminDasboardFragment();
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
    TextView tv_phone, tv_advise, tv_complent;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        context = getActivity();
        view = inflater.inflate(R.layout.fragment_admin_dasboard, container, false);
        init();
        return view;
    }

    private void init() {
        tv_phone = view.findViewById(R.id.tv_phone);
        tv_advise = view.findViewById(R.id.tv_advise);
        tv_complent = view.findViewById(R.id.tv_complent);
        tv_phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AdminPhoneMgmtFragment fragment = AdminPhoneMgmtFragment.newInstance("", "");
                moveFragment(fragment);
            }
        });
        tv_advise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AdminAdviseMgmtFragment adminAdviseMgmtFragment = AdminAdviseMgmtFragment.newInstance("", "");
                moveFragment(adminAdviseMgmtFragment);
            }
        });
        tv_complent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AdminComplentMgmtFragment adminAdviseMgmtFragment = AdminComplentMgmtFragment.newInstance("", "");
                moveFragment(adminAdviseMgmtFragment);
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
