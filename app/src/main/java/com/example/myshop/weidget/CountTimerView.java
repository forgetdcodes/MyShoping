package com.example.myshop.weidget;

import android.os.CountDownTimer;
import android.widget.TextView;

/**
 * Created by 刘博良 on 2017/5/21.
 */

public class CountTimerView extends CountDownTimer {

    private static final long COUNT_TIME =61*1000;
    private TextView textView;
    public CountTimerView(long millisInFuture, long countDownInterval,
                          TextView textView) {
        super(millisInFuture, countDownInterval);
        this.textView =textView;
    }
    public CountTimerView(TextView textView) {
        super(COUNT_TIME, 1000);
        this.textView =textView;
    }

    @Override
    public void onTick(long millisUntilFinished) {
        textView.setEnabled(false);
        textView.setText(millisUntilFinished / 1000 + " 秒后就可以召唤神龙");
    }

    @Override
    public void onFinish() {
        textView.setEnabled(true);
        textView.setText("老铁，可以重新发送了");
    }
}
