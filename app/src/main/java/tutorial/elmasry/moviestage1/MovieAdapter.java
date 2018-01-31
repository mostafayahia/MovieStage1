package tutorial.elmasry.moviestage1;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import tutorial.elmasry.moviestage1.model.MovieInfo;

/**
 * Created by yahia on 1/30/18.
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieAdapterViewHolder> {

    private MovieInfo[] mMovieInfoArray;
    private Context mContext;

    public MovieAdapter(Context context) {
        mContext = context;
    }

    @Override
    public MovieAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.grid_list_item, parent, false);
        return new MovieAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieAdapterViewHolder holder, int position) {

        Picasso.with(mContext)
                .load(mMovieInfoArray[position].getPosterUrl())
                .into(holder.mPosterView);
    }

    @Override
    public int getItemCount() {
        if (mMovieInfoArray == null) return 0;
        else                         return mMovieInfoArray.length;
    }

    public class MovieAdapterViewHolder extends RecyclerView.ViewHolder {

        public final ImageView mPosterView;

        public MovieAdapterViewHolder(View itemView) {
            super(itemView);
            mPosterView = itemView.findViewById(R.id.iv_poster);
        }

    }

    public void setMovieInfoArray(MovieInfo[] mMovieInfoArray) {
        this.mMovieInfoArray = mMovieInfoArray;
        notifyDataSetChanged();
    }
}
