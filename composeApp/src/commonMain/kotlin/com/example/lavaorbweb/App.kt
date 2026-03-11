package com.example.lavaorbweb

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.lavaorbweb.di.platformModule
import com.example.lavaorbweb.di.dataModule
import com.example.lavaorbweb.di.viewModelsModule
import com.example.lavaorbweb.ui.navigation.GameNavigation
import com.example.lavaorbweb.ui.theme.LavaOrbTheme
import org.koin.compose.KoinApplication
import org.koin.core.module.Module
import org.koin.dsl.module

@Composable
@Preview
fun App() {
    KoinApplication(
        application = {
            modules(viewModelsModule, dataModule, platformModule)
        }
    ) {
        LavaOrbTheme {
            GameNavigation()
        }
    }
}