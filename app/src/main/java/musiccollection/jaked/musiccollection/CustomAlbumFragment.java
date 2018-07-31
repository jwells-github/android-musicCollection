package musiccollection.jaked.musiccollection;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RatingBar;

import musiccollection.jaked.musiccollection.database.RecordSaver;

public class CustomAlbumFragment extends Fragment {

    private String mAlbumName;
    private String mArtistName;
    private String mReleaseYear = "0000";
    private Boolean mOfficial = true;
    private int mRating = 0;

    public static CustomAlbumFragment newInstance(){
        return new CustomAlbumFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_custom_album, container, false);
        EditText etAlbumName = v.findViewById(R.id.etAlbumName);
        EditText etArtistName = v.findViewById(R.id.etArtistName);
        EditText etReleaseYear = v.findViewById(R.id.etReleaseYear);
        final CheckBox cbOfficial = v.findViewById(R.id.cbOfficial);
        final RatingBar rbRating = v.findViewById(R.id.rbRating);
        Button btCreate = v.findViewById(R.id.btCreate);

        etAlbumName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mAlbumName = charSequence.toString();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        etArtistName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mArtistName = charSequence.toString();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        etReleaseYear.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mReleaseYear = charSequence.toString();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        btCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mOfficial = cbOfficial.isChecked();
                mRating = rbRating.getNumStars();
                if(mAlbumName != null && mArtistName != null){
                    Album album = new Album(mAlbumName,mArtistName,mReleaseYear,mOfficial);
                    album.setRating(mRating);
                    RecordSaver recordSaver = new RecordSaver();
                    recordSaver.addRecord(album, getContext());
                    getActivity().finish();
                }

            }
        });

        return v;
    }
}
