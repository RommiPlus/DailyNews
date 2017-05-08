package com.dailynews.dailynews;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import com.blankj.utilcode.util.SpannableStringUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WelcomeActivity extends Activity {

    @BindView(R.id.text_view)
    TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        ButterKnife.bind(this);

        Typeface face = Typeface.createFromAsset(getAssets(), "Satisfy-Regular.ttf");
        mTextView.setTypeface(face);

         CharSequence text = SpannableStringUtils.getBuilder(getString(R.string.daily))
                .setForegroundColor(getResources().getColor(R.color.red))
                .append(getString(R.string.news))
                .setForegroundColor(getResources().getColor(R.color.black))
                .create();
        mTextView.setText(text);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(WelcomeActivity.this, HomePageActivity.class));
                finish();
            }
        }, 3000);
    }
}
