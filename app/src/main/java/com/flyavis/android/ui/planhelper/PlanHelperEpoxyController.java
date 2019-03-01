package com.flyavis.android.ui.planhelper;

import android.content.Context;
import android.widget.CompoundButton;

import com.airbnb.epoxy.AutoModel;
import com.airbnb.epoxy.TypedEpoxyController;
import com.flyavis.android.HelperItemBindingModel_;
import com.flyavis.android.HelperTilteBindingModel_;
import com.flyavis.android.NoteChipsBindingModel_;
import com.flyavis.android.NoteContentBindingModel_;
import com.flyavis.android.R;
import com.flyavis.android.data.database.Plan;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PlanHelperEpoxyController extends TypedEpoxyController<Plan> {
    private final PlanHelperCallbacks callbacks;

    private Context context;
    @AutoModel
    NoteChipsBindingModel_ noteChipsBindingModel_;
    @AutoModel
    NoteContentBindingModel_ noteContentBindingModel_;

    PlanHelperEpoxyController(PlanHelperCallbacks callbacks, Context context) {
        this.callbacks = callbacks;
        this.context = context;
    }

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
                .title("備忘標籤")
                .addTo(this);

        //chips state
        boolean climb = false;
        boolean water = false;
        boolean literary = false;
        boolean shopping = false;
        boolean eat = false;
        boolean history = false;
        boolean monument = false;
        boolean exciting = false;

        if (data.getSpotNotice() != null) {
            List<String> list = new ArrayList<>(Arrays.asList(data.getSpotNotice().split(", ")));
            climb = list.contains(context.getString(R.string.mountain_climbing));
            water = list.contains(context.getString(R.string.water_activities));
            literary = list.contains(context.getString(R.string.literary));
            shopping = list.contains(context.getString(R.string.shopping));
            eat = list.contains(context.getString(R.string.eat));
            history = list.contains(context.getString(R.string.history));
            monument = list.contains(context.getString(R.string.monument));
            exciting = list.contains(context.getString(R.string.exciting));
        }

        noteChipsBindingModel_
                .climbChecked(climb)
                .waterChecked(water)
                .literaryChecked(literary)
                .shoppingChecked(shopping)
                .eatChecked(eat)
                .historyChecked(history)
                .monumentChecked(monument)
                .excitingChecked(exciting)
                .checkedChangeListener(callbacks::onCheckedChange)
                .addTo(this);

//        noteContentBindingModel_
//                .addTo(this);

    }

    public interface PlanHelperCallbacks {
        void onCheckedChange(CompoundButton button, Boolean b);
    }
}
