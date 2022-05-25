package com.example.app_airplan

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.view.SurfaceHolder
import com.example.app_airplan.databinding.ActivityMainBinding
import android.view.SurfaceView
class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }
}
class MySurfaceview(context: Context?, attrs: AttributeSet?) : SurfaceView(context, attrs),SurfaceHolder.Callback2 {
    var surfaceHolder: SurfaceHolder = holder
    var BG: Bitmap = BitmapFactory.decodeResource(resources, R.drawable.background)

    init {
        surfaceHolder.addCallback(this)
    }
    override fun surfaceCreated(p0: SurfaceHolder) {
        var canvas: Canvas = surfaceHolder.lockCanvas()
        drawSomething(canvas)
        surfaceHolder.unlockCanvasAndPost(canvas)


    }

    private fun drawSomething(canvas: Canvas) {
        canvas.drawBitmap(BG, 0f, 0f, null)
    }

    override fun surfaceChanged(p0: SurfaceHolder, p1: Int, p2: Int, p3: Int) {

    }

    override fun surfaceDestroyed(p0: SurfaceHolder) {

    }

    override fun surfaceRedrawNeeded(p0: SurfaceHolder) {

    }
}