package com.phonescope.inspector.ui.scan

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

data class ScanUiState(
    val phase: ScanPhase = ScanPhase.Idle,
    val progress: Float = 0f,
    val currentModule: String = "",
    // Results
    val cpuData: CpuData? = null,
    val memoryData: MemoryData? = null,
    val gpuData: GpuData? = null,
    val thermalData: ThermalData? = null,
    val kernelData: KernelData? = null,
    val storageData: StorageData? = null,
    val aiData: AiData? = null,
    val processCount: Int = 0,
)

enum class ScanPhase { Idle, Scanning, Complete }

@HiltViewModel
class ScanViewModel @Inject constructor() : ViewModel() {

    private val json = Json { ignoreUnknownKeys = true; coerceInputValues = true }

    private val _uiState = MutableStateFlow(ScanUiState())
    val uiState: StateFlow<ScanUiState> = _uiState.asStateFlow()

    fun startScan() {
        if (_uiState.value.phase == ScanPhase.Scanning) return

        viewModelScope.launch(Dispatchers.IO) {
            _uiState.value = ScanUiState(phase = ScanPhase.Scanning, progress = 0f)

            val modules = listOf(
                "CPU" to { json.decodeFromString<CpuData>(NativeEngine.nativeGetCpuInfo()) },
                "Memory" to { json.decodeFromString<MemoryData>(NativeEngine.nativeGetMemoryInfo()) },
                "GPU" to { json.decodeFromString<GpuData>(NativeEngine.nativeGetGpuInfo()) },
                "Thermal" to { json.decodeFromString<ThermalData>(NativeEngine.nativeGetThermalZones()) },
                "Kernel" to { json.decodeFromString<KernelData>(NativeEngine.nativeGetKernelInfo()) },
                "Storage" to { json.decodeFromString<StorageData>(NativeEngine.nativeRunStorageBenchmark()) },
                "AI/NPU" to { json.decodeFromString<AiData>(NativeEngine.nativeGetNnapiInfo()) },
            )

            modules.forEachIndexed { index, (name, action) ->
                _uiState.value = _uiState.value.copy(
                    currentModule = name,
                    progress = index.toFloat() / modules.size
                )
                delay(150) // brief visual pause so user sees progress

                try {
                    val result = action()
                    _uiState.value = when (name) {
                        "CPU" -> _uiState.value.copy(cpuData = result as CpuData)
                        "Memory" -> _uiState.value.copy(memoryData = result as MemoryData)
                        "GPU" -> _uiState.value.copy(gpuData = result as GpuData)
                        "Thermal" -> _uiState.value.copy(thermalData = result as ThermalData)
                        "Kernel" -> _uiState.value.copy(kernelData = result as KernelData)
                        "Storage" -> _uiState.value.copy(storageData = result as StorageData)
                        "AI/NPU" -> _uiState.value.copy(aiData = result as AiData)
                        else -> _uiState.value
                    }
                } catch (_: Exception) { }
            }

            // Process count
            try {
                val procs = json.decodeFromString<List<ProcessEntry>>(NativeEngine.nativeGetProcessList())
                _uiState.value = _uiState.value.copy(processCount = procs.size)
            } catch (_: Exception) { }

            _uiState.value = _uiState.value.copy(
                phase = ScanPhase.Complete,
                progress = 1f,
                currentModule = ""
            )
        }
    }
}
