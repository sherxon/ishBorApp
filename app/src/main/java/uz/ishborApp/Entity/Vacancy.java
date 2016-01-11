package uz.ishborApp.Entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by sherxon on 1/10/16.
 */

public class Vacancy {

    private Integer id;

    @SerializedName(value = "st_date")
    private String date;

    @SerializedName(value = "st_price")
    private String price;

    @SerializedName(value = "company_name")
    private String companyName;

    @SerializedName(value = "vac_position")
    private String position;

    @SerializedName(value = "descc")
    private String desc;

    @SerializedName(value = "category_id")
    private Integer categoryId;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }
}
