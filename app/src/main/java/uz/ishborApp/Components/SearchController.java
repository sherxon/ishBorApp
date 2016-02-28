package uz.ishborApp.Components;

import com.google.gson.Gson;
import com.squareup.okhttp.OkHttpClient;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import uz.ishborApp.Entity.DaoMaster;
import uz.ishborApp.Entity.Search;
import uz.ishborApp.Entity.SearchDao;

/**
 * Created by sherxon on 1/21/16.
 */
public class SearchController {

    @Inject
    DaoMaster daoMaster;

    @Inject
    OkHttpClient okHttpClient;

    @Inject
    Gson gson;

    @Inject @Singleton
    public SearchController() {
    }

    public List<TagSuggestionItem> getSearchHistory(int count) {
        List<TagSuggestionItem> suggestionList= new ArrayList<>();
        List<Search> searchList=daoMaster.newSession().getSearchDao()
                .queryBuilder().orderDesc(SearchDao.Properties.Created)
                .limit(4).build().list();
        for (Search search : searchList) {
            suggestionList.add(new TagSuggestionItem(search, true));
        }
        return suggestionList;
    }





}
