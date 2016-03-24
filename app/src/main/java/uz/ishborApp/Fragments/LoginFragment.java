package uz.ishborApp.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

import butterknife.Bind;
import butterknife.ButterKnife;
import uz.ishborApp.R;

public class LoginFragment extends Fragment {

    @Bind(R.id.imageView) ImageView profileImage;

    @Bind(R.id.name) TextView profileName;

    @Bind(R.id.id) TextView profileId;

    @Bind(R.id.premail) TextView profileEmail;

    @Bind(R.id.appliedJob) RecyclerView appliedJobList;

    private CallbackManager callbackManager;

    @Bind(R.id.login_button) public LoginButton loginButton;

    private ProfileTracker mProfileTracker;

    private AccessTokenTracker accessTokenTracker;

    private String id, birthday, firstName, lastName, gender, name, email;
    private boolean verified;
   private AccessToken accessToken;

    public LoginFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View rootView= inflater.inflate(R.layout.content_login, container, false);
        ButterKnife.bind(this, rootView);

        callbackManager = CallbackManager.Factory.create();
        loginButton.setReadPermissions(Arrays.asList("public_profile", "email"));
        loginButton.setFragment(this);
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                accessToken = loginResult.getAccessToken();
                getAllInfo(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                System.out.println("saas");
            }

            @Override
            public void onError(FacebookException e) {
                System.out.println(e.toString());
            }
        });
        accessTokenTracker =new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
                if(currentAccessToken==null){
                    clearFields();
                }
            }
        };

        if(isLoggedIn()){
            getAllInfo(AccessToken.getCurrentAccessToken());
        }else{
            profileImage.setImageDrawable(getResources().getDrawable(R.drawable.user_96));
            profileEmail.setText("");
            profileName.setText("");
        }

        return rootView;
    }

    private void clearFields() {
        profileImage.setImageDrawable(getResources().getDrawable(R.drawable.user_96));
        profileEmail.setText("");
        profileName.setText("");
    }

    public boolean isLoggedIn() {
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        return accessToken != null;
    }

    private void getAllInfo(AccessToken  accessToken) {
        GraphRequest request = GraphRequest.newMeRequest(
                accessToken,
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        Profile profile = Profile.getCurrentProfile();
                        if (Profile.getCurrentProfile() == null) {
                            mProfileTracker = new ProfileTracker() {
                                @Override
                                protected void onCurrentProfileChanged(Profile profile, Profile profile2) {
                                    mProfileTracker.stopTracking();
                                }
                            };
                            mProfileTracker.startTracking();
                        } else {
                            profile = Profile.getCurrentProfile();
                        }
                        // Application code
                        object.toString();
                        try {
                            if (object.has("birthday")) {
                                birthday = object.getString("birthday");
                            }
                            if (object.has("email")) {
                                email = object.getString("email");
                            }
                            if (object.has("name")) {
                                name = object.getString("name");
                            }
                            if (object.has("gender")) {
                                gender = object.getString("gender");
                            }
                            if (object.has("id")) {
                                id = object.getString("id");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Picasso.with(getActivity())
                                .load(profile.getProfilePictureUri(500, 500))
                                .into(profileImage);
                        profileEmail.setText(email);
                        profileName.setText(profile.getName());
                    }
                });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,email,gender,birthday,location");
        request.setParameters(parameters);
        request.executeAsync();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }



}
