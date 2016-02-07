package hussamsh.example.com.musicretriversample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.hussamsherif.musicretriever.MusicQuery;
import com.hussamsherif.musicretriever.MusicRetriever;
import com.hussamsherif.musicretriever.Operator;
import com.hussamsherif.musicretriever.RowConstants;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        MusicQuery.Builder builder = new MusicQuery.Builder()
                .addMainArgument(RowConstants.ALBUM_NAME, Operator.EQUALS, "Adele");
        recyclerView.setAdapter(new Adapter(MusicRetriever.with(getContentResolver()).getArtists(builder.build())));
    }
}
