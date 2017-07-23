package com.dev.whatsappchatsimulator;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Devesh Chaturvedi on 017-17-06-2017.
 */

public class MessageAdapter extends ArrayAdapter<Message> {
    private static final String LOG_TAG = MessageAdapter.class.getSimpleName();

    public MessageAdapter(Context context, ArrayList<Message> users) {
        super(context, 0, users);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Message message = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
//        if (convertView == null) {
            if (message.ismOrientation()==1)
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_message_right, parent, false);
            else if (message.ismOrientation()==2)
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_message_left, parent, false);

//        }
        TextView msg = (TextView) convertView.findViewById(R.id.message);
        TextView sender = (TextView) convertView.findViewById(R.id.name);
        TextView time = (TextView) convertView.findViewById(R.id.time);
        TextView date = (TextView) convertView.findViewById(R.id.date);

        Boolean senderBoolean = true,dateBoolean = true;
        if(position>0) {
            Message previousMessage = getItem(position - 1);
            if (previousMessage.getmSender().equals(message.getmSender()))
                senderBoolean = false;
            else
                senderBoolean = true;
            if (previousMessage.getmDate().equals(message.getmDate()))
                dateBoolean = false;
            else
                dateBoolean = true;
        }
        // Populate the data into the template view using the data object

        if(dateBoolean&&!(message.getmDate().isEmpty())){
            date.setVisibility(View.VISIBLE);
            date.setText(message.getmDate());
        }
        else date.setVisibility(View.GONE);
        if(!message.getmTime().isEmpty())
            time.setText("\t"+message.getmTime());
        else
            time.setVisibility(View.GONE);
        if(senderBoolean&&!(message.getmSender().isEmpty())){
            sender.setVisibility(View.VISIBLE);
            sender.setText(message.getmSender());
        }
        else
            sender.setVisibility(View.GONE);
        if(!(message.getmSender().isEmpty()||message.getmTime().isEmpty()||message.getmDate().isEmpty()||message.getmMessage().isEmpty()))
            msg.setText(message.getmMessage());
        else {
            View previousView;
            Message previousMessage;
            int n=1;
            do{
            previousMessage = getItem(position-n);
            previousView = getView(position -n,convertView,parent);
                TextView prevoiousMsg = (TextView) previousView.findViewById(R.id.message);
                prevoiousMsg.append(message.getmMessage());
            ++n;
            } while (previousMessage.getmMessage().isEmpty());

        }
        // Return the completed view to render on screen
        return convertView;
    }

    public View getViewByPosition(int pos, ListView listView) {
        final int firstListItemPosition = listView.getFirstVisiblePosition();
        final int lastListItemPosition = firstListItemPosition + listView.getChildCount() - 1;

        if (pos < firstListItemPosition || pos > lastListItemPosition ) {
            return listView.getAdapter().getView(pos, null, listView);
        } else {
            final int childIndex = pos - firstListItemPosition;
            return listView.getChildAt(childIndex);
        }
    }
}
