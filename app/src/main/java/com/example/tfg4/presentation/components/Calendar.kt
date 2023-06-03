package com.example.tfg4.presentation.components

import android.app.DatePickerDialog
import android.widget.DatePicker
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import java.util.*


@Composable
fun Calendario(): MutableState<String> {
    val mContext = LocalContext . current

// Declaring integer values
// for year, month and day
    val mYear: Int
    val mMonth: Int
    val mDay: Int

// Initializing a Calendar
    val mCalendar = Calendar.getInstance()

// Fetching current year, month and day
    mYear = mCalendar.get(Calendar.YEAR)
    mMonth = mCalendar.get(Calendar.MONTH)
    mDay = mCalendar.get(Calendar.DAY_OF_MONTH)

    mCalendar.time = Date()

// Declaring a string value to
// store date in string format
    val mDate = remember { mutableStateOf("") }

// Declaring DatePickerDialog and setting
// initial values as current values (present year, month and day)
    val mDatePickerDialog = DatePickerDialog(
        mContext,
        { _: DatePicker, mYear: Int, mMonth: Int, mDayOfMonth: Int ->
            mDate.value = "$mDayOfMonth/${mMonth + 1}/$mYear"
        }, mYear, mMonth, mDay
    )

    return mDate

}
