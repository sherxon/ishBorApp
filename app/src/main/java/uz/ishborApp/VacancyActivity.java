package uz.ishborApp;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.FragmentManager;
import android.net.Uri;
import android.os.Bundle;

import java.util.List;

import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;
import uz.ishborApp.Components.Globals;
import uz.ishborApp.Entity.Category;
import uz.ishborApp.Entity.Vacancy;
import uz.ishborApp.Entity.VacancyDao;
import uz.ishborApp.Fragments.VacancyDesc;
import uz.ishborApp.Fragments.VacancyList;

public class VacancyActivity extends BaseDrawerActivity implements VacancyDesc.OnFragmentInteractionListener, VacancyList.OnFragmentInteractionListener {

    //@Bind(R.id.cardListVacancy) RecyclerView recList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vacancy);

        ButterKnife.bind(this);

        super.onCreateDrawer();

//        recList.setHasFixedSize(true);
//        LinearLayoutManager linearLayoutManager =  new LinearLayoutManager(this);
//        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
//        recList.setLayoutManager(linearLayoutManager);

        long categoryId= getIntent().getExtras().getLong("id");
        loadCategoryList(categoryId);
    }


    private void loadCategoryList(Long categoryId) {
        VacancyDao.createTable(daoMaster.getDatabase(), true);
        Category category=daoMaster.newSession().getCategoryDao().load(categoryId);
        List<Vacancy> vacancyList=category.getVacancyList();
        if(vacancyList.size()==0)
            dbBalance.loadVacancyToLocalDb(categoryId);
        else
            startListFragment(vacancyList);
    }

    private void startListFragment(List<Vacancy> vacancyList) {
        Fragment fragment=new VacancyList();
        FragmentManager manager=getFragmentManager();
        FragmentTransaction transaction=manager.beginTransaction();
        transaction.replace(R.id.fJobDesc, fragment, Globals.FRAGMENT_TAG);
        transaction.commit();

        EventBus.getDefault().post(vacancyList);
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
        Fragment fragment=new VacancyDesc();
        FragmentManager manager=getFragmentManager();
        FragmentTransaction transaction=manager.beginTransaction();
        transaction.replace(R.id.fJobDesc, fragment, Globals.FRAGMENT_TAG);
        transaction.commit();
    }


    @Override
    public void onVacancyDescFragmentInteraction(Uri uri) {

    }

    @Override
    public void onVacancyListFragmentInteraction() {

    }
}
