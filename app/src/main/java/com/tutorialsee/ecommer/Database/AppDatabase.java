package com.tutorialsee.ecommer.Database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import com.tutorialsee.ecommer.modal.Login;
import com.tutorialsee.ecommer.modal.LoginDao;
import com.tutorialsee.ecommer.modal.Productdetails;

@Database(entities = {Login.class, Productdetails.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract LoginDao loginDao();
}