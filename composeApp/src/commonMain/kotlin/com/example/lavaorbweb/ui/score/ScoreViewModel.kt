package com.example.lavaorbweb.ui.score

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lavaorbweb.data.Repository
import com.example.lavaorbweb.model.GameRecord
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ScoreViewModel (
    val repository: Repository
): ViewModel() {
    private val _highestScore = MutableStateFlow<List<GameRecord>>(emptyList())
    val results = _highestScore.asStateFlow()

    init {
        getHighestScore()
    }

    fun getHighestScore() {
        viewModelScope.launch {
            _highestScore.value = repository.getRecords()
        }
    }
}