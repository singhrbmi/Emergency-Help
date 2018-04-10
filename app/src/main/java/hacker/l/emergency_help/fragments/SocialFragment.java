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
import android.widget.Button;

import hacker.l.emergency_help.R;

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
    Button btn_police, btn_sakticomd, btn_tiger, btn_pcr, btn_policeNo, btn_highway;

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
        btn_police = view.findViewById(R.id.btn_police);
        btn_sakticomd = view.findViewById(R.id.btn_sakticomd);
        btn_tiger = view.findViewById(R.id.btn_tiger);
        btn_pcr = view.findViewById(R.id.btn_pcr);
        btn_highway = view.findViewById(R.id.btn_highway);
        btn_policeNo = view.findViewById(R.id.btn_policeNo);
        btn_police.setOnClickListener(this);
        btn_sakticomd.setOnClickListener(this);
        btn_tiger.setOnClickListener(this);
        btn_pcr.setOnClickListener(this);
        btn_highway.setOnClickListener(this);
        btn_policeNo.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_police:
                SocialNoViewFragment fragment = SocialNoViewFragment.newInstance("police", "");
                moveFragment(fragment);
                break;
            case R.id.btn_sakticomd:
                SocialNoViewFragment fragmentSakti = SocialNoViewFragment.newInstance("sakti", "");
                moveFragment(fragmentSakti);
                break;
            case R.id.btn_tiger:
                SocialNoViewFragment fragmenttiger = SocialNoViewFragment.newInstance("tiger", "");
                moveFragment(fragmenttiger);
                break;
            case R.id.btn_pcr:
                SocialNoViewFragment fragmentPcr = SocialNoViewFragment.newInstance("pcr", "");
                moveFragment(fragmentPcr);
                break;
            case R.id.btn_highway:
                SocialNoViewFragment fragmenthighway = SocialNoViewFragment.newInstance("highway", "");
                moveFragment(fragmenthighway);
                break;
            case R.id.btn_policeNo:
                SocialNoViewFragment fragmentPolice = SocialNoViewFragment.newInstance("policeNo", "");
                moveFragment(fragmentPolice);
                break;
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
