package com.dolezal.image

import android.app.Application
import com.dolezal.image.lib.ImageToothpickModule
import toothpick.Toothpick

@ExperimentalUnsignedTypes
class ImageApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        Toothpick.openScope(applicationContext).apply {
            installModules(ImageToothpickModule, AppToothpickModule)
        }
    }
}