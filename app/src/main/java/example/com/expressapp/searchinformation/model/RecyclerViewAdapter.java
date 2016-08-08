package example.com.expressapp.searchinformation.model;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView.OnItemClickListener;

import example.com.expressapp.R;

/**
 * Created by lxs on 2016/8/7.
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<MyViewHolder> {
    private Context context;
    private ExpressInfoManager mExpressInfo;
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
        this.mExpressInfo=expressInfoManager;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View itemView= LayoutInflater.from(context).inflate(R.layout.itemview_layout,parent,false);
        MyViewHolder viewHolder=new MyViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position)
    {
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
        return 0;
    }
}
