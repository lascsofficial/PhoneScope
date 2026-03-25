package com.phonescope.inspector.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.IBinder
import androidx.core.app.NotificationCompat

/**
 * Foreground service for the real-time flight recorder.
 * Samples CPU, Memory, Thermal, Battery, and GPU every 1 second.
 * Stub implementation — full logic in Sprint S13.
 */
class PhoneScopeMonitorService : Service() {

    companion object {
        private const val NOTIFICATION_ID = 1001
        private const val CHANNEL_ID = "phonescope_monitor"
        private const val CHANNEL_NAME = "PhoneScope Monitor"
    }

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val notification = buildNotification()
        startForeground(NOTIFICATION_ID, notification)

        // TODO: Sprint S13 — Start coroutine scope with 1-second ticker
        // CpuSampler, MemorySampler, ThermalSampler, BatterySampler, GpuSampler
        // Ring buffer (3600 samples = 1 hour)
        // Room write every 60 samples (1 min aggregate)
        // ThrottleDetector alert system

        return START_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onDestroy() {
        // TODO: Sprint S13 — Cancel coroutine scope
        super.onDestroy()
    }

    private fun createNotificationChannel() {
        val channel = NotificationChannel(
            CHANNEL_ID,
            CHANNEL_NAME,
            NotificationManager.IMPORTANCE_LOW,
        ).apply {
            description = "PhoneScope is monitoring your device"
            setShowBadge(false)
        }
        val notificationManager = getSystemService(NotificationManager::class.java)
        notificationManager.createNotificationChannel(channel)
    }

    private fun buildNotification(): Notification {
        return NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("PhoneScope")
            .setContentText("Monitoring device performance…")
            .setSmallIcon(android.R.drawable.ic_menu_info_details) // TODO: custom icon
            .setOngoing(true)
            .setSilent(true)
            .build()
    }
}
