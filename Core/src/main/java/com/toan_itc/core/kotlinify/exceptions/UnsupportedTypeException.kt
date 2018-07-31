package com.toan_itc.core.kotlinify.exceptions

class UnsupportedTypeException(classType: String) :
        RuntimeException("SharedPreferences does not support $classType")