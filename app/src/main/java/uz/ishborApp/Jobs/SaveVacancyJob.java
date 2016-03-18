package uz.ishborApp.Jobs;

import com.path.android.jobqueue.Params;

import uz.ishborApp.AppComponent;

/**
 * Created by sherxon on 3/18/16.
 */
public class SaveVacancyJob extends BaseJob {
    Long id;
    public SaveVacancyJob(Long vacancyId) {
        super(new Params(800).persist().requireNetwork());
        this.id=vacancyId;
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
    public void onRun() throws Throwable {

    }

    @Override
    protected void onCancel() {

    }
}
