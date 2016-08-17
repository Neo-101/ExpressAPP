package example.com.expressapp.searchinformation.model;

import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import example.com.expressapp.R;

/**
 * Created by lxs on 2016/8/7.
 */
public class MyViewHolder extends RecyclerView.ViewHolder {
    public CardView mCardView;
    private boolean selected;
    public MyViewHolder(View itemView)
    {
        super(itemView);
        mCardView=(CardView)itemView.findViewById(R.id.itemview_layout_cardview);
        setItemUnDrag();
    }

    public void setIDInfo(String id)
    {
        TextView idTextView=(TextView) mCardView.findViewById(R.id.itemview_layout_id_textview);
        idTextView.setText(id);
    }

    public void setNameInfo(String name)
    {
        TextView nameTextView=(TextView)mCardView.findViewById(R.id.itemview_layout_name_textview);
        nameTextView.setText(name);
    }

    public void setPhoneInfo(String phone)
    {
        TextView phoneTextView=(TextView)mCardView.findViewById(R.id.itemview_layout_phone_textview);
        phoneTextView.setText(phone);
    }

    public  void setAddressInfo(String address)
    {
        TextView addressTextView=(TextView)mCardView.findViewById(R.id.itemview_layout_address_textview);
        addressTextView.setText(address);
    }

    public void setUpdateTime(String updateTime)
    {
        TextView updateTimeView=(TextView)mCardView.findViewById(R.id.itemview_layout_updatetime_textview);
        updateTimeView.setText(updateTime);
    }

    public void setDelivered(boolean isDelivered)
    {
        ImageView deliveredView=(ImageView)mCardView.findViewById(R.id.itemview_layout_delivered_imageview);
        if(!isDelivered)
            deliveredView.setVisibility(View.INVISIBLE);
        else
            deliveredView.setVisibility(View.VISIBLE);
    }

    public void click()
    {
        if(selected) {
            setItemUnselected();
            selected=false;
        }
        else {
            setItemSelected();
            selected=true;
        }
    }
    public void setItemSelected()
    {
       mCardView.setCardBackgroundColor(Color.parseColor("#B6B6B6"));
    }

    public void setItemUnselected()
    {
        mCardView.setCardBackgroundColor(Color.WHITE);
    }

    public void setItemDrag()
    {
        mCardView.setCardElevation(25.0f);
    }

    public void setItemUnDrag()
    {
        mCardView.setCardElevation(6.0f);
    }
}
