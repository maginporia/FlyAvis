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

    PlanningModelGroup(Plan plan, Plan previousPlan, PlanningEpoxyController.PlanningCallbacks callbacks, boolean last) {
        super(R.layout.planning_item, buildModels(plan, previousPlan, callbacks, last));
        id(plan.getPlanId());
    }

    private static List<EpoxyModel<?>> buildModels
            (Plan plan, Plan nextPlan, PlanningEpoxyController.PlanningCallbacks callbacks, boolean last) {
        ArrayList<EpoxyModel<?>> models = new ArrayList<>();
        long l = plan.getSpotEndTime().getTime() - plan.getSpotStartTime().getTime();
        String planNotice = "目前沒有標籤";
        if (plan.getSpotNotice() != null && !plan.getSpotNotice().equals("")) {
            planNotice = plan.getSpotNotice();
        }
        models.add(
                new SpotItemBindingModel_()
                        .id("spotItem")
                        .spotName(plan.getSpotName())
                        .arriveTime(plan.getSpotStartTime().toString())
                        .leaveTime(plan.getSpotEndTime().toString())
                        .spotNotice(planNotice)
                        .stayTime(l * 1.0 / (60 * 60 * 1000) + "小時")
                        .clickListener(view -> {
                            callbacks.onSpotViewClick(plan);
                        })
                        .moreButtonClickListener(view -> {
                            callbacks.onMoreButtonClick(plan);
                        })
        );
        if (!last) {

            models.add(
                    new TrafficTimeBindingModel_()
                            .id("trafficTime")
                            .trafficTime("∞小時")
                            .clickListener(view -> {
                                callbacks.onTrafficTimeClick(plan, nextPlan);
                            })
            );
        }

        return models;
    }


}
