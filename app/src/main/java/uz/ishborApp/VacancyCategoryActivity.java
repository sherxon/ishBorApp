package uz.ishborApp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.squareup.okhttp.OkHttpClient;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;
import uz.ishborApp.DAO.DbBalance;
import uz.ishborApp.Entity.Category;
import uz.ishborApp.Entity.CategoryDao;

public class VacancyCategoryActivity extends BaseDrawerActivity {

    @Bind(R.id.cardList)
    RecyclerView recList;

    @Inject
    OkHttpClient okHttpClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vacancy_category);
        ButterKnife.bind(this);
        super.onCreateDrawer();

        recList.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager =  new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recList.setLayoutManager(linearLayoutManager);
        getListOfCategoryAndRenderView();

    }

    private void getListOfCategoryAndRenderView() {
        CategoryDao.createTable(daoMaster.getDatabase(), true);
        CategoryDao categoryDao=daoMaster.newSession().getCategoryDao();

        if(categoryDao.count()==0)
            dbBalance.loadCategoryToLocalDb();
        else
            onEventMainThread(categoryDao.loadAll());

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

    public void onEventMainThread(List<Category> categoryList){ //on download
        if(categoryList==null  || !(categoryList.get(0) instanceof Category))return;
        CategoryAdapter categoryAdapter=new CategoryAdapter(categoryList);
        recList.setAdapter(categoryAdapter);
    }
    public void onEventMainThread(Category category){ //onclick
        Intent intent=new Intent(this, VacancyActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        intent.putExtra("id", category.getId());

        startActivity(intent);
    }


}
