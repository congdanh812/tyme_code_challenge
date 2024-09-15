package dc.danh.tyme.code.challenge.utils

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

fun RecyclerView.addOnScrollListener(onEndReached: () -> Unit) {
    this.addOnScrollListener(object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            val layoutManager = recyclerView.layoutManager as? LinearLayoutManager
            if (layoutManager != null && layoutManager.findLastCompletelyVisibleItemPosition() == recyclerView.adapter?.itemCount?.minus(1)) {
                onEndReached()
            }
        }
    })
}
