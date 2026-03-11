package com.example.lavaorbweb.utils

interface LocalStorageManager {
    fun saveItem(item: String, key: String)
    fun getItem(key: String): String
}