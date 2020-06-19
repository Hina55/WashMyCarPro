package com.hina.washmycarpro.ui.account;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import com.hina.washmycarpro.R;

public class AccountFragment extends Fragment {

    private AccountViewModel accountViewModel;
    TextView mPhone, mEmail, mFullName, mAppName;
    View mView;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        mView = inflater.inflate(R.layout.fragment_account, container, false);
        accountViewModel = ViewModelProviders.of(this).get(AccountViewModel.class);
        accountViewModel.getUserProfile().observe(getViewLifecycleOwner(), new Observer<UserProfile>() {
            @Override
            public void onChanged(UserProfile userProfile) {
                if(userProfile != null){
                    //UPDATE YOUR UI HERE
                    mPhone = (TextView) mView.findViewById(R.id.txtPhone);
                    mEmail = (TextView) mView.findViewById(R.id.txtEmail);
                    mFullName = (TextView) mView.findViewById(R.id.txtName);
                    mAppName = (TextView) mView.findViewById(R.id.txtappName);

                    mPhone.setText(userProfile.phone);
                    mEmail.setText(userProfile.email);
                    mFullName.setText(userProfile.Name);
                    mAppName.setText(userProfile.Name);

                } else {
                    //SHOW ERROR MESSAGE
                }
            }
        });

      // final TextView textView = root.findViewById(R.id.txtName);
       /* accountViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });*/


        return mView;
    }





}