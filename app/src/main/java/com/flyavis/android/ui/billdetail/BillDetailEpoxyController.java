package com.flyavis.android.ui.billdetail;

import com.airbnb.epoxy.AutoModel;
import com.airbnb.epoxy.TypedEpoxyController;
import com.flyavis.android.BillDetailCheckoutFinishBindingModel_;
import com.flyavis.android.BillDetailCheckoutTitleBindingModel_;
import com.flyavis.android.BillDetailTransactionBindingModel_;
import com.flyavis.android.BilllDetailCheckoutItemBindingModel_;
import com.flyavis.android.data.database.Bill;

public class BillDetailEpoxyController extends TypedEpoxyController<Bill> {
    @AutoModel
    BillDetailTransactionBindingModel_ billDetailTransactionBindingModel;
    @AutoModel
    BillDetailCheckoutTitleBindingModel_ billDetailCheckoutTitleBindingModel;
    @AutoModel
    BillDetailCheckoutFinishBindingModel_ billDetailCheckoutFinishBindingModel;

    @Override
    protected void buildModels(Bill data) {
        billDetailTransactionBindingModel
                .addTo(this);

        billDetailCheckoutTitleBindingModel
                .addTo(this);

        new BilllDetailCheckoutItemBindingModel_()
                .id("item")
                .addTo(this);

        billDetailCheckoutFinishBindingModel
                .addTo(this);
    }
}
