package com.example.lavaorbweb

import androidx.compose.runtime.key
import com.example.lavaorbweb.utils.LocalStorageManager
import kotlinx.browser.window
import org.w3c.dom.get
import org.w3c.dom.set

class LocalLocalStorageManager: LocalStorageManager {

    override fun saveItem(item: String, key: String) {
        window.localStorage[key] = item
    }

    override fun getItem(key: String): String {
        return window.localStorage[key] ?: ""
    }
}