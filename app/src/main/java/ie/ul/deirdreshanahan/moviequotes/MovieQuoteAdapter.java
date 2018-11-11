package ie.ul.deirdreshanahan.moviequotes;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

import javax.annotation.Nullable;

public class MovieQuoteAdapter extends RecyclerView.Adapter<MovieQuoteAdapter.MovieQuoteViewHolder> {
    private List<DocumentSnapshot> mMovieQuotesSnapshots = new Arraylist<>();
    public MovieQuoteAdapter(){
        CollectionReference moviequotesRef = FirebaseFirestore.getInstance().collection(Constants.COLLECTION_PATH";
                moviequotesRef.addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot documentSnapshots, @Nullable FirebaseFirestoreException e) {
                        if (e !=null){
                            Log.w(Constants.TAG, "Listening Failed!");
                            return;
                        }
                        mMovieQuotesSnapshots = documentSnapshots.getDocuments();
                        notifyDataSetChanged();

                    }
                });
    }

    @NonNull
    @Override
    public MovieQuoteViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.moviequote_itemview,viewGroup,false);
        return new MovieQuoteViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieQuoteViewHolder movieQuoteViewHolder, int i) {
        DocumentSnapshot ds = mMovieQuotesSnapshots.get(i);
        String quote = (String)ds.get(Constants.Key_QUOTE);
        String movie = (String)ds.get(Constants.Key_MOVIE);
        movieQuoteViewHolder.mMovieTextView.setText(quote);
        movieQuoteViewHolder.mQuoteTextView.setText(movie);

    }

    @Override
    public int getItemCount() {
        return mMovieQuotesSnapshots.size();
    }

    class MovieQuoteViewHolder extends RecyclerView.ViewHolder {
        private TextView mQuoteTextView;
        private TextView mMovieTextView;

        public MovieQuoteViewHolder(@NonNull View itemView) {
            super(itemView);
            mQuoteTextView=itemView.findViewById(R.id.itemview_quote);
            mMovieTextView=itemView.findViewById(R.id.itemview_movie);


        }
    }
}
