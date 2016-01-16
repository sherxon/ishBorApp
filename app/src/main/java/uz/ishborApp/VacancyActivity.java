package uz.ishborApp;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;
import uz.ishborApp.DAO.DbBalance;
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

        long categoryId= getIntent().getExtras().getLong("id");
        new DbBalance(this).checkVacancyUpdate(categoryId);
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
