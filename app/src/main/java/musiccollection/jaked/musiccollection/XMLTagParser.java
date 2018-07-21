package musiccollection.jaked.musiccollection;

import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class XMLTagParser {

    private static final String ns = null;

    public List<Album> parse(InputStream in) throws XmlPullParserException, IOException {
        try {
            XmlPullParser parser = Xml.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(in, null);

            parser.nextTag(); // Steps into the <metadata> tag
            parser.nextTag(); // Steps into the <release-list> tag

            return readFeed(parser);
        } finally {
            in.close();
        }
    }

    private List<Album> readFeed(XmlPullParser parser) throws XmlPullParserException, IOException {
        List albums = new ArrayList();
        parser.require(XmlPullParser.START_TAG, ns, "release-list");

        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            // Starts by looking for the album tag
            if (name.equals("release")) {
                albums.add(readAlbum(parser));
            } else {
                skip(parser);
            }
        }
        return albums;
    }

    private Album readAlbum(XmlPullParser parser) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, ns, "release");
        String albumName = null;
        Boolean official = true;
        String artist = null;
        String date = null;

        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            // Gets the album title
            if (name.equals("title")) {
                albumName = readTitle(parser);
            }
            // Finds out whether the album is official or a bootleg
            else if (name.equals("status")) {
                official = readStatus(parser);
            }
            //  Gets the artist's name for the album
            else if (name.equals("artist-credit")) {
                artist  = readArtist(parser);
            }
            // Gets the year that the album was publsihed
            else if (name.equals("date")){
                date = readDate(parser).substring(0,4);
            }
            // Skips over a tag
            else {
                skip(parser);
            }

        }
/*
        System.out.println("Album End Tag " + parser.getName());
        System.out.println("album name " + albumName);
        System.out.println("Date " + date);
        System.out.println("officlal " + official.toString());
        System.out.println("artist " + artist);
        */
        Album album = new Album();
        album.setOfficial(official);
        album.setTitle(albumName);

        return album;
    }

    // Processes title tags in the feed.
    private String readTitle(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, "title");
        String title = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, "title");
        return title;
    }


    // Processes the album's status using the status tag
    private Boolean readStatus(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, "status");
        String official = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, "status");
        if(official.equals("Official")){
            return true;
        }
        else{
            return false;
        }

    }

    // Processes the artist tag
    private String readArtist(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, "artist-credit");
        String artist;


        while(!parser.getName().equals("name")){
            parser.nextTag();
        }
        artist = readText(parser);

        // Exits out of the nested tags
        parser.next();
        skip(parser);
        while (parser.next() != XmlPullParser.END_TAG) {
            skip(parser);
            parser.next();
        }
        while (parser.next() != XmlPullParser.END_TAG) {
            skip(parser);
            parser.next();
        }
        while (parser.next() != XmlPullParser.END_TAG) {
            skip(parser);
            parser.next();
        }

        parser.require(XmlPullParser.END_TAG, ns, "artist-credit");

        return artist;
    }

    // Processes the album's date tags
    private String readDate(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, "date");
        String date = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, "date");
        return date;
    }


    // For the tags title and summary, extracts their text values.
    private String readText(XmlPullParser parser) throws IOException, XmlPullParserException {
        String result = "";
        if (parser.next() == XmlPullParser.TEXT) {
            result = parser.getText();
            parser.nextTag();
        }
        return result;
    }


    private void skip(XmlPullParser parser) throws XmlPullParserException, IOException {
        if (parser.getEventType() != XmlPullParser.START_TAG) {
            throw new IllegalStateException();
        }
        int depth = 1;
        while (depth != 0) {
            switch (parser.next()) {
                case XmlPullParser.END_TAG:
                    depth--;
                    break;
                case XmlPullParser.START_TAG:
                    depth++;
                    break;
            }
        }
    }

}
