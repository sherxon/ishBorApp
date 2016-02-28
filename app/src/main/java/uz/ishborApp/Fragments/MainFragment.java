package uz.ishborApp.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arlib.floatingsearchview.FloatingSearchView;
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;
import com.path.android.jobqueue.JobManager;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;
import uz.ishborApp.Activity.MainActivity;
import uz.ishborApp.Components.SearchController;
import uz.ishborApp.Components.TagSuggestionItem;
import uz.ishborApp.Entity.Search;
import uz.ishborApp.Events.SearchSuggestionItemResultEvent;
import uz.ishborApp.Events.TagSuggestionClickedEvent;
import uz.ishborApp.Jobs.SearchTagJob;
import uz.ishborApp.Jobs.SearchVacancyByTagJob;
import uz.ishborApp.MyApplication;
import uz.ishborApp.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainFragment extends Fragment {

    @Bind(R.id.floating_search_view)
    FloatingSearchView mSearchView;

    @Inject
    JobManager jobManager;

    @Inject
    SearchController searchController;

   static private MainActivity parentActivity;
    public MainFragment() {
    }
    public static Fragment newInstance(MainActivity mainActivity) {
        parentActivity=mainActivity;
        MainFragment mainFragment=new MainFragment();
        return mainFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyApplication.get(getActivity()).getAppComponent().inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView= inflater.inflate(R.layout.fragment_main, container, false);

        ButterKnife.bind(this, rootView);

        if(parentActivity.getSupportActionBar()!=null){
           parentActivity.getSupportActionBar().hide();
        }


        mSearchView.setOnQueryChangeListener(new FloatingSearchView.OnQueryChangeListener() {
            @Override
            public void onSearchTextChanged(String oldQuery, String newQuery) {
                if (!oldQuery.equals("") && newQuery.equals("")) {
                    mSearchView.clearSuggestions();
                } else {
                    mSearchView.showProgress();
                    //setSearchResultListFragment();
                    jobManager.addJob(new SearchTagJob(newQuery));
                    jobManager.start();
                }
            }
        });
        mSearchView.setOnFocusChangeListener(new FloatingSearchView.OnFocusChangeListener() {
            @Override
            public void onFocus() {
                //parentActivity.toolbar.animate().translationY(-parentActivity.toolbar.getBottom()).setInterpolator(new AccelerateInterpolator()).start();
                //parentActivity.appBar.animate().translationY(-parentActivity.appBar.getBottom()).setInterpolator(new AccelerateInterpolator()).start();
                //parentActivity.toolbar.setVisibility(View.GONE);
                //parentActivity.getSupportActionBar().hide();

                List<TagSuggestionItem> suggestionList = searchController.getSearchHistory(4);
                mSearchView.swapSuggestions(suggestionList);

            }

            @Override
            public void onFocusCleared() {
                    mSearchView.hideProgress();
            }
        });

        mSearchView.setOnSearchListener(new FloatingSearchView.OnSearchListener() {
            @Override
            public void onSuggestionClicked(SearchSuggestion searchSuggestion) {
                Search search = ((TagSuggestionItem) searchSuggestion).getSearch();
                jobManager.addJob(new SearchVacancyByTagJob(search.getWord()));
                jobManager.start();
                EventBus.getDefault().post(new TagSuggestionClickedEvent(search));
                mSearchView.setSearchHint(search.getWord());
            }

            @Override
            public void onSearchAction() {
                System.out.println("onSearchAction()");
            }
        });
        mSearchView.setOnLeftMenuClickListener(new FloatingSearchView.OnLeftMenuClickListener() {
            @Override
            public void onMenuOpened() {
                this.onMenuClosed();
                parentActivity.drawer.openDrawer(GravityCompat.START);

            }

            @Override
            public void onMenuClosed() {

            }
        });

        return rootView;
    }

    public void onEventMainThread(SearchSuggestionItemResultEvent event){
        mSearchView.swapSuggestions(event.getList());
        mSearchView.hideProgress();
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }


}
