package com.example.lavaorbweb

import com.example.lavaorbweb.utils.GetParams
import kotlinx.browser.window
import org.w3c.dom.url.URLSearchParams

class GetHashParams: GetParams {
    @OptIn(ExperimentalWasmJsInterop::class)
    override fun getParams(): Map<String, String> {
        val search = window.location.search
        if (search.isEmpty()) return emptyMap()
        val allParams = search.substringAfter("?")
        val separatedParams = allParams.split("&")
        val paramsMap = separatedParams.map {
            val (key, value) = it.split("=")
            key to value
        }.toMap()

        println(paramsMap)

        return paramsMap
    }
}