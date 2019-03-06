package com.flyavis.android.ui.bills;

import com.airbnb.epoxy.AutoModel;
import com.airbnb.epoxy.TypedEpoxyController;
import com.flyavis.android.BillsItemBindingModel_;
import com.flyavis.android.R;
import com.flyavis.android.TitleItemBindingModel_;
import com.flyavis.android.data.database.MyTrip;

import java.util.List;

public class BillsEpoxyController extends TypedEpoxyController<List<MyTrip>> {

    private BillsCallbacks callbacks;
    @AutoModel
    TitleItemBindingModel_ titleItemBindingModel;

    BillsEpoxyController(BillsCallbacks callbacks) {
        this.callbacks = callbacks;
    }

    @Override
    protected void buildModels(List<MyTrip> data) {
        titleItemBindingModel
                .title(R.string.title_travelling_expenses)
                .addTo(this);
        for (MyTrip myTrip : data) {
            new BillsItemBindingModel_()
                    .id(myTrip.getMyTripId())
                    .title(myTrip.getTripName())
                    .itemClickListener((model, parentView, clickedView, position) -> {
                        callbacks.onItemClick((int) model.id());
                    })
                    .addTo(this);
        }
    }

    public interface BillsCallbacks {
        void onItemClick(int id);
    }
}
