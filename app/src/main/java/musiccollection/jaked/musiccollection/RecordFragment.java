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

                List<Album> abc = new XMLTagParser().parse(in);
                System.out.println("Album titles");
                for(Album a: abc){
                    System.out.println(a.getTitle());
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
