package example.com.expressapp.history.view;


import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.eazegraph.lib.charts.BarChart;
import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.BarModel;
import org.eazegraph.lib.models.PieModel;

import example.com.expressapp.R;
import example.com.expressapp.adminGUID;
import example.com.expressapp.history.model.CountInfo;
import example.com.expressapp.history.presenter.HistoryPresenterImpl;
import example.com.expressapp.history.presenter.iHistoryPresenter;

/**
 * A simple {@link Fragment} subclass.
 */
public class HistoryFragment extends Fragment implements iHistory{
    private BarChart numCountBarChart;
    private BarChart weightCountBarChart;
    private PieChart DeliverInfoPieChart;
    private CountInfo countinfo;
    private adminGUID mGuid;
    private iHistoryPresenter iPresenter;

    private Handler handler=new Handler()
    {
        public void handleMessage(android.os.Message msg)
        {
            if(msg.what==1)
            {
                if(!msg.obj.toString().equals("#"))
                {
                    Log.d("Test",msg.obj.toString());
                    countinfo=new CountInfo(msg.obj.toString());
                    for(int i=0;i<7;i++)
                        Log.d("Test",countinfo.getDailyCount(i).getDailyWeight()+" ");
                }
                else
                    Log.d("Test","Fail");
                numCountShow();
                weightCountShow();
                deliverInfoShow();
            }
        }
    };

    public HistoryFragment(adminGUID guid) {
        // Required empty public constructor
        mGuid=guid;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View thisView=inflater.inflate(R.layout.history_fragment_layout,container,false);
        numCountBarChart=(BarChart) thisView.findViewById(R.id.history_fragment_layout_numCount_barChart);
        weightCountBarChart=(BarChart) thisView.findViewById(R.id.history_fragment_layout_weightCount_barChart) ;
        DeliverInfoPieChart=(PieChart) thisView.findViewById(R.id.history_fragment_layout_DeliverInfo_pieChart);
        iPresenter=new HistoryPresenterImpl(this);
        iPresenter.historyJudge(handler);
        return thisView;
    }

    private void numCountShow()
    {
        for(int i=0;i<7;i++)
        {
            numCountBarChart.addBar(new BarModel(countinfo.getDailyCount(i).getDateTime(),countinfo.getDailyCount(i).getDailyNum(),0xFF0097A7));
        }
        numCountBarChart.startAnimation();
    }

    private void weightCountShow()
    {
        for(int i=0;i<7;i++)
        {
            weightCountBarChart.addBar(new BarModel(countinfo.getDailyCount(i).getDateTime(),countinfo.getDailyCount(i).getDailyWeight()/1f,0xFF0097A7));
        }
        weightCountBarChart.startAnimation();
    }

    private void deliverInfoShow()
    {
        int delivered=0,undelivered=0;
        for(int i=0;i<7;i++)
        {
            delivered+=countinfo.getDailyCount(i).getDailyDelivered();
            undelivered+=countinfo.getDailyCount(i).getDailyUndelivered();
        }
        DeliverInfoPieChart.addPieSlice(new PieModel("已送达", delivered, Color.parseColor("#FE6DA8")));
        DeliverInfoPieChart.addPieSlice(new PieModel("未送达", undelivered, Color.parseColor("#56B7F1")));

        DeliverInfoPieChart.startAnimation();
    }
    public String getGUID()
    {
        return mGuid.getGUID();
    }
}
