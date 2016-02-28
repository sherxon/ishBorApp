//package uz.ishborApp.Components;
//
//import android.annotation.TargetApi;
//import android.content.Context;
//import android.content.res.Resources;
//import android.os.Build;
//import android.support.v7.widget.SearchView;
//import android.text.InputType;
//import android.text.TextUtils;
//import android.util.Log;
//import android.view.Menu;
//import android.view.MenuItem;
//import android.view.View;
//import android.widget.AutoCompleteTextView;
//import android.widget.ImageView;
//import android.widget.TextView;
//import com.path.android.jobqueue.JobManager;
//import java.lang.reflect.Field;
//import java.util.ArrayList;
//import java.util.List;
//import javax.inject.Inject;
//import de.greenrobot.event.EventBus;
//import uz.ishborApp.Adaptars.TagSuggestionsAdapter;
//import uz.ishborApp.Entity.Search;
//import uz.ishborApp.Events.SearchSuggestionItemResultEvent;
//import uz.ishborApp.Jobs.SearchTagJob;
//import uz.ishborApp.R;
//
//public class SearchViewController {
//
//    private static final int MENU_SEARCH_SIMPLE = 1010;
//    private Context mContext;
//    private AutoCompleteTextView mSearchTextView;
//    private String mSearchTerm;
//    private TagSuggestionsAdapter mSearchAdapter;
//    private boolean mSubmitted = false;
//    private List<Search> foundTracks;
//    private SearchView mSearchView;
//    private List<TagSuggestionItem> mTagList=new ArrayList<>();
//
//    @Inject
//    JobManager jobManager;
//
//    @Inject
//    public SearchViewController() {
//        EventBus.getDefault().register(this);
//    }
//
//    public MenuItem addSearchMenuItem(Menu menu, final Context context) {
//        this.mContext = context;
//        MenuItem menuSearch = menu.add(0, MENU_SEARCH_SIMPLE, 0, "Search");
//        menuSearch.setIcon(R.drawable.ic_search_black_24dp);
//        menuSearch.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
//        SearchView searchView=createSearchView(context);
//        menuSearch.setActionView(searchView);
//        return menuSearch;
//    }
//
//    public void clearSearch() {
//        //mSearchTerm = null;
//    }
//
//    /**
//     * Create an instance of SearchView
//     *
//     * @param context current ThemedContext
//     * @return styled SearchView
//     */
//    @TargetApi(Build.VERSION_CODES.KITKAT)
//    private SearchView createSearchView(Context context) {
//
//        SearchView searchView = new SearchView(context);
//        Resources resources = context.getResources();
//
//        ImageView imageViewSearchMagIcon =
//                (ImageView) searchView.findViewById(android.support.v7.appcompat.R.id.search_mag_icon);
//        imageViewSearchMagIcon.setImageResource(R.drawable.ic_search_black_24dp);
//
//        View viewSearchPlate = searchView.findViewById(android.support.v7.appcompat.R.id.search_plate);
//        viewSearchPlate.setBackgroundResource(R.drawable.textfield_searchview_holo_light);
//
//        ImageView imageViewSearchCloseBtn =
//                (ImageView) searchView.findViewById(android.support.v7.appcompat.R.id.search_close_btn);
//        imageViewSearchCloseBtn.setImageResource(R.drawable.ic_clear_holo_light);
//
//        mSearchTextView =
//                (AutoCompleteTextView) searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
//        int colorWhite = resources.getColor(android.R.color.white);
//        mSearchTextView.setTextColor(colorWhite);
//        mSearchTextView.setRawInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
//        mSearchTextView.setThreshold(3);
//        mSearchAdapter = new TagSuggestionsAdapter(mContext, mTagList);
//        mSearchTextView.setAdapter(mSearchAdapter);
//
//        try {
//            Field mCursorDrawableResField = TextView.class.getDeclaredField("mCursorDrawableRes");
//            mCursorDrawableResField.setAccessible(true);
//            mCursorDrawableResField.setInt(mSearchTextView, R.drawable.textfield_search_default_holo_light);
//        } catch (NoSuchFieldException | IllegalAccessException ignored) {
//            ignored.printStackTrace();
//        }
//
//        searchView.setIconifiedByDefault(false);
//        searchView.setQueryHint("Hint");
//
//        setupListeners(searchView);
//        mSearchView = searchView;
//        return searchView;
//    }
//
//    private void setupListeners(final SearchView searchView) {
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                Log.d("myLogs", "onQueryTextSubmit()");
//                mSubmitted = true;
//                searchView.clearFocus();
//                return true;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                mSubmitted = false;
//                search(newText);
//                return true;
//            }
//
//            private void search(String term) {
//                if (!TextUtils.isEmpty(term)) {
//                    String query = term.trim();
//                    if (query.length() >= 3 && !query.equals(mSearchTerm)) {
//                        mSearchTerm = query;
//                        mSearchAdapter.setLoading(true);
//                        // TODO: 2/27/16 ook for term here
//                        jobManager.addJob(new SearchTagJob(query));
//                        jobManager.start();
////                        mContentController.searchMediasAsYouType(mSearchTerm);
//                    }
//                }
//            }
//        });
//
//        searchView.setOnSuggestionListener(new SearchView.OnSuggestionListener() {
//            @Override
//            public boolean onSuggestionSelect(int position) {
//                launchSuggestion(position);
//                mSubmitted = false;
//                return true;
//            }
//
//            @Override
//            public boolean onSuggestionClick(int position) {
//                launchSuggestion(position);
//                return true;
//            }
//
//            private void launchSuggestion(int position) {
//                Search media = (Search) mSearchAdapter.getItem(position);
//                mSubmitted = false;
//                // TODO: 2/27/16
//                //mEventBus.post(new MediaEvents.MediaLaunchSearchSuggestion(media));
//            }
//        });
//    }
//
//
//    public void onEvent(SearchSuggestionItemResultEvent event) {
//            mTagList.clear();
//            mTagList.addAll(event.getList());
//            mSearchAdapter.notifyDataSetChanged();
//            mSearchAdapter.setLoading(false);
//    }
//
////    public void onSearchMediaErrorEvent(SearchEvents.SearchMediaErrorEvent event) {
////        mSearchAdapter.setItems(new ArrayList(), mSearchTerm);
////        mSearchAdapter.setLoading(false);
////    }
//
////    // TODO: 2/27/16 Open vacancy activity here
////    public void onSearchedItemClickedEventListener(SearchEvents.SearchedItemClicked event){
////        mPlaybackController.play(event.getTrack(), foundTracks);
////        mSearchView.setQuery("", false);
////    }
//}