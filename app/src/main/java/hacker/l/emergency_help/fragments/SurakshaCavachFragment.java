package hacker.l.emergency_help.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import hacker.l.emergency_help.R;


public class SurakshaCavachFragment extends Fragment {
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public static SurakshaCavachFragment newInstance(String param1, String param2) {
        SurakshaCavachFragment fragment = new SurakshaCavachFragment();
        Bundle args = new Bundle();
        args.putString("", param1);
        args.putString("", param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString("");
            mParam2 = getArguments().getString("");
        }
    }

    View view;
    Context context;
    List<String> spinnerList;
    Spinner spinner;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        context = getActivity();
        view = inflater.inflate(R.layout.fragment_suraksha_cavach, container, false);
        intit();
        return view;
    }

    private void intit() {
        spinnerList = new ArrayList<>();
        spinnerList.add("Select Any One");
        spinnerList.add("Sri Maheswari Sabha,Ranchi");
        spinnerList.add("Lions Club of Ranchi Central");
        spinnerList.add("Rotaract Club of Ranchi City");
        spinnerList.add("Marwari Yuwa Manch,Ranchi South");
        spinnerList.add("Lions Club of Ranchi North");
        spinnerList.add("Lions Club of Ranchi Citizen");
        spinnerList.add("Rotaract Club of ICWAI");
        spinnerList.add("Rotaract Club of Social Revolution");
        spinnerList.add("Rotaract Club of NIFFT");
        spinnerList.add("Rotaract Club of St.Xavier's College");
        spinnerList.add("Rotaract Club of Kalimati");
        spinnerList.add("Rotaract Club of Chaibase");
        spinnerList.add("Rotaract Club of BIT MESRA");
        spinnerList.add("FJCCI");
        spinner = view.findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, spinnerList);
        spinner.setAdapter(adapter);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setSelection(0, true);
        View v = spinner.getSelectedView();
        ((TextView) v).setTextColor(getResources().getColor(R.color.grey_light));
        ((TextView) v).setTextSize(14);
    }

}
