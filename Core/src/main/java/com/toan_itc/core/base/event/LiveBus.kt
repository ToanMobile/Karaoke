package com.toan_itc.core.base.event

import androidx.collection.ArrayMap
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer


@Suppress("UNCHECKED_CAST")
class LiveBus {
    private val mEventMap: MutableMap<Class<out Event>, LiveEvent<out Event>>

    init {
        mEventMap = ArrayMap()
    }

    fun <T : Event> observe(lifecycleOwner: LifecycleOwner, eventClass: Class<T>, observer: Observer<T>) {
        var liveEvent: LiveEvent<T>? = mEventMap[eventClass] as LiveEvent<T>?
        if (liveEvent == null) {
            liveEvent = initLiveEvent(eventClass)
        }
        liveEvent.observe(lifecycleOwner, observer)
    }

    fun <T : Event> send(event: T) {
        var liveEvent: LiveEvent<T>? = mEventMap[event.javaClass] as LiveEvent<T>?
        if (liveEvent == null) {
            liveEvent = initLiveEvent(event.javaClass)
        }
        liveEvent.setValue(event)
    }


    private fun <T : Event> initLiveEvent(eventClass: Class<T>): LiveEvent<T> {
        val liveEvent = LiveEvent<T>()
        mEventMap[eventClass] = liveEvent
        return liveEvent
    }
}
