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
import android.widget.TextView;
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
    TextView tv_type;
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
        tv_type = view.findViewById(R.id.type);
        linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        recycleView.setLayoutManager(linearLayoutManager);
        if (type.equalsIgnoreCase("police")) {
            List<Result> policelist = getPoliceContact();
            socialContactsAdapter = new SocialContactsAdapter(context, policelist);
            recycleView.setAdapter(socialContactsAdapter);
            tv_type.setText("Police Station List");
        }
        if (type.equalsIgnoreCase("sakti")) {
            List<Result> saktilist = getSaktiContact();
            socialContactsAdapter = new SocialContactsAdapter(context, saktilist);
            recycleView.setAdapter(socialContactsAdapter);
            tv_type.setText("Sakti Commando List");
        }
        if (type.equalsIgnoreCase("tiger")) {
            List<Result> tigerlist = getTigerContact();
            socialContactsAdapter = new SocialContactsAdapter(context, tigerlist);
            recycleView.setAdapter(socialContactsAdapter);
            tv_type.setText("Tiger Mobile List");
        }
        if (type.equalsIgnoreCase("pcr")) {
            List<Result> pcrlist = getPerContact();
            socialContactsAdapter = new SocialContactsAdapter(context, pcrlist);
            recycleView.setAdapter(socialContactsAdapter);
            tv_type.setText("PCR Number List");
        }
        if (type.equalsIgnoreCase("highway")) {
            List<Result> highwaylist = getHighwayContact();
            socialContactsAdapter = new SocialContactsAdapter(context, highwaylist);
            recycleView.setAdapter(socialContactsAdapter);
            tv_type.setText("Highway Number List");
        }
        if (type.equalsIgnoreCase("policeNo")) {
            List<Result> policeNoList = getPoliceNoListContact();
            socialContactsAdapter = new SocialContactsAdapter(context, policeNoList);
            recycleView.setAdapter(socialContactsAdapter);
            tv_type.setText("Police Number List");
        }
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
    }

    private List<Result> getPoliceNoListContact() {
        List<Result> PoliceNoContacts = new ArrayList<>();
        result = new Result("A V Homkar,IPS", "9431706118", "DIG,SC RANGE,RANCHI", "2481876");
        PoliceNoContacts.add(result);
        result = new Result("KULDEEP DWIVEDI,IPS", "9431706136", "SR.S.P,RANCHI", "2200237");
        PoliceNoContacts.add(result);
        result = new Result("AMAN KUMAR,IPS", "9431706137", "CITY .S.P,RANCHI", "2200898");
        PoliceNoContacts.add(result);
        result = new Result("AJIT PETER DUNGDUNG", "9431706138", "RURAL SP,RANCHI", "2200238");
        PoliceNoContacts.add(result);
        result = new Result("SANJAY RANJAN SINGH,IPS", "9431706140", "TRAFFIC S.P,RANCHI", "2206266");
        PoliceNoContacts.add(result);
        result = new Result("BHOLA PRASAD SINGH", "9431770077", "DSP,KOTWALI", "2212680");
        PoliceNoContacts.add(result);
        result = new Result("BHOLA PRASAD SINGH", "9431375451", "DSP,KOTWALI", "2212680");
        PoliceNoContacts.add(result);
        return PoliceNoContacts;
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
        result = new Result("Kanke PS", "9431706185");
        PoliceContacts.add(result);
        result = new Result("Khelari PS", "9431706188");
        PoliceContacts.add(result);
        result = new Result("Kotwali PS", "9431706158");
        PoliceContacts.add(result);
        result = new Result("Lalpur PS", "9431706159");
        PoliceContacts.add(result);
        result = new Result("Lapung PS", "9431706178");
        PoliceContacts.add(result);
        result = new Result("Lower Bazar PS", "9431706171");
        PoliceContacts.add(result);
        result = new Result("Mackluskiganj PS", "9934902010");
        PoliceContacts.add(result);
        result = new Result("Mahila PS", "9431746865");
        PoliceContacts.add(result);
        result = new Result("Mandar PS", "9431706187");
        PoliceContacts.add(result);
        result = new Result("Muri OP PS", "9431727241");
        PoliceContacts.add(result);
        result = new Result("Nagri PS", "9431706176");
        PoliceContacts.add(result);
        result = new Result("Namkum PS", "9431706173");
        PoliceContacts.add(result);
        result = new Result("Narkopi PS", "9430182082");
        PoliceContacts.add(result);
        result = new Result("Oramanjhi PS", "9431706183");
        PoliceContacts.add(result);
        result = new Result("Pandra PS", "9431369111");
        PoliceContacts.add(result);
        result = new Result("Pithoria PS", "9431706186");
        PoliceContacts.add(result);
        result = new Result("Pundag PS", "9431163177");
        PoliceContacts.add(result);
        result = new Result("Rahe OP PS", "9431706194");
        PoliceContacts.add(result);
        result = new Result("Ratu PS", "9431706175");
        PoliceContacts.add(result);
        result = new Result("Sadar PS", "9431706160");
        PoliceContacts.add(result);
        result = new Result("Sikidiri PS", "9431706184");
        PoliceContacts.add(result);
        result = new Result("Silli PS", "9431217120");
        PoliceContacts.add(result);
        result = new Result("Sonahatu PS", "9431706192");
        PoliceContacts.add(result);
        result = new Result("ST/SC PS", "9431177785");
        PoliceContacts.add(result);
        result = new Result("Sukhdeo Nagar PS", "9431187548");
        PoliceContacts.add(result);
        result = new Result("Tamar PS", "9431706193");
        PoliceContacts.add(result);
        result = new Result("Tatisilway PS", "9431706174");
        PoliceContacts.add(result);
        result = new Result("Traffic PS", "9431706172");
        PoliceContacts.add(result);
        result = new Result("Tupudana PS", "94319431706167163177");
        PoliceContacts.add(result);
        return PoliceContacts;
    }

    public List<Result> getSaktiContact() {
        List<Result> saktiContacts = new ArrayList<>();
        result = new Result("Lower Bazar Thana", "7070194350");
        saktiContacts.add(result);
        result = new Result("Lower Bazar Thana", "7070194351");
        saktiContacts.add(result);
        result = new Result("Lalpur Thana", "7070194352");
        saktiContacts.add(result);
        result = new Result("Lalpur Thana", "7070194253");
        saktiContacts.add(result);
        result = new Result("Doranda Thana", "7070194255");
        saktiContacts.add(result);
        result = new Result("Lalpur Thana", "7070194356");
        saktiContacts.add(result);
        result = new Result("Lalpur Thana", "7070194357");
        saktiContacts.add(result);
        result = new Result("Kotwali Thana", "7070194358");
        saktiContacts.add(result);
        result = new Result("Kotwali Thana", "7070194259");
        saktiContacts.add(result);
        result = new Result("Doranda Thana", "7070194360");
        saktiContacts.add(result);
        result = new Result("Doranda Thana", "7070194261");
        saktiContacts.add(result);
        result = new Result("Jagannathpur Thana", "7070194362");
        saktiContacts.add(result);
        result = new Result("Chutia Thana", "7070194364");
        saktiContacts.add(result);
        result = new Result("Chutia Thana", "7070194365");
        saktiContacts.add(result);
        result = new Result("Sadar Thana", "7070194267");
        saktiContacts.add(result);
        result = new Result("Sadar Thana", "7070194366");
        saktiContacts.add(result);
        result = new Result("Dhurwa Thana", "7070194368");
        saktiContacts.add(result);
        result = new Result("Dhurwa Thana", "7070194269");
        saktiContacts.add(result);
        return saktiContacts;
    }

    public List<Result> getHighwayContact() {
        List<Result> highwayContacts = new ArrayList<>();
        result = new Result("NAMKUM THANA", "8987790648");
        highwayContacts.add(result);
        result = new Result("BUNDU THANA", "8987790649");
        highwayContacts.add(result);
        result = new Result("TAMAR THANA", "8987790650");
        highwayContacts.add(result);
        result = new Result("NAGRI THANA", "8987790651");
        highwayContacts.add(result);
        result = new Result("NAGRI/BERO THANA", "8987790652");
        highwayContacts.add(result);
        result = new Result("MANDAR/CHANHO THANA", "8987790653");
        highwayContacts.add(result);
        result = new Result("BARIATU/MESRA OP THANA", "8987790654");
        highwayContacts.add(result);
        result = new Result("MESRA OP/ORMANJHI THANA", "8987790655");
        highwayContacts.add(result);
        result = new Result("NAMKUM/TATISILWAY THANA", "8987790656");
        highwayContacts.add(result);
        result = new Result("ANGARA/MURI OP THANA", "8987790657");
        highwayContacts.add(result);
        result = new Result("CHANHO THANA", "8987790658");
        highwayContacts.add(result);
        result = new Result("RATU THANA", "8987790659");
        highwayContacts.add(result);
        result = new Result("PITHORIA THANA", "8987790660");
        highwayContacts.add(result);
        result = new Result("TUPUDANA THANA", "8987790661");
        highwayContacts.add(result);
        result = new Result("ORMANJHI THANA", "8987790662");
        highwayContacts.add(result);
        return highwayContacts;
    }

    public List<Result> getPerContact() {
        List<Result> pcrContacts = new ArrayList<>();
        result = new Result("Kotwali Thana", "8987790717");
        pcrContacts.add(result);
        result = new Result("ower Bazar/Chutia Thana", "8987790718");
        pcrContacts.add(result);
        result = new Result("Doranda Thana", "8987790719");
        pcrContacts.add(result);
        result = new Result("Dhurwa Thana", "8987790720");
        pcrContacts.add(result);
        result = new Result("Argora Thana", "8987790721");
        pcrContacts.add(result);
        result = new Result("Lower Bazar Thana", "8987790722");
        pcrContacts.add(result);
        result = new Result("Lalpur Thana", "8987790723");
        pcrContacts.add(result);
        result = new Result("Chutia/Lower Bazar Thana", "8987790724");
        pcrContacts.add(result);
        result = new Result("Bariatu Thana", "8987790725");
        pcrContacts.add(result);
        result = new Result("Gonda Thana", "8987790726");
        pcrContacts.add(result);
        result = new Result("Nagri Thana", "8987790727");
        pcrContacts.add(result);
        result = new Result("Lalpur/Bariatu Thana", "8987790728");
        pcrContacts.add(result);
        result = new Result("Jagannathpur Thana", "8987790729");
        pcrContacts.add(result);
        result = new Result("Jagannathpur Thana", "8987790730");
        pcrContacts.add(result);
        result = new Result("Doranda Thana", "8987790731");
        pcrContacts.add(result);
        result = new Result("Jagannathpur Thana", "8987790732");
        pcrContacts.add(result);
        result = new Result("TUPUDANA Thana", "8987790733");
        pcrContacts.add(result);
        result = new Result("Namkum Thana", "8987790734");
        pcrContacts.add(result);
        result = new Result("Namkum Thana", "8987790735");
        pcrContacts.add(result);
        result = new Result("Chutia Thana", "8987790736");
        pcrContacts.add(result);
        result = new Result("Chutia Thana", "8987790737");
        pcrContacts.add(result);
        result = new Result("Sadar Thana", "8987790738");
        pcrContacts.add(result);
        result = new Result("Lalpur Thana", "8987790739");
        pcrContacts.add(result);
        result = new Result("Lower Bazar Thana", "8987790740");
        pcrContacts.add(result);
        result = new Result("Lalpur/Kotwali Thana", "8987790741");
        pcrContacts.add(result);
        result = new Result("Pundag OP Thana", "8987790742");
        pcrContacts.add(result);
        result = new Result("Bariatu Thana", "8987790743");
        pcrContacts.add(result);
        result = new Result("Sukhdev Nagar Thana", "8987790744");
        pcrContacts.add(result);
        result = new Result("Ratu Thana", "8987790745");
        pcrContacts.add(result);
        result = new Result("Khelgaon OP Thana", "8987790746");
        pcrContacts.add(result);
        return pcrContacts;
    }

    public List<Result> getTigerContact() {
        List<Result> tigerContacts = new ArrayList<>();
        result = new Result("Argora Thana", "8987790751");
        tigerContacts.add(result);
        result = new Result("Argora Thana", "8987790752");
        tigerContacts.add(result);
        result = new Result("Bariatu Thana", "8987790753");
        tigerContacts.add(result);
        result = new Result("Bariatu Thana", "8987790754");
        tigerContacts.add(result);
        result = new Result("Chutia Thana", "8987790755");
        tigerContacts.add(result);
        result = new Result("Chutia Thana", "8987790756");
        tigerContacts.add(result);
        result = new Result("Chutia Thana", "8987790757");
        tigerContacts.add(result);
        result = new Result("Chutia Thana", "8987790758");
        tigerContacts.add(result);
        result = new Result("Doranda Thana", "8987790759");
        tigerContacts.add(result);
        result = new Result("DORANDA Thana", "8987790760");
        tigerContacts.add(result);
        result = new Result("DORANDA Thana", "8987790761");
        tigerContacts.add(result);
        result = new Result("DORANDA Thana", "8987790762");
        tigerContacts.add(result);
        result = new Result("Gonda Thana", "8987790763");
        tigerContacts.add(result);
        result = new Result("Gonda Thana", "8987790764");
        tigerContacts.add(result);
        result = new Result("Gonda Thana", "8987790765");
        tigerContacts.add(result);
        result = new Result("Dhurwa Thana", "8987790766");
        tigerContacts.add(result);
        result = new Result("Dhurwa Thana", "8987790767");
        tigerContacts.add(result);
        result = new Result("Jaganthpur Thana", "8987790768");
        tigerContacts.add(result);
        result = new Result("Jaganthpur Thana", "8987790769");
        tigerContacts.add(result);
        result = new Result("Jaganthpur Thana", "8987790771");
        tigerContacts.add(result);
        result = new Result("Tupudana Thana", "8987790770");
        tigerContacts.add(result);
        result = new Result("Kotwali Thana", "8987790772");
        tigerContacts.add(result);
        result = new Result("Kotwali Thana", "8987790773");
        tigerContacts.add(result);
        result = new Result("Kotwali Thana", "8987790774");
        tigerContacts.add(result);
        result = new Result("Kotwali Thana", "8987790775");
        tigerContacts.add(result);
        result = new Result("Kotwali Thana", "8987790776");
        tigerContacts.add(result);
        result = new Result("Kotwali Thana", "8987790777");
        tigerContacts.add(result);
        result = new Result("Lalpur Thana", "8987790778");
        tigerContacts.add(result);
        result = new Result("Lalpur Thana", "8987790779");
        tigerContacts.add(result);
        result = new Result("Lalpur Thana", "8987790780");
        tigerContacts.add(result);
        result = new Result("Lalpur Thana", "8987790781");
        tigerContacts.add(result);
        result = new Result("Lalpur Thana", "8987790782");
        tigerContacts.add(result);
        result = new Result("Lalpur Thana", "8987790783");
        tigerContacts.add(result);
        result = new Result("Lower Bazar Thana", "8987790784");
        tigerContacts.add(result);
        result = new Result("Lower Bazar Thana", "8987790785");
        tigerContacts.add(result);
        result = new Result("Lower Bazar Thana", "8987790786");
        tigerContacts.add(result);
        result = new Result("Lower Bazar Thana", "8987790787");
        tigerContacts.add(result);
        result = new Result("Lower Bazar Thana", "8987790788");
        tigerContacts.add(result);
        result = new Result("Lower Bazar Thana", "8987790789");
        tigerContacts.add(result);
        result = new Result("Pandra OP Thana", "8987790790");
        tigerContacts.add(result);
        result = new Result("Pandra OP Thana", "8987790791");
        tigerContacts.add(result);
        result = new Result("Pandra OP Thana", "8987790792");
        tigerContacts.add(result);
        result = new Result("Sadar Thana", "8987790793");
        tigerContacts.add(result);
        result = new Result("Sadar Thana", "8987790794");
        tigerContacts.add(result);
        result = new Result("Sadar Thana", "8987790795");
        tigerContacts.add(result);
        result = new Result("Sadar Thana", "8987790796");
        tigerContacts.add(result);
        result = new Result("SukhdevNagar Thana", "8987790797");
        tigerContacts.add(result);
        result = new Result("SukhdevNagar Thana", "8987790798");
        tigerContacts.add(result);
        result = new Result("SukhdevNagar Thana", "8987790799");
        tigerContacts.add(result);
        result = new Result("SukhdevNagar Thana", "8987790800");
        tigerContacts.add(result);
        return tigerContacts;
    }
}
