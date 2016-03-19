package uz.ishborApp.Events;

import uz.ishborApp.Entity.Vacancy;

/**
 * Created by sherxon on 3/19/16.
 */
public class FavouriteJobEvent {
   private ACTION action;
   private Long id;
   private Vacancy vacancy;

    public Vacancy getVacancy() {
        return vacancy;
    }

    public void setVacancy(Vacancy vacancy) {
        this.vacancy = vacancy;
    }

    public enum ACTION{
       DELETE, NOTHING, SAVE
   }

    public FavouriteJobEvent(ACTION action, Vacancy vacancy) {
        this.action = action;
        this.vacancy = vacancy;
    }

    public FavouriteJobEvent(ACTION action, Long id) {
        this.action = action;
        this.id = id;
    }

    public ACTION getAction() {
        return action;
    }

    public void setAction(ACTION action) {
        this.action = action;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
