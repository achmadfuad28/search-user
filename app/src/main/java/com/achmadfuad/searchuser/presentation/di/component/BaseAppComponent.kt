package com.achmadfuad.searchuser.presentation.di.component

import com.achmadfuad.searchuser.presentation.di.module.baseAppModule
import com.achmadfuad.searchuser.presentation.di.module.viewModelModule
import org.koin.core.module.Module

val baseAppComponent: List<Module> = listOf(
        baseAppModule,
        viewModelModule
)