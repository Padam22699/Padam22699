package com.example.e_shoping.RoomDatabase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [productModel::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    companion object{
        private var database:AppDatabase?=null
        private var DATABASE_NAME="eshoppin"

        @Synchronized
        fun getinstance(context: Context):AppDatabase{
            if(database == null){
              database= Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                    DATABASE_NAME
              ).allowMainThreadQueries()
                  .fallbackToDestructiveMigration()
                  .build()
            }
            return database!!
        }
    }



    abstract fun productdao():productDao
}