package com.example.smartani.Activities.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartani.Adapters.PostAdapter;
import com.example.smartani.Models.Post;
import com.example.smartani.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

//    private OnFragmentInteractionListener mListener;

    RecyclerView postRecyclerView ;
    PostAdapter postAdapter ;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference ;
    List<Post> postList;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup drawer,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragmentView = inflater.inflate(R.layout.fragment_home, drawer, false);
        postRecyclerView  = fragmentView.findViewById(R.id.postRV);
        postRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        postRecyclerView.setHasFixedSize(true);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Posts");
        return fragmentView ;
    }


    @Override
    public void onStart() {
        super.onStart();


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                postList = new ArrayList<>();
                for (DataSnapshot postsnap: dataSnapshot.getChildren()) {

//                    Map<String, Object> map = (Map<String, Object>) postsnap.getValue();
//                    Long timeStampLong = (Long) postsnap.child("timestamp").getValue();
//                    DateFormat dateFormat = getDateTimeInstance();
//                    Date netDate = (new Date(timeStampLong));
                    Post manual=new Post();
                    manual.setTitle((String) postsnap.child("title").getValue());
//                    Timestamp x=new Timestamp(timeStampLong);
//                    manual.setTimestamp((Map<String, String>) x);
                    manual.setPicture((String) postsnap.child("picture").getValue());
                    manual.setUserId((String) postsnap.child("userId").getValue());

                    manual.setDescription((String) postsnap.child("description").getValue());

                    manual.setUserId((String) postsnap.child("userId").getValue());
                    manual.setPostKey((String) postsnap.child("postKey").getValue());

                    manual.setUserPhoto((String) postsnap.child("userPhoto").getValue());



//                    Post post = postsnap.getValue(Post.class);
                    postList.add(manual) ;



                }

                postAdapter = new PostAdapter(getActivity(),postList);
                postRecyclerView.setAdapter(postAdapter);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}