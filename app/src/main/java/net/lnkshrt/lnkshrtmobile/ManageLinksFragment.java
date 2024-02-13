package net.lnkshrt.lnkshrtmobile;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ManageLinksFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ManageLinksFragment extends Fragment {

    public ManageLinksFragment() {
        // Required empty public constructor
    }


    public static ManageLinksFragment newInstance() {
        ManageLinksFragment fragment = new ManageLinksFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_manage_links, container, false);
    }
}