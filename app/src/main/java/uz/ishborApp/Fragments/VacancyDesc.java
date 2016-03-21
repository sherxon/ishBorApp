package uz.ishborApp.Fragments;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.path.android.jobqueue.JobManager;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;
import uz.ishborApp.Activity.BaseDrawerActivity;
import uz.ishborApp.Entity.DaoMaster;
import uz.ishborApp.Entity.Vacancy;
import uz.ishborApp.Events.FavouriteJobEvent;
import uz.ishborApp.MyApplication;
import uz.ishborApp.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link VacancyDesc#newInstance} factory method to
 * create an instance of this fragment.
 */
public class VacancyDesc extends Fragment {

    @Bind(R.id.header_position) TextView headerPosition;

    @Bind(R.id.header_company) TextView headerCompany;

    @Bind(R.id.header_location) TextView headerLocation;

    @Bind(R.id.headerDate) TextView headerDate;

    @Bind(R.id.btnApply) Button btnApply;

    @Bind(R.id.btnLike) Button btnLike;

    @Bind(R.id.jobdesc) TextView jobdesc;

    @Inject
    JobManager jobManager;

    @Inject
    DaoMaster daoMaster;

    BaseDrawerActivity parentActivity;
   private String position,company, location, desc, stDate;
    private Long id;

    // TODO: Rename and change types and number of parameters
    public static VacancyDesc newInstance(Vacancy vacancy) {
        VacancyDesc fragment = new VacancyDesc();
        Bundle args = new Bundle();
        args.putString("position", vacancy.getPosition());
        args.putString("company", vacancy.getCompanyName());
        args.putLong("id", vacancy.getId());
        args.putString("desc", vacancy.getDescc());
        args.putString("date", vacancy.getStDate());
        fragment.setArguments(args);
        return fragment;
    }

    public VacancyDesc() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            id = getArguments().getLong("id");
            company = getArguments().getString("company");
            position = getArguments().getString("position");
            desc = getArguments().getString("desc");
            stDate = getArguments().getString("date");
        }
        MyApplication.get(getActivity()).getAppComponent().inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_vacancy_desc, container, false);

        parentActivity= (BaseDrawerActivity) getActivity();
        ButterKnife.bind(this, view);
        headerPosition.setText(position);
        headerCompany.setText(company);
        headerLocation.setText("Mirzo Ulugbek, Tashkent");
        jobdesc.setText(desc);
        headerDate.setText(stDate);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(daoMaster.newSession().getVacancyDao().load(id)!=null){
            Drawable drawable=getContext().getResources().getDrawable(R.drawable.likefill_24);
            btnLike.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);
            btnLike.setText("Liked");
            btnLike.setClickable(false);
        }
    }

    @OnClick(R.id.btnLike)
    public void OnLiked(Button button){
        Vacancy vacancy= new Vacancy();
        vacancy.setId(id);
        vacancy.setPosition(position);
        vacancy.setDescc(desc);
        vacancy.setCompanyName(company);
        vacancy.setStDate(stDate);
        EventBus.getDefault().post(new FavouriteJobEvent(FavouriteJobEvent.ACTION.SAVE, vacancy));
        Drawable drawable=getContext().getResources().getDrawable(R.drawable.likefill_24);
        btnLike.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);
    }

    @OnClick(R.id.btnApply)
    public void OnApplied(Button button){
        System.out.println("OnApplied()");
    }
}
