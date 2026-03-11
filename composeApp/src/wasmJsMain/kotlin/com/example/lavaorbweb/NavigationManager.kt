package com.example.lavaorbweb

import com.example.lavaorbweb.utils.PageManager
import kotlinx.browser.window

class NavigationManager: PageManager {
    override fun navigateTo(url: String) {
        window.location.href = url
    }
}