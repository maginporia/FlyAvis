package com.flyavis.android.ui.billdetail;

import com.flyavis.android.data.BillDetailRepository;
import com.flyavis.android.data.BillRepository;
import com.flyavis.android.data.TeamMemberRepository;
import com.flyavis.android.data.database.Bill;
import com.flyavis.android.data.database.BillDetail;
import com.flyavis.android.data.database.TeamMember;

import java.util.List;

import javax.inject.Inject;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.ViewModel;

public class BillDetailViewModel extends ViewModel {
    private BillRepository billRepository;
    private BillDetailRepository billDetailRepository;
    private TeamMemberRepository teamMemberRepository;

    @Inject
    BillDetailViewModel(BillRepository billRepository, BillDetailRepository billDetailRepository
            , TeamMemberRepository teamMemberRepository) {
        this.billRepository = billRepository;
        this.billDetailRepository = billDetailRepository;
        this.teamMemberRepository = teamMemberRepository;
    }

    LiveData<List<Bill>> getBills(int tripId) {
        return LiveDataReactiveStreams.fromPublisher(billRepository.getBills(tripId));
    }

    LiveData<List<BillDetail>> getBillDetails(int tripId) {
        return LiveDataReactiveStreams.fromPublisher(billDetailRepository.getBillDetail(tripId));
    }

    LiveData<List<TeamMember>> getTeamMembers(int tripId) {
        return LiveDataReactiveStreams.fromPublisher(teamMemberRepository.getTeamMembers(tripId));
    }

}
