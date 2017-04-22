package com.adedo;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Color;
import android.net.Uri;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by raulstriglio on 4/17/17.
 */

public class DayTripsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    protected List<Item_viaje> mItems = new ArrayList<>();
    private Context context;
    private IActivityCallBack iActivityCallBack;

    public DayTripsAdapter(Context context, Set<Item_viaje> items, IActivityCallBack activityCallBack) {
        this.context = context;
        mItems.addAll(items);
        iActivityCallBack = activityCallBack;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.day_item, parent, false);
        return new DayTripViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof DayTripViewHolder) {

            final DayTripViewHolder dayTripViewHolder = (DayTripViewHolder) holder;
            final Item_viaje item_viaje = mItems.get(position);

            dayTripViewHolder.sendMail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    RelativeLayout rl = (RelativeLayout) v.getParent();
                    TextView tv = (TextView) rl.findViewById(R.id.mailValue);
                    String mail = tv.getText().toString();

                    Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
                    emailIntent.setType("text/plain");
                    emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Contacto desde A Dedo");
                    emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[] {mail.trim()});
                    final PackageManager pm = context.getPackageManager();
                    final List<ResolveInfo> matches = pm.queryIntentActivities(emailIntent, 0);
                    ResolveInfo best = null;
                    for (final ResolveInfo info : matches) {
                        if (info.activityInfo.packageName.endsWith(".gm") || info.activityInfo.name.toLowerCase().contains("gmail"))
                            best = info;
                        if (best != null)
                            emailIntent.setClassName(best.activityInfo.packageName, best.activityInfo.name);
                    }
                    context.startActivity(emailIntent);
                }
            });

            if (item_viaje.getCantidad_asientos_disponibles() > 10) {
                dayTripViewHolder.tipo.setText("Pasajero");
                dayTripViewHolder.tipo.setBackgroundColor(context.getResources().getColor(R.color.Orange));
            } else {
                dayTripViewHolder.tipo.setText("Chofer");
                dayTripViewHolder.tipo.setBackgroundColor(Color.BLUE);
            }

            if (item_viaje.getPerfil() == null || item_viaje.getPerfil().isEmpty() || item_viaje.getPerfil().equals("null")) {
                dayTripViewHolder.viewPrfile.setVisibility(View.GONE);
            } else {
                dayTripViewHolder.viewPrfile.setVisibility(View.VISIBLE);
                dayTripViewHolder.viewPrfile.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(item_viaje.getPerfil().contains("google")){
                            iActivityCallBack.callGplusProfile(item_viaje.getNombre(), item_viaje.getMailc(), item_viaje.getPerfil());
                        }else {
                            getFacebookIntent(item_viaje.getPerfil());
                        }
                    }
                });
            }


            if (item_viaje.getComentarios() == null || item_viaje.getComentarios().isEmpty() || item_viaje.getComentarios().equals("null")) {
                dayTripViewHolder.comentariosValue.setVisibility(View.GONE);
            } else {
                dayTripViewHolder.comentariosValue.setVisibility(View.VISIBLE);
                dayTripViewHolder.comentariosValue.setText(item_viaje.getComentarios());
            }

            dayTripViewHolder.partida.setText(item_viaje.getPartida());
            dayTripViewHolder.llegada.setText(item_viaje.getLlegada());
            dayTripViewHolder.hora.setText(item_viaje.getHora() + ":00");
            dayTripViewHolder.mailValue.setText(item_viaje.getMailc());
        }

    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public class DayTripViewHolder extends RecyclerView.ViewHolder {

        Button sendMail, viewPrfile;
        TextView tipo;
        TextView nombre;
        TextView comentariosValue;
        TextView partida;
        TextView llegada;
        TextView hora;
        TextView mailValue;


        public DayTripViewHolder(View v) {
            super(v);

            nombre = (TextView) v.findViewById(R.id.name_value);
            tipo = (TextView) v.findViewById(R.id.tipo);
            comentariosValue = (TextView) v.findViewById(R.id.comentariosValue);
            partida = (TextView) v.findViewById(R.id.origen_value);
            llegada = (TextView) v.findViewById(R.id.dest_value);
            hora = (TextView) v.findViewById(R.id.hour_value);
            mailValue = (TextView) v.findViewById(R.id.mailValue);
            viewPrfile = (Button) v.findViewById(R.id.viewPrfile);
            sendMail = (Button) v.findViewById(R.id.sendMail);
        }
    }

    public void getFacebookIntent(String url) {

        PackageManager pm = context.getPackageManager();
        Uri uri = Uri.parse(url);

        Intent i = null;

        try {
            ApplicationInfo applicationInfo = pm.getApplicationInfo("com.facebook.katana", 0);
            if (applicationInfo.enabled) {
                uri = Uri.parse("fb://facewebmodal/f?href=" + url);
                i = new Intent(Intent.ACTION_VIEW, uri);
            }
        } catch (PackageManager.NameNotFoundException ignored) {

            i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));

        }

        context.startActivity(i);
    }

    public interface IActivityCallBack{
        void callGplusProfile(String name, String email, String profileUrl);
    }
}
