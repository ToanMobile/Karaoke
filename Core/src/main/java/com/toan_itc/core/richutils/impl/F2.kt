package com.toan_itc.core.richutils.impl

interface F2<in A, in B> {
    operator fun invoke(`object`: A, object1: B)
}