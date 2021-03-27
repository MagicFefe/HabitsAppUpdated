package com.swaptech.habitstwo

import android.view.inputmethod.InputMethodManager
import android.widget.Button
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.viewModelScope
import com.swaptech.habitstwo.listhabits.HabitsListViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

fun Button.getButtonColor(): String? {
    return this.tag?.toString()
}

inline fun FragmentManager.inTransaction(block: (FragmentTransaction) -> Unit) {
    val transaction = this.beginTransaction()
    block(transaction)
    transaction.commit()
}

fun Fragment.hide() {
    this.activity?.currentFocus.let {
        val inputMethodManager = view?.let { it1 -> ContextCompat.getSystemService(it1.context, InputMethodManager::class.java) }
        inputMethodManager?.hideSoftInputFromWindow(it?.windowToken, 0)
    }
}