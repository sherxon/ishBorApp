package uz.ishborApp.Jobs;

import com.google.gson.reflect.TypeToken;
import com.path.android.jobqueue.Params;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.lang.reflect.Type;
import java.util.LinkedList;
import java.util.List;

import javax.inject.Inject;

import de.greenrobot.event.EventBus;
import uz.ishborApp.AppComponent;
import uz.ishborApp.Components.Globals;
import uz.ishborApp.Components.SearchController;
import uz.ishborApp.Components.TagSuggestionItem;
import uz.ishborApp.Entity.Search;
import uz.ishborApp.Events.SearchSuggestionItemResultEvent;

/**
 * Created by sherxon on 1/21/16.
 */
public class SearchTagJob extends BaseJob{

    @Inject
    transient SearchController searchController;

    String query;

    public SearchTagJob(String query) {
        super(new Params(1000).requireNetwork());
        this.query=query;
    }

    @Override
    public void inject(AppComponent appComponent) {
        super.inject(appComponent);
        appComponent.inject(this);
    }

    @Override
    public void onAdded() {

    }

    @Override
    protected int getRetryLimit() {
       return  5;
    }

    @Override
    public void onRun() throws Throwable {
        Request request= new Request.Builder().url(Globals.LOCAL_SEARCH_URL+ query).build();
        Response response = okHttpClient.newCall(request).execute();
        String result= response.body().string();
        List<TagSuggestionItem> list=searchSuggestionList(result);
        EventBus.getDefault().post(new SearchSuggestionItemResultEvent(list));
    }

    public List<TagSuggestionItem> searchSuggestionList(String json){
        Type searchType=new TypeToken<List<Search>>(){}.getType();
        List<Search> searchList=gson.fromJson(json, searchType);
        List<TagSuggestionItem> suggestionList=new LinkedList<>();
        for (Search search : searchList) {
            suggestionList.add(new TagSuggestionItem(search, false));
        }
        return suggestionList;
    }

    @Override
    protected void onCancel() {

    }


}
