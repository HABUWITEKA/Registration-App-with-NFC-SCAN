package com.example.nfc_studentapp;



import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

public class SuccessFragment extends Fragment {
    public static final String EMAIL_PARAM = "email";
    public static final String NAME_PARAM = "name";
    public static final String PASSWORD_PARAM = "password";
    private String password;
    private String name;
    private String email;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            email = getArguments().getString(EMAIL_PARAM);
            name = getArguments().getString(NAME_PARAM);
            password = getArguments().getString(PASSWORD_PARAM);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_success, container, false);
        return view;
    }
}

