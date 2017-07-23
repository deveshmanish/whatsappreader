package com.dev.whatsappchatsimulator;

/**
 * Created by Devesh Chaturvedi on 017-17-06-2017.
 */

public class Message {

    private String mSender;
    private String mDate;
    private String mTime;
    private String mMessage;
    private int mOrientation;

    public Message(String sender, String date, String time,String message, int orientation) {
        mSender = sender;
        mDate = date;
        mTime = time;
        mMessage = message;
        mOrientation = orientation;
    }

    public String getmDate() {
        return mDate;
    }

    public String getmTime() {
        return mTime;
    }

    public String getmMessage() {
        return mMessage;
    }

    public String getmSender() {
        return mSender;
    }

    public int ismOrientation() {
        return mOrientation;
    }
}
