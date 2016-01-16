package uz.ishborApp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;
import uz.ishborApp.DAO.DbBalance;
import uz.ishborApp.Entity.Category;

public class VacancyCategoryActivity extends BaseDrawerActivity {

    @Bind(R.id.cardList)
    RecyclerView recList;


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
        new DbBalance(this).checkCategoryUpdate();

    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    public void onEventMainThread(ArrayList categoryList){ //on download
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
