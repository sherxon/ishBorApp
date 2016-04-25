package uz.ishborApp.Events;

/**
 * Created by sherxon on 4/25/16.
 */
public class UploadEvent {
    private String messasge;

    public UploadEvent(String messasge) {
        this.messasge = messasge;
    }

    public String getMessasge() {
        return messasge;
    }

    public void setMessasge(String messasge) {
        this.messasge = messasge;
    }
}
