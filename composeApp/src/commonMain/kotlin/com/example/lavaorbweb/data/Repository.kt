package com.example.lavaorbweb.data

import com.example.lavaorbweb.model.GameRecord
import com.example.lavaorbweb.utils.LocalStorageManager
import kotlinx.serialization.json.Json

class Repository(
    private val storage: LocalStorageManager
) {
    companion object {
        private const val RECORDS_KEY = "game_records"
    }
    fun saveResult(score: Int) {
        val recordsJson = storage.getItem(RECORDS_KEY)
        val records = try {
            if (recordsJson.isEmpty()) mutableListOf()
            else Json.decodeFromString<List<GameRecord>>(recordsJson).toMutableList()
        } catch (e: Exception) {
            mutableListOf()
        }

        if (records.size >= 6) {
            records.sortByDescending { it.score }
            records.removeLastOrNull()
        }
        records.add(GameRecord(score))
        storage.saveItem(Json.encodeToString(records), RECORDS_KEY)
    }

    fun getRecords(): List<GameRecord> {
        val recordsJson = storage.getItem(RECORDS_KEY)
        if (recordsJson.isEmpty()) return emptyList()
        return try {
            Json.decodeFromString<List<GameRecord>>(recordsJson).sortedByDescending { it.score }
        } catch (e: Exception) {
            emptyList()
        }
    }
}