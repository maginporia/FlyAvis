package com.flyavis.android.ui.planning;

import com.airbnb.epoxy.AutoModel;
import com.airbnb.epoxy.TypedEpoxyController;
import com.flyavis.android.AddViewBindingModel_;
import com.flyavis.android.DateTitleBindingModel_;
import com.flyavis.android.data.database.Plan;

import java.util.List;

public class PlanningEpoxyController extends TypedEpoxyController<List<Plan>> {
    private final PlanningCallbacks callbacks;
    @AutoModel
    AddViewBindingModel_ addViewBindingModel;
    @AutoModel
    DateTitleBindingModel_ dateTitleBindingModel;

    PlanningEpoxyController(PlanningCallbacks callbacks) {
        this.callbacks = callbacks;
    }

    @Override
    protected void buildModels(List<Plan> data) {
        dateTitleBindingModel
                //TODO change to real data
                .title("2019.02.14 星期四")
                .subTitle("浮潛日")
                .addTo(this);

//        if (data.size() > 0) {
//            if (data.size() > 1) {
        for (int i = 0; i < data.size(); i++) {
                    add(new PlanningModelGroup(data.get(i), callbacks));
//                }
//            }

//            // Last item without traffic time
//            Plan lastSpot = data.get(data.size() - 1);
//            new SpotItemBindingModel_()
//                    .id(data.size())
//                    .spotName(lastSpot.getPlaceName())
//                    .clickListener((model, parentView, clickedView, position) -> {
//
//                    })
//                    .addTo(this);

        }
        addViewBindingModel
                .clickListener(view -> callbacks.onAddNewSpotViewClick())
                .addTo(this);
    }

    public interface PlanningCallbacks {
        void onAddNewSpotViewClick();

        void onSpotViewClick();

        void onTrafficTimeClick();
    }

}
