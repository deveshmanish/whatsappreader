package com.dev.whatsappchatsimulator;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

/**
 * Created by Devesh Chaturvedi on 017-17-06-2017.
 */

public class MessageLoader extends AsyncTaskLoader<List<Message>> {

    String message;
    public MessageLoader(Context context,String msg){
        super(context);
        message=msg;
    }
    @Override
    protected void onStartLoading() {
        forceLoad();
    }
    @Override
    public List<Message> loadInBackground() {
        if(message==null)
            return null;
        List<Message> result = FormatMessage.format(message);
        return result;

    }
}
