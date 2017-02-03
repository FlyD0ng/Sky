package com.example.myapplication.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.RelativeLayout;

import com.example.myapplication.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SplashActivity extends AppCompatActivity {

    @BindView(R.id.rl_rootview)
    RelativeLayout mRlRootview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);

        initAnimation();
    }

    private void initAnimation() {
        ObjectAnimator alpha = ObjectAnimator.ofFloat(mRlRootview, "alpha", 0.2f, 1.0f);
        alpha.setDuration(2000).start();//适配器模式我们用这个
        alpha.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                 startActivity(new Intent(getApplicationContext(),MainActivity.class));
                finish();
            }
        });
    }
}
