package com.flyavis.android.ui.checklist;

import com.airbnb.epoxy.AutoModel;
import com.airbnb.epoxy.TypedEpoxyController;
import com.flyavis.android.R;
import com.flyavis.android.TitleItemBindingModel_;
import com.flyavis.android.data.database.ToDoList;

import java.util.List;

public class CheckListEpoxyController extends TypedEpoxyController<List<ToDoList>> {
    @AutoModel
    TitleItemBindingModel_ titleItemBindingModel;

    @Override
    protected void buildModels(List<ToDoList> data) {
        titleItemBindingModel
                .title(R.string.title_checklist)
                .addTo(this);
    }
}
