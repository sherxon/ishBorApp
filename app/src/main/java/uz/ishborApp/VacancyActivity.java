package uz.ishborApp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;
import uz.ishborApp.Components.Globals;
import uz.ishborApp.DAO.CategoryDao;
import uz.ishborApp.DAO.VacancyDao;
import uz.ishborApp.Entity.Category;
import uz.ishborApp.Entity.Vacancy;

public class VacancyActivity extends BaseDrawerActivity {

    @Bind(R.id.cardListVacancy)
    RecyclerView recList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vacancy);

        ButterKnife.bind(this);

        super.onCreateDrawer();

        recList.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager =  new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recList.setLayoutManager(linearLayoutManager);

        int categoryId= getIntent().getExtras().getInt("id");
        new VacancyDao().execute(Globals.LOCAL_VACANCY_URL + "?categoryId=" + categoryId);
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

    public void onEventMainThread(List<Vacancy> vacancyList){
        if(vacancyList==null  || !(vacancyList.get(0) instanceof Vacancy))return;
        VacancyAdapter vacancyAdapter=new VacancyAdapter(vacancyList);
        recList.setAdapter(vacancyAdapter);
    }

}
