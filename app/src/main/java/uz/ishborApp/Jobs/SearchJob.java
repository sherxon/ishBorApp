package uz.ishborApp.Jobs;

import com.path.android.jobqueue.Job;
import com.path.android.jobqueue.Params;

import java.util.List;

import de.greenrobot.event.EventBus;
import uz.ishborApp.Components.SearchController;
import uz.ishborApp.Components.VacancySearchSuggestion;
import uz.ishborApp.Events.SearchSuggestionItemResultEvent;

/**
 * Created by sherxon on 1/21/16.
 */
public class SearchJob extends Job{
    String query;

    public SearchJob(String query) {
        super(new Params(1000).requireNetwork().persist());
        this.query=query;
    }

    @Override
    public void onAdded() {

    }

    @Override
    public void onRun() throws Throwable {
        SearchController searchController=SearchController.getInstance();
        String result= searchController.searchTags(query);
        List<VacancySearchSuggestion> list=searchController.searchSuggestionList(result);
        EventBus.getDefault().post(new SearchSuggestionItemResultEvent(list));
    }

    @Override
    protected void onCancel() {

    }
}
