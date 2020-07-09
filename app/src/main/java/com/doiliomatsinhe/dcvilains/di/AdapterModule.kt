package com.doiliomatsinhe.dcvilains.di

import android.content.Context
import android.widget.Toast
import com.doiliomatsinhe.dcvilains.adapter.VillainAdapter
import com.doiliomatsinhe.dcvilains.adapter.VillainClickListener
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ApplicationContext

@InstallIn(ActivityComponent::class)
@Module
object AdapterModule {

    @Provides
    fun provideAdapter(@ApplicationContext appContext: Context): VillainAdapter {
        return VillainAdapter(VillainClickListener {
            Toast.makeText(appContext, "Clicked: ${it.name}", Toast.LENGTH_SHORT).show()
        })
    }

}