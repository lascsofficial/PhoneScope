package com.phonescope.inspector.ui.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.phonescope.inspector.data.jni.NativeEngine
import com.phonescope.inspector.domain.model.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import javax.inject.Inject

/**
 * UI State for the Dashboard screen.
 */
data class DashboardUiState(
    val isLoading: Boolean = true,
    // Deep inspection data
    val cpuData: CpuData? = null,
    val memoryData: MemoryData? = null,
    val gpuData: GpuData? = null,
    val thermalData: ThermalData? = null,
    val kernelData: KernelData? = null,
    val storageData: StorageData? = null,
    val aiData: AiData? = null,
    // Live samples
    val memorySample: MemorySample? = null,
    val thermalSample: ThermalSample? = null,
    val cpuSample: CpuSample? = null,
    // Derived
    val cpuUsagePercent: Float = 0f,
    val memUsagePercent: Float = 0f,
    val gpuUsagePercent: Float = 0f,
    val maxThermalTemp: Float = 0f,
)

@HiltViewModel
class DashboardViewModel @Inject constructor() : ViewModel() {

    private val json = Json { ignoreUnknownKeys = true; coerceInputValues = true }

    private val _uiState = MutableStateFlow(DashboardUiState())
    val uiState: StateFlow<DashboardUiState> = _uiState.asStateFlow()

    init {
        loadInspectionData()
        startLiveSampling()
    }

    private fun loadInspectionData() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val cpu = runCatching { json.decodeFromString<CpuData>(NativeEngine.nativeGetCpuInfo()) }.getOrNull()
                val mem = runCatching { json.decodeFromString<MemoryData>(NativeEngine.nativeGetMemoryInfo()) }.getOrNull()
                val gpu = runCatching { json.decodeFromString<GpuData>(NativeEngine.nativeGetGpuInfo()) }.getOrNull()
                val thermal = runCatching { json.decodeFromString<ThermalData>(NativeEngine.nativeGetThermalZones()) }.getOrNull()
                val kernel = runCatching { json.decodeFromString<KernelData>(NativeEngine.nativeGetKernelInfo()) }.getOrNull()
                val ai = runCatching { json.decodeFromString<AiData>(NativeEngine.nativeGetNnapiInfo()) }.getOrNull()

                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    cpuData = cpu,
                    memoryData = mem,
                    gpuData = gpu,
                    thermalData = thermal,
                    kernelData = kernel,
                    aiData = ai,
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(isLoading = false)
            }
        }
    }

    private fun startLiveSampling() {
        viewModelScope.launch(Dispatchers.IO) {
            while (true) {
                delay(1000)
                try {
                    val memSample = runCatching { json.decodeFromString<MemorySample>(NativeEngine.nativeSampleMemory()) }.getOrNull()
                    val thermalSample = runCatching { json.decodeFromString<ThermalSample>(NativeEngine.nativeSampleThermal()) }.getOrNull()
                    val cpuSample = runCatching { json.decodeFromString<CpuSample>(NativeEngine.nativeSampleCpu()) }.getOrNull()

                    val memPercent = if (memSample != null && memSample.memTotal > 0) {
                        ((memSample.memTotal - memSample.memAvailable).toFloat() / memSample.memTotal * 100f)
                    } else 0f

                    val maxTemp = thermalSample?.temperatures
                        ?.maxByOrNull { it.temp }?.temp?.let { it / 1000f } ?: 0f

                    _uiState.value = _uiState.value.copy(
                        memorySample = memSample,
                        thermalSample = thermalSample,
                        cpuSample = cpuSample,
                        memUsagePercent = memPercent.coerceIn(0f, 100f),
                        maxThermalTemp = maxTemp,
                    )
                } catch (_: Exception) { }
            }
        }
    }
}
