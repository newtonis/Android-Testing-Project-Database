package com.gnd.calificaprofesores.RecyclerForClassFrontPageCapital;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RatingBar;

import com.gnd.calificaprofesores.R;
import com.jesusm.holocircleseekbar.lib.HoloCircleSeekBar;

/** layout_score_selector.xml **/

public class ScoreSelectorViewHolder extends RecyclerView.ViewHolder {
    View mView;
    RatingBar ratingBar;
    HoloCircleSeekBar picker1, picker2, picker3;

    public ScoreSelectorViewHolder(View view){
        super(view);
        mView = view;
    }
    public void SetDetails(final ScoreSelectorData model, long score1, long score2, long score3){
        picker1 = mView.findViewById(R.id.picker1);
        picker2 = mView.findViewById(R.id.picker2);
        picker3 = mView.findViewById(R.id.picker3);

        picker1.setMax(5);
        picker2.setMax(5);
        picker3.setMax(5);

        picker1.setValue(score1);
        picker2.setValue(score2);
        picker3.setValue(score3);

        ratingBar = mView.findViewById(R.id.ratingBar2);

        HoloCircleSeekBar.OnCircleSeekBarChangeListener listener =
                new HoloCircleSeekBar.OnCircleSeekBarChangeListener(){
                    @Override
                    public void onProgressChanged(HoloCircleSeekBar holoCircleSeekBar, int i, boolean b) {
                        UpdateInputs(model);
                    }

                    @Override
                    public void onStartTrackingTouch(HoloCircleSeekBar holoCircleSeekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(HoloCircleSeekBar holoCircleSeekBar) {

                    }
                };

        picker1.setOnSeekBarChangeListener(listener);
        picker2.setOnSeekBarChangeListener(listener);
        picker3.setOnSeekBarChangeListener(listener);
        UpdateInputs(model);
    }
    public void UpdateInputs(ScoreSelectorData model){
        Float value = (picker1.getValue() + picker2.getValue() + picker3.getValue() ) / 3f;
        ratingBar.setRating( (float) Math.ceil(value*2f) / 2f );

        model.SetValue(0, picker1.getValue());
        model.SetValue(1, picker2.getValue());
        model.SetValue(2, picker3.getValue());

    }
}
