package org.him.medicalmonitor;

import org.him.net.NetConnector;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

@SuppressLint("NewApi") public class InitViewer extends Activity {

	ProgressBar pb;
	//NetConnector nc = new NetConnector();
	TextView netStatus;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.init_viewer);
		pb = (ProgressBar) findViewById(R.id.helpProgress);
		netStatus = (TextView) findViewById(R.id.netStatusTextView);
		netStatus.setText("");
		pb.setVisibility(View.INVISIBLE);
        //Start a THREAD Handler after a 2 second delay
        Handler handler = new Handler();
        //Start the Spinner animation in 1.5 seconds.
        handler.postDelayed(new Runnable() { 
        	//Run the thread
             public void run() { 
            	 pb.setVisibility(View.VISIBLE);
            	 pb.animate();
             }
        }, 1500);
        //Start checking for internet connection after 3 seconds. 
        handler.postDelayed(new Runnable() { 
        	//Run the thread
             public void run() { 
                  if(NetConnector.isNetworkAvailable(InitViewer.this))
                  {
                	  Intent i = new Intent(InitViewer.this, DrawerTest.class);
                	  i.putExtra("netStat", "true");
                	  startActivity(i);
                  }
                  else
                  {
                	  Intent i = new Intent(InitViewer.this, DrawerTest.class);
                	  i.putExtra("netStat", "false");
                	  startActivity(i);
                  }
             }
        }, 3000);
		
	}
	
	@Override
	public void onBackPressed()
	{
		return;
	}
}
