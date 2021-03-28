package com.swaptech.habitstwo.listhabits

import androidx.lifecycle.ViewModel

interface FragmentWithViewModel<T: ViewModel> {
    var viewModel: T
}