package com.dev.whatsappchatsimulator;

import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Intent;
import android.content.Loader;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Devesh Chaturvedi on 017-17-06-2017.
 */

public class MessageListActivity extends AppCompatActivity implements LoaderCallbacks<List<Message>> {
    private static final String LOG_TAG = MessageListActivity.class.getSimpleName();

    private static final int MESSAGE_LOADER_ID = 1;
    String message;
    private MessageAdapter mMessageAdapter;
    private TextView mEmptyStateTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_message);

        Intent intent = getIntent();
        Bundle b = intent.getExtras();

        message = getIntent().getExtras().getString("message");

        Log.i(LOG_TAG, "ListActivity is executed" + message);

        ListView messageListView = (ListView) findViewById(R.id.list);
        mEmptyStateTextView = (TextView) findViewById(R.id.empty_view);
        messageListView.setEmptyView(mEmptyStateTextView);

        mMessageAdapter = new MessageAdapter(this, new ArrayList<Message>());
        messageListView.setAdapter(mMessageAdapter);

        LoaderManager loaderManager = getLoaderManager();
        Log.i(LOG_TAG, "Loader is executed");

        // Initialize the loader. Pass in the int ID constant defined above and pass in null for
        // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
        // because this activity implements the LoaderCallbacks interface).
        loaderManager.initLoader(MESSAGE_LOADER_ID, null, this);
    }

    @Override
    public Loader<List<Message>> onCreateLoader(int id, Bundle args) {
        Log.i(LOG_TAG, "Loader is created");
        return new MessageLoader(this, message);
    }

    @Override
    public void onLoadFinished(Loader<List<Message>> loader, List<Message> messages) {

        mMessageAdapter.clear();
        ProgressBar progressBar = (ProgressBar) findViewById(R.id.loading_indicator);
        progressBar.setVisibility(View.GONE);
        mEmptyStateTextView.setText("No Messages found");
        // If there is a valid list of {@link Earthquake}s, then add them to the adapter's
        // data set. This will trigger the ListView to update.
        if (messages != null && !messages.isEmpty()) {
            mMessageAdapter.addAll(messages);

            Log.i(LOG_TAG, "Loader is initialized");
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Message>> loader) {
        mMessageAdapter.clear();

    }
}
