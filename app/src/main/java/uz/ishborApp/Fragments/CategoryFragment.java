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
import uz.ishborApp.Activity.MainActivity;
import uz.ishborApp.Adaptars.CategoryAdapter;
import uz.ishborApp.Events.CategoryListEvent;
import uz.ishborApp.Jobs.CategoryListJob;
import uz.ishborApp.MyApplication;
import uz.ishborApp.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link CategoryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CategoryFragment extends Fragment {

    @Bind(R.id.cardList)
    RecyclerView recList;

    @Inject
    JobManager jobManager;
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment CategoryFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CategoryFragment newInstance() {
        CategoryFragment fragment = new CategoryFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    public CategoryFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyApplication.get(getActivity()).getAppComponent().inject(this);
        if (getArguments() != null) {}
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }
    public void onEventMainThread(CategoryListEvent categoryListEvent){
        CategoryAdapter categoryAdapter=new CategoryAdapter(categoryListEvent.getCategoryList());
        recList.setAdapter(categoryAdapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView= inflater.inflate(R.layout.fragment_category, container, false);
        ButterKnife.bind(this, rootView);
        recList.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager =  new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recList.setLayoutManager(linearLayoutManager);

        if(((MainActivity)getActivity()).getSupportActionBar()!=null)
            ((MainActivity)getActivity()).getSupportActionBar().show();

        jobManager.addJob(new CategoryListJob());
        jobManager.start();
        return rootView;
    }

}
