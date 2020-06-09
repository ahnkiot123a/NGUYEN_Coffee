package com.koit.project_prm391_1.ui.news;

import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.koit.project_prm391_1.R;

public class DashboardFragment extends Fragment {

    private DashboardViewModel dashboardViewModel;

    ViewFlipper viewFlipper;
    ImageButton pre, aft;
    private float lastX;
    float currentX;
    private LinearLayout dotsLayout;
    private TextView[] dots;
    private int[] layouts;
    int currentPage = 0;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                ViewModelProviders.of(this).get(DashboardViewModel.class);
        View root = inflater.inflate(R.layout.fragment_news, container, false);
        // final TextView textView = root.findViewById(R.id.text_dashboard);
        viewFlipper = root.findViewById(R.id.viewFlipper);
        viewFlipper.setInAnimation(getContext(), R.anim.in_from_right);
        viewFlipper.setOutAnimation(getContext(), R.anim.out_to_left);
        viewFlipper.setFlipInterval(3000);
        viewFlipper.setAutoStart(true);
        dotsLayout = (LinearLayout) root.findViewById(R.id.layoutDots);
        // adding bottom dots
        addBottomDots(0);
        // Method to handle touch event like left to right swap and right to left swap
        root.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    // when user first touches the screen to swap
                    case MotionEvent.ACTION_DOWN: {
                        lastX = event.getX();
                        break;
                    }
                    case MotionEvent.ACTION_UP: {
                        currentX = event.getX();
                        // if left to right swipe on screen
                        if (lastX < currentX) {
                            viewFlipper.setInAnimation(getContext(), R.anim.in_from_left);
                            viewFlipper.setOutAnimation(getContext(), R.anim.out_to_right);
                            // Show the next Screen
                            if (viewFlipper.isAutoStart()) {
                                viewFlipper.stopFlipping();
                                viewFlipper.showNext();
                                viewFlipper.startFlipping();
                                viewFlipper.setAutoStart(true);
                            }
                            if (currentPage == 0) currentPage = 4;
                            currentPage -= 1;
                            addBottomDots(currentPage);

                        }
                        // if right to left swipe on screen
                        if (lastX > currentX) {
                            viewFlipper.setInAnimation(getContext(), R.anim.in_from_right);
                            viewFlipper.setOutAnimation(getContext(), R.anim.out_to_left);
                            // Show The Previous Screen
                            if (viewFlipper.isAutoStart()) {
                                viewFlipper.stopFlipping();
                                viewFlipper.showPrevious();
                                viewFlipper.startFlipping();
                                viewFlipper.setAutoStart(true);
                            }
                            currentPage += 1;
                            if (currentPage == 4) currentPage = 0;
                            addBottomDots(currentPage);
                        }
                        break;
                    }
                }
                return true;
            }
        });
        return root;
    }

    private void addBottomDots(int currentPage) {
        dots = new TextView[4];
        int[] colorsActive = getResources().getIntArray(R.array.array_dot_active);
        int[] colorsInactive = getResources().getIntArray(R.array.array_dot_inactive);

        dotsLayout.removeAllViews();
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(this.getContext());
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(colorsInactive[1]);
            dotsLayout.addView(dots[i]);
        }
        if (dots.length > 0)
            dots[currentPage].setTextColor(colorsActive[0]);
    }
}