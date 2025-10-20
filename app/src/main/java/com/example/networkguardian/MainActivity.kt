val filter = IntentFilter().apply {
    addAction(Intent.ACTION_SCREEN_OFF)
    addAction(Intent.ACTION_SCREEN_ON)
}
registerReceiver(ScreenReceiver(), filter)
