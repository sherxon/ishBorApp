package uz.ishborApp.Activity;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.facebook.FacebookSdk;
import com.github.rahatarmanahmed.cpv.CircularProgressView;
import com.path.android.jobqueue.JobManager;

import javax.inject.Inject;

import butterknife.Bind;
import uz.ishborApp.Components.Globals;
import uz.ishborApp.Fragments.AboutFragment;
import uz.ishborApp.Fragments.CategoryFragment;
import uz.ishborApp.Fragments.LoginFragment;
import uz.ishborApp.Fragments.MainFragment;
import uz.ishborApp.Fragments.ProfileFragment;
import uz.ishborApp.Fragments.VacancyListFragment;
import uz.ishborApp.MyApplication;
import uz.ishborApp.R;

/**
 * Created by sherxon on 1/5/16.
 */
public  class BaseDrawerActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

   @Bind(R.id.toolbar)
   public Toolbar toolbar;

   @Bind(R.id.appBar)
   public AppBarLayout appBar;

   @Bind(R.id.drawer_layout)
   public DrawerLayout drawer;

   @Bind(R.id.progress_view)
   public CircularProgressView progressView;

   @Inject
   JobManager jobManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
    }


    protected void onCreateDrawer() {
        setSupportActionBar(toolbar);
        NavigationView navigationView= (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        MyApplication.get(this).getAppComponent().inject(this);
    }

    public void showProgress(){
        progressView.setVisibility(View.VISIBLE);
        progressView.startAnimation();

    }
    public void hideProgress(){
        progressView.stopAnimation();
        progressView.setVisibility(View.GONE);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawerLayout= (DrawerLayout) findViewById(R.id.drawer_layout);
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else {
//            Fragment fragment=getFragmentManager().findFragmentByTag(Globals.FRAGMENT_TAG);
//            if(fragment!=null &&  fragment.isVisible()){
//                getFragmentManager().popBackStack("VACANCY_LIST_TAG", FragmentManager.POP_BACK_STACK_INCLUSIVE);
//            }else
                super.onBackPressed();
        }
    }

//    @OnClick(R.id.toolbar)
//    public void toolBarNavigataionOnClicked(View view){
//        View view1=view;
//    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        int id = item.getItemId();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        Fragment fragment=null;
        if(id==R.id.nav_search_vacancy ){
            fragment=MainFragment.newInstance();
        }else if(id==R.id.nav_category){
             fragment = CategoryFragment.newInstance();
        }else if(id==R.id.nav_login){
            fragment=new LoginFragment();
        } else if(id==R.id.nav_favourites){
            fragment=VacancyListFragment.newInstance(Globals.FAVOURITES, 0l);
        } else if(id==R.id.nav_send){
            fragment= AboutFragment.newInstance();
        } else if(id==R.id.nav_profile){
            fragment= ProfileFragment.newInstance();
        }

        if(fragment!=null){
            transaction.replace(R.id._fragment, fragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
