package uz.ishborApp.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import com.github.gorbin.asne.core.SocialNetwork;
import com.github.gorbin.asne.core.SocialNetworkManager;
import com.github.gorbin.asne.core.listener.OnLoginCompleteListener;
import com.github.gorbin.asne.facebook.FacebookSocialNetwork;
import com.github.gorbin.asne.googleplus.GooglePlusSocialNetwork;
import com.github.gorbin.asne.linkedin.LinkedInSocialNetwork;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;
import uz.ishborApp.Components.Globals;
import uz.ishborApp.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */

public class LoginFragment extends Fragment implements SocialNetworkManager.OnInitializationCompleteListener,
        OnLoginCompleteListener{

    public static SocialNetworkManager mSocialNetworkManager;

    @Bind(R.id.facebook)
    Button facebook;

    @Bind(R.id.googleplus)
    Button googleplus;

    public static LoginFragment newInstance() {
        LoginFragment fragment = new LoginFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    public void onEventMainThread(LoginFragment loginFragment){

    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView= inflater.inflate(R.layout.fragment_login, container, false);
        ButterKnife.bind(this, rootView);

        facebook.setOnClickListener(loginClick);
        googleplus.setOnClickListener(loginClick);

        ArrayList<String> fbScope = new ArrayList<String>();
        fbScope.addAll(Arrays.asList("public_profile, email, user_friends"));

        mSocialNetworkManager = (SocialNetworkManager) getFragmentManager().findFragmentByTag(Globals.SOCIAL_NETWORK_TAG);

            if(mSocialNetworkManager==null){
                mSocialNetworkManager= new SocialNetworkManager();

                FacebookSocialNetwork fbNetwork=new FacebookSocialNetwork(this, fbScope);
                mSocialNetworkManager.addSocialNetwork(fbNetwork);

//                GooglePlusSocialNetwork gpNetwork = new GooglePlusSocialNetwork(this);
//                mSocialNetworkManager.addSocialNetwork(gpNetwork);

                getFragmentManager().beginTransaction().add(mSocialNetworkManager, Globals.SOCIAL_NETWORK_TAG).commit();
                mSocialNetworkManager.setOnInitializationCompleteListener(this);
            }else{
                if(!mSocialNetworkManager.getInitializedSocialNetworks().isEmpty()){
                    List<SocialNetwork> socialNetworks = mSocialNetworkManager.getInitializedSocialNetworks();
                    for (SocialNetwork socialNetwork : socialNetworks) {
                        socialNetwork.setOnLoginCompleteListener(this);
                        initSocialNetwork(socialNetwork);
                    }
                }
            }
        return rootView;
    }

    private void initSocialNetwork(SocialNetwork socialNetwork) {
        if(socialNetwork.isConnected()){
            switch (socialNetwork.getID()){
                case FacebookSocialNetwork.ID:
                    facebook.setText("Show Facebook profile");
                    break;
                case GooglePlusSocialNetwork.ID:
                    googleplus.setText("Show GooglePlus profile");
                    break;
            }
        }

    }

    @Override
    public void onSocialNetworkManagerInitialized() {
        for (SocialNetwork socialNetwork : mSocialNetworkManager.getInitializedSocialNetworks()) {
            socialNetwork.setOnLoginCompleteListener(this);
            initSocialNetwork(socialNetwork);
        }
    }

    private View.OnClickListener loginClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            int networkId = 0;
            switch (view.getId()){
                case R.id.facebook:
                    networkId = FacebookSocialNetwork.ID;
                    break;
                case R.id.linkedin:
                    networkId = LinkedInSocialNetwork.ID;
                    break;
                case R.id.googleplus:
                    networkId = GooglePlusSocialNetwork.ID;
                    break;
            }
            SocialNetwork socialNetwork = mSocialNetworkManager.getSocialNetwork(networkId);
            if(!socialNetwork.isConnected()) {
                if(networkId != 0) {
                    socialNetwork.requestLogin();
                } else {
                    Toast.makeText(getActivity(), "Wrong networkId", Toast.LENGTH_LONG).show();
                }
            } else {
                startProfile(socialNetwork.getID());
            }
        }
    };

    private void startProfile(int id) {
        System.out.println(id);
    }

    @Override
    public void onLoginSuccess(int socialNetworkID) {
        startProfile(socialNetworkID);
    }

    @Override
    public void onError(int socialNetworkID, String requestID, String errorMessage, Object data) {

    }
}
