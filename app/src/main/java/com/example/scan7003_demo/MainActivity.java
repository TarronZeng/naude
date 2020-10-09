package com.example.scan7003_demo;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.pt.scan.Scan;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends Activity {
    ScanReceiver mScanReceiver;
    final Scan scan = new Scan();

    TextView tv_scan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        tv_scan = (TextView) findViewById(R.id.tv_scan);

        mScanReceiver = new ScanReceiver();
        IntentFilter scanFilter = new IntentFilter();
        scanFilter.addAction(Scan.scanIntentFilter);
        registerReceiver(mScanReceiver, scanFilter);
    }

    @Override
    protected void onResume()
    {
        scan.open();

        findViewById(R.id.btn_scan).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View arg0)
            {
                scan.scan2(0);
            }
        });

        findViewById(R.id.btn_scan_10).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View arg0)
            {
                scan.scan2(10);
            }
        });

        findViewById(R.id.btn_cancelScan).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View arg0)
            {
                String cs = scan.cancelScan();
                Log.d("lhj", "cancelScan return=" + cs);
            }
        });
        super.onResume();
    }

    @Override
    protected void onPause()
    {
        scan.cancelScan();
        if(scan!=null)
        {
            int close = scan.close();
            Log.d("lhj", "close return=" + close);
        }

        super.onPause();
    }

    public class ScanReceiver extends BroadcastReceiver
    {
        @Override
        public void onReceive(Context arg0, Intent intent)
        {
            int res = intent.getIntExtra(Scan.res,0);
            String scanDataString = intent.getStringExtra(Scan.scanDataString);

            Log.d("daveni", "daveni MainActivity res = "+res);
            Log.d("daveni", "daveni MainActivity scanDataString = "+scanDataString);

            tv_scan.setText("Scan data:"+scanDataString);
        }
    }
}

