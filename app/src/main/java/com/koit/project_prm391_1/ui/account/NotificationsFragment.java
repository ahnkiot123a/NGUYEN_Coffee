package com.koit.project_prm391_1.ui.account;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.koit.project_prm391_1.LoginActivity;
import com.koit.project_prm391_1.R;
import com.koit.project_prm391_1.object.User;
import de.hdodenhof.circleimageview.CircleImageView;

public class NotificationsFragment extends Fragment {

    private NotificationsViewModel notificationsViewModel;
    private static FirebaseUser currentUser;
    private CircleImageView avatar;
    private TextView tvNameProfile;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel =
                ViewModelProviders.of(this).get(NotificationsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_account, container, false);
        avatar = root.findViewById(R.id.profile_image);
        tvNameProfile = root.findViewById(R.id.tvNameProfile);

        currentUser = FirebaseAuth.getInstance().getCurrentUser();

        tvNameProfile.setText(currentUser.getDisplayName());
        Glide.with(this).load(currentUser.getPhotoUrl()).into(avatar);
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.dontAnimate();

        return root;
    }


}