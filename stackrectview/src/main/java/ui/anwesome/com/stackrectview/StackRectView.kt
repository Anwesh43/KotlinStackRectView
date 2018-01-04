package ui.anwesome.com.stackrectview

/**
 * Created by anweshmishra on 04/01/18.
 */
import android.view.*
import android.content.*
import android.graphics.*
class StackRectView(ctx:Context):View(ctx) {
    override fun onDraw(canvas:Canvas) {

    }
    override fun onTouchEvent(event:MotionEvent):Boolean {
        when(event.action) {
            MotionEvent.ACTION_DOWN -> {

            }
        }
        return true
    }
    data class StackRect(var i:Int,var size:Float,var maxW:Float,var x:Float = -size,var y:Float = 0f,var destX:Float = maxW - (i+1)*size) {
        val state = State()
        fun draw(canvas:Canvas,paint:Paint) {
            x = -size+(destX+size)*state.scale
            canvas.save()
            canvas.translate(x,y)
            canvas.drawRoundRect(RectF(0f,0f,size,size),size/10,size/10,paint)
            canvas.restore()
        }
        fun update(stopcb:(Float)->Unit) {
            state.update(stopcb)
        }
        fun startUpdating(startcb:()->Unit) {
            state.startUpdating(startcb)
        }
    }
    data class State(var dir:Int = 0,var scale:Float = 0f,var prevScale:Float = 0f) {
        fun update(stopcb:(Float)->Unit) {
            scale += 0.1f*dir
            if(Math.abs(scale - prevScale) > 1) {
                scale = prevScale + dir
                dir = 0
                prevScale = scale
                stopcb(scale)
            }
        }
        fun startUpdating(startcb:()->Unit) {
            if(dir == 0) {
                dir = (1-2*scale.toInt())
                startcb()
            }
        }

    }
}