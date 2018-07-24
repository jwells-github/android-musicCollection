package musiccollection.jaked.musiccollection;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AlbumPickerFragment extends DialogFragment {


    private static final String ARG_ = "ar";

    public static AlbumPickerFragment newInstance(ArrayList<Album> albums){
        Bundle args = new Bundle();
        args.putSerializable(ARG_, (Serializable) albums);
        AlbumPickerFragment fragment = new AlbumPickerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View v = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_album_picker,null);
        ArrayList <Album> albums = (ArrayList<Album>) getArguments().getSerializable(ARG_);
        System.out.println(albums.size());


        ListView lvAlbumPicker = v.findViewById(R.id.lvAlbumPicker);
        final AlbumAdapter adapter = new AlbumAdapter(getContext(), albums);
        lvAlbumPicker.setAdapter(adapter);
        adapter.addAll(albums);
        adapter.notifyDataSetChanged();

        return new AlertDialog.Builder(getActivity())
                .setTitle("Albums Found")
                .setPositiveButton(android.R.string.ok, null)
                .setView(v)
                .create();


    }

    public class AlbumAdapter extends ArrayAdapter<Album>{
        public AlbumAdapter(Context context, ArrayList<Album> albums){
            super(context,0,albums);
        }

        // Adds each row to the list view
        @Override
        public View getView(int position, View convertView, ViewGroup parent){
            Album album = getItem(position);

            if (convertView == null) {
                convertView = getActivity().getLayoutInflater().inflate(R.layout.list_item_album, null);
            }

            TextView tvAlbumName = (TextView) convertView.findViewById(R.id.tvAlbumName);
            TextView tvArtistName = (TextView) convertView.findViewById(R.id.tvArtistName);
            TextView tvYear = (TextView) convertView.findViewById(R.id.tvYear);

            tvArtistName.setText(album.getArtistName());
            tvAlbumName.setText(album.getTitle());
            tvYear.setText(album.getReleaseYear());


            return convertView;


        }
    }
}
