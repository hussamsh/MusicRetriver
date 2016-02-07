package hussamsh.example.com.musicretriversample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.github.hussamsh.musicretriever.MusicClasses.MusicQuery;
import com.github.hussamsh.musicretriever.MusicRetriever;
import com.github.hussamsh.musicretriever.RowConstants;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        MusicQuery.Builder builder = new MusicQuery.Builder()
                .sortBy(RowConstants.ARTIST_NAME);
        recyclerView.setAdapter(new Adapter(MusicRetriever.with(getContentResolver()).getArtists(builder.build())));
    }
}
