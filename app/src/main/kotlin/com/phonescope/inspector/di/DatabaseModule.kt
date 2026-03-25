package com.phonescope.inspector.di

import android.content.Context
import androidx.room.Room
import com.phonescope.inspector.data.local.AppDatabase
import com.phonescope.inspector.data.local.MonitorDao
import com.phonescope.inspector.data.local.ScanDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(
        @ApplicationContext context: Context
    ): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "phonescope.db"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideScanDao(db: AppDatabase): ScanDao = db.scanDao()

    @Provides
    fun provideMonitorDao(db: AppDatabase): MonitorDao = db.monitorDao()
}
