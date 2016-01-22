package uz.ishborApp.Events;

import java.util.List;

import uz.ishborApp.Components.VacancySearchSuggestion;

/**
 * Created by sherxon on 1/21/16.
 */
public class SearchSuggestionItemResultEvent {
   private List<VacancySearchSuggestion> list;

    public SearchSuggestionItemResultEvent(List<VacancySearchSuggestion> list) {
        this.list = list;
    }

    public List<VacancySearchSuggestion> getList() {
        return list;
    }
}
