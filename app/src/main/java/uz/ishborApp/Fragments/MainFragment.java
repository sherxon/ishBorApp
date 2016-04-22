package uz.ishborApp.Fragments;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.arlib.floatingsearchview.FloatingSearchView;
import com.arlib.floatingsearchview.suggestions.SearchSuggestionsAdapter;
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;
import com.arlib.floatingsearchview.util.view.BodyTextView;
import com.arlib.floatingsearchview.util.view.IconImageView;
import com.path.android.jobqueue.JobManager;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;
import uz.ishborApp.Activity.BaseDrawerActivity;
import uz.ishborApp.Adaptars.VacancyAdapter;
import uz.ishborApp.Components.SearchController;
import uz.ishborApp.Components.TagSuggestionItem;
import uz.ishborApp.Entity.Search;
import uz.ishborApp.Events.FavouriteJobEvent;
import uz.ishborApp.Events.SearchSuggestionItemResultEvent;
import uz.ishborApp.Events.TagSuggestionClickedEvent;
import uz.ishborApp.Events.VacancyListEvent;
import uz.ishborApp.Jobs.LatestVacancyListJob;
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

    @Bind(R.id.recycleSearchResult)
    RecyclerView recyclerView;

    @Bind(R.id.txtSearchListInfo)
    TextView txtSearchListInfo;

    @Inject JobManager jobManager;

    @Inject SearchController searchController;

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
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyApplication.get(getActivity()).getAppComponent().inject(this);
    }
    public void onEventMainThread(SearchSuggestionItemResultEvent event){
        mSearchView.swapSuggestions(event.getList());
        mSearchView.hideProgress();
    }

    static private BaseDrawerActivity parentActivity;

    public MainFragment() {}

    public static Fragment newInstance() {return new MainFragment();}



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView= inflater.inflate(R.layout.fragment_main, container, false);
        parentActivity= (BaseDrawerActivity) getActivity();
        ButterKnife.bind(this, rootView);

        parentActivity.showProgress();

        recyclerView.setHasFixedSize(true);
        LinearLayoutManager llm= new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);

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
                    jobManager.addJob(new SearchTagJob(newQuery));
                    jobManager.start();
                }
            }
        });
        mSearchView.setOnFocusChangeListener(new FloatingSearchView.OnFocusChangeListener() {
            @Override
            public void onFocus() {
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
                txtSearchListInfo.setText("Search Results");
            }

            @Override
            public void onSearchAction() {
                System.out.println("onSearchAction()");
            }
        });

        mSearchView.setOnBindSuggestionCallback(new SearchSuggestionsAdapter.OnBindSuggestionCallback() {
            @Override
            public void onBindSuggestion(IconImageView leftIcon, BodyTextView bodyText, SearchSuggestion item, int itemPosition) {
                TagSuggestionItem tagSuggestionItem = (TagSuggestionItem) item;
                if (tagSuggestionItem.isHistory()) {
                    leftIcon.setImageDrawable(leftIcon.getResources().getDrawable(R.drawable.ic_history_black_24dp));
                    leftIcon.setAlpha(.36f);
                } else {
                    leftIcon.setImageDrawable(new ColorDrawable(Color.parseColor("#CD9575")));
                }
            }
        });
        mSearchView.setOnLeftMenuClickListener(new FloatingSearchView.OnLeftMenuClickListener() {
            @Override
            public void onMenuOpened() {
                parentActivity.drawer.openDrawer(GravityCompat.START);
            }

            @Override
            public void onMenuClosed() {
                parentActivity.drawer.openDrawer(GravityCompat.START);
            }
        });
        parentActivity.drawer.setDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {

            }

            @Override
            public void onDrawerOpened(View drawerView) {
                mSearchView.closeMenu(false);
            }

            @Override
            public void onDrawerClosed(View drawerView) {

            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });

        return rootView;
    }




    public void onEventMainThread(VacancyListEvent vacancyListEvent){
        if(!vacancyListEvent.getTargetClass().equals(MainFragment.class))return;
        VacancyAdapter vacancyAdapter= new VacancyAdapter(vacancyListEvent.getVacancyList(), FavouriteJobEvent.ACTION.NOTHING);
        parentActivity.hideProgress();
        recyclerView.setAdapter(vacancyAdapter);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        jobManager.addJob(new LatestVacancyListJob());
        jobManager.start();
    }




}
