package uz.ishborApp.Jobs;

import com.path.android.jobqueue.Params;

import de.greenrobot.event.EventBus;
import okhttp3.Request;
import okhttp3.Response;
import uz.ishborApp.AppComponent;
import uz.ishborApp.Components.Globals;
import uz.ishborApp.Entity.Vacancy;

/**
 * Created by sherxon on 3/18/16.
 */
public class SaveVacancyJob extends BaseJob {
    Long id;
    String title;
    String companyName;
    String stDate;
    public SaveVacancyJob(Long vacancyId, String title, String companyName, String stDate) {
        super(new Params(800).persist().requireNetwork());
        this.id=vacancyId;
        this.title=title;
        this.companyName=companyName;
        this.stDate=stDate;
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
        Request request=new Request.Builder().url(Globals.LOCAL_JOBDESC+id).build();
        Response response= okHttpClient.newCall(request).execute();
        String result=response.body().string();
        Vacancy vacancy=new Vacancy();
        vacancy.setId(id);
        vacancy.setCompanyName(companyName);
        vacancy.setPosition(title);
        vacancy.setStDate(stDate);
        vacancy.setDescc(result);
        daoMaster.newSession().getVacancyDao().insertOrReplaceInTx(vacancy);
    }

    @Override
    protected void onCancel() {
        EventBus.getDefault().post(false);
    }
}
