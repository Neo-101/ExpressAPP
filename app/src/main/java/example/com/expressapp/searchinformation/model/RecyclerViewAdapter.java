package example.com.expressapp.searchinformation.model;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import example.com.expressapp.R;
import example.com.expressapp.searchinformation.view.InformationFragment;

/**
 * Created by lxs on 2016/8/7.
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<MyViewHolder> {
    private Context context;
    private ExpressInfoManager mExpressInfoManager;
    private OnItemClickListener mOnItemClickListener;

    public interface OnItemClickListener
    {
        void OnItemClick(View view,int position);
        void OnItemLongClick(View view,int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener)
    {
        this.mOnItemClickListener = listener;
    }

    public RecyclerViewAdapter(Context context,ExpressInfoManager expressInfoManager)
    {
        this.context=context;
        this.mExpressInfoManager =expressInfoManager;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View itemView= LayoutInflater.from(this.context).inflate(R.layout.itemview_layout,parent,false);
        MyViewHolder viewHolder=new MyViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position)
    {
        ExpressInfo expressInfoTemp= mExpressInfoManager.getExpressInfo(position);
        holder.setIDInfo(expressInfoTemp.getIdNum());
        holder.setNameInfo(expressInfoTemp.getReceiverName());
        holder.setPhoneInfo(expressInfoTemp.getReceiverPhone());
        holder.setAddressInfo(expressInfoTemp.getReceiverAddress());
        holder.setUpdateTime(expressInfoTemp.getUpDataTime());
        holder.setDelivered(expressInfoTemp.getIsDelivered());
        if(InformationFragment.selectedItems.contains(position))
            holder.setItemSelected();
        else holder.setItemUnselected();
        if(mOnItemClickListener!=null)
        {
            holder.mCardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position=holder.getLayoutPosition();
                    mOnItemClickListener.OnItemClick(holder.mCardView,position);
                }
            });
            holder.mCardView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int position=holder.getLayoutPosition();
                    mOnItemClickListener.OnItemLongClick(holder.mCardView,position);
                    return true;
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mExpressInfoManager.getInfoNum();
    }

    public void remove(ExpressInfo expressInfo){
        mExpressInfoManager.removeExpressInfo(expressInfo);
        notifyDataSetChanged();
    }

    public void updateData(ExpressInfoManager expressInfoManager)
    {
        this.mExpressInfoManager=expressInfoManager;
        notifyDataSetChanged();
    }
}
