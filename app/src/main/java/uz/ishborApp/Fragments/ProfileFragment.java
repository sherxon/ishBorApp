//package uz.ishborApp.Fragments;
//
//
//import android.os.Bundle;
//import android.support.v4.app.Fragment;
//import android.support.v7.widget.RecyclerView;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.facebook.login.LoginFragment;
//import com.github.gorbin.asne.core.SocialNetwork;
//import com.github.gorbin.asne.core.listener.OnRequestSocialPersonCompleteListener;
//import com.github.gorbin.asne.core.persons.SocialPerson;
//import com.squareup.picasso.Picasso;
//
//import butterknife.Bind;
//import butterknife.ButterKnife;
//import uz.ishborApp.R;
//
///**
// * A simple {@link Fragment} subclass.
// * Use the {@link ProfileFragment#newInstance} factory method to
// * create an instance of this fragment.
// */
//public class ProfileFragment extends Fragment implements OnRequestSocialPersonCompleteListener {
//
//    private static final String NETWORK_ID = "NETWORK_ID";
//
//    @Bind(R.id.imageView)
//    ImageView profileImage;
//
//    @Bind(R.id.name)
//    TextView profileName;
//
//    @Bind(R.id.id)
//    TextView profileId;
//
//    @Bind(R.id.premail)
//    TextView profileEmail;
//
//    @Bind(R.id.appliedJobs)
//    RecyclerView appliedJobList;
//    private int networkId;
//    private SocialNetwork socialNetwork;
//
//    public static ProfileFragment newInstance(int networkID) {
//        ProfileFragment fragment = new ProfileFragment();
//        Bundle args = new Bundle();
//        args.putInt(NETWORK_ID, networkID);
//        fragment.setArguments(args);
//        return fragment;
//    }
//
//    public ProfileFragment() {
//        // Required empty public constructor
//    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//
//        }
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);
//        networkId = getArguments().containsKey(NETWORK_ID) ? getArguments().getInt(NETWORK_ID) : 0;
//        ButterKnife.bind(this, rootView);
//        socialNetwork = LoginFragment.mSocialNetworkManager.getSocialNetwork(networkId);
//        socialNetwork.setOnRequestCurrentPersonCompleteListener(this);
//        socialNetwork.requestCurrentPerson();
//
//
//        return rootView;
//    }
//
//
//    @Override
//    public void onRequestSocialPersonSuccess(int socialNetworkId, SocialPerson socialPerson) {
//        Picasso.with(getActivity())
//                .load(socialPerson.avatarURL)
//                .into(profileImage);
//        profileName.setText(socialPerson.name);
//        profileId.setText(socialPerson.id);
//        String socialPersonString = socialPerson.toString();
//        String infoString = socialPersonString.substring(socialPersonString.indexOf("{")+1, socialPersonString.lastIndexOf("}"));
//       profileEmail.setText(infoString.replace(", ", "\n"));
//    }
//
//    @Override
//    public void onError(int socialNetworkID, String requestID, String errorMessage, Object data) {
//        Toast.makeText(getActivity(), "ERROR: " + errorMessage, Toast.LENGTH_LONG).show();
//    }
//}
