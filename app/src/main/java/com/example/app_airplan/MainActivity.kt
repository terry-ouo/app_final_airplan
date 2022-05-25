package com.example.app_airplan

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.SurfaceHolder
import com.example.app_airplan.databinding.ActivityMainBinding
import android.view.SurfaceView
import android.view.View
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity(), View.OnTouchListener {
    lateinit var binding: ActivityMainBinding
    lateinit var job: Job
    var game_status = false
    lateinit var mper: MediaPlayer
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        mper = MediaPlayer()

        binding.airplan.setOnTouchListener(this)
        binding.bottom.setOnClickListener {
            game_status = !game_status
            job = GlobalScope.launch(Dispatchers.Main) {
                while (game_status) {
                    delay(25)
                    val canvas: Canvas = binding.surfaceView.holder.lockCanvas()
                    binding.surfaceView.drawSomething(canvas)
                    binding.surfaceView.holder.unlockCanvasAndPost(canvas)
                    if (!game_status) {

                    }
                    if (game_status) {

                    }
                }
            }
        }

    }

    override fun onPause() {
        super.onPause()
        job.cancel()
    }

    override fun onResume() {
        super.onResume()
        if (!game_status) {
            job = GlobalScope.launch(Dispatchers.Main) {
                delay(25)
                val canvas: Canvas = binding.surfaceView.holder.lockCanvas()
                binding.surfaceView.drawSomething(canvas)
                binding.surfaceView.holder.unlockCanvasAndPost(canvas)
            }
        }
    }

    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        if (event?.action == MotionEvent.ACTION_MOVE) {
            v?.x = 20f
            v?.y = event.rawY - v!!.height / 2
        }

        return true
    }
    fun Start_play(v:View){
        mper = MediaPlayer.create(this,R.raw.shoot)
        mper.start()
    }


}

class MySurfaceView(context: Context?, attrs: AttributeSet?) : SurfaceView(context, attrs),
    SurfaceHolder.Callback2 {
    var BGmoveX: Int = 0
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

    fun drawSomething(canvas: Canvas) {
        //canvas.drawBitmap(BG, 0f, 0f, null)
        BGmoveX--
        var BGnewX: Int = BG.width + BGmoveX

        // 如果已捲動整張圖，則重新開始
        if (BGnewX <= 0) {
            BGmoveX = 0
            // only need one draw
            canvas.drawBitmap(BG, BGmoveX.toFloat(), 0f, null)
        } else {
            // need to draw original and wrap
            canvas.drawBitmap(BG, BGmoveX.toFloat(), 0f, null)
            canvas.drawBitmap(BG, BGnewX.toFloat(), 0f, null)
        }

    }

    override fun surfaceChanged(p0: SurfaceHolder, p1: Int, p2: Int, p3: Int) {

    }

    override fun surfaceDestroyed(p0: SurfaceHolder) {

    }

    override fun surfaceRedrawNeeded(p0: SurfaceHolder) {

    }
}