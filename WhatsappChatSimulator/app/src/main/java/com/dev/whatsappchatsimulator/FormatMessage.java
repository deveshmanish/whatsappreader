package com.dev.whatsappchatsimulator;

import android.text.TextUtils;
import android.util.Log;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static android.R.attr.lines;


/**
 * Created by Devesh Chaturvedi on 017-17-06-2017.
 */

public final class FormatMessage {
    private static final String LOG_TAG = FormatMessage.class.getSimpleName();

    public FormatMessage() {

    }

    public static List<Message> format(String message) {
        if (TextUtils.isEmpty(message))
            return null;
        List<Message> messages = new ArrayList<>();
        String date=null,dateString=null, sender=null, name=null, msg=null, receiver=null, time=null;
        int orientation;
        String [] messageArray = extract(message);
        sender = extractSender(message);
        receiver = extractReceiver(message, sender);

        for(int i=0;i<messageArray.length;++i){
            dateString = extractTime(messageArray[i]);
            name = extractName(messageArray[i]);
            msg = extractMessage(messageArray[i]);
        }
        for (int i = 0; i < lines; ++i) {
//            date = extractDate(message);
//            if(isValidIndex(extractDateIndex(message)))
//                message = newMessage(message, extractDateIndex(message));
            time = extractTime(message);
            if(isValidIndex(extractTimeIndex(message)))
                message = newMessage(message, extractTimeIndex(message));
            name = extractName(message);
            if(isValidIndex(extractNameIndex(message)))
                message = newMessage(message, extractNameIndex(message));
            msg = extractMessage(message);
            if(isValidIndex(extractMessageIndex(message)))
                message = newMessage(message, extractMessageIndex(message));
            orientation = extractOrientation(name, sender, receiver);

            Message newMessage = new Message(name, time, time, msg, orientation);
            messages.add(newMessage);
        }
//        date = extractDate(message);
//        if(isValidIndex(extractDateIndex(message)))
//            message = newMessage(message, extractDateIndex(message));
//        time = extractTime(message);
//        if(isValidIndex(extractTimeIndex(message)))
//            message = newMessage(message, extractTimeIndex(message));
//        name = extractName(message);
//        if(isValidIndex(extractNameIndex(message)))
//            message = newMessage(message, extractNameIndex(message));
//        else
//            message = null;
//        orientation = extractOrientation(name, sender, receiver);
//        Message newMessage = new Message(name, date, time, message, orientation);
//        messages.add(newMessage);
        return messages;
    }

    private static String extractDate(String message) {
        String date;
        if(isValidIndex(extractDateIndex(message))) {
            date = message.substring(0, extractDateIndex(message)).trim();
            date = formatDate(date);
        }else
            date = null;
        return date;
    }

    private static int extractDateIndex(String message) {
        int i = message.indexOf(",");
        return i;
    }

    private static String extractTime(String message,int index) {
        String time;
        if(isValidIndex(extractTimeIndex(message)))
            time = message.substring(++index, extractTimeIndex(message)).trim();
        else
            time = null;
        return time;
    }

    private static int extractTimeIndex(String message) {
        int i = message.indexOf("-");
        return i;
    }

    private static String extractName(String message,int index) {
        String sender;
        if(isValidIndex(extractNameIndex(message)))
            sender = message.substring(++index, extractNameIndex(message)).trim();
        else
            sender = null;
        return sender;
    }

    private static int extractNameIndex(String message) {
        int i = message.indexOf(":");
        return i;
    }

    private static String extractMessage(String message,int index) {
        String msg;
        if(isValidIndex(extractMessageIndex(message)))
            msg = message.substring(++index, extractMessageIndex(message)).trim();
        else
            msg = null;
        return msg;
    }

    private static int extractMessageIndex(String message) {
        int i = message.indexOf("\n");
        return i;
    }

    private static String newMessage(String message, int position) {
        ++position;
        String msg = message.substring(position);
        return msg;
    }

    private static String extractSender(String message) {
        String sender;
        do {
            if (isValidIndex(extractDateIndex(message)))
                message = newMessage(message, extractDateIndex(message));
            if (isValidIndex(extractTimeIndex(message)))
                message = newMessage(message, extractTimeIndex(message));
            sender = extractName(message);
            if(isValidIndex(extractNameIndex(message)))
                message = newMessage(message, extractNameIndex(message));
            if(isValidIndex(extractMessageIndex(message)))
                message = newMessage(message, extractMessageIndex(message));
        }while(sender.isEmpty());
        if(!sender.isEmpty())
            sender = sender.trim();
        return sender;
    }

    private static String extractReceiver(String message, String sender) {
        String receiver;
        do {
            if(isValidIndex(extractDateIndex(message)))
                message = newMessage(message, extractDateIndex(message));
            if(isValidIndex(extractTimeIndex(message)))
                message = newMessage(message, extractTimeIndex(message));
            receiver = extractName(message);
            if(!receiver.isEmpty())
                receiver = receiver.trim();
            if(isValidIndex(extractNameIndex(message)))
                message = newMessage(message, extractNameIndex(message));
            if(isValidIndex(extractMessageIndex(message)))
                message = newMessage(message, extractMessageIndex(message));

        } while (receiver.equals(sender));
        return receiver;
    }

    private static int numberOfLines(String message) {
        int i, counter = 0;
        while (message.endsWith("\n")) {
            i = message.indexOf("\n");
            message = newMessage(message,i);
            ++counter;
        }
        Log.i(LOG_TAG,"Lines "+counter);
        return counter;
    }

    private static int extractOrientation(String name, String sender, String receiver) {
        int orientation;
        if (name.equals(sender))
            orientation = 1;
        else if (name.equals(receiver))
            orientation = 2;
        else {
            Log.i(LOG_TAG, "else is called,please check");
            orientation = 0;
        }
        return orientation;
    }

    private static String formatDate(String inputDate) {

        DateFormat inputFormat = new SimpleDateFormat("mm/dd/yy, h:mm a");
        DateFormat outputFormat = new SimpleDateFormat("MMM, d yyyy, h:mm a");
        Date date = null;
        try {
            date = inputFormat.parse(inputDate);
        } catch (Exception e) {
            Log.e("formattedDateFromString", "Exception in formateDateFromstring(): " + e.getMessage());
        }
        String outputDate = outputFormat.format(date);
        return outputDate;
    }
    private static boolean isValidIndex(int index){
        if(index == -1)
            return false;
        else
            return true;
    }
    private static String[] extract(String message){

        int n=numberOfLines(message);
        String [] msg = new String[n] ;
        for(int i =0;i<n;++i){
            msg[i] = extractMessage(message);
            message = newMessage(message,extractMessageIndex(message));
        }
        return msg;
    }
    private static String extractData(String[] message){
        String dateString=null,name = null,msg=null;
        for(int i=0;i<message.length;++i){
            dateString = extractTime(message[i]);
            name = extractName(message[i]);
            msg = extractMessage(message[i]);
        }
    }
    private static String extractSender(String[] message){
        String sender = null,index;
        for(int i=0;i<message.length;++i){
            if(isValidIndex(extractTimeIndex(message[i]))&&isValidIndex(extractNameIndex(message[i]))) {
                sender = extractName(message[i], extractTimeIndex(message[i]));
                return sender;
            }
        }
        return sender;
    }
    private static String extractReceiver(String[] message,String sender){
        String receiver = null,index;
        for(int i=0;i<message.length;++i){
            if(isValidIndex(extractTimeIndex(message[i]))&&isValidIndex(extractNameIndex(message[i]))) {
                receiver = extractName(message[i], extractTimeIndex(message[i]));
                if(!receiver.isEmpty())
                return receiver;
            }
        }
        return sender;
    }
}
