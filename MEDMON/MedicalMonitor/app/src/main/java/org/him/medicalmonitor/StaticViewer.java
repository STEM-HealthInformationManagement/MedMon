package org.him.medicalmonitor;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.TextView;

public class StaticViewer extends Fragment
{
    public StaticViewer()
    {
        // Empty constructor required for fragment subclasses
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.static_viewer, container, false);
        getActivity().setTitle("static_viewer");

        //Start blinking the Text View so people notice it...
        TextView slideBlinkerView = (TextView) rootView.findViewById(R.id.textView1); // Alpha parameters, used for transparency (SCALE: 0.0 [Completely Transparent] - 1.0 [Fully Visible])
        Animation slideBlinker = new AlphaAnimation(0.3f, 1.0f);
        slideBlinker.setDuration(500); //Time Of blink
        slideBlinker.setStartOffset(20);
        slideBlinker.setRepeatMode(Animation.REVERSE);
        slideBlinker.setRepeatCount(Animation.INFINITE);
        slideBlinkerView.startAnimation(slideBlinker);

        //Return View
        return rootView;
    }

}
