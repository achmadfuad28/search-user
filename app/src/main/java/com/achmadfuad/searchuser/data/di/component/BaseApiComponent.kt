package com.achmadfuad.searchuser.data.di.component

import com.achmadfuad.searchuser.data.di.module.searchUsersRepositoryModule
import org.koin.core.module.Module

val baseApiComponent: List<Module> = listOf(
        applicationApiModule,
        searchUsersRepositoryModule)