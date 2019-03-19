package com.flyavis.android.data.database;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(foreignKeys = @ForeignKey(entity = Plan.class,
        parentColumns = "planId",
        childColumns = "planId",
        onDelete = ForeignKey.CASCADE),
        indices = {@Index("planId")})
public class ToDoList {

    @PrimaryKey(autoGenerate = true)
    private Integer toDoId;

    private String toDoItem;

    private Boolean done;

    private Integer planId;

    public ToDoList(Integer toDoId, String toDoItem, Boolean done, Integer planId) {
        this.toDoId = toDoId;
        this.toDoItem = toDoItem;
        this.done = done;
        this.planId = planId;
    }

    @Ignore
    public ToDoList() {
    }

    public Integer getToDoId() {
        return toDoId;
    }

    public void setToDoId(Integer toDoId) {
        this.toDoId = toDoId;
    }

    public String getToDoItem() {
        return toDoItem;
    }

    public void setToDoItem(String toDoItem) {
        this.toDoItem = toDoItem;
    }

    public Boolean getDone() {
        return done;
    }

    public void setDone(Boolean done) {
        this.done = done;
    }

    public Integer getPlanId() {
        return planId;
    }

    public void setPlanId(Integer planId) {
        this.planId = planId;
    }
}
