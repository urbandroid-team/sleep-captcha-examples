package com.urbandroid.sleep.captcha.example;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.urbandroid.slee.captcha.example.R;
import com.urbandroid.sleep.captcha.CaptchaSupport;
import com.urbandroid.sleep.captcha.CaptchaSupportFactory;

public class ReverseCaptchaActivity extends Activity {

    private CaptchaSupport captchaSupport;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        captchaSupport = CaptchaSupportFactory.create(this);

        final TextView difficultyView = (TextView) findViewById(R.id.difficulty);
        difficultyView.setText(getResources().getString(R.string.difficulty, captchaSupport.getDifficulty()));

        findViewById(R.id.done_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                captchaSupport.solved();
                finish();
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        captchaSupport.unsolved();
    }

    @Override
    public void onUserInteraction() {
        super.onUserInteraction();
        captchaSupport.alive();
    }
}
