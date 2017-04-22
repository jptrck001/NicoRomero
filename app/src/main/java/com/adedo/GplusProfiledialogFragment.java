package com.adedo;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import static com.adedo.Constants.EMAIL;
import static com.adedo.Constants.NAME;
import static com.adedo.Constants.PROFILE_PIC;

/**
 * Created by raulstriglio on 4/22/17.
 */

public class GplusProfiledialogFragment extends DialogFragment {

    private RelativeLayout rl_close;
    private RelativeLayout gplusLayout;
    private RelativeLayout trip_info_container;
    private TextView tv_name;
    private TextView tv_email;
    private ImageView imgProfilePic;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.gplus_user_profile, container, false);
        getDialog().setTitle("Perfil Google Plus");

        rl_close = (RelativeLayout) rootView.findViewById(R.id.rl_close);
        trip_info_container = (RelativeLayout) rootView.findViewById(R.id.trip_info_container);
        gplusLayout = (RelativeLayout) rootView.findViewById(R.id.gplusLayout);
        tv_name = (TextView) rootView.findViewById(R.id.tv_name);
        tv_email =  (TextView) rootView.findViewById(R.id.tv_email);
        imgProfilePic =  (ImageView) rootView.findViewById(R.id.imgProfilePic);

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();

        if(getArguments() != null){

            String email = getArguments().getString(EMAIL);
            String name = getArguments().getString(NAME);
            String profilePic = getArguments().getString(PROFILE_PIC);

            if(email != null && !email.isEmpty()){
                tv_email.setText(email);
            }

            if(name != null && !name.isEmpty()){
                tv_name.setText(name);
            }

            if(profilePic != null && !profilePic.isEmpty()){
                Glide.with(getActivity()).load(profilePic)
                     .thumbnail(0.5f)
                     .crossFade()
                     .diskCacheStrategy(DiskCacheStrategy.ALL)
                     .into(imgProfilePic);
            }
        }

        rl_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    @Override
    public void onStop() {
        super.onStop();
    }
}
