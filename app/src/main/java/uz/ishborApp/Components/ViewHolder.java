//package uz.ishborApp.Components;
//
//import android.content.Context;
//import android.support.v7.widget.RecyclerView;
//import android.view.View;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import butterknife.Bind;
//import butterknife.ButterKnife;
//import butterknife.OnClick;
//import uz.ishborApp.MyApplication;
//import uz.ishborApp.R;
//
///**
// * Created by sherxon on 2/28/16.
// */
//public class ViewHolder extends RecyclerView.ViewHolder {
//
//    @Bind(R.id.imageViewCover)
//    ImageView imageViewCover;
//
//    @Bind(R.id.type)
//    ImageView imageViewType;
//
//    @Bind(R.id.textViewPrimary)
//   public TextView textViewPrimary;
//
//    private Context context;
//    private Object data;
//
//    public ViewHolder(View itemView) {
//        super(itemView);
//        ButterKnife.bind(this, itemView);
//        MyApplication.get(context).getAppComponent().inject(this);
//    }
//
//    void setData(Context context, Object data) {
//        this.context = context;
//        this.data = data;
//    }
//
//    @OnClick(R.id.mainLayout)
//    public void onSearchItemClicked() {
////        LocalCache cache = LocalCache.getInstance();
////        if (data instanceof Artist) {
////            Artist artist = (Artist) data;
////            ArtistPageActivity.startActivity(context, artist);
////            cache.addSearchedTerm(artist.getName());
////        } else if (data instanceof Track) {
////            Track track = (Track) data;
////            mBus.post(new SearchEvents.SearchedItemClicked(track));
////            cache.addSearchedTerm(track.getTitle());
////        } else {
////            PlayList playList = (PlayList) data;
////            DetailedPlayListInfoActivity.startActivity(context, playList, imageViewCover);
////            cache.addSearchedTerm(playList.getName());
////        }
////        UiUtils.hideSoftKeyboard((Activity) context);
//        // TODO: 2/28/16 add to last searched items
//        System.out.println("onSearchItemClicked()");
//
//    }
//}