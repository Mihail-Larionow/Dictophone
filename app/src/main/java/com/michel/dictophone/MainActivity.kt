package com.michel.dictophone

import android.content.ContextWrapper
import android.graphics.Canvas
import android.media.MediaRouter.SimpleCallback
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.michel.dictophone.adapters.RecyclerAdapter
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator
import java.io.File

class MainActivity : AppCompatActivity() {

    private val utils = Utils()
    private var permissionWasGranted = false
    private val recordCards: MutableList<RecordCard> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onResume() {
        super.onResume()
        if(!permissionWasGranted && utils.readPermissionGranted(this)) {
            permissionWasGranted = true
            init()
            Log.w("Resume", "Resumed")
        }
    }

    //Initialization
    private fun init() {
        val directory = getDirectory()
        val files = directory!!.listFiles()
        val recordButton = findViewById<View>(R.id.recordButton) as ImageView
        val durationTextView = findViewById<View>(R.id.durationText) as TextView
        val recycler = findViewById<View>(R.id.recyclerView) as RecyclerView
        val recorder = Recorder(resources, directory.path)
        recorder.loadData(files, recordCards)
        val adapter = RecyclerAdapter(recordCards, recorder, this)
        recycler.adapter = adapter

        val itemTouchHelper = ItemTouchHelper(getCallBack(adapter))
        itemTouchHelper.attachToRecyclerView(recycler)

        recorder.setRecordButton(recordButton, durationTextView, recordCards, adapter, this)
    }


    //Returns directory with files
    private fun getDirectory(): File? {
        val contextWrapper = ContextWrapper(applicationContext)
        return contextWrapper.getExternalFilesDir(Environment.DIRECTORY_MUSIC)
    }

    private fun getCallBack(adapter: RecyclerAdapter) : ItemTouchHelper.SimpleCallback {
        return object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                TODO("Not yet implemented")
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                recordCards[position].deleteRecord()
                recordCards.removeAt(position)
                adapter.notifyItemRemoved(position)
            }

            override fun onChildDraw(
                c: Canvas,
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                dX: Float,
                dY: Float,
                actionState: Int,
                isCurrentlyActive: Boolean
            ) {

                RecyclerViewSwipeDecorator.Builder(
                    c,
                    recyclerView,
                    viewHolder,
                    dX,
                    dY,
                    actionState,
                    isCurrentlyActive
                ).addSwipeLeftActionIcon(R.drawable.delete)
                    .setActionIconTint(resources.getColor(R.color.red))
                    .create()
                    .decorate()

                super.onChildDraw(
                    c,
                    recyclerView,
                    viewHolder,
                    dX,
                    dY,
                    actionState,
                    isCurrentlyActive
                )
            }

        }
    }
}