package ui.anwesome.com.stackrectview

/**
 * Created by anweshmishra on 04/01/18.
 */
import android.view.*
import android.content.*
import android.graphics.*
import java.util.concurrent.ConcurrentLinkedQueue

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
        fun update(stopcb:(Int,Float)->Unit) {
            state.update{
                stopcb(i,it)
            }
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
    data class StackRectContainer(var w:Float,var h:Float,var n:Int = 5) {
        val stackRects:ConcurrentLinkedQueue<StackRect> = ConcurrentLinkedQueue()
        val containerState = ContainerState(n)
        init {
            for(i in 0..n-1) {
                stackRects.add(StackRect(i,Math.min(w,h)/10,w))
            }
        }
        fun draw(canvas:Canvas,paint:Paint) {
            stackRects.forEach {
                it.draw(canvas,paint)
            }
        }
        fun update(stopcb:(Int,Float)->Unit) {
            containerState.executeFn {
                stackRects.at(it)?.update({i,scale ->
                    containerState.increment()
                    stopcb(i,scale)
                })
            }
        }
        fun startUpdating(startcb:()->Unit) {
            containerState.executeFn {
                stackRects.at(it)?.startUpdating(startcb)
            }
        }
    }
    data class ContainerState(var n:Int,var j:Int = 0,var dir:Int = 1) {
        fun increment() {
            j+=dir
            if(j == n || j == -1) {
                dir *= -1
                j += dir
            }
        }
        fun executeFn(cb:(Int)->Unit) {
            cb(j)
        }
    }
    data class StackRectRenderer(var view:StackRectView,var time:Int = 0) {
        var container:StackRectContainer?=null
        val animator = Animator(view)
        fun render(canvas:Canvas,paint:Paint) {
            if(time == 0) {
                val w = canvas.width.toFloat()
                val h = canvas.height.toFloat()
                container = StackRectContainer(w,h)
            }
            container?.draw(canvas,paint)
            time++
            animator.update{
                container?.update{i,scale ->
                    animator.stop()
                }
            }
        }
        fun startUpdating() {
            container?.startUpdating {
                animator.startUpdating()
            }
        }
    }
    data class Animator(var view:StackRectView,var animated:Boolean = false) {
        fun update(updatecb:()->Unit) {
            if(animated) {
                updatecb()
                try {
                    Thread.sleep(50)
                    view.invalidate()
                }
                catch(ex:Exception) {

                }
            }
        }
        fun stop() {
            if(animated) {
                animated = false
            }
        }
        fun startUpdating() {
            if(!animated) {
                animated = true
                view.postInvalidate()
            }
        }
    }
}
fun ConcurrentLinkedQueue<StackRectView.StackRect>.at(i:Int):StackRectView.StackRect? {
    var index = 0
    forEach {
        if(index == i) {
            return it
        }
        index++
    }
    return null
}