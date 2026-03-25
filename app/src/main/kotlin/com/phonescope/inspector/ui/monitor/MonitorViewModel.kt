package com.phonescope.inspector.ui.monitor

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.phonescope.inspector.data.jni.NativeEngine
import com.phonescope.inspector.domain.model.MemorySample
import com.phonescope.inspector.domain.model.ThermalSample
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import javax.inject.Inject

data class MonitorUiState(
    val isRunning: Boolean = true,
    // Current values
    val memUsedPercent: Float = 0f,
    val memUsedMb: Long = 0,
    val memTotalMb: Long = 0,
    val maxTemp: Float = 0f,
    val tempLabel: String = "",
    // History buffers (last 60 samples = 1 minute)
    val memHistory: List<Float> = emptyList(),
    val tempHistory: List<Float> = emptyList(),
)

@HiltViewModel
class MonitorViewModel @Inject constructor() : ViewModel() {

    private val json = Json { ignoreUnknownKeys = true; coerceInputValues = true }
    private val maxHistory = 60

    private val _uiState = MutableStateFlow(MonitorUiState())
    val uiState: StateFlow<MonitorUiState> = _uiState.asStateFlow()

    private val memBuffer = mutableListOf<Float>()
    private val tempBuffer = mutableListOf<Float>()

    init {
        startSampling()
    }

    private fun startSampling() {
        viewModelScope.launch(Dispatchers.IO) {
            while (true) {
                delay(1000)
                try {
                    val memSample = json.decodeFromString<MemorySample>(NativeEngine.nativeSampleMemory())
                    val thermalSample = json.decodeFromString<ThermalSample>(NativeEngine.nativeSampleThermal())

                    val totalMb = memSample.memTotal / 1024
                    val usedMb = (memSample.memTotal - memSample.memAvailable) / 1024
                    val memPct = if (memSample.memTotal > 0)
                        ((memSample.memTotal - memSample.memAvailable).toFloat() / memSample.memTotal * 100f)
                    else 0f

                    val hotZone = thermalSample.temperatures.maxByOrNull { it.temp }
                    val tempC = (hotZone?.temp ?: 0L) / 1000f

                    memBuffer.add(memPct.coerceIn(0f, 100f))
                    if (memBuffer.size > maxHistory) memBuffer.removeFirst()

                    tempBuffer.add(tempC.coerceIn(0f, 120f))
                    if (tempBuffer.size > maxHistory) tempBuffer.removeFirst()

                    _uiState.value = MonitorUiState(
                        isRunning = true,
                        memUsedPercent = memPct.coerceIn(0f, 100f),
                        memUsedMb = usedMb,
                        memTotalMb = totalMb,
                        maxTemp = tempC,
                        tempLabel = hotZone?.type ?: "",
                        memHistory = memBuffer.toList(),
                        tempHistory = tempBuffer.toList()
                    )
                } catch (_: Exception) { }
            }
        }
    }
}
