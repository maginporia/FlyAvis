package com.flyavis.android.data.database;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(foreignKeys = @ForeignKey(entity = Bill.class,
        parentColumns = "billId",
        childColumns = "billId",
        onDelete = ForeignKey.CASCADE),
        indices = {@Index("billDetailId")})
public class BillDetail {
    @PrimaryKey(autoGenerate = true)
    private int billDetailId;
    private String userId;
    private int amount;
    private Boolean join;
    private int weight;
    private int billId;

    @Ignore
    public BillDetail() {
    }

    @Ignore
    public BillDetail(String userId, int amount) {
        this.userId = userId;
        this.amount = amount;
    }

    @Ignore
    public BillDetail(String userId, int amount, Boolean join) {
        this.userId = userId;
        this.amount = amount;
        this.join = join;
    }

    public BillDetail(int billDetailId, String userId, int amount, Boolean join, int weight, int billId) {
        this.billDetailId = billDetailId;
        this.userId = userId;
        this.amount = amount;
        this.join = join;
        this.weight = weight;
        this.billId = billId;
    }

    public int getBillDetailId() {
        return billDetailId;
    }

    public void setBillDetailId(int billDetailId) {
        this.billDetailId = billDetailId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Boolean getJoin() {
        return join;
    }

    public void setJoin(Boolean join) {
        this.join = join;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getBillId() {
        return billId;
    }

    public void setBillId(int billId) {
        this.billId = billId;
    }
}
