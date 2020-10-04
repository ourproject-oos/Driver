package com.example.driver.Notifications;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging ;
import com.google.firebase.messaging.FirebaseMessagingService;

public class MyFirebaseIdService extends FirebaseMessagingService {

    public void onNewToken (String s)
    {
        super.onNewToken(s);
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        String refreshToken = FirebaseInstanceId.getInstance().getToken();
        if (firebaseUser!=null)
        {
            updateToken(refreshToken);
        }

    }

    private void updateToken (String refreshToken)
    {
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        Token token1 = new Token(refreshToken);
        FirebaseDatabase.getInstance().getReference("Token").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(token1);
    }


}
