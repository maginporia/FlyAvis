package com.flyavis.android.ui.planhelper;

import com.airbnb.epoxy.AutoModel;
import com.airbnb.epoxy.TypedEpoxyController;
import com.flyavis.android.HelperItemBindingModel_;
import com.flyavis.android.HelperTilteBindingModel_;
import com.flyavis.android.NoteChipsBindingModel_;
import com.flyavis.android.data.database.Plan;

public class PlanHelperEpoxyController extends TypedEpoxyController<Plan> {

    @AutoModel
    NoteChipsBindingModel_ noteChipsBindingModel_;

    @Override
    protected void buildModels(Plan data) {
        new HelperTilteBindingModel_()
                .id("budget")
                .title("預計花費")
                .addTo(this);
        new HelperItemBindingModel_()
                .id("budgetCost")
                .title("支出")
                .amount("5000")
                .addTo(this);
        new HelperItemBindingModel_()
                .id("budgetTravelCost")
                .title("交通支出")
                .amount("5000")
                .addTo(this);

        new HelperTilteBindingModel_()
                .id("actual")
                .title("實際花費")
                .addTo(this);
        new HelperItemBindingModel_()
                .id("actualCost")
                .title("支出")
                .amount("5000")
                .addTo(this);
        new HelperItemBindingModel_()
                .id("actualTravelCost")
                .title("交通支出")
                .amount("5000")
                .addTo(this);

        new HelperTilteBindingModel_()
                .id("notice")
                .title("備忘事項")
                .addTo(this);

        noteChipsBindingModel_
                .addTo(this);
    }
}
