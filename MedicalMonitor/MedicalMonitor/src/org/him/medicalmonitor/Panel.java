package org.him.medicalmonitor;

import java.util.Locale;

import org.him.net.NetConnector;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

public class Panel extends Fragment {

	Button b;
		public Panel() {
            // Empty constructor required for fragment subclasses
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.panel, container, false);
            getActivity().setTitle("Panel");
            
            Button b = (Button) rootView.findViewById(R.id.button1);
            Button b2 = (Button) rootView.findViewById(R.id.button2);
            b.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent i = new Intent(getActivity(), MainActivity.class);
					if(NetConnector.isConnected(v.getContext()))
					{
						i.putExtra("netStat", "true");
					}
					else
					{
						i.putExtra("netStat", "false");
					}
					startActivity(i);
				}
			});
            
            b2.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent i = new Intent(getActivity(), NextActivity.class);
					if(NetConnector.isConnected(v.getContext()))
					{
						i.putExtra("netStat", "true");
					}
					else
					{
						i.putExtra("netStat", "false");
					}
					startActivity(i);
					
				}
			});
            
            return rootView;
        }

}
