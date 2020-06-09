package com.koit.project_prm391_1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.koit.project_prm391_1.authentication.LoginActivityPlus;

public class SplashActivity extends AppCompatActivity {

    private ImageView ivLogoFirstTime, iv1FirstTime, iv2FirstTime;
    private Animation animation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spalash);

        ivLogoFirstTime = findViewById(R.id.ivLogoFirstTime);
        iv1FirstTime = findViewById(R.id.iv1FirstTime);
        iv2FirstTime = findViewById(R.id.iv2FirstTime);

        animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.push_down);
        ivLogoFirstTime.setAnimation(animation);

        animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.push_right);
        iv1FirstTime.setAnimation(animation);


        Thread thread = new Thread(){
            @Override
            public void run() {

                try {
                    sleep(4000);
                    Intent intent = new Intent(getApplicationContext(), LoginActivityPlus.class);
                    startActivity(intent);
                    finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                super.run();
            }
        };
        thread.start();
    }
}
