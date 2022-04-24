package com.mitm.android.grocerysharedlist.di

import android.content.Context
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.mitm.android.grocerysharedlist.core.AppSettings
import com.mitm.android.grocerysharedlist.domain.repository.RepositoryGrocery
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideAppSettings(@ApplicationContext context: Context): AppSettings {
        return AppSettings(context)
    }

    @Provides
    @Singleton
    fun provideRemoteDB(): FirebaseFirestore {
        return Firebase.firestore
    }

    @Provides
    @Singleton
    fun provideGroceryRepository(
        dbRemote: FirebaseFirestore,
        appSettings: AppSettings,
        @ApplicationContext context: Context
    ): RepositoryGrocery {
        return RepositoryGrocery(dbRemote, appSettings, context)
    }
}