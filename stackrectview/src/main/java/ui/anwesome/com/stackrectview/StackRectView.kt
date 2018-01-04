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
}