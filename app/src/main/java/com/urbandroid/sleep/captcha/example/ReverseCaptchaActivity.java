package com.urbandroid.sleep.captcha.example;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.urbandroid.slee.captcha.example.R;
import com.urbandroid.sleep.captcha.CaptchaConstant;
import com.urbandroid.sleep.captcha.CaptchaSupport;
import com.urbandroid.sleep.captcha.CaptchaSupportFactory;
import com.urbandroid.sleep.captcha.annotation.CaptchaDifficulty;
import com.urbandroid.sleep.captcha.domain.CaptchaInfo;

public class ReverseCaptchaActivity extends Activity {

    private CaptchaSupport captchaSupport;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_even_odd_captcha);

        captchaSupport = CaptchaSupportFactory.create(this);

        final TextView modeView = (TextView) findViewById(R.id.mode);
        if (captchaSupport.isPreviewMode()) {
            modeView.setText("PREVIEW MODE");
        } else if (captchaSupport.isConfigurationMode()) {
            modeView.setText("CONFIGURATION MODE");
        } else {
            modeView.setText("");
        }

        final TextView difficultyView = (TextView) findViewById(R.id.difficulty);
        difficultyView.setText("DIFFICULTY: " + captchaSupport.getDifficulty());

        findViewById(R.id.solve_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                captchaSupport.solved();
                finish();
            }
        });

        findViewById(R.id.launch_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final CaptchaInfo fallbackCaptcha = captchaSupport.getFinder().findById(CaptchaInfo.FALLBACK_ID);
                Log.i(CaptchaConstant.TAG, "--- CAPTCHA FINDER: " + captchaSupport.getFinder());
                Log.i(CaptchaConstant.TAG, "--- LAUNCHING captcha: " + fallbackCaptcha);

                captchaSupport.getLauncher()
                        .difficulty(CaptchaDifficulty.SIMPLE)
                        .start(fallbackCaptcha);

            }
        });

        findViewById(R.id.alive_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                captchaSupport.alive();
            }
        });
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        captchaSupport.alive();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        captchaSupport.unsolved();
    }
}
