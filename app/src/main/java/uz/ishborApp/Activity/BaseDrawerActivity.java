package uz.ishborApp.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import com.path.android.jobqueue.JobManager;
import javax.inject.Inject;
import butterknife.Bind;
import uz.ishborApp.Components.Globals;
import uz.ishborApp.MyApplication;
import uz.ishborApp.R;

/**
 * Created by sherxon on 1/5/16.
 */
public  class BaseDrawerActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Bind(R.id.drawer_layout)
    DrawerLayout drawer;

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
            Fragment fragment=getFragmentManager().findFragmentByTag(Globals.FRAGMENT_TAG);
            if(fragment!=null &&  fragment.isVisible()){
                getFragmentManager().popBackStack("VACANCY_LIST_TAG", FragmentManager.POP_BACK_STACK_INCLUSIVE);
            }else
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
        if(id==R.id.nav_search_vacancy ){
            Intent intent=new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            startActivity(intent);
        }else if(id==R.id.nav_category){
            Intent intent=new Intent(this, CategoryActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            startActivity(intent);
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
