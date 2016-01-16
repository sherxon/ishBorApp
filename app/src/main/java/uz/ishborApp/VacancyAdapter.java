package uz.ishborApp;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import uz.ishborApp.Entity.Vacancy;

/**
 * Created by sherxon on 1/9/16.
 */
public class VacancyAdapter extends RecyclerView.Adapter<VacancyAdapter.VacancyViewHolder>{

    private List<Vacancy> vacancyList;

    public VacancyAdapter(List<Vacancy> vacancyList) {
        this.vacancyList = vacancyList;
    }

    @Override
    public VacancyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.vacancy_list_item, parent, false);
        return new VacancyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(VacancyViewHolder holder, int position) {
        Vacancy vacancy=vacancyList.get(position);
        holder.position.setText(vacancy.getPosition());
        holder.comapnyName.setText(String.valueOf(vacancy.getCompanyName()));
        holder.date.setText(String.valueOf(vacancy.getStDate()));
    }

    @Override
    public int getItemCount() {
        return vacancyList.size();
    }

    public static class VacancyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        protected TextView position;
        protected TextView comapnyName;
        protected TextView date;

        public VacancyViewHolder(View itemView) {
            super(itemView);
            position= (TextView) itemView.findViewById(R.id.st_position);
            comapnyName= (TextView) itemView.findViewById(R.id.st_company);
            date= (TextView) itemView.findViewById(R.id.st_Date);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
               int pos=getLayoutPosition();
        }
    }
}
