package com.flyavis.android.ui.mytrips;

import android.view.View;

import com.airbnb.epoxy.AutoModel;
import com.airbnb.epoxy.TypedEpoxyController;
import com.flyavis.android.MyTripsItemBindingModel_;
import com.flyavis.android.TitleItemBindingModel_;
import com.flyavis.android.data.database.MyTrip;

import java.util.ArrayList;
import java.util.List;

public class MyTripsEpoxyController extends TypedEpoxyController<List<MyTrip>> {// TypedEpoxyController<List<Photo>>

    public interface MyTripsCallbacks {
        void onMyTripItemClick
                (MyTripsFragmentDirections.ActionMyTripsFragmentToPlanningFragment action);

        void onMyTripItemLongClick(int id, boolean deleteState, int clickedCount);

    }

    /*
     * xml檔模組化RecyclerView內容
     * 使用package-info.java產生Model
     * */
    @AutoModel//Auto create and assign a unique id
            TitleItemBindingModel_ titleItemBindingModel;

    private final MyTripsCallbacks callbacks;
    private boolean deleteState = false;
    private int clickedCount = 0;

    MyTripsEpoxyController(MyTripsCallbacks callbacks) {
        this.callbacks = callbacks;
    }

    @Override
    protected void buildModels(List<MyTrip> list) {// buildModels(List<Photo> photos)

        //組合RecyclerView內容
        titleItemBindingModel
                .addTo(this);

        if (list != null) {
            for (MyTrip myTrip : list) {
                new MyTripsItemBindingModel_()
                        //All models must set a unique id.
                        .id(myTrip.getMyTripId())
                        .title(myTrip.getTripName())
                        .date(String.valueOf(myTrip.getStartDate()) + "-" + myTrip.getEndDate())
                        .clickListener((model, parentView, clickedView, position) -> {
                            if (!deleteState) {
                                MyTripsFragmentDirections.ActionMyTripsFragmentToPlanningFragment action
                                        = MyTripsFragmentDirections
                                        .actionMyTripsFragmentToPlanningFragment((int) model.id());
                                callbacks.onMyTripItemClick(action);
                            } else {
                                setClicked(clickedView);
                                callbacks.onMyTripItemLongClick((int)(model.id()), deleteState
                                        , clickedCount);
                            }
                        })
                        .longClickListener((model, parentView, clickedView, position) -> {
                            setClicked(clickedView);
                            callbacks.onMyTripItemLongClick((int)(model.id()), deleteState
                                    , clickedCount);
                            return true;
                        })
                        .addTo(this);
            }
        }
    }

    private void setClicked(View clickedView) {
        if (clickedView.getElevation() != 10f) {
            clickedView.setHovered(true);
            clickedView.setElevation(10f);
            deleteState = true;
            clickedCount++;
        } else {
            clickedView.setHovered(false);
            clickedView.setElevation(4f);
            clickedCount--;
            if (clickedCount == 0) deleteState = false;
        }
    }

}
