package com.flyavis.android.ui.billdetail;

import android.net.Uri;

import com.airbnb.epoxy.AutoModel;
import com.airbnb.epoxy.Typed3EpoxyController;
import com.flyavis.android.BillDetailCheckoutFinishBindingModel_;
import com.flyavis.android.BillDetailCheckoutItemBindingModel_;
import com.flyavis.android.BillDetailTitleBindingModel_;
import com.flyavis.android.BillDetailTransactionBindingModel_;
import com.flyavis.android.CardBottomBindingModel_;
import com.flyavis.android.data.database.Bill;
import com.flyavis.android.data.database.BillDetail;
import com.flyavis.android.data.database.TeamMember;
import com.flyavis.android.util.FlyAvisUtils;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import timber.log.Timber;

public class BillDetailEpoxyController extends
        Typed3EpoxyController<List<Bill>, List<BillDetail>, List<TeamMember>> {

    @AutoModel
    BillDetailCheckoutFinishBindingModel_ billDetailCheckoutFinishBindingModel;

    @Override
    protected void buildModels(List<Bill> data, List<BillDetail> billDetails, List<TeamMember> teamMembers) {

        FirebaseAuth auth = FirebaseAuth.getInstance();
        //最新的交易
        new BillDetailTitleBindingModel_()
                .id("new")
                .title("最新的交易")
                .expend("顯示全部")
                .addTo(this);

        int count = 0;
        int amounts = 0;
        Uri imageUri = null;
        String payer = null;
        if (data.size() > 0) {
            int size = data.size();
            if (size > 3) {
                size = 3;
            }
            for (int i = 0; i < size; i++) {
                count = 0;
                amounts = 0;
                for (BillDetail billDetail : billDetails) {
                    if (billDetail.getBillId() == data.get(i).getBillId()) {
                        if (billDetail.getAmount() > 0) {
                            Timber.d(String.valueOf(billDetail.getAmount()));
                            amounts = amounts + billDetail.getAmount();
                            count++;
                            for (TeamMember teamMember : teamMembers) {
                                if (teamMember.getUserId().equals(billDetail.getUserId())) {
                                    payer = teamMember.getUserName();
                                    imageUri = teamMember.getUserPhoto();
                                }
                            }
                        }
                    }
                }
                if (count > 1) {
                    payer = "多人";
                }
                new BillDetailTransactionBindingModel_()
                        .id("transaction", data.get(i).getBillId())
                        .image(imageUri)
                        .cost(amounts + "$")
                        .costDate(FlyAvisUtils.longToString(data.get(i).getCostDate()))
                        .costTitle(data.get(i).getCostTitle())
                        .payer(payer + "支付")
                        .addTo(this);
            }
        } else {
            new BillDetailTransactionBindingModel_()
                    .id("transaction", "demo")
                    .image(auth.getCurrentUser().getPhotoUrl())
                    .cost("???$")
                    .costDate(FlyAvisUtils.longToString(System.currentTimeMillis()))
                    .costTitle("這是一個Demo")
                    .payer("加入新的支出吧")
                    .addTo(this);
        }

        new CardBottomBindingModel_()
                .id("bottom")
                .addTo(this);


        //結算
        new BillDetailTitleBindingModel_()
                .id("conclusion")
                .title("結算")
                .addTo(this);

        int amounts2 = 0;
        int joinCount = 0;
        List<String> joinList;
        List<String> payerList = null;
        if (data.size() > 0) {

            Map<String, Integer> map = new HashMap<>();
            map.put(auth.getCurrentUser().getUid(), 0);
            for (TeamMember teamMember : teamMembers) {
                map.put(teamMember.getUserId(), 0);
            }

            for (Bill bill : data) {
                amounts2 = 0;
                joinList = new ArrayList<>();
                payerList = new ArrayList<>();
                for (BillDetail billDetail : billDetails) {
                    if (bill.getBillId() == billDetail.getBillId()) {
                        if (billDetail.getAmount() > 0) {
                            amounts2 = amounts2 + billDetail.getAmount();
                            payerList.add(billDetail.getUserId());
                        }
                        if (billDetail.getJoin()) {
                            joinList.add(billDetail.getUserId());
                        }
                    }
                }
                //分帳
                for (String s : joinList) {
                    if (!payerList.contains(s)) {
                        for (String p : payerList) {
                            if ((amounts2 / joinList.size()) <= map.get(p)) {
                                map.put(p, map.get(p) - (amounts2 / joinList.size()));
                            } else {
                                map.put(s, map.get(s) + (amounts2 / joinList.size()));
                            }
                        }


                    }
                }
            }
            for (String s : map.keySet()) {
                String payerName = null;
                String receiverName = null;
                Uri payerImage = null;
                Uri receiverImage = null;
                for (TeamMember teamMember : teamMembers) {
                    if (s.equals(teamMember.getUserId())) {
                        payerName = teamMember.getUserName();
                        payerImage = teamMember.getUserPhoto();
                    }
                    if (payerList != null) {
                        for (String p : payerList) {
                            if (map.get(p) > 0) {
                                receiverName = teamMember.getUserName();
                                receiverImage = teamMember.getUserPhoto();
                            }
                        }
                    }
                }
                //去除0元項目
                if (map.get(s) != 0) {
                    new BillDetailCheckoutItemBindingModel_()
                            .id("checkout", s)
                            .amount(map.get(s) + "$")
                            .payerName(payerName)
                            .payerImage(payerImage)
//                            .receiverImage(receiverImage)
                            .receiverName(receiverName)
                            .addTo(this);
                }

            }
        } else {
            new BillDetailCheckoutItemBindingModel_()
                    .id("checkout", "demo")
                    .amount("???$")
                    .payerName("財神爺")
//                    .payerImage()
                    .receiverImage(auth.getCurrentUser().getPhotoUrl())
                    .receiverName(auth.getCurrentUser().getDisplayName())
                    .addTo(this);
        }
        billDetailCheckoutFinishBindingModel
                .addTo(this);
    }
}
