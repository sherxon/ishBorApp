package uz.ishborApp.Fragments;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.nbsp.materialfilepicker.ui.FilePickerActivity;
import com.path.android.jobqueue.JobManager;

import java.io.File;
import java.util.regex.Pattern;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.dao.query.QueryBuilder;
import de.greenrobot.event.EventBus;
import uz.ishborApp.Activity.BaseDrawerActivity;
import uz.ishborApp.Entity.AppliedDao;
import uz.ishborApp.Entity.DaoMaster;
import uz.ishborApp.Entity.Vacancy;
import uz.ishborApp.Events.FavouriteJobEvent;
import uz.ishborApp.Events.UploadEvent;
import uz.ishborApp.Jobs.FileUploadJob;
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

    @Override
    public void onStart() {
        super.onStart();
        if(!EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().register(this);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }


    public VacancyDesc() {
        // Required empty public constructor
    }

    public void onEventMainThread(UploadEvent uploadEvent){ // when problem occured
        Toast.makeText(parentActivity, uploadEvent.getMessasge(), Toast.LENGTH_LONG).show();
        btnApply.setText(R.string.applied);
        btnApply.setClickable(false);
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        if(!EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().register(this);
        }
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
            btnLike.setText(R.string.liked);
            btnLike.setClickable(false);
        }
        AppliedDao.createTable(daoMaster.getDatabase(), true);
        AppliedDao appliedDao=daoMaster.newSession().getAppliedDao();
        QueryBuilder queryBuilder=daoMaster.newSession().getAppliedDao().queryBuilder();
        if(queryBuilder.where(AppliedDao.Properties.VacancyId.eq(id)).count()>0){
            btnApply.setText(R.string.applied);
            btnApply.setClickable(false);
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
        if(AccessToken.getCurrentAccessToken()!=null){
//            new MaterialFilePicker()
//                    .withActivity(parentActivity)
//                    .withRequestCode(1)
//                    .withFilter(Pattern.compile(".*\\.pdf")) // Filtering files and directories by file name using regexp
//                    .withFilterDirectories(true) // Set directories filterable (false by default)
//                    .start();

            Intent intent = new Intent(parentActivity, FilePickerActivity.class);
            intent.putExtra(FilePickerActivity.ARG_FILE_FILTER, Pattern.compile(".*\\.pdf$"));
            intent.putExtra(FilePickerActivity.ARG_DIRECTORIES_FILTER, true);
            startActivityForResult(intent, 1);
        }else{
            EventBus.getDefault().post(parentActivity.getString(R.string.loginfirst));
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 &&  AccessToken.getCurrentAccessToken()!=null) {
            String filePath = data.getStringExtra(FilePickerActivity.RESULT_FILE_PATH);
            File file= new File(filePath);
            jobManager.addJob(new FileUploadJob(file,
                    AccessToken.getCurrentAccessToken().getUserId(),
                    id,
                    position,
                    company
            ));

        }
    }
}
