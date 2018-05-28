package com.toan_itc.core.base.view

import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import com.toan_itc.core.R
import java.util.*

/**
 * Created by ToanDev on 11/29/17.
 * Email:Huynhvantoan.itc@gmail.com
 */

class StatefulLayout
@JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : FrameLayout(context, attrs, defStyleAttr) {
    private var mInitialState: Int = 0
    private var mProgressLayoutId: Int = 0
    private var mOfflineLayoutId: Int = 0
    private var mEmptyLayoutId: Int = 0
    private val mInvisibleWhenHidden: Boolean
    lateinit var mContentLayoutList: MutableList<View>
    lateinit var mProgressLayout: View
    lateinit var mOfflineLayout: View
    lateinit var mEmptyLayout: View

    var state: Int = 0
        set(state) {
            field = state

            for (i in mContentLayoutList.indices) {
                mContentLayoutList[i].visibility = determineVisibility(state == CONTENT)
            }

            mProgressLayout.visibility = determineVisibility(state == PROGRESS)
            mOfflineLayout.visibility = determineVisibility(state == OFFLINE)
            mEmptyLayout.visibility = determineVisibility(state == EMPTY)

            if (mOnStateChangeListener != null) mOnStateChangeListener!!.onStateChange(this, state)
        }
    private var mOnStateChangeListener: OnStateChangeListener? = null

    interface OnStateChangeListener {
        fun onStateChange(view: View,state: Int)
    }


    init {

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.StatefulLayout)

        if (typedArray.hasValue(R.styleable.StatefulLayout_state)) {

            mInitialState = typedArray.getInt(R.styleable.StatefulLayout_state, CONTENT)
        }

        if (typedArray.hasValue(R.styleable.StatefulLayout_progressLayout) &&
                typedArray.hasValue(R.styleable.StatefulLayout_offlineLayout) &&
                typedArray.hasValue(R.styleable.StatefulLayout_emptyLayout)) {
            mProgressLayoutId = typedArray.getResourceId(R.styleable.StatefulLayout_progressLayout, 0)
            mOfflineLayoutId = typedArray.getResourceId(R.styleable.StatefulLayout_offlineLayout, 0)
            mEmptyLayoutId = typedArray.getResourceId(R.styleable.StatefulLayout_emptyLayout, 0)
        } else {
            throw IllegalArgumentException("Attributes progressLayout, offlineLayout and emptyLayout are mandatory")
        }

        mInvisibleWhenHidden = typedArray.getBoolean(R.styleable.StatefulLayout_invisibleWhenHidden, false)

        typedArray.recycle()
    }


    override fun onFinishInflate() {
        super.onFinishInflate()
        setupView()
    }


    fun showContent() {
        state = CONTENT
    }


    fun showProgress() {
        state = PROGRESS
    }


    fun showOffline() {
        state = OFFLINE
    }


    fun showEmpty() {
        state = EMPTY
    }


    fun setOnStateChangeListener(l: OnStateChangeListener) {
        mOnStateChangeListener = l
    }


    fun saveInstanceState(outState: Bundle) {
        outState.putInt(SAVED_STATE, state)
    }


    fun restoreInstanceState(savedInstanceState: Bundle?): Int {
        var state = CONTENT
        if (savedInstanceState != null && savedInstanceState.containsKey(SAVED_STATE)) {

            state = savedInstanceState.getInt(SAVED_STATE)
            state = state
        }
        return state
    }


    private fun setupView() {
        if (!isInEditMode) {
            mContentLayoutList = ArrayList()
            for (i in 0 until childCount) {
                mContentLayoutList.add(getChildAt(i))
            }

            mProgressLayout = LayoutInflater.from(context).inflate(mProgressLayoutId, this, false)
            mOfflineLayout = LayoutInflater.from(context).inflate(mOfflineLayoutId, this, false)
            mEmptyLayout = LayoutInflater.from(context).inflate(mEmptyLayoutId, this, false)

            addView(mProgressLayout)
            addView(mOfflineLayout)
            addView(mEmptyLayout)

            state = mInitialState
        }
    }


    private fun determineVisibility(visible: Boolean): Int {
        return if (visible) {
            View.VISIBLE
        } else {
            if (mInvisibleWhenHidden) {
                View.INVISIBLE
            } else {
                View.GONE
            }
        }
    }

    companion object {
        val CONTENT = 0
        val PROGRESS = 1
        val OFFLINE = 2
        val EMPTY = 3

        private val SAVED_STATE = "stateful_layout_state"
    }
}
