package uz.ishborApp.Events;

import uz.ishborApp.Entity.Search;

/**
 * Created by sherxon on 1/22/16.
 */
public class SearchSuggestionItemSelected {
   private Search search;

    public SearchSuggestionItemSelected(Search search) {
        this.search = search;
    }

    public Search getSearch() {
        return search;
    }
}