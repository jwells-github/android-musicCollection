package musiccollection.jaked.musiccollection;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
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

public class RecordFragment extends Fragment {

    public static RecordFragment newInstance() {
        return new RecordFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_record, container, false);
        new XMLParser().execute();
        return v;
    }

    private class XMLParser extends AsyncTask<Void,Void,Void>{

        @Override
        protected Void doInBackground(Void... params){
            try {
                URL u = new URL("http://musicbrainz.org/ws/2/release/?query=artist:the_white_stripes%20AND%20primarytype:Album");
                URLConnection uc = u.openConnection();
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


                List<Album> albumsSorted = new ArrayList<>();   
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
