package uz.ishborApp.Entity;

// THIS CODE IS GENERATED BY greenDAO, EDIT ONLY INSIDE THE "KEEP"-SECTIONS

// KEEP INCLUDES - put your custom includes here
// KEEP INCLUDES END
/**
 * Entity mapped to table "APPLIED".
 */
public class Applied {

    private Long id;
    private String userId;
    private Long vacancyId;
    private String vacancyName;
    private String categoryName;
    private String dateName;

    // KEEP FIELDS - put your custom fields here
    // KEEP FIELDS END

    public Applied() {
    }

    public Applied(Long id) {
        this.id = id;
    }

    public Applied(Long id, String userId, Long vacancyId, String vacancyName, String categoryName, String dateName) {
        this.id = id;
        this.userId = userId;
        this.vacancyId = vacancyId;
        this.vacancyName = vacancyName;
        this.categoryName = categoryName;
        this.dateName = dateName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Long getVacancyId() {
        return vacancyId;
    }

    public void setVacancyId(Long vacancyId) {
        this.vacancyId = vacancyId;
    }

    public String getVacancyName() {
        return vacancyName;
    }

    public void setVacancyName(String vacancyName) {
        this.vacancyName = vacancyName;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getDateName() {
        return dateName;
    }

    public void setDateName(String dateName) {
        this.dateName = dateName;
    }

    // KEEP METHODS - put your custom methods here
    // KEEP METHODS END

}
