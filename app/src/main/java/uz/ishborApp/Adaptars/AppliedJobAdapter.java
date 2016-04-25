package uz.ishborApp.Adaptars;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import uz.ishborApp.Entity.Applied;
import uz.ishborApp.R;

/**
 * Created by sherxon on 4/25/16.
 */
public class AppliedJobAdapter extends ArrayAdapter<Applied> {
    private static class ViewHolder {
        public TextView tvName;
        public TextView tvCategory;
        public TextView tvDate;
    }
    public AppliedJobAdapter(Context context, ArrayList<Applied> vendors) {
        super(context, 0, vendors);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        final Applied applied= getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_applied, parent, false);
            viewHolder.tvName = (TextView)convertView.findViewById(R.id.tvName);
            viewHolder.tvCategory = (TextView)convertView.findViewById(R.id.tvCategory);
            viewHolder.tvDate = (TextView)convertView.findViewById(R.id.tvDate);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        // Populate the data into the template view using the data object
        viewHolder.tvName.setText(applied.getVacancyName());
        viewHolder.tvCategory.setText(applied.getCategoryName());
        viewHolder.tvDate.setText(applied.getDateName());

        // Return the completed view to render on screen
        return convertView;
    }
}
