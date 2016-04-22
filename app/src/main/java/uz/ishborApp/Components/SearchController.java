package uz.ishborApp.Components;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import okhttp3.OkHttpClient;
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
                .limit(count).build().list();
        for (Search search : searchList) {
            suggestionList.add(new TagSuggestionItem(search, true));
        }
        return suggestionList;
    }

//
/*
As I am web developer with more than two years of experience, I would like to enhance my knowledge in web development specifically back end development. Mobile applications especially Android is also in my interest area.

    I’ve been improving my programming skills for a few years now and I’m looking for a position where I will be able to enhance those skills. I am looking for a position that can help me to grow my personal as well as professional development.
            Moreover, I would love to be a team member that shares the same vision and make impact that I can see with my own eyes. This truly motivates me.

    Working as a key member in a team of professional people and seeing the end result is really motivating for me. Besides, I am truly motivated by finding out-of-box and efficient solutions to challenging tasks in the project.
    I know that my great strength is that I really work hard and try to make my work perfect. Sometimes, I got appraisal  from senior developers when they do code reviews.
    I will get advice from my friends who had been to USA before about dos and donts
*/

}
