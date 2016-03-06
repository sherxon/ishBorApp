package uz.ishborApp.Activity;

import android.support.design.widget.AppBarLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.path.android.jobqueue.JobManager;

import javax.inject.Inject;

import butterknife.Bind;
import uz.ishborApp.Fragments.CategoryFragment;
import uz.ishborApp.Fragments.LoginFragment;
import uz.ishborApp.Fragments.MainFragment;
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

    @Inject
    JobManager jobManager;

    protected void onCreateDrawer() {
        setSupportActionBar(toolbar);

        NavigationView navigationView= (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        MyApplication.get(this).getAppComponent().inject(this);

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
        //getSupportFragmentManager().popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        if(id==R.id.nav_search_vacancy ){
            getSupportFragmentManager().beginTransaction().
                    addToBackStack("search").
                    replace(R.id._fragment, MainFragment.newInstance())
                    .commit();
        }else if(id==R.id.nav_category){
            getSupportFragmentManager().beginTransaction().
                    addToBackStack("category").
                    replace(R.id._fragment, CategoryFragment.newInstance())
                    .commit();
        }else if(id==R.id.nav_login){
            getSupportFragmentManager().beginTransaction().
                    addToBackStack("login").
                    replace(R.id._fragment, LoginFragment.newInstance())
                    .commit();
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
