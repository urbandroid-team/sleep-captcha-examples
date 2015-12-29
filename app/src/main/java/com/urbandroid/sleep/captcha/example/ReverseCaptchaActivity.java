package com.urbandroid.sleep.captcha.example;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.urbandroid.sleep.captcha.CaptchaSupport;
import com.urbandroid.sleep.captcha.CaptchaSupportFactory;
import com.urbandroid.sleep.captcha.RemainingTimeListener;

import java.util.Random;

// this is the main captcha activity
public class ReverseCaptchaActivity extends Activity {

    private CaptchaSupport captchaSupport; // include this in every captcha

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        captchaSupport = CaptchaSupportFactory.create(this); // include this in every captcha, in onCreate()

        // show timeout in TextView with id "timeout"
        final TextView timeoutView = (TextView) findViewById(R.id.timeout);
        captchaSupport.setRemainingTimeListener(new RemainingTimeListener() {
            @Override
            public void timeRemain(int seconds, int aliveTimeout) {
                timeoutView.setText(seconds + "/" + aliveTimeout);
            }
        });

        // show difficulty in TextView with id "difficulty", read from captchaSupport.getDifficulty()
        final TextView difficultyView = (TextView) findViewById(R.id.difficulty);
        difficultyView.setText(getResources().getString(R.string.difficulty, captchaSupport.getDifficulty()));

        // show a random string of length = (difficulty * 3) in TextView with id "captcha_text"
        final TextView captchaTextView = (TextView) findViewById(R.id.captcha_text);
        final String captchaText = randomString(captchaSupport.getDifficulty() * 3);
        captchaTextView.setText(captchaText);


        final EditText input_text = (EditText) findViewById(R.id.input_text);

        // send captchaSupport.solved() when correct string entered and "Done" button tapped
        findViewById(R.id.done_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("ReverseCaptchaText", captchaText);
                Log.i("ReverseCaptchaInput", input_text.getText().toString());
                if (input_text.getText().toString().equals(new StringBuilder(captchaText).reverse().toString())) {
                    captchaSupport.solved(); // .solved() broadcasts an intent back to Sleep as Android to let it know that captcha is solved
                    finish();
                }
            }
        });

    }

    public static String randomString(int length) {
        char[] chars = "abcdefghijklmnopqrstuvwxyz".toCharArray();
        Random generator = new Random();
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < length; i++ ) {
            char c = chars[generator.nextInt(chars.length)];
            sb.append(c);
        }
        return sb.toString();
    }

    @Override
    protected void onResume() {
        super.onResume();
        final EditText input_text = (EditText) findViewById(R.id.input_text);
        input_text.requestFocus();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        captchaSupport.unsolved(); // .unsolved() broadcasts an intent back to AlarmAlertFullScreen that captcha was not solved
    }

    @Override
    public void onUserInteraction() {
        super.onUserInteraction();
        captchaSupport.alive(); // .alive() refreshes captcha timeout - intended to be sent on user interaction primarily, but can be called anytime anywhere
    }
}
