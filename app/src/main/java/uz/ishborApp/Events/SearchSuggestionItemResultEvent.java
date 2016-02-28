package uz.ishborApp.Events;

import java.util.List;

import uz.ishborApp.Components.TagSuggestionItem;

/**
 * Created by sherxon on 1/21/16.
 */
public class SearchSuggestionItemResultEvent {
   private List<TagSuggestionItem> list;

    public SearchSuggestionItemResultEvent(List<TagSuggestionItem> list) {
        this.list = list;
    }

    public List<TagSuggestionItem> getList() {
        return list;
    }
}
