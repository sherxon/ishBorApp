package uz.ishborApp.Activity;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.view.Menu;
import android.view.MenuItem;
import com.arlib.floatingsearchview.FloatingSearchView;
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;
import java.util.List;
import javax.inject.Inject;
import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;
import uz.ishborApp.Components.SearchController;
import uz.ishborApp.Components.VacancySearchSuggestion;
import uz.ishborApp.Entity.Search;
import uz.ishborApp.Events.SearchSuggestionItemResultEvent;
import uz.ishborApp.Fragments.SearchResultListFragment;
import uz.ishborApp.Jobs.SearchTagJob;
import uz.ishborApp.Jobs.SearchVacancyByTagJob;
import uz.ishborApp.MyApplication;
import uz.ishborApp.R;

public class MainActivity extends BaseDrawerActivity
        implements SearchResultListFragment.OnFragmentInteractionListener{


    @Bind(R.id.floating_search_view)
    FloatingSearchView mSearchView;

    @Inject
    SearchController searchController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        super.onCreateDrawer();
        getSupportActionBar().hide();
        MyApplication.get(this).getAppComponent().inject(this);


        mSearchView.setOnQueryChangeListener(new FloatingSearchView.OnQueryChangeListener() {
            @Override
            public void onSearchTextChanged(String oldQuery, String newQuery) {
                if (!oldQuery.equals("") && newQuery.equals("")) {
                    mSearchView.clearSuggestions();
                } else {
                    mSearchView.showProgress();
                    setSearchResultListFragment();
                    jobManager.addJob(new SearchTagJob(newQuery));
                    jobManager.start();

                }
            }
        });
        mSearchView.setOnFocusChangeListener(new FloatingSearchView.OnFocusChangeListener() {
            @Override
            public void onFocus() {
                List<VacancySearchSuggestion> suggestionList = searchController.getSearchHistory(4);
                mSearchView.swapSuggestions(suggestionList);
            }

            @Override
            public void onFocusCleared() {

            }
        });
        mSearchView.setOnSearchListener(new FloatingSearchView.OnSearchListener() {
            @Override
            public void onSuggestionClicked(SearchSuggestion searchSuggestion) {
                Search search = ((VacancySearchSuggestion) searchSuggestion).getSearch();
                //EventBus.getDefault().post(new SearchSuggestionSelectedEvent(search));
                jobManager.addJob(new SearchVacancyByTagJob(search.getWord()));
                jobManager.start();
            }

            @Override
            public void onSearchAction() {
                System.out.println("onSearchAction()");
            }
        });
        mSearchView.setOnLeftMenuClickListener(new FloatingSearchView.OnLeftMenuClickListener() {
            @Override
            public void onMenuOpened() {
                drawer.openDrawer(GravityCompat.START);
            }

            @Override
            public void onMenuClosed() {
                drawer.closeDrawer(GravityCompat.START);
            }
        });

    }

    private void setSearchResultListFragment() {
        Fragment fragment= new SearchResultListFragment();
        FragmentTransaction transaction=getFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentSearchResult, fragment);
        transaction.commit();
    }

    public void onEventMainThread(SearchSuggestionItemResultEvent event){
        mSearchView.swapSuggestions(event.getList());
        mSearchView.hideProgress();
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

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }


        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
