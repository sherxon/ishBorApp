package uz.ishborApp.Activity;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.github.gorbin.asne.core.SocialNetwork;
import com.github.gorbin.asne.core.SocialNetworkManager;
import com.github.gorbin.asne.facebook.FacebookSocialNetwork;

import java.util.ArrayList;
import java.util.Arrays;

import uz.ishborApp.Components.Globals;
import uz.ishborApp.R;

public class AuthActivity extends AppCompatActivity {

   private Button facebook;
   private SocialNetworkManager socialNetworkManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        facebook= (Button) findViewById(R.id.facebook);
        facebook.setOnClickListener(loginClick);
        socialNetworkManager= (SocialNetworkManager) getSupportFragmentManager().findFragmentByTag(Globals.SOCIAL_NETWORK_TAG);
        if (socialNetworkManager == null) socialNetworkManager = new SocialNetworkManager();
        ArrayList<String> fbScope = new ArrayList<String>();
        fbScope.addAll(Arrays.asList("public_profile, email, user_friends"));
//        FacebookSocialNetwork fbNetwork = new FacebookSocialNetwork(this.getFragmentManager().findFragmentByTag(), fbScope);
//        socialNetworkManager.addSocialNetwork(fbNetwork);
//        socialNetworkManager

    }

    private View.OnClickListener loginClick=new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            int networkId = 0;
            switch (view.getId()){
                case R.id.facebook:
                    networkId = FacebookSocialNetwork.ID;
                    break;
            }
//            SocialNetwork socialNetwork=


        }
    };


}
