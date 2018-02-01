package tutorial.elmasry.moviestage1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import tutorial.elmasry.moviestage1.model.MovieInfo;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_MOVIE_INFO = "movie-info";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();
        MovieInfo movieInfo = null;
        if (intent != null) {
            movieInfo = intent.getExtras().getParcelable(EXTRA_MOVIE_INFO);
        }

        if (intent == null || movieInfo == null) {
            showErrorToast();
            return;
        }

        ((TextView) findViewById(R.id.detail_title)).setText(movieInfo.getOriginalTitle());
        ((TextView) findViewById(R.id.detail_release_date)).setText(movieInfo.getReleaseDate());
        ((TextView) findViewById(R.id.detail_user_rating)).setText(movieInfo.getUserRating()+"");
        ((TextView) findViewById(R.id.detail_plot_synopsis)).setText(movieInfo.getPlotSynopsis());

        ImageView posterView = findViewById(R.id.detail_iv_poster);

        Picasso.with(this)
                .load(movieInfo.getPosterUrl())
                .into(posterView);

    }

    private void showErrorToast() {
        Toast.makeText(this, R.string.message_failing_to_get_movie_detail, Toast.LENGTH_SHORT).show();
    }

}
