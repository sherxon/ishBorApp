package uz.ishborApp.Components;

import android.os.Parcel;

import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;

import uz.ishborApp.Entity.Search;

/**
 * Created by sherxon on 1/21/16.
 */
public class VacancySearchSuggestion implements SearchSuggestion {

    private Search search;

    private boolean isHistory;

    public VacancySearchSuggestion(Search search, boolean isHistory) {
        this.search = search;
        this.isHistory = isHistory;
    }

    public VacancySearchSuggestion(Parcel parcel) {

    }


    public Search getSearch() {
        return search;
    }

    public void setSearch(Search search) {
        this.search = search;
    }

    public boolean isHistory() {
        return isHistory;
    }

    public void setIsHistory(boolean isHistory) {
        this.isHistory = isHistory;
    }

    @Override
    public String getBody() {
        return search.getWord();
    }


    @Override
    public Creator getCreator() {
        return CREATOR;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(search.getWord());
    }

    public static final Creator<VacancySearchSuggestion> CREATOR= new Creator<VacancySearchSuggestion>() {
        @Override
        public VacancySearchSuggestion createFromParcel(Parcel parcel) {
            return new VacancySearchSuggestion(parcel);
        }

        @Override
        public VacancySearchSuggestion[] newArray(int i) {
            return new VacancySearchSuggestion[i];
        }
    };
}
