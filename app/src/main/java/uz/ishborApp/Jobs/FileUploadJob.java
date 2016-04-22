package uz.ishborApp.Jobs;

import com.google.gson.reflect.TypeToken;
import com.path.android.jobqueue.Params;

import java.io.File;
import java.lang.reflect.Type;
import java.util.Map;

import de.greenrobot.event.EventBus;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import uz.ishborApp.AppComponent;
import uz.ishborApp.Components.Globals;


/**
 * Created by sherxon on 4/22/16.
 */
public class FileUploadJob extends BaseJob {

    private File file;
    private String userId;
    public FileUploadJob(File file, String userId) {
        super(new Params(800).requireNetwork().persist());
        this.file=file;
        this.userId=userId;
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

        RequestBody requestBody=new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("file", file.getName(),
                        RequestBody.create(
                                MediaType.parse("application/pdf"), file))
                        .addFormDataPart("userId", userId)
                .build();

        Request request=new Request.Builder()
                .url(Globals.LOCAL_UPLOAD_CV)
                .post(requestBody)
                .build();
        Response response=okHttpClient.newCall(request).execute();

        Type type= new TypeToken<Map<String, String>>(){}.getType();
        Map<String, String> result=gson.fromJson(response.body().string(), type);

        if("ok".equals(result.get("status"))){
               String filename=result.get("filename");
                RequestBody requestBody1= new FormBody.Builder()
                        .add("userId", userId)
                        .add("filename", filename)
                        .build();
                okHttpClient.newCall(new Request.Builder()
                        .post(requestBody1)
                        .url(Globals.LOCAL_UPLOADED_ID).build())
                        .execute();
        }else{
            EventBus.getDefault().post("there is something wrong with server");
        }
    }

    @Override
    protected void onCancel() {

    }
}
