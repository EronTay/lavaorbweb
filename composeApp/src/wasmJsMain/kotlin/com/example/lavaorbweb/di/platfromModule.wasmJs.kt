package com.example.lavaorbweb.di

import com.example.lavaorbweb.GetHashParams
import com.example.lavaorbweb.LocalLocalStorageManager
import com.example.lavaorbweb.NavigationManager
import com.example.lavaorbweb.utils.GetParams
import com.example.lavaorbweb.utils.LocalStorageManager
import com.example.lavaorbweb.utils.PageManager
import org.koin.dsl.module

actual val platformModule = module {
    single<GetParams> { GetHashParams() }
    single<LocalStorageManager> { LocalLocalStorageManager() }
    single<PageManager> { NavigationManager() }
}