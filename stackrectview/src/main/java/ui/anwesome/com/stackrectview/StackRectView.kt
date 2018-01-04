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
        fun draw(canvas:Canvas,paint:Paint) {
            x = -size+(destX+size)
            canvas.save()
            canvas.translate(x,y)
            canvas.drawRoundRect(RectF(0f,0f,size,size),size/10,size/10,paint)
            canvas.restore()
        }
        fun update(stopcb:(Float)->Unit) {

        }
        fun startUpdating(startcb:()->Unit) {

        }
    }
}