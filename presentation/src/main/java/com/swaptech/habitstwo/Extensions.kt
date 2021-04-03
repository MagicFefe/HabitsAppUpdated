package com.swaptech.habitstwo

import android.text.Editable
import android.text.TextWatcher
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction

fun Button.getButtonColorFromTag(): String? {
    return this.tag?.toString()
}

inline fun FragmentManager.inTransaction(block: (FragmentTransaction) -> Unit) {
    val transaction = this.beginTransaction()
    block(transaction)
    transaction.commit()
}

fun Fragment.hideKeyboard() {
    this.activity?.currentFocus.let {
        val inputMethodManager = view?.let { it1 -> ContextCompat.getSystemService(it1.context, InputMethodManager::class.java) }
        inputMethodManager?.hideSoftInputFromWindow(it?.windowToken, 0)
    }
}
fun EditText.onTextChangedListener(block: (Editable?) -> Unit) {
    this.addTextChangedListener(object: TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

        }

        override fun afterTextChanged(s: Editable?) {
            block(s)
        }
    })
}
