package uz.ishborApp;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import de.greenrobot.event.EventBus;
import uz.ishborApp.Entity.Category;

/**
 * Created by sherxon on 1/9/16.
 */
public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>{

    private List<Category> categoryList;

    public CategoryAdapter(List<Category> categoryList) {
        this.categoryList = categoryList;
    }

    @Override
    public CategoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.category_list_item, parent, false);
        return new CategoryViewHolder(itemView, categoryList);
    }

    @Override
    public void onBindViewHolder(CategoryViewHolder holder, int position) {
        Category category=categoryList.get(position);
        holder.title.setText(category.getTitle());
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public static class CategoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        protected TextView title;
        List<Category> categoryList;
        public CategoryViewHolder(View itemView, List<Category> categoryList) {
            super(itemView);
            this.categoryList=categoryList;
            title= (TextView) itemView.findViewById(R.id.title);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int pos=getLayoutPosition();
            EventBus.getDefault().post(categoryList.get(pos));
        }
    }
}
