package com.flyavis.android.ui.billdetail;

import com.airbnb.epoxy.AutoModel;
import com.airbnb.epoxy.TypedEpoxyController;
import com.flyavis.android.BillDetailCheckoutFinishBindingModel_;
import com.flyavis.android.BillDetailCheckoutItemBindingModel_;
import com.flyavis.android.BillDetailTitleBindingModel_;
import com.flyavis.android.BillDetailTransactionBindingModel_;
import com.flyavis.android.data.database.Bill;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

public class BillDetailEpoxyController extends TypedEpoxyController<List<Bill>> {

    @AutoModel
    BillDetailCheckoutFinishBindingModel_ billDetailCheckoutFinishBindingModel;

    @Override
    protected void buildModels(List<Bill> data) {

        FirebaseAuth auth = FirebaseAuth.getInstance();
        //最新的交易
        new BillDetailTitleBindingModel_()
                .id("new")
                .title("最新的交易")
                .expend("顯示全部")
                .addTo(this);

        new BillDetailTransactionBindingModel_()
                .id("transaction")
                .image(auth.getCurrentUser().getPhotoUrl())
                .cost(50 + "$")
                .costDate("2019.03.24 08:30")
                .costTitle("飲料")
                .payer("Felix Wang 支付")
                .addTo(this);

        for (Bill bill : data) {
            new BillDetailTransactionBindingModel_()
                    .id("transaction", bill.getCostId())
                    .image(auth.getCurrentUser().getPhotoUrl())
                    .cost(bill.getSingleCost() + "$")
                    .costDate("2019.03.24 22:26")
                    .costTitle(bill.getCostTitle())
                    .payer("Felix Wang 支付")
                    .addTo(this);
        }
        //結算
        new BillDetailTitleBindingModel_()
                .id("conclusion")
                .title("結算")
                .addTo(this);
        for (Bill bill : data) {
            new BillDetailCheckoutItemBindingModel_()
                    .id("item")
                    .amount(bill.getSinglePayer() + "$")
                    .payerName("MaginPoria")
                    .receiverImage(auth.getCurrentUser().getPhotoUrl())
                    .receiverName(auth.getCurrentUser().getDisplayName())
                    .addTo(this);
        }
        billDetailCheckoutFinishBindingModel
                .addTo(this);
    }
}
