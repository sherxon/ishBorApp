//package uz.ishborApp.Adaptars;
//
///**
// * Created by sherxon on 2/27/16.
// */
//
//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.BaseAdapter;
//import android.widget.Filter;
//import android.widget.Filterable;
//import android.widget.TextView;
//
//import java.util.List;
//
//import uz.ishborApp.Components.TagSuggestionItem;
//import uz.ishborApp.R;
//
//
//public class TagSuggestionsAdapter extends BaseAdapter implements Filterable {
//
//    private static final int SEARCH_ITEM = 0;
//    private static final int NO_RESULT = 1;
//    private static final int LOADING = 2;
//
//    private Context context;
//    private final List<TagSuggestionItem> mTags;
//    private MediaFilter mMediaFilter;
//
//    private boolean isLoading;
//    private String mNoResultString = "No result";
//
//    private LayoutInflater mLayoutInflater;
//
//    public TagSuggestionsAdapter(Context context, List<TagSuggestionItem> mTags) {
//        this.context = context;
//        this.mTags = mTags;
//        this.mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//    }
//
//    public void setLoading(boolean isLoading) {
//        this.isLoading = isLoading;
//        notifyDataSetChanged();
//    }
//
//    @Override
//    public boolean areAllItemsEnabled() {
//        return false;
//    }
//
//    @Override
//    public boolean isEnabled(int position) {
//        if (isLoading) {
//            return false;
//        }
//        return !mTags.isEmpty();
//    }
//
//    @Override
//    public int getCount() {
//        if (isLoading) {
//            return 1;
//        }
//        return !mTags.isEmpty() ? mTags.size() : 1;
//    }
//
//    @Override
//    public Object getItem(int position) {
//        if (isLoading) {
//            return null;
//        }
//        return !mTags.isEmpty() ? mTags.get(position) : null;
//    }
//
//    @Override
//    public long getItemId(int position) {
//        if (isLoading) {
//            return -2;
//        }
//        return !mTags.isEmpty() ? position : -1;
//    }
//
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//
//        switch (getItemViewType(position)) {
//            case LOADING:
//                if (convertView == null) {
//                    convertView =
//                            LayoutInflater.from(context).inflate(R.layout.row_search_item_progress, parent, false);
//                }
//                break;
//            case NO_RESULT:
//                if (convertView == null) {
//                    convertView = LayoutInflater.from(context).inflate(R.layout.row_search_item_empty, parent, false);
//                    ((TextView) convertView.findViewById(R.id.textViewTitle)).setText(mNoResultString);
//                }
//                break;
//            case SEARCH_ITEM:
//                ViewHolder viewHolder;
//                if (convertView == null) {
//                    convertView = mLayoutInflater.inflate(R.layout.row_search_item, parent, false);
//                    viewHolder = new ViewHolder(convertView);
//                    convertView.setTag(viewHolder);
//                } else {
//                    viewHolder = (ViewHolder) convertView.getTag();
//                }
//
//                TagSuggestionItem tagSuggestionItem= (TagSuggestionItem) getItem(position);
//                viewHolder.textViewPrimary.setText(tagSuggestionItem.getBody());
//                break;
//        }
//
//        return convertView;
//    }
//
//    @Override
//    public int getViewTypeCount() {
//        return 3;
//    }
//
//    @Override
//    public int getItemViewType(int position) {
//        if (isLoading) {
//            return LOADING;
//        }
//        return !mTags.isEmpty() ? SEARCH_ITEM : NO_RESULT;
//    }
//
//    @Override
//    public Filter getFilter() {
//        if (mMediaFilter == null) {
//            mMediaFilter = new MediaFilter();
//        }
//
//        return mMediaFilter;
//    }
//
//    private class MediaFilter extends Filter {
//
//        @Override
//        protected FilterResults performFiltering(CharSequence constraint) {
//            FilterResults results = new FilterResults();
//            results.count = getCount();
//            return results;
//        }
//
//        @Override
//        protected void publishResults(CharSequence constraint, FilterResults results) {
//            // NO-OP We do not want to filter
//        }
//    }
//
//}