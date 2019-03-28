package com.flyavis.android.ui.addnewbill;

import com.airbnb.epoxy.TypedEpoxyController;
import com.flyavis.android.PayerItemBindingModel_;
import com.flyavis.android.data.database.TeamMember;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

public class AddNewBillEpoxyController extends TypedEpoxyController<List<TeamMember>> {
    private AddNewBillEpoxyControllerCallbacks callbacks;
    FirebaseAuth auth = FirebaseAuth.getInstance();

    AddNewBillEpoxyController(AddNewBillEpoxyControllerCallbacks callbacks) {
        this.callbacks = callbacks;
    }

    @Override
    protected void buildModels(List<TeamMember> data) {
        int i = 0;
        for (TeamMember teamMember : data) {
            int finalI = i;
            new PayerItemBindingModel_()
                    .id(teamMember.getUserId())
                    .name(teamMember.getUserName())
                    .image(teamMember.getUserPhoto())
                    .onTextChanged((s, start, before, count) ->
                            callbacks.onAmountTextChanged(finalI, String.valueOf(s)))
                    .addTo(this);
            i++;
        }
    }

    public interface AddNewBillEpoxyControllerCallbacks {
        void onAmountTextChanged(int i, String string);
    }
}
