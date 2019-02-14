package com.gnd.calificaprofesores.RecyclerForClassFrontPageCapital;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.gnd.calificaprofesores.R;
import com.jesusm.holocircleseekbar.lib.HoloCircleSeekBar;

/** layout_score_selector.xml **/

public class ScoreSelectorViewHolder extends RecyclerView.ViewHolder {
    View mView;

    public ScoreSelectorViewHolder(View view){
        super(view);
        mView = view;
    }
    public void SetDetails(long score1, long score2, long score3){
        HoloCircleSeekBar picker1 = mView.findViewById(R.id.picker1);
        HoloCircleSeekBar picker2 = mView.findViewById(R.id.picker2);
        HoloCircleSeekBar picker3 = mView.findViewById(R.id.picker3);

        picker1.setMax(5);
        picker2.setMax(5);
        picker3.setMax(5);

        picker1.setValue(score1);
        picker2.setValue(score2);
        picker3.setValue(score3);

    }
}
