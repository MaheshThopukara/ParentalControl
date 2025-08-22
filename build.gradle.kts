// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    // Declare plugin versions only here (do not apply them)
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false
    alias(libs.plugins.hilt) apply false
    alias(libs.plugins.ksp) apply false
}