package com.tutorialsee.ecommer.modal;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface LoginDao {

    // LoginDatils
    @Insert
    void insertAll(Login[] login);
    @Query("select count(*)  from tbl_Login")
    int logincount();
    @Query("select id,UserId,Password from tbl_Login")
    List<Login> getLoginDatils();

    @Insert
    void insertProducts(Productdetails productDetails);

}
