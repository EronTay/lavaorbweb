package com.example.lavaorbweb.model

import kotlin.time.Clock
import kotlinx.serialization.Serializable

@Serializable
data class GameRecord(
    val score: Int,
    val timestamp: Long = Clock.System.now().toEpochMilliseconds()
)