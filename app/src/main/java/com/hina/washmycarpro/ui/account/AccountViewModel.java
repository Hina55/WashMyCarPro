package com.hina.washmycarpro.ui.account;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class AccountViewModel extends ViewModel {

    private MutableLiveData<String> mText;
    String userId;
    FirebaseAuth fAuth;
    private MutableLiveData<UserProfile> userProfileMutableLiveData;


    public AccountViewModel(){


        userProfileMutableLiveData = new MutableLiveData<>();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();
        userId = fAuth.getCurrentUser().getUid();
        DocumentReference docRef = db.collection("users").document(userId);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    DocumentSnapshot document = task.getResult();
                    if (document != null && document.exists()) {

                        String name = document.getString("Name");
                        String phone = document.getString("phone");
                        String email = document.getString("email");
                        UserProfile userProfile = document.toObject(UserProfile.class);

                        userProfile.setName(name);
                        userProfile.setPhone(phone);
                        userProfile.setEmail(email);

                        userProfileMutableLiveData.setValue(userProfile);
                    }
                    else{
                        userProfileMutableLiveData.setValue(null);
                    }
                }else{
                    userProfileMutableLiveData.setValue(null);
                }
            }
        });

    }

    public LiveData<UserProfile> getUserProfile() {


        return userProfileMutableLiveData;
    }



   /* public AccountViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Hina Kanwal");
    }

    public LiveData<String> getText() {
        return mText;
    }*/
}