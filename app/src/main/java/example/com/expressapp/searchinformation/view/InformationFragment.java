package example.com.expressapp.searchinformation.view;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.ActionMode;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import android.os.Handler;

import example.com.expressapp.R;
import example.com.expressapp.adminGUID;
import example.com.expressapp.searchinformation.model.ExpressInfoManager;
import example.com.expressapp.searchinformation.model.ExpressInfo;
import example.com.expressapp.searchinformation.model.RecyclerViewAdapter;
import example.com.expressapp.searchinformation.presenter.GetInfoPresenterImpl;
import example.com.expressapp.searchinformation.presenter.iGetInfoPresenter;


public class InformationFragment extends Fragment implements iInformation{

    private ActionMode mActionMode;
    static public Set<Integer> selectedItems=new HashSet<>();
    private RecyclerView mRecyclerView;
    private TabLayout mTabLayout;
    private RecyclerViewAdapter mAdapter;
    private ExpressInfoManager mExpressInfoManager;
    private adminGUID mGuid;
    private iGetInfoPresenter iSearch;
    private Handler handler=new Handler()
    {
        public void handleMessage(android.os.Message msg) {
            if(msg.what==1)
            {
                mExpressInfoManager.setExpressInfoList(msg.obj.toString());
                for(int i=0;i<mExpressInfoManager.getExpressInfoList().size();i++)
                {
                    Log.d("test",mExpressInfoManager.getExpressInfoList().get(i).getReceiverName());
                }
                mExpressInfoManager.quickSortbyIdNum(0,mExpressInfoManager.getExpressInfoList().size()-1);
                mAdapter.updateData(mExpressInfoManager);
            }
        };
    };


    public InformationFragment(ExpressInfoManager expressInfoManager,adminGUID guid) {
        // Required empty public constructor
        this.mExpressInfoManager=expressInfoManager;
        mGuid=guid;
/*        Date date4=new Date(116,10,20);
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
*/
    }

    public String getGUID()
    {
        return mGuid.getGUID();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        final View thisView=inflater.inflate(R.layout.information_fragment_layout,container,false);
        iSearch=new GetInfoPresenterImpl(this);
        iSearch.judgeGetLadingInfo(handler);
        
        mAdapter=new RecyclerViewAdapter(this.getContext(), mExpressInfoManager);

        mTabLayout=(TabLayout)thisView.findViewById(R.id.information_fragment_layout_tablayout);
        mTabLayout.addTab(mTabLayout.newTab().setText("货物单号"));
        mTabLayout.addTab(mTabLayout.newTab().setText("接货时间"));
        mTabLayout.addTab(mTabLayout.newTab().setText("未派送"));
        mTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if(tab.getText().equals("货物单号"))
                    mExpressInfoManager.quickSortbyIdNum(0,mExpressInfoManager.getExpressInfoList().size()-1);
                else if(tab.getText().equals("接货时间"))
                    mExpressInfoManager.quickSortbyUpDataTime(0,mExpressInfoManager.getExpressInfoList().size()-1);
                else if(tab.getText().equals("未派送"))
                    mExpressInfoManager.quickSortbyIsDelivered(0,mExpressInfoManager.getExpressInfoList().size()-1);
                mAdapter.updateData(mExpressInfoManager);
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}
            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });

        mRecyclerView=(RecyclerView) thisView.findViewById(R.id.information_fragment_layout_recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(thisView.getContext()));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new RecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(View view, int position) {
                if(mActionMode!=null)
                    selectItemMode(position);
                //Snackbar.make(thisView,"ItemClick",Snackbar.LENGTH_LONG).show();
            }

            @Override
            public void OnItemLongClick(View view, int position) {
                if(mActionMode==null)
                    mActionMode=thisView.startActionMode(new ActionMode.Callback() {
                        @Override
                        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                            if(mActionMode==null){
                                mActionMode=mode;
                                mode.getMenuInflater().inflate(R.menu.menu_info_delete,menu);
                                return true;
                            }
                            else return false;
                        }

                        @Override
                        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                            return false;
                        }

                        @Override
                        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                            switch (item.getItemId())
                            {
                                case R.id.icon_menu_info_delete:
                                {
                                    Set<ExpressInfo> selectedExpressInfo=new HashSet<>();
                                    for(int position:selectedItems)
                                        selectedExpressInfo.add(mExpressInfoManager.getExpressInfo(position));
                                    for(ExpressInfo value:selectedExpressInfo)
                                        mAdapter.remove(value);
                                    mode.finish();

                                    return true;
                                }
                                default:
                                    return false;
                            }
                        }

                        @Override
                        public void onDestroyActionMode(ActionMode mode) {
                            mActionMode=null;
                            selectedItems.clear();
                            mAdapter.notifyDataSetChanged();
                        }
                    });
                //Snackbar.make(thisView,"ItemLongClick", Snackbar.LENGTH_LONG).show();
            }
        });
        return thisView;
    }

    private void selectItemMode(int position)
    {
        if(selectedItems.contains(position))
            selectedItems.remove(position);
        else
            selectedItems.add(position);
        if(selectedItems.size()==0)
            mActionMode.finish();
        else
        {
            mActionMode.setTitle(selectedItems.size()+"项已选择");
            mAdapter.notifyDataSetChanged();
        }
    }
}
