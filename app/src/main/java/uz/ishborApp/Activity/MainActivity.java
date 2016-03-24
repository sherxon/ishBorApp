package uz.ishborApp.Activity;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuItem;

import javax.inject.Inject;

import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;
import uz.ishborApp.Components.Globals;
import uz.ishborApp.Entity.Category;
import uz.ishborApp.Entity.DaoMaster;
import uz.ishborApp.Entity.Vacancy;
import uz.ishborApp.Events.FavouriteJobEvent;
import uz.ishborApp.Fragments.MainFragment;
import uz.ishborApp.Fragments.VacancyDesc;
import uz.ishborApp.Fragments.VacancyListFragment;
import uz.ishborApp.Jobs.VacancyListJob;
import uz.ishborApp.MyApplication;
import uz.ishborApp.R;

public class MainActivity extends BaseDrawerActivity implements FragmentManager.OnBackStackChangedListener {

    @Inject
    DaoMaster daoMaster;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        onCreateDrawer();
        getSupportFragmentManager().addOnBackStackChangedListener(this);
        homeAsUpByBackStack();
        if(savedInstanceState==null)
            getSupportFragmentManager().beginTransaction().
                    replace(R.id._fragment, MainFragment.newInstance()).
                    commit();
        MyApplication.get(this).getAppComponent().inject(this);

    }


    /*On category list item clicked*/
    public void onEventMainThread(Category category){
        Fragment fragment=VacancyListFragment.newInstance(Globals.BY_CATEGORY, category.getId());
        FragmentManager manager=getSupportFragmentManager();
        FragmentTransaction transaction=manager.beginTransaction();
        transaction.replace(R.id._fragment, fragment).addToBackStack("vacancyList");
        transaction.commit();

    }
    public void onEventMainThread(Boolean progress){ // when problem occured
        if(!progress){
            hideProgress();
            Snackbar.make(drawer, "Sorry, Your phone is dead", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }else{

        }
    }
    public void onEventMainThread(FavouriteJobEvent event){
        switch (event.getAction()){
            case DELETE: {
                daoMaster.newSession().getVacancyDao().deleteByKey(event.getId());
                break;
            }
            case SAVE:{
                daoMaster.newSession().getVacancyDao().insertOrReplace(event.getVacancy());
                break;
            }
        }
    }

    /*On vacancy list item clicked*/
    public void onEventMainThread(Vacancy vacancy){
        Fragment fragment=VacancyDesc.newInstance(vacancy);
        FragmentManager manager=getSupportFragmentManager();
        FragmentTransaction transaction=manager.beginTransaction();
        transaction.replace(R.id._fragment, fragment).addToBackStack("vacancyDesc");
        transaction.commit();
        jobManager.addJob(new VacancyListJob(vacancy.getId()));
        jobManager.start();
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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        int backStackEntryCount = getSupportFragmentManager().getBackStackEntryCount();
         if(id==android.R.id.home){
             if(backStackEntryCount>1){
                 getFragmentManager().popBackStackImmediate();
                 drawer.setDrawerListener(null);
                 homeAsUpByBackStack();
                 return true;
             }else if(backStackEntryCount==1) {
                 return true;
             }else{
                 System.out.println("smth");
                 return  true;
             }
         }
        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
        return super.onOptionsItemSelected(item);
    }

    private void homeAsUpByBackStack() {
        int backStackEntryCount = getSupportFragmentManager().getBackStackEntryCount();
        if (backStackEntryCount > 0) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        } else {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        }
    }
    @Override
    public void onBackStackChanged() {
     homeAsUpByBackStack();
    }
}
