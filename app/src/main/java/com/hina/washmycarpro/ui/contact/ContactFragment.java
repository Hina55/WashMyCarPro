package com.hina.washmycarpro.ui.contact;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.firebase.firestore.FirebaseFirestore;
import com.hina.washmycarpro.R;
import com.hina.washmycarpro.adapter.OrderListRecyclerViewAdapter;

import java.util.ArrayList;

public class ContactFragment extends Fragment {


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_contact, container, false);

        return view;
    }
}