package ie.ul.deirdreshanahan.moviequotes;

import android.content.DialogInterface;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        MovieQuoteAdapter movieQuoteAdapter = new MovieQuoteAdapter();
        recyclerView.setAdapter(movieQuoteAdapter);

//temp test of auth
//        FirebaseAuth auth = FirebaseAuth.getInstance();
//        //auth.signout()  //if you  want to sign back in as someone other anonymous
//        FirebaseUser currentUser = auth.getCurrentUser();
//        if (currentUser == null) {
//            Log.d(Constants.TAG, "there is no user . Need to sign in!");
//            auth.signInAnonymously();
//        }else  {
//            Log.d(Constants.TAG, "there is a user . All set!");
//        }
//end of test for Auth

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddDialog();


            }
        });
    }

    private void showAddDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.moviequote_dialog, null, false );
        builder.setView(view);
        builder.setTitle("Add a quote");
        final TextView quoteEditText = view.findViewById(R.id.dialog_quote_edittext);
        final TextView movieEditText = view.findViewById(R.id.dialog_movie_edittext);
        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                Map<String, Object> mq = new HashMap<>();

                mq.put(Constants.KEY_QUOTE, quoteEditText.getText().toString());
                mq.put(Constants.KEY_MOVIE, movieEditText.getText().toString());
                //DS CODE
                mq.put(Constants.KEY_USER_ID, FirebaseAuth.getInstance().getCurrentUser().getUid());
                //DSEND

                mq.put(Constants.KEY_CREATED, new Date());

                FirebaseFirestore.getInstance().collection(Constants.COLLECTION_PATH).add(mq);

            }
        });
        builder.setNegativeButton(android.R.string.cancel, null);


        builder.create().show();
    }
    //new code here
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_signout, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.action_signout:
               FirebaseAuth.getInstance().signOut();
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
