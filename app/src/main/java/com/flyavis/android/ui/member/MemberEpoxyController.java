package com.flyavis.android.ui.member;

import com.airbnb.epoxy.AutoModel;
import com.airbnb.epoxy.TypedEpoxyController;
import com.flyavis.android.MemberItemAddBindingModel_;
import com.flyavis.android.MemberItemBindingModel_;
import com.flyavis.android.data.database.TeamMember;

import java.util.List;

public class MemberEpoxyController extends TypedEpoxyController<List<TeamMember>> {
    @AutoModel
    MemberItemAddBindingModel_ memberItemAddBindingModel;

    @Override
    protected void buildModels(List<TeamMember> data) {
        new MemberItemBindingModel_()
                .id(0)
                .name("Felix")
                .email("abc@gmail.com")
                .addTo(this);
        memberItemAddBindingModel
                .addTo(this);
    }
}
