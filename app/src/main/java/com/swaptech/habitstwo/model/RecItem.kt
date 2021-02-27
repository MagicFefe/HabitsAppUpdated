package com.swaptech.habitstwo.model

data class RecItem constructor(
        var name: String? = null,
        var description: String? = null,
        var priority: String? = null,
        var typeOfHabit: String? = null,
        var periodicity: String? = null,
        var countOfExecsOfHabit: Int = -1,
        var frequencyOfExecs: Int = -1,
        var color: Int = -1,
        var id: Int = -1
        ) {

}



