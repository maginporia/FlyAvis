package com.flyavis.android.ui.billdetail;

import com.airbnb.epoxy.AutoModel;
import com.airbnb.epoxy.TypedEpoxyController;
import com.flyavis.android.BillDetailCheckoutFinishBindingModel_;
import com.flyavis.android.BillDetailTitleBindingModel_;
import com.flyavis.android.BillDetailTransactionBindingModel_;
import com.flyavis.android.BilllDetailCheckoutItemBindingModel_;
import com.flyavis.android.data.database.Bill;

public class BillDetailEpoxyController extends TypedEpoxyController<Bill> {

    @AutoModel
    BillDetailCheckoutFinishBindingModel_ billDetailCheckoutFinishBindingModel;


    @Override
    protected void buildModels(Bill data) {
        //最新的交易
        new BillDetailTitleBindingModel_()
                .id("new")
                .title("最新的交易")
                .expend("顯示全部")
                .addTo(this);

        new BillDetailTransactionBindingModel_()
                .id("transaction")
                .addTo(this);


        //結算
        new BillDetailTitleBindingModel_()
                .id("conclusion")
                .title("結算")
                .addTo(this);

        new BilllDetailCheckoutItemBindingModel_()
                .id("item")
                .addTo(this);

        billDetailCheckoutFinishBindingModel
                .addTo(this);
    }
}
