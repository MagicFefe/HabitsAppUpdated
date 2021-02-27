package com.swaptech.habitstwo

import android.widget.Button

fun Button.getButtonColor(): String? {
    return this.tag?.toString()
}