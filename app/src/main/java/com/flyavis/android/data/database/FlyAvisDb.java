package com.flyavis.android.data.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@TypeConverters({DateConverter.class, TimeConverter.class, UriConverter.class})
@Database(entities = {MyTrip.class, Plan.class, Bill.class, BillDetail.class, TeamMember.class
        , ToDoList.class}, version = 1)
public abstract class FlyAvisDb extends RoomDatabase {
    public abstract MyTripDao myTripDao();

    public abstract PlanDao planningDao();

    public abstract BillDao billDao();

    public abstract BillDetailDao billDetailDao();

    public abstract TeamMemberDao teamMemberDao();

    public abstract ToDoListDao toDoListDao();
}
