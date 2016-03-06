package uz.ishborApp.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;
import uz.ishborApp.Adaptars.VacancyAdapter;
import uz.ishborApp.Events.VacancyListEvent;
import uz.ishborApp.R;

/**
 * A simple {@link Fragment} subclass.
 * to handle interaction events.
 * Use the {@link SearchResultListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchResultListFragment extends Fragment {



    @Bind(R.id.recycleSearchResult)
    RecyclerView recyclerView;

    public static SearchResultListFragment newInstance() {
        SearchResultListFragment fragment = new SearchResultListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public SearchResultListFragment() {
        // Required empty public constructor
      //  EventBus.getDefault().register(this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_search_result_list, container, false);
        ButterKnife.bind(this, view);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager llm= new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);
        return view;
    }
    public void onEvent(VacancyListEvent vacancyListEvent){
        if(!vacancyListEvent.getTargetClass().equals(SearchResultListFragment.class))return;
        VacancyAdapter vacancyAdapter= new VacancyAdapter(vacancyListEvent.getVacancyList());
        recyclerView.setAdapter(vacancyAdapter);
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
}
