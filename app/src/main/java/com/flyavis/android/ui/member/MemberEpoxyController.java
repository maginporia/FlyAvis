package com.flyavis.android.ui.member;

import com.airbnb.epoxy.AutoModel;
import com.airbnb.epoxy.TypedEpoxyController;
import com.flyavis.android.MemberItemAddBindingModel_;
import com.flyavis.android.MemberItemBindingModel_;
import com.flyavis.android.data.database.TeamMember;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;
import java.util.Objects;

public class MemberEpoxyController extends TypedEpoxyController<List<TeamMember>> {

    private MemberCallbacks callbacks;

    @AutoModel
    MemberItemAddBindingModel_ memberItemAddBindingModel;

    MemberEpoxyController(MemberCallbacks callbacks) {
        this.callbacks = callbacks;
    }

    @Override
    protected void buildModels(List<TeamMember> data) {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        new MemberItemBindingModel_()
                .id(0)
                .name(Objects.requireNonNull(auth.getCurrentUser()).getDisplayName())
                .email(auth.getCurrentUser().getEmail())
                .image(auth.getCurrentUser().getPhotoUrl())
                .addTo(this);
        for (TeamMember teamMember : data) {
            new MemberItemBindingModel_()
                    .id(teamMember.getMemberId())
                    .name(teamMember.getUserName())
                    .email(teamMember.getUserEmail())
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
