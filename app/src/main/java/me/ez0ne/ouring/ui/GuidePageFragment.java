package me.ez0ne.ouring.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Ezreal.Wan on 2016/5/10.
 */
public class GuidePageFragment extends Fragment {
    final static String LAYOUT_ID = "layoutid";

    public static GuidePageFragment newInstance(int layoutId) {
        GuidePageFragment pane = new GuidePageFragment();
        Bundle args = new Bundle();
        args.putInt(LAYOUT_ID, layoutId);
        pane.setArguments(args);
        return pane;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(getArguments().getInt(LAYOUT_ID, -1), container, false);
        return rootView;
    }
}
