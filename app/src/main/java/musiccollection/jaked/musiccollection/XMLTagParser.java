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

    public void parse(InputStream in) throws XmlPullParserException, IOException {
        System.out.println("Parse called");
        try {
            XmlPullParser parser = Xml.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(in, null);
            parser.nextTag();
            System.out.println("PARSER " + parser.getName());
           // parser.nextTag();
            //return readFeed(parser);
        } finally {
            in.close();
        }
    }


}
