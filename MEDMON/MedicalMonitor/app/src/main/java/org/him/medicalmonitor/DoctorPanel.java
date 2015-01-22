package org.him.medicalmonitor;

import org.him.net.NetConnector;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;


@SuppressLint("ClickableViewAccessibility")
public class DoctorPanel extends Fragment {

    public DoctorPanel() {
        // Empty constructor required for fragment subclasses
    }

    ViewPager pager;
    TextView backView;
    RelativeLayout rl;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.doctor_panel, container, false);
        getActivity().setTitle("Doctor Panel");

        pager = (ViewPager) rootView.findViewById(R.id.pager);
        backView = (TextView) rootView.findViewById(R.id.backViewer);
        //RelativeLayout rl = (RelativeLayout) rootView.findViewById(R.id.relativeDoctorLayout);
        backView.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                //Change the image when a touch event is triggered
                backView.setBackgroundResource(R.drawable.back_selected);
                return false;
            }
        });


        backView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), MDMListDraw.class);
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
