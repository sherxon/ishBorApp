package uz.ishborApp.Fragments;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;
import uz.ishborApp.Activity.VacancyAdapter;
import uz.ishborApp.Events.VacancyListEvent;
import uz.ishborApp.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SearchResultListFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SearchResultListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchResultListFragment extends Fragment {

    private OnFragmentInteractionListener mListener;

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

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

}
