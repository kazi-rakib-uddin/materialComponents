package com.example.materialcomponent

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.util.Pair
import com.example.materialcomponent.databinding.ActivityMainBinding
import com.google.android.material.checkbox.MaterialCheckBox
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointBackward
import com.google.android.material.datepicker.DateValidatorPointForward
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.MaterialTimePicker.INPUT_MODE_CLOCK
import com.google.android.material.timepicker.MaterialTimePicker.INPUT_MODE_KEYBOARD
import com.google.android.material.timepicker.TimeFormat
import java.text.SimpleDateFormat
import java.util.Date

class MainActivity : AppCompatActivity() {

    private var _binding : ActivityMainBinding? = null
    private val binding get() = _binding!!
    private var isUpdatingChildren = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnDatepicker.setOnClickListener {

            val disiablePreviousDate =
                CalendarConstraints.Builder()
                    .setValidator(
                        DateValidatorPointForward.now())

            val disiableNextDate =
                CalendarConstraints.Builder()
                    .setValidator(
                        DateValidatorPointBackward.now())

            val datePicker =
                MaterialDatePicker.Builder.datePicker()
                    .setTitleText("Select date")
                    .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                    .setCalendarConstraints(disiablePreviousDate.build())
                    .build()

            datePicker.show(supportFragmentManager,"")




            datePicker.addOnPositiveButtonClickListener {

                binding.btnDatepicker.text = convertLongToDate(it)
            }


        }



        binding.btnDialog.setOnClickListener {

            MaterialAlertDialogBuilder(this)
                .setTitle("Basic dialog title")
                .setMessage("There are many variations" +
                        " of passages of Lorem Ipsum available, but the majority have " +
                        "suffered alteration in some form, by injected humour")
                .setNeutralButton("Cancel") { dialog, which ->
                    // Respond to neutral button press
                }
                .setNegativeButton("Decline") { dialog, which ->
                    // Respond to negative button press
                }
                .setPositiveButton("Accept") { dialog, which ->
                    // Respond to positive button press
                }
                .show()
        }



        binding.btnTimepicker.setOnClickListener {

            var picker =
                MaterialTimePicker.Builder()
                    .setInputMode(INPUT_MODE_CLOCK) //INPUT_MODE_KEYBOARD AND INPUT_MODE_CLOCK
                    .setTimeFormat(TimeFormat.CLOCK_12H)
                    .setHour(12)
                    .setMinute(10)
                    .setTitleText("Select Appointment time")
                    .build()

            picker.show(supportFragmentManager,"")


            picker.addOnPositiveButtonClickListener {

                val pickedHour: Int = picker.hour
                val pickedMinute: Int = picker.minute



                // check for single digit hour hour and minute
                // and update TextView accordingly
                val formattedTime: String = when {
                    pickedHour > 12 -> {
                        if (pickedMinute < 10) {
                            "${picker.hour - 12}:0${picker.minute} pm"
                        } else {
                            "${picker.hour - 12}:${picker.minute} pm"
                        }
                    }
                    pickedHour == 12 -> {
                        if (pickedMinute < 10) {
                            "${picker.hour}:0${picker.minute} pm"
                        } else {
                            "${picker.hour}:${picker.minute} pm"
                        }
                    }
                    pickedHour == 0 -> {
                        if (pickedMinute < 10) {
                            "${picker.hour + 12}:0${picker.minute} am"
                        } else {
                            "${picker.hour + 12}:${picker.minute} am"
                        }
                    }
                    else -> {
                        if (pickedMinute < 10) {
                            "${picker.hour}:0${picker.minute} am"
                        } else {
                            "${picker.hour}:${picker.minute} am"
                        }
                    }
                }

                // then update the preview TextView
                binding.btnTimepicker.text = formattedTime
            }

        }


        binding.btnDatepickerRange.setOnClickListener {

            val disiablePreviousDate =
            CalendarConstraints.Builder()
                .setValidator(
                    DateValidatorPointForward.now())


            val dateRangePicker =
                MaterialDatePicker.Builder.dateRangePicker()
                    .setTitleText("Select dates")
                    .setCalendarConstraints(disiablePreviousDate.build())
                    .setSelection(

                        Pair(
                            MaterialDatePicker.thisMonthInUtcMilliseconds(),
                            MaterialDatePicker.todayInUtcMilliseconds()
                        )
                    )
                    .build()

            dateRangePicker.show(supportFragmentManager,"")


            dateRangePicker.addOnPositiveButtonClickListener {

                binding.btnDatepickerRange.text = "${convertLongToDate(it.first)} + ${convertLongToDate(it.second)}"

            }

        }



    }

    fun convertLongToDate(time: Long): String {
        val date = Date(time)
        val format = SimpleDateFormat("dd/MM/yyyy")
        return format.format(date)
    }
}
