package com.example.lavaorbweb.di

import com.example.lavaorbweb.data.Repository
import org.koin.dsl.module

val dataModule = module {
    single<Repository> { Repository(get()) }
}