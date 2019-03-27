package com.flyavis.android.ui.addnewbill;

import android.annotation.SuppressLint;

import com.flyavis.android.data.BillDetailRepository;
import com.flyavis.android.data.BillRepository;
import com.flyavis.android.data.PlanRepository;
import com.flyavis.android.data.TeamMemberRepository;
import com.flyavis.android.data.database.Bill;
import com.flyavis.android.data.database.BillDetail;
import com.flyavis.android.data.database.SimplifyPlan;
import com.flyavis.android.data.database.TeamMember;

import java.util.List;

import javax.inject.Inject;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class AddNewBillViewModel extends ViewModel {
    private PlanRepository planRepository;
    private TeamMemberRepository teamMemberRepository;
    private BillRepository billRepository;
    private BillDetailRepository billDetailRepository;
    private LiveData<List<SimplifyPlan>> planLiveData;
    private LiveData<List<TeamMember>> memberLiveData;
    private MutableLiveData<Long> billId = new MutableLiveData<>();

    @Inject
    AddNewBillViewModel(PlanRepository planRepository, TeamMemberRepository teamMemberRepository
            , BillRepository billRepository, BillDetailRepository billDetailRepository) {
        this.planRepository = planRepository;
        this.teamMemberRepository = teamMemberRepository;
        this.billRepository = billRepository;
        this.billDetailRepository = billDetailRepository;
    }

    LiveData<List<SimplifyPlan>> getSimplifyPlan() {
        return planLiveData;
    }

    @SuppressLint("CheckResult")
    void insertNewBill(Bill bill) {
        billId.setValue(billRepository.insertNewBill(bill).subscribeOn(Schedulers.io()).blockingGet());
    }

    void insertNewBillDetail(BillDetail billDetail) {
        billDetail.setBillId(billId.getValue().intValue());
        Completable.fromAction(() -> billDetailRepository.insertNewBillDetail(billDetail))
                .subscribeOn(Schedulers.io())
                .subscribe();
    }

    LiveData<List<TeamMember>> getTeamMembers() {
        return memberLiveData;
    }

    void setTripId(int tripId) {
        Flowable<List<SimplifyPlan>> planFlowable = planRepository.getSimplifyPlan(tripId)
                .observeOn(AndroidSchedulers.mainThread());
        planLiveData = LiveDataReactiveStreams.fromPublisher(planFlowable);
        Flowable<List<TeamMember>> memberFlowable = teamMemberRepository.getTeamMembers(tripId)
                .observeOn(AndroidSchedulers.mainThread());
        memberLiveData = LiveDataReactiveStreams.fromPublisher(memberFlowable);
    }

}
