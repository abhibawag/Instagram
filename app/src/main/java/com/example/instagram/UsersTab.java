package com.example.instagram;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.sdsmdg.tastytoast.TastyToast;


/**
 * A simple {@link Fragment} subclass.
 */
public class UsersTab extends Fragment {

    private EditText edtProfileName, edtProfileFavouriteSport, edtProfileHobbies, edtProfileBio, edtProfileProfession;
    private Button btnUpdateInfo;

    public UsersTab() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_users_tab, container, false);


        edtProfileName = view.findViewById(R.id.edtProfileName);
        edtProfileBio = view.findViewById(R.id.edtProfileBio);
        edtProfileHobbies = view.findViewById(R.id.edtProfileHobbies);
        edtProfileFavouriteSport = view.findViewById(R.id.edtProfileSport);
        edtProfileProfession = view.findViewById(R.id.edtProfileProfession);

        btnUpdateInfo = view.findViewById(R.id.btnUpdateInfo);

        final ParseUser parseUser = ParseUser.getCurrentUser();

        if(parseUser.get("profileName") != null
                && parseUser.get("profileProfession") != null
                && parseUser.get("profileSport") != null
                && parseUser.get("profileHobbies") != null
                && parseUser.get("profileBio") != null) {

                    edtProfileName.setText(parseUser.get("profileName")+"");
                    edtProfileProfession.setText(parseUser.get("profileProfession")+"");
                    edtProfileFavouriteSport.setText(parseUser.get("profileSport")+"");
                    edtProfileHobbies.setText(parseUser.get("profileHobbies")+"");
                    edtProfileBio.setText(parseUser.get("profileBio")+"");

        }


        btnUpdateInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                parseUser.put("profileName", edtProfileName.getText().toString());
                parseUser.put("profileBio", edtProfileBio.getText().toString());
                parseUser.put("profileProfession", edtProfileProfession.getText().toString());
                parseUser.put("profileHobbies", edtProfileHobbies.getText().toString());
                parseUser.put("profileSport", edtProfileFavouriteSport.getText().toString());

                final ProgressDialog progressDialog = new ProgressDialog(getContext());
                progressDialog.show();

                parseUser.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if(e==null){
                            TastyToast.makeText(getContext(), "saved", TastyToast.LENGTH_SHORT, TastyToast.SUCCESS).show();
                            progressDialog.dismiss();
                        }else{
                            TastyToast.makeText(getContext(), e.getMessage(), TastyToast.LENGTH_SHORT, TastyToast.ERROR).show();

                        }
                    }
                });

            }
        });
        return view;
    }
}
