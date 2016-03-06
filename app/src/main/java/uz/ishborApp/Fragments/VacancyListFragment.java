package uz.ishborApp.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;
import uz.ishborApp.Adaptars.VacancyAdapter;
import uz.ishborApp.Entity.Vacancy;
import uz.ishborApp.Events.VacancyListEvent;
import uz.ishborApp.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link VacancyListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class VacancyListFragment extends Fragment {

    @Bind(R.id.cardListVacancy)
    RecyclerView recList;

    List<Vacancy> data;
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment VacancyListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static VacancyListFragment newInstance(List<Vacancy> list) {
        VacancyListFragment fragment = new VacancyListFragment();
        //fragment.onEvent(list);
        return fragment;
    }

    public VacancyListFragment() {
        // Required empty public constructor

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
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
        ButterKnife.bind(this, view);
        recList.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager =  new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recList.setLayoutManager(linearLayoutManager);
        if(data!=null) recList.setAdapter(new VacancyAdapter(data));
        return view;
    }

    public void onEventMainThread(VacancyListEvent vacancyList){
        if(!vacancyList.getTargetClass().equals(VacancyListFragment.class))return;
        data=vacancyList.getVacancyList();
        if(recList!=null){
            VacancyAdapter vacancyAdapter=new VacancyAdapter(vacancyList.getVacancyList());
            recList.setAdapter(vacancyAdapter);
        }
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


}
