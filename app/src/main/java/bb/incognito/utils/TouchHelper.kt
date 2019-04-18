package bb.incognito.utils

import android.graphics.Canvas
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper

class TouchHelper : ItemTouchHelper.Callback() {

    override fun getMovementFlags(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder): Int {
        val dragFlags = 0//ItemTouchHelper.UP | ItemTouchHelper.DOWN;
        val swipeFlags = ItemTouchHelper.START or ItemTouchHelper.END
        return ItemTouchHelper.Callback.makeMovementFlags(dragFlags, swipeFlags)
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        //NoteAdapter.getInstance().onItemDismiss(viewHolder.getAdapterPosition());

    }

    override fun onMove(recyclerView: RecyclerView, source: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
        return if (source.itemViewType != target.itemViewType) {
            false
        } else true
        //NoteAdapter.getInstance().onItemMove(source.getAdapterPosition(), target.getAdapterPosition());
    }

    override fun onChildDraw(c: Canvas, recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean) {
        if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
            val alpha = ALPHA_FULL - Math.abs(dX) / viewHolder.itemView.width.toFloat()
            viewHolder.itemView.alpha = alpha
            viewHolder.itemView.translationX = dX
        } else {
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
        }
    }

    override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int) {
        if (actionState != ItemTouchHelper.ACTION_STATE_IDLE) {
            //            if (viewHolder instanceof NoteViewHolder) {
            //                // Let the view holder know that this item is being moved or dragged
            //                NoteViewHolder itemViewHolder = (NoteViewHolder) viewHolder;
            //                itemViewHolder.onItemSelected();
            //            }
        }

        super.onSelectedChanged(viewHolder, actionState)
    }

    override fun clearView(recyclerView: RecyclerView?, viewHolder: RecyclerView.ViewHolder) {
        super.clearView(recyclerView, viewHolder)

        viewHolder.itemView.alpha = ALPHA_FULL

        //        if (viewHolder instanceof NoteViewHolder) {
        //            // Tell the view holder it's time to restore the idle state
        //            NoteViewHolder itemViewHolder = (NoteViewHolder) viewHolder;
        //            itemViewHolder.onItemClear();
        //        }
    }

    companion object {

        val ALPHA_FULL = 1.0f
    }
}