package uz.ishborApp.Events;

import java.util.List;

import uz.ishborApp.Entity.Vacancy;

/**
 * Created by sherxon on 2/14/16.
 */
public class VacancyListEvent {

    private List<Vacancy> vacancyList;
    private Class targetClass;

    public VacancyListEvent(List<Vacancy> vacancyList, Class targetClass) {
        this.vacancyList=vacancyList;
        this.targetClass = targetClass;
    }

    public Class getTargetClass() {
        return targetClass;
    }

    public List<Vacancy> getVacancyList() {
        return vacancyList;
    }
}
