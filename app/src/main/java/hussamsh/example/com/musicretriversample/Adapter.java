package hussamsh.example.com.musicretriversample;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hussamsherif.musicretriever.MusicClasses.Album;
import com.hussamsherif.musicretriever.MusicClasses.Artist;
import com.hussamsherif.musicretriever.MusicClasses.Song;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.CustomViewHolder> {

    ArrayList data;

    public Adapter(ArrayList data){
        this.data = data;
    }
    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CustomViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.view_holder , parent , false));
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {
        if (data.get(0) instanceof Song){
            holder.textView.setText("Title : " + ((Song)data.get(position)).getSongTitle());
            holder.secondTextView.setText("Album : " + ((Song)data.get(position)).getAlbumName());
            holder.thirdTextView.setText("Artist : " + ((Song)data.get(position)).getArtistName());
            return;
        }

        if (data.get(0) instanceof Album){
            holder.textView.setText("Album : " + ((Album)data.get(position)).getName());
            holder.secondTextView.setText("Artist : " + ((Album)data.get(position)).getArtistName());
            holder.thirdTextView.setText("Num tracks : " + ((Album)data.get(position)).getNumberOfSongs());
            return;
        }

        if (data.get(0) instanceof Artist){
            holder.textView.setText("Artist : " + ((Artist)data.get(position)).getName());
            holder.secondTextView.setText("Num tracks : " + ((Artist)data.get(position)).getNumberOfSongs());
            holder.thirdTextView.setText("Num albums : " + ((Artist)data.get(position)).getNumberOfAlbums());
            return;
        }


    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    static class CustomViewHolder extends RecyclerView.ViewHolder{

        TextView textView ;
        TextView secondTextView ;
        TextView thirdTextView ;

        public CustomViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.text_view);
            secondTextView = (TextView) itemView.findViewById(R.id.text_view_2);
            thirdTextView = (TextView) itemView.findViewById(R.id.text_view_3);
        }
    }
}