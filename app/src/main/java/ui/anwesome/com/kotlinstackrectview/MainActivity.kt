package ui.anwesome.com.kotlinstackrectview

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import ui.anwesome.com.stackrectview.StackRectView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        StackRectView.create(this)
    }
}
