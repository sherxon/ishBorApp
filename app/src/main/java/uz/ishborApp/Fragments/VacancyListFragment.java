package uz.ishborApp.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.path.android.jobqueue.JobManager;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;
import uz.ishborApp.Activity.BaseDrawerActivity;
import uz.ishborApp.Adaptars.VacancyAdapter;
import uz.ishborApp.Entity.DaoMaster;
import uz.ishborApp.Events.FavouriteJobEvent;
import uz.ishborApp.Events.VacancyListEvent;
import uz.ishborApp.Jobs.VacancyListJob;
import uz.ishborApp.MyApplication;
import uz.ishborApp.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the to handle interaction events.
 * Use the {@link VacancyListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class VacancyListFragment extends Fragment {

    @Bind(R.id.cardListVacancy)
    RecyclerView recList;

    @Inject
    JobManager jobManager;

    @Inject
    DaoMaster daoMaster;

    BaseDrawerActivity parentActivity;

    private String type;
    private Long categoryId;
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment VacancyListFragment.
     */
    public static VacancyListFragment newInstance(String type, Long categoryId) {
        VacancyListFragment fragment = new VacancyListFragment();
        Bundle args = new Bundle();
        args.putString("type", type);
        args.putLong("categoryId", categoryId);
        fragment.setArguments(args);
        return fragment;
    }

    public VacancyListFragment() {
        // Required empty public constructor

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            type = getArguments().getString("type");
            categoryId = getArguments().getLong("categoryId");
        }
        MyApplication.get(getActivity()).getAppComponent().inject(this);
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
        View view=inflater.inflate(R.layout.fragment_vacancy_list, container, false);

        parentActivity= (BaseDrawerActivity) getActivity();
        ButterKnife.bind(this, view);
        parentActivity.showProgress();
        recList.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager =  new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recList.setLayoutManager(linearLayoutManager);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(categoryId!=0) jobManager.addJob(new VacancyListJob(categoryId)); // when category is clicked
        else{
            recList.setAdapter(new VacancyAdapter(daoMaster.newSession().getVacancyDao().loadAll(),
                    FavouriteJobEvent.ACTION.DELETE)); // when favourites opened
            parentActivity.hideProgress();
        }
    }
    // when category is clicked the results came here
    public void onEventMainThread(VacancyListEvent vacancyList){

        if(!vacancyList.getTargetClass().equals(VacancyListFragment.class)) return;

        VacancyAdapter vacancyAdapter=new VacancyAdapter(vacancyList.getVacancyList(), FavouriteJobEvent.ACTION.SAVE);
        recList.setAdapter(vacancyAdapter);
        parentActivity.hideProgress();
    }
}
