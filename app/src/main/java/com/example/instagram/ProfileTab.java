package com.example.instagram;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileTab extends Fragment {
    private ArrayList mArrayList;
    private ListView mListView;
    private ArrayAdapter mArrayAdapter;

    public ProfileTab() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile_tab, container, false);

        final TextView txtLoadingUsers = view.findViewById(R.id.txtLoadingUsers);
        mListView = view.findViewById(R.id.listView);
        mArrayList = new ArrayList();
        mArrayAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1, mArrayList);
        ParseQuery<ParseUser> parseQuery = ParseUser.getQuery();
        parseQuery.whereNotEqualTo("username", ParseUser.getCurrentUser().getUsername());
        parseQuery.findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> users, ParseException e) {
                if(e==null){

                    if(users.size()>0){

                        for(ParseUser user: users){
                            mArrayList.add(user.getUsername());
                        }
                        mListView.setAdapter(mArrayAdapter);
                        txtLoadingUsers.animate().alpha(0).setDuration(1500);
                        mListView.setVisibility(View.VISIBLE);

                    }

                }
            }
        });

        return view;
    }
}
