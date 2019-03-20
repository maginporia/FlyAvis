package com.flyavis.android.ui.mytrips;

import android.view.View;

import com.airbnb.epoxy.AutoModel;
import com.airbnb.epoxy.EpoxyModel;
import com.airbnb.epoxy.EpoxyViewHolder;
import com.airbnb.epoxy.TypedEpoxyController;
import com.flyavis.android.MyTripsItemBindingModel_;
import com.flyavis.android.R;
import com.flyavis.android.TitleItemBindingModel_;
import com.flyavis.android.data.database.MyTrip;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class MyTripsEpoxyController extends TypedEpoxyController<List<MyTrip>> {
    private final MyTripsCallbacks callbacks;
    /*
     * xml檔模組化RecyclerView內容
     * 使用package-info.java產生Model
     * */
    @AutoModel//Auto create and assign a unique id
            TitleItemBindingModel_ titleItemBindingModel;
    private boolean deleteState = false;
    private int clickedCount = 0;
    private List<View> needToResetList = new ArrayList<>();
    private Set<Integer> selectedPotion = new HashSet<>();

    MyTripsEpoxyController(MyTripsCallbacks callbacks) {
        this.callbacks = callbacks;
    }

    @Override
    protected void buildModels(List<MyTrip> list) {
        resetViews();
        //組合RecyclerView內容
        titleItemBindingModel
                .title(R.string.title_my_trips)
                .addTo(this);

        if (list != null) {
            for (MyTrip myTrip : list) {
                new MyTripsItemBindingModel_()
                        //All models must set a unique id.

                        .id(myTrip.getMyTripId())
                        .title(myTrip.getTripName())
                        .date(String.valueOf(myTrip.getStartDate()) + " ~ " + myTrip.getEndDate())
                        .clickListener((model, parentView, clickedView, position) -> {
                            if (!deleteState) {
                                //set args in nav_graph.xml first
                                callbacks.onMyTripItemClick((int) model.id(), model.date());
                            } else {
                                setClicked(clickedView);
                                addToList(position);
                                callbacks.onMyTripItemLongClick((int) (model.id()), deleteState
                                        , clickedCount);
                            }
                        })
                        .longClickListener((model, parentView, clickedView, position) -> {
                            setClicked(clickedView);
                            addToList(position);
                            callbacks.onMyTripItemLongClick((int) (model.id()), deleteState
                                    , clickedCount);
                            return true;
                        })
                        .editClickListener((model, parentView, clickedView, position) -> {
                            callbacks.onEditTripClick((int) model.id());
                        })
                        .addPersonClickListener((model, parentView, clickedView, position) -> {
                            callbacks.onAddPersonClick((int) model.id());
                        })
                        .addTo(this);
            }
        }
    }

    private void addToList(int position) {
        if (selectedPotion.contains(position)) {
            selectedPotion.remove(position);
        } else {
            selectedPotion.add(position);
        }
    }

    private void setClickedStyle(View view) {
        view.setHovered(true);
        view.setElevation(16f);
    }

    private void setUnClickedStyle(View view) {
        view.setHovered(false);
        view.setElevation(4f);
    }

    private void resetViews() {
        if (needToResetList.size() != 0) {
            for (View view : needToResetList) {
                setUnClickedStyle(view);
            }
            needToResetList.clear();
            deleteState = false;
            clickedCount = 0;
        }
    }

    private void setClicked(View clickedView) {
        if (clickedView.getElevation() != 16f) {
            setClickedStyle(clickedView);
            needToResetList.add(clickedView);
            deleteState = true;
            clickedCount++;
        } else {
            setUnClickedStyle(clickedView);
            clickedCount--;
            if (clickedCount == 0) deleteState = false;
        }
    }

    //用來重設正確的選取位置
    @Override
    protected void onModelBound(@NonNull EpoxyViewHolder holder, @NonNull EpoxyModel<?> boundModel
            , int position, @Nullable EpoxyModel<?> previouslyBoundModel) {
        super.onModelBound(holder, boundModel, position, previouslyBoundModel);
        if (selectedPotion.contains(position)) {
            setClickedStyle(holder.itemView);
        } else {
            setUnClickedStyle(holder.itemView);
        }
    }

    public interface MyTripsCallbacks {
        void onMyTripItemClick(int id, String dateRange);

        void onMyTripItemLongClick(int id, boolean deleteState, int clickedCount);

        void onEditTripClick(int id);

        void onAddPersonClick(int id);
    }
}
