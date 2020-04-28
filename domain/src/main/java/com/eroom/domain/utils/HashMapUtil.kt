package com.eroom.domain.utils


fun HashMap<Long, String>.getKeyFromValue(value: String): Long {
    for ((K, V) in this) {
        if (V == value) {
            return K
        }
        else if(value ==""){
            return -2L //dummy
        }
    }
    return -1L //dummy
}
