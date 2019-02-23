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
                .title("2019.02.22 星期五")
                .subTitle("")
                .addTo(this);

//        if (data.size() > 0) {
//            if (data.size() > 1) {
        if (data != null) {
            for (int i = 0; i < data.size() - 1; i++) {
                add(new PlanningModelGroup(data.get(i), callbacks, false));
            }
            add(new PlanningModelGroup(data.get(data.size() - 1), callbacks, true));
        }
        addViewBindingModel
                .clickListener(view -> callbacks.onAddNewSpotViewClick())
                .addTo(this);
    }

    public interface PlanningCallbacks {
        void onAddNewSpotViewClick();

        void onSpotViewClick();

        void onMoreButtonClick(Plan plan);

        void onTrafficTimeClick();
    }

}
