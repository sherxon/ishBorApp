package uz.ishborApp.Events;

import java.util.List;

import uz.ishborApp.Entity.Category;

/**
 * Created by sherxon on 2/14/16.
 */
public class CategoryListEvent {
    private List<Category> categoryList;

    public CategoryListEvent(List<Category> categoryList) {
        this.categoryList = categoryList;
    }

    public List<Category> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(List<Category> categoryList) {
        this.categoryList = categoryList;
    }
}
