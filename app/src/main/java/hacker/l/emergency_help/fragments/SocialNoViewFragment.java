package hacker.l.emergency_help.fragments;

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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import hacker.l.emergency_help.R;
import hacker.l.emergency_help.adapter.SocialContactsAdapter;
import hacker.l.emergency_help.models.Result;

public class SocialNoViewFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String TYPE = "type";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String type;
    private String mParam2;

    public static SocialNoViewFragment newInstance(String type, String param2) {
        SocialNoViewFragment fragment = new SocialNoViewFragment();
        Bundle args = new Bundle();
        args.putString(TYPE, type);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            type = getArguments().getString(TYPE);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    RecyclerView recycleView;
    View view;
    Context context;
    LinearLayoutManager linearLayoutManager;
    Result result;
    SearchView search_barUser;
    SocialContactsAdapter socialContactsAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        context = getActivity();
        view = inflater.inflate(R.layout.fragment_social_no_view, container, false);
        init();
        return view;
    }

    private void init() {
        recycleView = view.findViewById(R.id.recycleView);
        linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        recycleView.setLayoutManager(linearLayoutManager);
        if (type.equalsIgnoreCase("police")) {
            List<Result> policelist = getPoliceContact();
            socialContactsAdapter = new SocialContactsAdapter(context, policelist);
            recycleView.setAdapter(socialContactsAdapter);
        }
        if (type.equalsIgnoreCase("sakti")) {
            List<Result> saktilist = getSaktiContact();
            socialContactsAdapter = new SocialContactsAdapter(context, saktilist);
            recycleView.setAdapter(socialContactsAdapter);
        }
        if (type.equalsIgnoreCase("tiger")) {
            List<Result> tigerlist = getTigerContact();
            socialContactsAdapter = new SocialContactsAdapter(context, tigerlist);
            recycleView.setAdapter(socialContactsAdapter);
        }
        if (type.equalsIgnoreCase("pcr")) {
            List<Result> pcrlist = getPerContact();
            socialContactsAdapter = new SocialContactsAdapter(context, pcrlist);
            recycleView.setAdapter(socialContactsAdapter);
        }
        if (type.equalsIgnoreCase("highway")) {
            List<Result> highwaylist = getHighwayContact();
            socialContactsAdapter = new SocialContactsAdapter(context, highwaylist);
            recycleView.setAdapter(socialContactsAdapter);
        }
        search_barUser = (SearchView) view.findViewById(R.id.search_barUser);
        search_barUser.setIconified(false);
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
    }

    public List<Result> getPoliceContact() {
        List<Result> PoliceContacts = new ArrayList<>();
        result = new Result("Angara PS", "9431706180");
        PoliceContacts.add(result);
        result = new Result("Argora PS", "9431706170");
        PoliceContacts.add(result);
        result = new Result("Bariyatu PS", "9437106161");
        PoliceContacts.add(result);
        result = new Result("Bero PS", "9431706179");
        PoliceContacts.add(result);
        result = new Result("BIT Mesra OP", "9470590705");
        PoliceContacts.add(result);
        result = new Result("Bundu PS", "9431706191");
        PoliceContacts.add(result);
        result = new Result("Burmu PS", "9431706190");
        PoliceContacts.add(result);
        result = new Result("Chanho PS", "9431706189");
        PoliceContacts.add(result);
        result = new Result("Chutia PS", "9431706165");
        PoliceContacts.add(result);
        result = new Result("Daily Market PS", "9431706163");
        PoliceContacts.add(result);
        result = new Result("Dhurwa PS", "9431706166");
        PoliceContacts.add(result);
        result = new Result("Doranda PS", "9431706168");
        PoliceContacts.add(result);
        result = new Result("Etki PS", "9431706177");
        PoliceContacts.add(result);
        result = new Result("Gonda PS", "9431706162");
        PoliceContacts.add(result);
        result = new Result("Hindipiri PS", "9431706164");
        PoliceContacts.add(result);
        result = new Result("Jagnnathpur PS", "9431706169");
        PoliceContacts.add(result);
        return PoliceContacts;
    }

    public List<Result> getSaktiContact() {
        List<Result> saktiContacts = new ArrayList<>();
        result = new Result("sakyi PS", "9431706180");
        saktiContacts.add(result);
        result = new Result("Argora PS", "9431706170");
        saktiContacts.add(result);
        return saktiContacts;
    }

    public List<Result> getHighwayContact() {
        List<Result> highwayContacts = new ArrayList<>();
        result = new Result("highway PS", "9431706180");
        highwayContacts.add(result);
        result = new Result("Argora PS", "9431706170");
        highwayContacts.add(result);
        return highwayContacts;
    }

    public List<Result> getPerContact() {
        List<Result> pcrContacts = new ArrayList<>();
        result = new Result("pcr", "9431706180");
        pcrContacts.add(result);
        result = new Result("Argora PS", "9431706170");
        pcrContacts.add(result);
        return pcrContacts;
    }

    public List<Result> getTigerContact() {
        List<Result> tigerContacts = new ArrayList<>();
        result = new Result("Tiger PS", "9431706180");
        tigerContacts.add(result);
        result = new Result("Argora PS", "9431706170");
        tigerContacts.add(result);
        return tigerContacts;
    }
}
