package uz.ishborApp.Components;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.path.android.jobqueue.JobManager;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
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

    public List<VacancySearchSuggestion> getSearchHistory(int count) {
        List<VacancySearchSuggestion> suggestionList= new ArrayList<>();
        List<Search> searchList=daoMaster.newSession().getSearchDao()
                .queryBuilder().orderDesc(SearchDao.Properties.Created)
                .limit(4).build().list();
        for (Search search : searchList) {
            suggestionList.add(new VacancySearchSuggestion(search, true));
        }
        return suggestionList;
    }





}
