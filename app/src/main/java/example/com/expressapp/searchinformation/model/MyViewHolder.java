package example.com.expressapp.searchinformation.model;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import example.com.expressapp.R;

/**
 * Created by lxs on 2016/8/7.
 */
public class MyViewHolder extends RecyclerView.ViewHolder {
    public CardView mCardView;
    public MyViewHolder(View itemView)
    {
        super(itemView);
        mCardView=(CardView)itemView.findViewById(R.id.itemview_layout_cardview);
    }
}
