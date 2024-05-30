package com.bitmavrick.lumolight.ui.screen.setting

sealed interface SettingUiEvent {
    data class UpdateSaveQuickActionSetting(val saveQuickAction : Boolean) : SettingUiEvent
    data class UpdateShowSosButtonPreference(val sosButtonPreference : Boolean) : SettingUiEvent
    data class UpdateThemeDialog(val visible: Boolean) : SettingUiEvent
    data class UpdateAppearance(val appearance: Appearance) : SettingUiEvent
    data class UpdateDynamicTheme(val dynamicTheme: Boolean) : SettingUiEvent
}