package com.example.stickerviewexample.sticker

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.os.Parcelable
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.widget.AppCompatImageView
import androidx.constraintlayout.widget.ConstraintLayout

open class StickerView(
    context: Context,
    attrs: AttributeSet?
) : ConstraintLayout(context, attrs), View.OnTouchListener {

    var touchEnabled = false
    private var mLastTouchX = 0f
    private var mLastTouchY = 0f
    private var mPosX = 0f
    private var mPosY = 0f
    private lateinit var btnMove: Button

    init {
        Log.d("PJS", "init")

        // 버튼 초기화 및 설정
        btnMove = Button(context)
        btnMove.text = "Move"
        btnMove.setBackgroundColor(Color.TRANSPARENT)
        btnMove.setTextColor(Color.WHITE)
        btnMove.setPadding(16, 8, 16, 8)

        val lp = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
        lp.bottomToBottom = LayoutParams.PARENT_ID
        lp.endToEnd = LayoutParams.PARENT_ID
        lp.setMargins(0, 0, 32, 32) // 오른쪽 아래 여백 설정
        addView(btnMove, lp)

        setOnTouchListener { _, event ->
            if (touchEnabled) {
                for (i in 0 until childCount) {
                    getChildAt(i).dispatchTouchEvent(event)
                }
            }
            true
        }

        layoutParams = LayoutParams(
            500, 500
        )

        onHideBtn()
    }

    private fun onShowBtn() {
        touchEnabled = true
        btnMove.visibility = View.VISIBLE
    }

    private fun onHideBtn() {
        touchEnabled = false
        btnMove.visibility = View.GONE
    }

    override fun onTouch(view: View, event: MotionEvent): Boolean {
        val x = event.rawX
        val y = event.rawY

        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                if (!touchEnabled) {
                    onShowBtn()
                    return false
                }
                mLastTouchX = event.rawX
                mLastTouchY = event.rawY
            }
            MotionEvent.ACTION_MOVE -> {
                val deltaX = x - mLastTouchX
                val deltaY = y - mLastTouchY
                mPosX += deltaX
                mPosY += deltaY
                val parent = parent as View
                val maxX = parent.width - width
                val maxY = parent.height - height
                if (mPosX < 0) {
                    mPosX = 0f
                } else if (mPosX > maxX) {
                    mPosX = maxX.toFloat()
                }
                if (mPosY < 0) {
                    mPosY = 0f
                } else if (mPosY > maxY) {
                    mPosY = maxY.toFloat()
                }
                var newX = mPosX + event.rawX - mLastTouchX
                var newY = mPosY + event.rawY - mLastTouchY
                mLastTouchX = event.rawX
                mLastTouchY = event.rawY
                view.x = newX
                view.y = newY
            }
            MotionEvent.ACTION_UP -> {
                postDelayed({ onHideBtn() }, 3_000L)
            }
        }

        return true
    }


    override fun onViewAdded(view: View?) {
        super.onViewAdded(view)
        Log.d("PJS", "onViewAdded")
    }

    override fun onSaveInstanceState(): Parcelable {
        val bundle = Bundle()
        bundle.putParcelable("instanceState", super.onSaveInstanceState())
        bundle.putFloat("posX", mPosX)
        bundle.putFloat("posY", mPosY)
        return bundle
    }

    override fun onRestoreInstanceState(state: Parcelable?) {
        if (state is Bundle) {
            mPosX = state.getFloat("posX")
            mPosY = state.getFloat("posY")
            state.getParcelable<Parcelable>("instanceState")
        }
        super.onRestoreInstanceState(state)
        x = mPosX
        y = mPosY
    }
}
