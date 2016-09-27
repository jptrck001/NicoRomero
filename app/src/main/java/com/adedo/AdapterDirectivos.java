package com.adedo;

import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.facebook.messenger.MessengerUtils;
import com.facebook.messenger.MessengerThreadParams;
import com.facebook.messenger.ShareToMessengerParams;

/**
 * Created by Rulo-PC on 23/4/2016.
 */
public class AdapterDirectivos extends BaseAdapter {

    protected Activity activity;
    protected ArrayList<Item_viaje> items = new ArrayList<>();
    Button sendMail;

    public AdapterDirectivos(Activity activity, Set<Item_viaje> items) {
        this.activity = activity;
        this.items.addAll(items);
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int arg0) {
        return items.get(arg0);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;
        if (convertView == null) {
            LayoutInflater inf = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inf.inflate(R.layout.day_item, null);
        }

        sendMail = (Button) v.findViewById(R.id.sendMail);
        sendMail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RelativeLayout rl = (RelativeLayout) v.getParent();
                TextView tv = (TextView) rl.findViewById(R.id.mailValue);
                String mail = tv.getText().toString();

                Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
                emailIntent.setType("text/plain");
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Contacto desde A Dedo");
                emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{mail.trim()});
                final PackageManager pm = activity.getPackageManager();
                final List<ResolveInfo> matches = pm.queryIntentActivities(emailIntent, 0);
                ResolveInfo best = null;
                for (final ResolveInfo info : matches) {
                    if (info.activityInfo.packageName.endsWith(".gm") || info.activityInfo.name.toLowerCase().contains("gmail"))
                        best = info;
                    if (best != null)
                        emailIntent.setClassName(best.activityInfo.packageName, best.activityInfo.name);
                }

                activity.startActivity(emailIntent);


                //Facebook messenger POC
                /*String text = activity.getResources().getString(R.string.message_facebook);

                Uri uri = Uri.parse("fb-messenger://user/");
                uri = ContentUris.withAppendedId(uri, Long.parseLong("10209292729105833"));

                Intent sendIntent = new Intent(Intent.ACTION_VIEW, uri);

                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, text);
                sendIntent.setType("text/plain");
                sendIntent.setPackage("https://www.facebook.com/messages/");
                //startActivity(intent);

                try {
                    activity.startActivity(sendIntent);
                }
                catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(activity,"Please Install Facebook Messenger", Toast.LENGTH_LONG).show();
                }*/

            }
        });

        Item_viaje dir = items.get(position);

        TextView tipo = (TextView) v.findViewById(R.id.tipo);
        if (dir.getCantidad_asientos_disponibles() > 10){
            tipo.setText("Pasajero");
        }else{
            tipo.setText("Chofer");
        }

        TextView nombre = (TextView) v.findViewById(R.id.name_value);
        nombre.setText(dir.getNombre().split(",")[0]);

        TextView partida = (TextView) v.findViewById(R.id.origen_value);
        partida.setText(dir.getPartida());

        TextView llegada = (TextView) v.findViewById(R.id.dest_value);
        llegada.setText(dir.getLlegada());

        TextView hora = (TextView) v.findViewById(R.id.hour_value);
        hora.setText(dir.getHora() + ":00");

        TextView mailValue = (TextView) v.findViewById(R.id.mailValue);
        mailValue.setText(dir.getMailc());

        return v;
    }
}