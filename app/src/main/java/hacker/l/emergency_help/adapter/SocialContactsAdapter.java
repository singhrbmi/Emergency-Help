package hacker.l.emergency_help.adapter;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.RecyclerView;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import hacker.l.emergency_help.R;
import hacker.l.emergency_help.models.Result;

/**
 * Created by lalitsingh on 05/03/18.
 */

public class SocialContactsAdapter extends RecyclerView.Adapter<SocialContactsAdapter.MyViewHolder> implements Filterable {
    private Typeface materialdesignicons_font, ProximaNovaRegular, ProximaNovaLight;
    private Context mContext;
    private List<Result> dataList, FilteruserList;

    public SocialContactsAdapter(Context mContext, List<Result> dataList) {
        this.mContext = mContext;
        this.dataList = dataList;
        this.FilteruserList = dataList;
//        this.ProximaNovaRegular = FontManager.getFontTypeface(mContext, "fonts/ProximaNova-Regular.otf");
//        this.ProximaNovaLight = FontManager.getFontTypeface(mContext, "fonts/ProximaNova-Light.otf");
//        this.materialdesignicons_font = FontManager.getFontTypefaceMaterialDesignIcons(mContext, "fonts/materialdesignicons-webfont.otf");
    }

    @Override
    public SocialContactsAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_social_contacts, parent, false);
        return new SocialContactsAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(SocialContactsAdapter.MyViewHolder holder, final int position) {
        String name = FilteruserList.get(position).getName();
        holder.name.setText(name);
        holder.phone.setText(FilteruserList.get(position).getPhone());
        holder.sms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String phone = FilteruserList.get(position).getPhone();
                try {
                    String msg = "test message";
                        SmsManager smsManager = SmsManager.getDefault();
                        smsManager.sendTextMessage(phone, null, msg, null, null);
                        Toast.makeText(mContext, "SMS sent.On" + phone,
                                Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    Toast.makeText(mContext,
                            "SMS faild, Please try Again.",
                            Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        });
        holder.call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String phone = FilteruserList.get(position).getPhone();
                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:" + phone));//change the number
                    if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return;
                    }
                    mContext.startActivity(callIntent);

            }
        });
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString().trim();
                // name match condition. this might differ depending on your requirement
                // here we are looking for name or phone number match
                if (charString.isEmpty()) {
                    FilteruserList = dataList;
                } else {
                    List<Result> filteredList = new ArrayList<>();
                    for (Result row : dataList) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getName().toLowerCase().trim().contains(charString.toLowerCase()) | row.getPhone().toLowerCase().trim().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }


                    FilteruserList = filteredList;
                }


                FilterResults filterResults = new FilterResults();
                filterResults.values = FilteruserList;
                return filterResults;
            }


            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                FilteruserList = (ArrayList<Result>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    @Override
    public int getItemCount() {
        return FilteruserList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name, phone;
        ImageView sms, call;

        public MyViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name);
            phone = (TextView) itemView.findViewById(R.id.phone);
            sms = (ImageView) itemView.findViewById(R.id.sms);
            call = (ImageView) itemView.findViewById(R.id.call);
        }

    }

}
