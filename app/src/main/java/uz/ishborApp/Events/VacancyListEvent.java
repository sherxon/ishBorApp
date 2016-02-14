package uz.ishborApp.Events;

import java.util.List;

import uz.ishborApp.Entity.Vacancy;

/**
 * Created by sherxon on 2/14/16.
 */
public class VacancyListEvent {

    private List<Vacancy> vacancyList;

    public VacancyListEvent(List<Vacancy> vacancyList) {
        this.vacancyList=vacancyList;
    }

    public List<Vacancy> getVacancyList() {
        return vacancyList;
    }
}
