package com.flyavis.android.ui.member;

import com.airbnb.epoxy.AutoModel;
import com.airbnb.epoxy.TypedEpoxyController;
import com.flyavis.android.MemberItemAddBindingModel_;
import com.flyavis.android.MemberItemBindingModel_;
import com.flyavis.android.data.database.TeamMember;

import java.util.List;

public class MemberEpoxyController extends TypedEpoxyController<List<TeamMember>> {

    private MemberCallbacks callbacks;

    @AutoModel
    MemberItemAddBindingModel_ memberItemAddBindingModel;

    MemberEpoxyController(MemberCallbacks callbacks) {
        this.callbacks = callbacks;
    }

    @Override
    protected void buildModels(List<TeamMember> data) {

        for (TeamMember teamMember : data) {
            new MemberItemBindingModel_()
                    .id(teamMember.getMemberId())
                    .name(teamMember.getUserName())
                    .email(teamMember.getUserEmail())
                    .image(teamMember.getUserPhoto())
                    .addTo(this);
        }
        memberItemAddBindingModel
                .clickListener((model, parentView, clickedView, position) -> {
                    callbacks.onAddMemberClick((int) model.id());
                })
                .addTo(this);
    }

    public interface MemberCallbacks {
        void onAddMemberClick(int id);
    }
}
