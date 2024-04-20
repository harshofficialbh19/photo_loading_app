package com.android.photoapp.common

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.net.URL
import java.util.concurrent.Callable
import java.util.concurrent.Executors

object CommonUtils {

    fun loadUrl(imageUrl: String): Bitmap? {
        val executor = Executors.newSingleThreadExecutor()

        val future = executor.submit(Callable<Bitmap?> {
            try {
                val stream = URL(imageUrl).openStream()
                BitmapFactory.decodeStream(stream)
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        })

        return future.get() // This will block until the result is available
    }

}