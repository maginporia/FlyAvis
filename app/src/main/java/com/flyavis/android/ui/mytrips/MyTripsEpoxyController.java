package com.flyavis.android.ui.mytrips;

import com.airbnb.epoxy.AutoModel;
import com.airbnb.epoxy.TypedEpoxyController;
import com.flyavis.android.MyTripsItemBindingModel_;
import com.flyavis.android.TitleItemBindingModel_;
import com.flyavis.android.data.database.MyTrip;

import java.util.List;

public class MyTripsEpoxyController extends TypedEpoxyController<List<MyTrip>> {// TypedEpoxyController<List<Photo>>

    public interface MyTripsCallbacks {
        void onMyTripItemClick
                (MyTripsFragmentDirections.ActionMyTripsFragmentToPlanningFragment action);
    }

    /*
     * xml檔模組化RecyclerView內容
     * 使用package-info.java產生Model
     * */
    @AutoModel//Auto create and assign a unique id
            TitleItemBindingModel_ titleItemBindingModel;

    private final MyTripsCallbacks callbacks;

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
                            MyTripsFragmentDirections.ActionMyTripsFragmentToPlanningFragment action
                                    = MyTripsFragmentDirections
                                    .actionMyTripsFragmentToPlanningFragment((int) model.id());
                            callbacks.onMyTripItemClick(action);
                        })
                        .addTo(this);
            }
        }

    }
}
