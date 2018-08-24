package com.mt.countdown;

import android.content.Context;
import android.os.CountDownTimer;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.TextView;

public class CountDownView extends TextView {
    public int totalTime; // 单位：秒

    private int remainTime; // 单位：秒
    private int duration; // 单位：秒   计时时长
    private int durationTemp;

    private OnTimerListener onTimerListener;
    private CountDownTimer countDownTimer;

    public CountDownView(Context context) {
        this(context, null);
    }

    public CountDownView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CountDownView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 重新开始
     */
    public void start() {
        duration = 0;
        durationTemp = 0;

        startTimer(totalTime);
    }

    /**
     * 结束
     */
    public void stop() {
        if (countDownTimer == null) {
            return;
        }

        duration = 0;
        durationTemp = 0;
        countDownTimer.cancel();

        resetText(totalTime);
    }

    /**
     * 暂停
     */
    public void pause() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }

        durationTemp = duration;
    }

    /**
     * 继续
     */
    public void goon() {
        startTimer(remainTime);
    }

    public void startTimer(int totalTime) {
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }

        countDownTimer = new CountDownTimer(totalTime * 1000, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                remainTime = (int) (millisUntilFinished / 1000);

                if (onTimerListener != null) {
                    if (durationTemp != 0) {
                        duration = durationTemp + totalTime - remainTime;
                    } else {
                        duration = totalTime - remainTime;
                    }
                    onTimerListener.onTick(duration);
                }

                resetText((int) (millisUntilFinished / 1000));
            }

            @Override
            public void onFinish() {
                Log.e("onTick", "结束");
            }
        }.start();
    }

    public int getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(int totalTime) {
        this.totalTime = totalTime;
        resetText(totalTime);
    }

    public void setOnTimerListener(OnTimerListener onTimerListener) {
        this.onTimerListener = onTimerListener;
    }

    private void resetText(int totalSec) {
        int day = totalSec / (60 * 60 * 24);
        int hour = (totalSec - day * 60 * 60 * 24) / (60 * 60);
        int min = (totalSec - (hour * 60 * 60) - day * 60 * 60 * 24) / 60;
        int sec = totalSec % 60;

        String secStr = sec >= 10 ? String.valueOf(sec) : "0" + sec;
        String minStr = min >= 10 ? String.valueOf(min) : "0" + min;
        String hourStr = hour >= 10 ? String.valueOf(hour) : "0" + hour;
        String dayStr = day >= 10 ? String.valueOf(day) : "0" + day;

        setText(String.format("%s:%s:%s:%s", dayStr, hourStr, minStr, secStr));
    }

    public interface OnTimerListener {

        /**
         * 计时时长
         *
         * @param duration 秒
         */
        void onTick(int duration);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();

        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }
}
