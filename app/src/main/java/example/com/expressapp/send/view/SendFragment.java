package example.com.expressapp.send.view;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import example.com.expressapp.R;
import example.com.expressapp.adminGUID;
import example.com.expressapp.searchinformation.model.ExpressInfoManager;
import example.com.expressapp.send.presenter.UpdataLadingPresenterImpl;
import example.com.expressapp.send.presenter.iUpdataLadingPresenter;

/**
 * A simple {@link Fragment} subclass.
 */
public class SendFragment extends Fragment implements iSend{
    private iUpdataLadingPresenter updataLading;
    private adminGUID mGuid;
    private ExpressInfoManager mExpressInfoManager;
    Button updataLading_btn;
    Button callReceiver_btn;

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
        mGuid=guid;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View thisView=inflater.inflate(R.layout.send_fragment_layout,container,false);
        updataLading=new UpdataLadingPresenterImpl(this);
        updataLading_btn=(Button) thisView.findViewById(R.id.updataLading_btn);
        updataLading_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updataLading.judgeUpLading(handler);
            }
        });
        callReceiver_btn=(Button) thisView.findViewById(R.id.send_fragment_layout_callReceiver_btn);
        callReceiver_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callReceiver("18362919691");
            }
        });
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
