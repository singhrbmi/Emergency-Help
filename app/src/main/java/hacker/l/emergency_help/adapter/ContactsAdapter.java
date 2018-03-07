package hacker.l.emergency_help.adapter;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Collections;
import java.util.List;

import hacker.l.emergency_help.R;
import hacker.l.emergency_help.models.Result;

/**
 * Created by lalitsingh on 05/03/18.
 */

public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.MyViewHolder> {
    private Typeface materialdesignicons_font, ProximaNovaRegular, ProximaNovaLight;
    private Context mContext;
    private List<Result> dataList;
    String cutPhone;

    public ContactsAdapter(Context mContext, List<Result> dataList) {
        this.mContext = mContext;
        this.dataList = dataList;
//        this.ProximaNovaRegular = FontManager.getFontTypeface(mContext, "fonts/ProximaNova-Regular.otf");
//        this.ProximaNovaLight = FontManager.getFontTypeface(mContext, "fonts/ProximaNova-Light.otf");
//        this.materialdesignicons_font = FontManager.getFontTypefaceMaterialDesignIcons(mContext, "fonts/materialdesignicons-webfont.otf");
    }

    @Override
    public ContactsAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_contacts, parent, false);
        return new ContactsAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ContactsAdapter.MyViewHolder holder, final int position) {
        String name = dataList.get(position).getName();
        holder.name.setText(name);
        holder.phone.setText(dataList.get(position).getPhone());
        holder.sms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String phone = dataList.get(position).getPhone();
                cutPhoneFun(phone);
                try {
                    String msg = "test message";
                    if (cutPhone != null) {
                        SmsManager smsManager = SmsManager.getDefault();
                        smsManager.sendTextMessage(cutPhone, null, msg, null, null);
                        Toast.makeText(mContext, "SMS sent." + cutPhone,
                                Toast.LENGTH_LONG).show();
                    } else {
                        SmsManager smsManager = SmsManager.getDefault();
                        smsManager.sendTextMessage(phone, null, msg, null, null);
                        Toast.makeText(mContext, "SMS sent." + cutPhone,
                                Toast.LENGTH_LONG).show();
                    }
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
                final String phone = dataList.get(position).getPhone();
                cutPhoneFun(phone);
                if (cutPhone != null) {
                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:" + cutPhone));//change the number
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

                } else {
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
            }
        });
    }

    public void cutPhoneFun(String phone) {
        if (phone.startsWith("+91")) {
            cutPhone = phone.substring(3);
        }
        if (phone.startsWith("0")) {
            cutPhone = phone.substring(1);
        }
    }

    @Override
    public int getItemCount() {
        return dataList.size();
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
