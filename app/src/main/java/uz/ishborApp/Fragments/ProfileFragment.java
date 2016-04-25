package uz.ishborApp.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.path.android.jobqueue.JobManager;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import uz.ishborApp.Activity.BaseDrawerActivity;
import uz.ishborApp.Activity.MainActivity;
import uz.ishborApp.Adaptars.AppliedJobAdapter;
import uz.ishborApp.Entity.Applied;
import uz.ishborApp.Entity.AppliedDao;
import uz.ishborApp.Entity.DaoMaster;
import uz.ishborApp.MyApplication;
import uz.ishborApp.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {

    @Bind(R.id.lvApplied)
    ListView lvApplied;


    @Inject
    JobManager jobManager;

    @Inject
    DaoMaster daoMaster;
    BaseDrawerActivity parentActivity;
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance() {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
        MyApplication.get(getActivity()).getAppComponent().inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View rootView= inflater.inflate(R.layout.profile_fragment, container, false);
        parentActivity= (BaseDrawerActivity) getActivity();
        ButterKnife.bind(this, rootView);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if(((MainActivity)getActivity()).getSupportActionBar()!=null) {
            ((MainActivity) getActivity()).getSupportActionBar().show();
            ((MainActivity) getActivity()).getSupportActionBar().setTitle(R.string.applied);
        }

        AppliedDao appliedDao=daoMaster.newSession().getAppliedDao();
        ArrayList<Applied> list= new ArrayList<>(appliedDao.loadAll());
        AppliedJobAdapter appliedJobAdapter=new AppliedJobAdapter(parentActivity, list);
        lvApplied.setAdapter(appliedJobAdapter);


    }
}
