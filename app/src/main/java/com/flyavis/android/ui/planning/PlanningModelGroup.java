package com.flyavis.android.ui.planning;

import com.airbnb.epoxy.EpoxyModel;
import com.airbnb.epoxy.EpoxyModelGroup;
import com.flyavis.android.R;
import com.flyavis.android.SpotItemBindingModel_;
import com.flyavis.android.TrafficTimeBindingModel_;
import com.flyavis.android.data.database.Plan;

import java.util.ArrayList;
import java.util.List;

//把Model做群組
public class PlanningModelGroup extends EpoxyModelGroup {
    public final Plan plan;

    PlanningModelGroup(Plan plan, PlanningEpoxyController.PlanningCallbacks callbacks) {
        super(R.layout.planning_item, buildModels(plan, callbacks));
        this.plan = plan;
    }

    private static List<EpoxyModel<?>> buildModels
            (Plan plan, PlanningEpoxyController.PlanningCallbacks callbacks) {
        ArrayList<EpoxyModel<?>> models = new ArrayList<>();
        models.add(
                new SpotItemBindingModel_()
                        .spotName(plan.getPlaceName())
        );
        models.add(new TrafficTimeBindingModel_());
        return models;
    }


}
