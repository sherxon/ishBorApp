//package uz.ishborApp.Components;
//
///**
// * Created by sherxon on 2/27/16.
// */
//
//import android.content.Context;
//import android.content.res.Resources;
//import android.text.InputType;
//import android.text.TextUtils;
//import android.view.Menu;
//import android.view.MenuItem;
//import android.view.View;
//import android.widget.AutoCompleteTextView;
//import android.widget.ImageView;
//import android.widget.SearchView;
//import android.widget.TextView;
//
//import java.lang.reflect.Field;
//import java.util.ArrayList;
//import java.util.List;
//
//import javax.inject.Inject;
//import javax.inject.Singleton;
//
//import de.greenrobot.event.EventBus;
//import uz.ishborApp.Adaptars.TagSuggestionsAdapter;
//import uz.ishborApp.Entity.Search;
//import uz.ishborApp.Events.SearchSuggestionItemResultEvent;
//import uz.ishborApp.R;
//
//@Singleton
//public class SearchHelper {
//
//
//    private String mSearchTerm;
//    private TagSuggestionsAdapter mSearchAdapter;
//    private List<Search> mTagList = new ArrayList<>();
//    private AutoCompleteTextView mSearchTextView;
//
//    @Inject
//    public SearchHelper() {
//        EventBus.getDefault().register(this);
//    }
//
//    public void addSearchMenuItem(Menu menu) {
//
//        MenuItem menuSearch = menu.add(0, 1, 0, "Nimadir");
//        menuSearch.setIcon(R.drawable.ic_search_black_24dp);
//        menuSearch.setShowAsAction(MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW | MenuItem.SHOW_AS_ACTION_IF_ROOM);
//        SearchView searchView = createSearchView();
//        menuSearch.setActionView(searchView);
//    }
//
//    /**
//     * create an instance of SearchView
//     * @param themedContext current ThemedContext
//     * @return styled SearchView
//     */
//    private  SearchView createSearchView( Context themedContext) {
//
//        SearchView searchView = new SearchView(themedContext);
//
//        Resources resources = themedContext.getResources();
//
//        int searchMagIconId = resources.getIdentifier("android:id/search_mag_icon", null, null);
//        ImageView imageViewSearchMagIcon = (ImageView) searchView.findViewById(searchMagIconId);
//        imageViewSearchMagIcon.setImageResource(R.drawable.ic_search_black_24dp);
//
//        int searchPlateId = resources.getIdentifier("android:id/search_plate", null, null);
//        View viewSearchPlate = searchView.findViewById(searchPlateId);
//        viewSearchPlate.setBackgroundResource(R.drawable.textfield_searchview_holo_light);
//
//        final int searchCloseBtnId = resources.getIdentifier("android:id/search_close_btn", null, null);
//        ImageView imageViewSearchCloseBtn = (ImageView) searchView.findViewById(searchCloseBtnId);
//        imageViewSearchCloseBtn.setImageResource(R.drawable.ic_clear);
//
//        int searchSrcText = resources.getIdentifier("android:id/search_src_text", null, null);
//        mSearchTextView = (AutoCompleteTextView) searchView.findViewById(searchSrcText);
//        int colorGreyText = resources.getColor(R.color.bkg_card);
//        mSearchTextView.setTextColor(colorGreyText);
//        mSearchTextView.setRawInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
//        mSearchTextView.setHint("hint here");
//
//        try {
//            Field mCursorDrawableResField = TextView.class.getDeclaredField("mCursorDrawableRes");
//            mCursorDrawableResField.setAccessible(true);
//            mCursorDrawableResField.setInt(mSearchTextView, R.drawable.text_cursor_holo_light);
//        } catch (NoSuchFieldException ignored) {
//            ignored.printStackTrace();
//        } catch (IllegalAccessException ignored) {
//            ignored.printStackTrace();
//        }
//
//
//        searchView.setIconifiedByDefault(false);
//
//        setupListeners(searchView);
//
//        return searchView;
//    }
//
//    private void setupListeners( SearchView searchView) {
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                return true;
//            }
//
//            @Override
//            public boolean onQueryTextChange(final String newText) {
//                search(newText);
//                return true;
//            }
//
//            private void search(String term) {
//                if (term.length() >= 2) {
//                    mSearchTerm = term;
//                    // TODO: 2/27/16  Look for tag here
//                }
//            }
//        });
//
//        searchView.setOnSuggestionListener(new SearchView.OnSuggestionListener() {
//            @Override
//            public boolean onSuggestionSelect(int position) {
//                launchSuggestion(position);
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
//                ContentItem itemSelected = mSearchAdapter.getItem(position);
//
//                if (position == 0 && TextUtils.equals(itemSelected.getDisplay().getTitle(),
//                        NO_RESULT_STRING) || itemSelected == null) {
//
//                    return;
//                }
//
//            }
//        });
//    }
//
//
//
//    public void onMediaSearchCollectionReadyEvent(SearchSuggestionItemResultEvent event) {
//        if(mSearchTerm == null || event.getSearchTerm() == null) return;
//        if (mContext != null && mSearchTerm != null && mSearchTerm.equals(event.getSearchTerm()) && !mSubmitted) {
//            SearchResult searchResult = event.getSearchResult();
//            List medias = new ArrayList();
//
//            medias.addAll(searchResult.getArtists());
//            medias.addAll(searchResult.getPlayLists());
//            medias.addAll(searchResult.getTracks());
//
//            foundTracks = searchResult.getTracks();
//            if (medias.isEmpty()) {
//                return;
//            }
//
//            mSearchAdapter.setItems(medias, mSearchTerm);
//            mSearchAdapter.setLoading(false);
//        }
//    }
//
//
//    public void onContentSearchFailureEvent(ContentEvents.ContentSearchFailureEvent event) {
//        if (mSearchAdapter != null) {
//            mSearchAdapter.clear();
//            mSearchAdapter.notifyDataSetChanged();
//        }
//    }
//}