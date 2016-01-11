package uz.ishborApp.Entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by sherxon on 1/9/16.
 */
public class Category {

    @SerializedName(value = "id")
    private Integer id;

    @SerializedName(value = "cat_name")
    private String title;

    @SerializedName(value = "cat_link")
    private String link;

    private Integer jobQuantity;



    public Category() {
    }
    public Category(String title, Integer jobQuantity) {
        this.title = title;
        this.jobQuantity = jobQuantity;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getJobQuantity() {
        return jobQuantity;
    }

    public void setJobQuantity(Integer jobQuantity) {
        this.jobQuantity = jobQuantity;
    }
}
