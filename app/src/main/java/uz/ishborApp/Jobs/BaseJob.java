package uz.ishborApp.Jobs;

import com.google.gson.Gson;
import com.path.android.jobqueue.Job;
import com.path.android.jobqueue.Params;
import com.squareup.okhttp.OkHttpClient;

import javax.inject.Inject;

import uz.ishborApp.AppComponent;
import uz.ishborApp.DAO.DbBalance;
import uz.ishborApp.Entity.DaoMaster;

/**
 * Created by sherxon on 2/13/16.
 */
public abstract class BaseJob extends Job {

    @Inject
    transient DaoMaster daoMaster;

    @Inject
    transient DbBalance dbBalance;

    @Inject
    transient OkHttpClient okHttpClient;

    @Inject
    transient Gson gson;



    public BaseJob(Params params) {
        super(params);
    }

    public void inject(AppComponent appComponent) {

    }
}
