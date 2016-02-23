package uz.ishborApp.Activity;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.FragmentManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import java.util.List;

import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;
import uz.ishborApp.Activity.BaseDrawerActivity;
import uz.ishborApp.Components.Globals;
import uz.ishborApp.Entity.Category;
import uz.ishborApp.Entity.Vacancy;
import uz.ishborApp.Entity.VacancyDao;
import uz.ishborApp.Fragments.VacancyDesc;
import uz.ishborApp.Fragments.VacancyList;
import uz.ishborApp.Jobs.VacancyListJob;
import uz.ishborApp.R;

public class VacancyActivity extends BaseDrawerActivity implements VacancyDesc.OnFragmentInteractionListener,
        VacancyList.OnFragmentInteractionListener, View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vacancy);

        ButterKnife.bind(this);

        super.onCreateDrawer();
        toolbar.setTitle(getIntent().getExtras().getString("title"));
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        toolbar.setNavigationOnClickListener(this);

        long categoryId = getIntent().getExtras().getLong("id");
        loadCategoryList(categoryId);
    }


    private void loadCategoryList(Long categoryId) {
        startListFragment();
        jobManager.addJob(new VacancyListJob(categoryId));
        jobManager.start();
    }

    private void startListFragment() {
        Fragment fragment=new VacancyList();
        FragmentManager manager=getFragmentManager();
        FragmentTransaction transaction=manager.beginTransaction();
        transaction.replace(R.id.fJobDesc, fragment);
        transaction.commit();
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    public void onEventMainThread(Vacancy vacancy){
        String url=Globals.LOCAL_JOBDESC + vacancy.getId();
        Fragment fragment=VacancyDesc.newInstance(url);
        FragmentManager manager=getFragmentManager();
        FragmentTransaction transaction=manager.beginTransaction();
        transaction.replace(R.id.fJobDesc, fragment, Globals.FRAGMENT_TAG).addToBackStack("VACANCY_LIST_TAG");
        transaction.commit();
    }


    @Override
    public void onVacancyDescFragmentInteraction(Uri uri) {

    }

    @Override
    public void onVacancyListFragmentInteraction() {

    }

    @Override
    public void onClick(View view) {
        super.onBackPressed();
    }
}
