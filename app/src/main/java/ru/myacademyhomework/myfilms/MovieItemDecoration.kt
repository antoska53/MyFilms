package ru.myacademyhomework.myfilms

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MovieItemDecoration(private var offset: Int) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        val layoutParams :GridLayoutManager.LayoutParams = view.layoutParams as GridLayoutManager.LayoutParams
        if(layoutParams.spanIndex % 2 == 0){
            outRect.top = offset
            outRect.left = offset
            outRect.right = offset / 2
        }else{
            outRect.top = offset
            outRect.left = offset / 2
            outRect.right = offset
        }
    }
}