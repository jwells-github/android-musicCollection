    package musiccollection.jaked.musiccollection;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import musiccollection.jaked.musiccollection.database.DatabaseReader;
import musiccollection.jaked.musiccollection.database.RecordSaver;

public class RecordListFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private AlbumAdapter mAdapter;

    private ActionMode mActionMode;

    ArrayList<Album> mAlbums = new ArrayList<Album>();
    ArrayList<Album> mAlbumsToDelete = new ArrayList<Album>();
    private String mSearchQuery;
    private static final String DIALOG_ALBUM = "DialogAlbum";
    private static final String EDIT_ALBUM = "EditAlbum";
    private static final int REQUEST_ALBUM = 0;


    public static RecordListFragment newInstance() {
        return new RecordListFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        setHasOptionsMenu(true);
        mAlbums = new DatabaseReader().DatabaseReader(getContext());
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mActionMode = null;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateList();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_record_list, container, false);
        mRecyclerView = (RecyclerView) v.findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));



        updateList();


        return v;
    }

    private void updateList(){
        mAlbums = new DatabaseReader().DatabaseReader(getContext());
        mAdapter = new AlbumAdapter(mAlbums);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }

    private class AlbumHolder extends RecyclerView.ViewHolder{
        public TextView mAlbumName;
        public TextView mArtistName;
        public TextView mYear;
        public TextView mOfficial;
        public RatingBar mRatingBar;


        public AlbumHolder(LayoutInflater inflater, ViewGroup parent){
            super(inflater.inflate(R.layout.list_item_album, parent, false));
            mAlbumName = itemView.findViewById(R.id.tvAlbumName);
            mArtistName = itemView.findViewById(R.id.tvArtistName);
            mYear = itemView.findViewById(R.id.tvYear);
            mOfficial = itemView.findViewById(R.id.tvOfficial);
            mRatingBar = itemView.findViewById(R.id.ratingBar);

        }
    }

    private class AlbumAdapter extends RecyclerView.Adapter<AlbumHolder>{
        private ArrayList<Album> mAlbumArrayList;

        public AlbumAdapter(ArrayList<Album> albums){
            mAlbumArrayList = albums;
        }

        @Override
        public AlbumHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new AlbumHolder(layoutInflater, viewGroup);
        }

        @Override
        public void onBindViewHolder(final AlbumHolder albumHolder, final int i) {
            final Album album = mAlbumArrayList.get(i);
            albumHolder.mAlbumName.setText(album.getTitle());
            albumHolder.mArtistName.setText(album.getArtistName());
            if(!album.getReleaseYear().equals("0000")){
                albumHolder.mYear.setText(album.getReleaseYear());
            }
            else{
                albumHolder.mYear.setText("");
            }

            if(album.isOfficial()){
                albumHolder.mOfficial.setText("Official");
            }
            else{
                albumHolder.mOfficial.setText("Unofficial");
            }
            Log.d("RATING", String.valueOf(album.getRating()));
            albumHolder.mRatingBar.setRating(album.getRating());


            albumHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getActivity(), CustomAlbumActivity.class);
                    intent.putExtra(EDIT_ALBUM, mAlbumArrayList.get(i));
                    startActivity(intent);

                }
            });

            albumHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {

            System.out.println(view.isSelected());

                    if(!view.isSelected()){
                        mAlbumsToDelete.add(mAlbums.get(i));
                        Log.d("albumcount", String.valueOf(mAlbumsToDelete.size()));
                        view.setSelected(true);
                        view.setBackgroundColor(getResources().getColor(android.R.color.holo_red_dark));
                    }
                    else{
                        mAlbumsToDelete.remove(mAlbums.get(i));
                        view.setSelected(false);
                        view.setBackgroundColor(getResources().getColor(android.R.color.background_light));
                    }

                    if( mActionMode == null &&   mAlbumsToDelete.size() > 0 ){
                        mActionMode = getActivity().startActionMode(mActionModeCallback);
                    }
                    else if( mAlbumsToDelete.size() < 1  && mActionMode != null){
                        mActionMode.finish();
                        return true; // Update list is called, so the view no longer exists
                    }
                    return view.isSelected();
                }
            });

        }

        @Override
        public int getItemCount() {
            if (mAlbumArrayList != null){
                return mAlbumArrayList.size();
            }
            else{
                return 0;
            }


        }

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        super.onCreateOptionsMenu(menu, menuInflater);
        menuInflater.inflate(R.menu.fragment_record_list, menu)  ;
        MenuItem searchItem = menu.findItem(R.id.menu_item_search);
        final SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if(query != ""){
                    mSearchQuery = query;
                    mSearchQuery = mSearchQuery.replace(" ", "_");
                    new XMLParser().execute();
                }

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_add_custom:
                // Add album and open album viewer
                Intent intent = new Intent(getActivity(), CustomAlbumActivity.class);
                startActivity(intent);
            default:
                return super.onOptionsItemSelected(item);
        }


    }

    private void AlbumChoice(ArrayList<Album> albums){
        FragmentManager manager = getFragmentManager();
        AlbumPickerFragment dialog =  AlbumPickerFragment.newInstance(albums);
        dialog.setTargetFragment(RecordListFragment.this, REQUEST_ALBUM);
        dialog.show(manager,DIALOG_ALBUM);

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if( resultCode != Activity.RESULT_OK){
            return;
        }

        if(requestCode == REQUEST_ALBUM){
            Album album = (Album) data.getParcelableExtra(AlbumPickerFragment.EXTRA_ALBUM);
            mAlbums.add(album);
            updateList();
        }
    }

    private ActionMode.Callback mActionModeCallback = new ActionMode.Callback() {

        // Called when the action mode is created; startActionMode() was called
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            // Inflate a menu resource providing context menu items
            MenuInflater inflater = mode.getMenuInflater();
            inflater.inflate(R.menu.context_menu_record_list, menu);
            return true;
        }

        // Called each time the action mode is shown. Always called after onCreateActionMode, but
        // may be called multiple times if the mode is invalidated.
        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false; // Return false if nothing is done
        }

        // Called when the user selects a contextual menu item
        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {


            switch (item.getItemId()) {
                case R.id.menu_item_delete:
                    if(mAlbumsToDelete.size() > 0){
                        RecordSaver recordSaver = new RecordSaver();

                        for(Album a : mAlbumsToDelete){
                            recordSaver.deleteRecord(a,getContext());
                        }

                        updateList();
                    }
                    mode.finish(); // Action picked, so close the CAB
                case R.id.menu_item_cancel:
                    mode.finish();
                    updateList();
                    mAlbumsToDelete .clear();
                    return true;
                default:
                    return false;
            }


        }

        // Called when the user exits the action mode
        @Override
        public void onDestroyActionMode(ActionMode mode) {

            mAlbumsToDelete .clear();
            mActionMode = null;
            updateList();
        }
    };

    private class XMLParser extends AsyncTask<Void,Void,Void>{

        @Override
        protected Void doInBackground(Void... params){
            try {
                System.out.println(mSearchQuery);
                URL u = new URL("http://musicbrainz.org/ws/2/release/?query=artist:" + mSearchQuery  +"%20AND%20primarytype:Album");
                URLConnection uc = u.openConnection();
                uc.setRequestProperty("User-Agent","HobbyApp ( jakewellsd@gmail.com )");
                HttpURLConnection connection = (HttpURLConnection) uc;

                InputStream in = connection.getInputStream();

                List<Album> albums = new XMLTagParser().parse(in);

                // Sort the albums by release date
                Collections.sort(albums, new Comparator<Album>() {
                    @Override
                    public int compare(Album album, Album albumTwo) {

                        if (album.getReleaseYear() == null || albumTwo.getReleaseYear() == null){
                            return 1;
                        }
                        return Integer.parseInt(album.getReleaseYear()) - Integer.parseInt(albumTwo.getReleaseYear());
                    }
                });


                ArrayList<Album> albumsSorted = new ArrayList<Album>();
                List<String> albumTitles = new ArrayList<String>();

                // Remove Duplicate Album listings
                for(Album a: albums){
                    Boolean duplicate = false;
                    for(String s : albumTitles){
                        if(a.getTitle().equals(s)) {
                            duplicate = true;
                            break;
                        }
                    }
                    if(!duplicate){
                        albumsSorted.add(a);
                        albumTitles.add(a.getTitle());
                    }
                }

                for(Album a :albumsSorted){
                    System.out.println(a.getTitle() + " " + a.getReleaseYear());
                }

                AlbumChoice(albumsSorted);



            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            }


            return null;
        }
    }

}
