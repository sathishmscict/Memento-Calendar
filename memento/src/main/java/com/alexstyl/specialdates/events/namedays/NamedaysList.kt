package com.alexstyl.specialdates.events.namedays

import com.alexstyl.specialdates.date.Date
import java.text.Collator
import java.util.TreeSet
import kotlin.collections.ArrayList

class NamedaysList {

    private val namedays = ArrayList<MutableNamesInADate>()
    private val names = TreeSet<String>(Collator.getInstance())

    fun getNamedaysFor(date: Date): NamesInADate {
        val index = indexOf(date)
        return if (index != -1) {
            namedays[index]
        } else ArrayNamesInADate(date, ArrayList())
    }

    fun addNameday(date: Date, name: String) {
        val names = getOrCreateNameDateFor(date)

        names.addName(name)
        this.names.add(name)
    }

    private fun getOrCreateNameDateFor(date: Date): MutableNamesInADate {
        val index = indexOf(date)
        if (index == -1) {
            val namesInADate = ArrayNamesInADate(date, ArrayList())
            namedays.add(namesInADate)
            return namesInADate
        } else {
            return namedays[index]
        }
    }

    private fun indexOf(date: Date): Int {
        for (i in namedays.indices) {
            val inlistDate = namedays[i]
            val comparingDate = inlistDate.date
            if (isRecurringEvent(comparingDate) && areMatching(comparingDate, date)) {
                return i
            } else if (comparingDate == date) {
                return i
            }
        }
        return -1
    }

    private fun isRecurringEvent(date: Date): Boolean {
        return !date.hasYear()
    }

    private fun areMatching(date: Date, otherDate: Date): Boolean {
        return date.dayOfMonth == otherDate.dayOfMonth && date.month == otherDate.month
    }

    fun getNames(): ArrayList<String> {
        return ArrayList(names)
    }
}
