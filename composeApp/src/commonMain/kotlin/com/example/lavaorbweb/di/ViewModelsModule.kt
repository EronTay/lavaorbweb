package com.example.lavaorbweb.di

import com.example.lavaorbweb.ui.game.GameViewModel
import com.example.lavaorbweb.ui.score.ScoreViewModel
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val viewModelsModule = module {
    factoryOf(::GameViewModel)
    factoryOf(::ScoreViewModel)
}