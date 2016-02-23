package uz.ishborApp.Activity;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.Menu;
import android.view.MenuItem;

import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;
import uz.ishborApp.Fragments.MainFragment;
import uz.ishborApp.Fragments.SearchResultListFragment;
import uz.ishborApp.MyApplication;
import uz.ishborApp.R;

public class MainActivity extends BaseDrawerActivity implements FragmentManager.OnBackStackChangedListener {


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
                    add(R.id._fragment, new MainFragment())
                    .commit();

        MyApplication.get(this).getAppComponent().inject(this);
    }

    private void setSearchResultListFragment() {
        Fragment fragment= new SearchResultListFragment();
        FragmentTransaction transaction=getFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentSearchResult, fragment);
        transaction.commit();
    }



    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    public void onEventMainThread(AuthActivity authActivity){

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
