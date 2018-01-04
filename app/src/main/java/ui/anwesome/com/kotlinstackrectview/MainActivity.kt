package ui.anwesome.com.kotlinstackrectview

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import ui.anwesome.com.stackrectview.StackRectView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val view = StackRectView.create(this)
        view.addRectMovementListener({i ->
            Toast.makeText(this,"$i moved to left",Toast.LENGTH_SHORT).show()
        },{i->
            Toast.makeText(this,"$i moved to right",Toast.LENGTH_SHORT).show()
        })
    }
}
