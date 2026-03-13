package com.example.lavaorbweb.ui.game

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.ImageBitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lavaorbweb.data.Repository
import com.example.lavaorbweb.model.Orb
import kotlin.math.min
import kotlin.math.sin
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class GameViewModel(
    private val repository: Repository,
) : ViewModel() {

    private val _isGameOver = MutableStateFlow(false)
    val isGameOver = _isGameOver.asStateFlow()

    private val _score = MutableStateFlow(0)
    val score = _score.asStateFlow()

    private val _isFalling = MutableStateFlow(false)
    val isFalling = _isFalling.asStateFlow()

    private val _orb = MutableStateFlow(Orb())
    val orb = _orb.asStateFlow()

    private val _isGameInitialized = MutableStateFlow(false)
    val isGameInitialized = _isGameInitialized.asStateFlow()

    private lateinit var _orbImgage: ImageBitmap
    private lateinit var _ringImage: ImageBitmap
    var ringPosition = Offset(0f, 0f)
    private lateinit var _dotImage: ImageBitmap
    var pivotingPosition = Offset(0f, 0f)
    private lateinit var _ropeImage: ImageBitmap
    var ropePosition = Offset(0f, 0f)

    var orbSize = Size.Zero
    var ringSize = Size.Zero
    var dotSize = Size.Zero
    var ropeSize = Size.Zero
    private var scale = 1f

    private var canvasSize = Size.Zero
    private var angle = 0f
    private val maxAngle = 60f
    private var angularSpeed = 0.5f

    fun frame() {
        if (!_isGameInitialized.value) return
        if (_isFalling.value) {
            val radians = angle * (kotlin.math.PI / 180.0)

            _orb.value = _orb.value.copy(
                x = (ropePosition.x + (ropeSize.height *
                        -sin(radians).toFloat()) +
                        (ropeSize.width / 2f)) - (orbSize.width / 2f),
                y = _orb.value.y + 10f * scale
            )
            checkCollision()
        } else {
            angle += angularSpeed

            if (angle > maxAngle) {
                angle = maxAngle
                angularSpeed *= -1f
            } else if (angle < -maxAngle) {
                angle = -maxAngle
                angularSpeed *= -1f
            }

            _orb.value = _orb.value.copy(
                rotation = angle,
            )
        }
    }

    fun initGame(size: Size, orbImage: ImageBitmap, ringImage: ImageBitmap, dotImage: ImageBitmap, ropeImage: ImageBitmap) {
        canvasSize = size
        _orbImgage = orbImage
        _ringImage = ringImage
        _ropeImage = ropeImage
        _dotImage = dotImage

        val minDimension = min(size.width, size.height)
        scale = (minDimension * 0.15f) / orbImage.width
        orbSize = Size(orbImage.width * scale, orbImage.height * scale)
        ringSize = Size(ringImage.width * scale, ringImage.height * scale)
        dotSize = Size(dotImage.width * scale, dotImage.height * scale)
        ropeSize = Size(ropeImage.width * scale, ropeImage.height * scale)

        pivotingPosition = Offset(
            x = canvasSize.width / 2f,
            y = canvasSize.height * 0.1f
        )
        ropePosition = Offset(
            x = canvasSize.width / 2f - ropeSize.width / 2f,
            y = pivotingPosition.y + dotSize.height / 2.5f
        )

        ringPosition = Offset(
            x = canvasSize.width / 2f - ringSize.width / 2f,
            y = canvasSize.height * 0.9f - ringSize.height / 2f
        )

        angle = -maxAngle

        val orbX = ropePosition.x + ropeSize.width / 2f - orbSize.width / 2f
        val orbY = ropePosition.y + ropeSize.height - orbSize.height / 2.5f

        _orb.value = _orb.value.copy(
            bitmap = orbImage,
            x = orbX,
            y = orbY
        )
        _isGameInitialized.value = true
    }

    fun dropOrb() {
        if (!_isGameInitialized.value) return
        _isFalling.value = true
        val radians = angle * (kotlin.math.PI / 180.0)
        _orb.value = _orb.value.copy(
            x = ropePosition.x + ropeSize.height *
                    -sin(radians).toFloat() +
                    ropeSize.width / 2f - orbSize.width / 2f,
            y = _orb.value.y + 10f * scale
        )
    }

    fun checkCollision() {
        val chickenRect = Rect(
            center = Offset(
                x = _orb.value.x + orbSize.width / 2f,
                y = _orb.value.y + orbSize.height / 2f
            ),
            radius = orbSize.width / 3f
        )
        val ringRect = Rect(
            center = Offset(
                x = ringPosition.x + ringSize.width / 2f,
                y = ringPosition.y + ringSize.height / 2f
            ),
            radius = ringSize.width / 3f
        )
        if (chickenRect.overlaps(ringRect)) {
            _isFalling.value = false
            val newScore = _score.value + 1
            _score.value = newScore
            angularSpeed += if (angularSpeed > 0) 0.2f else -0.2f
            _orb.value = _orb.value.copy(
                x = ropePosition.x + ropeSize.width / 2f - orbSize.width / 2f,
                y = ropePosition.y + ropeSize.height - orbSize.height / 2f
            )
        }
        if (_orb.value.y > canvasSize.height) {
            _isGameOver.value = true
        }
    }

    fun resetGame() {
        _isGameOver.value = false
        _score.value = 0
        _isFalling.value = false
        angle = -maxAngle
        angularSpeed = 0.5f
        initGame(canvasSize, _orbImgage, _ringImage, _dotImage, _ropeImage)
    }

    fun saveResult() {
        _score.value
        viewModelScope.launch {
            repository.saveResult(_score.value)
        }
    }
}
