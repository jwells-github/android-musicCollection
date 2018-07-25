package musiccollection.jaked.musiccollection;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

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

public class RecordListFragment extends Fragment {

    private String mSearchQuery;
    private static final String DIALOG_ALBUM = "DialogAlbum";
    private static final int REQUEST_ALBUM = 0;

    public static RecordListFragment newInstance() {
        return new RecordListFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_record_list, container, false);
        return v;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        super.onCreateOptionsMenu(menu, menuInflater);
        menuInflater.inflate(R.menu.fragment_record, menu)  ;
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
            System.out.println("we got " + album.getTitle());
        }
    }

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


                ArrayList<Album> albumsSorted = new ArrayList<>();
                List<String> albumTitles = new ArrayList<>();

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