package example.com.expressapp.send.view;


import android.content.Intent;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.Date;

import example.com.expressapp.R;
import example.com.expressapp.adminGUID;
import example.com.expressapp.searchinformation.model.ExpressInfo;
import example.com.expressapp.searchinformation.model.ExpressInfoManager;
import example.com.expressapp.searchinformation.model.MyViewHolder;
import example.com.expressapp.searchinformation.model.RecyclerViewAdapter;
import example.com.expressapp.send.model.MyAdapter;
import example.com.expressapp.send.presenter.UpdataLadingPresenterImpl;
import example.com.expressapp.send.presenter.iUpdataLadingPresenter;

/**
 * A simple {@link Fragment} subclass.
 */
public class SendFragment extends Fragment implements iSend{
    private iUpdataLadingPresenter updataLading;
    private adminGUID mGuid;
    private ExpressInfoManager mExpressInfoManager;
    private RecyclerViewAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private ItemTouchHelper itemTouchHelper;
    private ItemTouchHelper.SimpleCallback mCallback;

    private FloatingActionButton startSendFab;

    private Handler handler=new Handler()
    {
        public void handleMessage(android.os.Message msg)
        {
            if(msg.what==1)
            {
                Log.d("test",msg.obj.toString());
            }
        }
    };

    public SendFragment(ExpressInfoManager expressInfoManager,adminGUID guid) {
        // Required empty public constructor
        this.mExpressInfoManager=expressInfoManager;
        this.mGuid=guid;

        Date date4=new Date(116,10,20);
        Date date1=new Date(116,7,7);
        Date date2=new Date(116,5,6);
        Date date3=new Date(116,6,2);
        mExpressInfoManager.addExpressInfo(new ExpressInfo("耳机",0.2f,"中","肖一嘉","18351925810", "江苏省南京" +
                "市仙林大道135号","肖一嘉","18351925810","江苏省南京市仙林大道135号","2016080803",date1,true));
        mExpressInfoManager.addExpressInfo(new ExpressInfo("小耳机",0.1f,"小","廖祥森","13338513960","江苏省南京" +
                "市学则路456号","廖祥森","13338513960","江苏省南京市学则路456号","2016080805",date2,false));
        mExpressInfoManager.addExpressInfo(new ExpressInfo("大耳机",0.3f,"大","彭锋","13545603130","江苏省南京" +
                "市三山路12号","彭锋","13545603130","江苏省南京市三山路12号","2016080801",date3,true));
        mExpressInfoManager.addExpressInfo(new ExpressInfo("超大耳机",0.4f,"超大","卢竞择","16456123112","江苏省" +
                "南京市软件大道35号","卢竞择","16456123112","江苏省南京市软件大道35号","2016080802",date4,false));

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View thisView=inflater.inflate(R.layout.send_fragment_layout,container,false);
        startSendFab=(FloatingActionButton)thisView.findViewById(R.id.send_fragment_layout_start_send);

        startSendFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(thisView,"1231434",Snackbar.LENGTH_LONG).show();
            }
        });

        mAdapter=new RecyclerViewAdapter(this.getContext(), mExpressInfoManager);
        mRecyclerView=(RecyclerView) thisView.findViewById(R.id.send_fragment_layout_recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(thisView.getContext()));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mAdapter);

        this.mCallback = new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP|ItemTouchHelper.DOWN,ItemTouchHelper.RIGHT) {
            /**
             * @param recyclerView
             * @param viewHolder 拖动的ViewHolder
             * @param target 目标位置的ViewHolder
             * @return
             */
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                int fromPosition = viewHolder.getAdapterPosition();//得到拖动ViewHolder的position
                int toPosition = target.getAdapterPosition();//得到目标ViewHolder的position
                if (fromPosition < toPosition) {
                    //分别把中间所有的item的位置重新交换
                    for (int i = fromPosition; i < toPosition; i++) {
                        mExpressInfoManager.swapNearExpressInfo(i,i+1);
                    }
                } else {
                    for (int i = fromPosition; i > toPosition; i--) {
                        mExpressInfoManager.swapNearExpressInfo(i,i-1);
                    }
                }
                mAdapter.notifyItemMoved(fromPosition, toPosition);
                //返回true表示执行拖动
                return true;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                mExpressInfoManager.removeExpressInfo(position);
                mAdapter.notifyItemRemoved(position);
            }

            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
                if(actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
                    //滑动时改变Item的透明度
                    final float alpha = 1 - Math.abs(dX) / (float)viewHolder.itemView.getWidth();
                    viewHolder.itemView.setAlpha(alpha);
                    viewHolder.itemView.setTranslationX(dX);
                }

            }

            @Override
            public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                super.clearView(recyclerView, viewHolder);
                ((MyViewHolder)viewHolder).setItemUnDrag();
            }

            @Override
            public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
                super.onSelectedChanged(viewHolder, actionState);
                if(actionState==ItemTouchHelper.ACTION_STATE_DRAG)
                    ((MyViewHolder)viewHolder).setItemDrag();
            }
        };
        this.itemTouchHelper = new ItemTouchHelper(mCallback);
        this.itemTouchHelper.attachToRecyclerView(mRecyclerView);

        updataLading=new UpdataLadingPresenterImpl(this);
        return thisView;
    }


    @Override
    public String getGUID() {
        return mGuid.getGUID();
    }

    @Override
    public String getIdNum() {
        return mExpressInfoManager.getExpressInfoList().get(0).getIdNum();
    }

    private void callReceiver(String telephone)
    {
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:"+telephone));
        startActivity(intent);
    }
}
