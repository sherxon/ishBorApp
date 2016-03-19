package uz.ishborApp.Adaptars;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

import de.greenrobot.event.EventBus;
import uz.ishborApp.Entity.Vacancy;
import uz.ishborApp.Events.FavouriteJobEvent;
import uz.ishborApp.R;

/**
 * Created by sherxon on 1/9/16.
 */
public class VacancyAdapter extends RecyclerView.Adapter<VacancyAdapter.VacancyViewHolder>{

    private List<Vacancy> vacancyList;
    private FavouriteJobEvent.ACTION star;
    public VacancyAdapter(List<Vacancy> vacancyList, FavouriteJobEvent.ACTION star) {
        this.vacancyList = vacancyList;
        this.star=star;
    }

    @Override
    public VacancyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.vacancy_list_item, parent, false);
        return new VacancyViewHolder(itemView, vacancyList, star);
    }

    @Override
    public void onBindViewHolder(VacancyViewHolder holder, int position) {
        Vacancy vacancy=vacancyList.get(position);
        holder.position.setText(vacancy.getPosition());
        holder.companyName.setText(String.valueOf(vacancy.getCompanyName()));
        holder.date.setText(String.valueOf(vacancy.getStDate()));
    }

    @Override
    public int getItemCount() {
        return vacancyList.size();
    }

    public  class VacancyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        protected TextView position;
        protected TextView companyName;
        protected TextView date;
        protected ImageButton btnDelete;
        protected ImageButton btnSave;
        private List<Vacancy> vacancyList;

        public VacancyViewHolder(View itemView, final List<Vacancy> vacancyList, FavouriteJobEvent.ACTION star) {
            super(itemView);
            position= (TextView) itemView.findViewById(R.id.st_position);
            companyName = (TextView) itemView.findViewById(R.id.st_company);
            date= (TextView) itemView.findViewById(R.id.st_Date);
            btnDelete= (ImageButton) itemView.findViewById(R.id.btnDelete);
            btnSave= (ImageButton) itemView.findViewById(R.id.btnSave);
            this.vacancyList=vacancyList;

            switch (star){
                case DELETE:{
                    btnDelete.setVisibility(View.VISIBLE);
                    btnDelete.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Vacancy current = VacancyViewHolder.this.vacancyList.get(getLayoutPosition());
                            EventBus.getDefault().post(new FavouriteJobEvent(FavouriteJobEvent.ACTION.DELETE, current.getId()));
                            removeAt(getLayoutPosition());
                        }
                    });
                    break;
                }
                case SAVE:{
                    btnSave.setVisibility(View.VISIBLE);
                    btnSave.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Vacancy current = VacancyViewHolder.this.vacancyList.get(getLayoutPosition());
                            EventBus.getDefault().post(new FavouriteJobEvent(FavouriteJobEvent.ACTION.SAVE, current));

                        }
                    });
                    break;
                }
            }
            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View view) {
                EventBus.getDefault().post(vacancyList.get(getLayoutPosition()));
        }
    }
    private void removeAt(int layoutPosition) {
        vacancyList.remove(layoutPosition);
        notifyItemRemoved(layoutPosition);
        notifyItemRangeChanged(layoutPosition, vacancyList.size());
    }

}
