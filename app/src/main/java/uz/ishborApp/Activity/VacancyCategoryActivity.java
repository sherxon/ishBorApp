package uz.ishborApp.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.squareup.okhttp.OkHttpClient;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;
import uz.ishborApp.Entity.Category;
import uz.ishborApp.Entity.CategoryDao;
import uz.ishborApp.Events.CategoryListEvent;
import uz.ishborApp.Jobs.CategoryListJob;
import uz.ishborApp.Jobs.VacancyListJob;
import uz.ishborApp.MyApplication;
import uz.ishborApp.R;

public class VacancyCategoryActivity extends BaseDrawerActivity {

    @Bind(R.id.cardList)
    RecyclerView recList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vacancy_category);
        ButterKnife.bind(this);

        super.onCreateDrawer();

        ActionBarDrawerToggle toggle= new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        recList.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager =  new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recList.setLayoutManager(linearLayoutManager);
        getListOfCategoryAndRenderView();

    }

    private void getListOfCategoryAndRenderView() {
        jobManager.addJob(new CategoryListJob());
        jobManager.start();
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    public void onEventMainThread(CategoryListEvent categoryListEvent){ //on download
        CategoryAdapter categoryAdapter=new CategoryAdapter(categoryListEvent.getCategoryList());
        recList.setAdapter(categoryAdapter);
    }
    public void onEventMainThread(Category category){ //onclick
        Intent intent=new Intent(this, VacancyActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        intent.putExtra("id", category.getId());
        intent.putExtra("title", category.getTitle());

        startActivity(intent);
    }


}
