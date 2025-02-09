package com.example.myapplication

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.exception.ViewNotFoundCustomException

private const val ITEMS = "items"
private const val PORTRAIT_COLUMN_COUNT = 3
private const val LANDSCAPE_COLUMN_COUNT = 4

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var addButton: Button
    private lateinit var adapter: RectanglesAdapter

    private val viewModel: RectanglesViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()

        addButton.setOnClickListener {
            val nextElement = viewModel.items.lastOrNull()?.let { it + 1 } ?: 0
            viewModel.addItem(nextElement)
            adapter.setItems(viewModel.items)
            Log.d("MainActivity", "Item added, total items: ${viewModel.items.size}")
        }
    }

    private fun init() {
        adapter = RectanglesAdapter(viewModel)
        adapter.setItems(viewModel.items)

        addButton =
            findViewById(R.id.addButton) ?: throw ViewNotFoundCustomException(R.id.addButton)

        recyclerView = findViewById<RecyclerView?>(R.id.recyclerView)?.apply {
            adapter = this@MainActivity.adapter
            layoutManager = GridLayoutManager(this@MainActivity, getSpanCount())
        } ?: throw ViewNotFoundCustomException(R.id.recyclerView)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putIntegerArrayList(ITEMS, ArrayList(viewModel.items))
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        with(viewModel) {
            items.clear()
            items += savedInstanceState.getIntegerArrayList(ITEMS) ?: listOf()
        }
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        (recyclerView.layoutManager as GridLayoutManager).spanCount = getSpanCount()
    }

    private fun getSpanCount(): Int =
        when (resources.configuration.orientation) {
            Configuration.ORIENTATION_LANDSCAPE -> LANDSCAPE_COLUMN_COUNT
            else -> PORTRAIT_COLUMN_COUNT
        }
}
