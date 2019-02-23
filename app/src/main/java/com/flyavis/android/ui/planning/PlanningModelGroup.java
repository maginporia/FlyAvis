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
        id(plan.getPlanId());
    }

    private static List<EpoxyModel<?>> buildModels
            (Plan plan, PlanningEpoxyController.PlanningCallbacks callbacks) {
        ArrayList<EpoxyModel<?>> models = new ArrayList<>();
        long l = plan.getSpotEndTime().getTime() - plan.getSpotStartTime().getTime();
        models.add(
                new SpotItemBindingModel_()
                        .id("spotItem")
                        .spotName(plan.getSpotName())
                        .arriveTime(plan.getSpotStartTime().toString())
                        .leaveTime(plan.getSpotEndTime().toString())

                        .spotNotice("目前沒有備註")
                        .stayTime(l / (60 * 60 * 1000) + "小時")
                        .clickListener(view -> {

                        })
                        .moreButtonClickListener(view -> {
                            callbacks.onMoreButtonClick(plan);
                        })
        );
        models.add(
                new TrafficTimeBindingModel_()
                        .id("trafficTime")
                        .trafficTime("∞小時")
                        .clickListener(view -> {

                        })
        );
        return models;
    }


}
