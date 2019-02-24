package com.flyavis.android.ui.planning;

import com.airbnb.epoxy.AutoModel;
import com.airbnb.epoxy.Typed2EpoxyController;
import com.flyavis.android.AddViewBindingModel_;
import com.flyavis.android.DateTitleBindingModel_;
import com.flyavis.android.data.database.Plan;

import java.util.List;

public class PlanningEpoxyController extends Typed2EpoxyController<List<Plan>, String> {
    private final PlanningCallbacks callbacks;
    @AutoModel
    AddViewBindingModel_ addViewBindingModel;
    @AutoModel
    DateTitleBindingModel_ dateTitleBindingModel;

    PlanningEpoxyController(PlanningCallbacks callbacks) {
        this.callbacks = callbacks;
    }

    @Override
    protected void buildModels(List<Plan> data, String dateString) {

        dateTitleBindingModel
                .title(dateString)
                .subTitle("")
                .addTo(this);

        if (data != null && data.size() != 0) {
            for (int i = 0; i < data.size() - 1; i++) {
                if (i < data.size() - 1) {
                    add(new PlanningModelGroup(data.get(i), data.get(i + 1), callbacks, false));
                } else {
                    add(new PlanningModelGroup(data.get(i), null, callbacks, false));
                }

            }
            add(new PlanningModelGroup(data.get(data.size() - 1), null, callbacks, true));
        }
        addViewBindingModel
                .clickListener(view -> callbacks.onAddNewSpotViewClick())
                .addTo(this);
    }

    public interface PlanningCallbacks {
        void onAddNewSpotViewClick();

        void onSpotViewClick();

        void onMoreButtonClick(Plan plan);

        void onTrafficTimeClick(Plan plan, Plan nextPlan);
    }

}
