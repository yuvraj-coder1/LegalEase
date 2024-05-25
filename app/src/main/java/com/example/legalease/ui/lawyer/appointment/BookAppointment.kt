package com.example.legalease.ui.lawyer.appointment

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.widget.DatePicker
import android.widget.TimePicker
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInWindow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.ui.theme.Inter
import java.util.Calendar
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookAppointment(
    modifier: Modifier = Modifier,
    caseId: String = "",
    clientId: String = "",
    navigateBack: () -> Unit = {}
) {
    val bookAppointmentViewModel: BookAppointmentViewModel = hiltViewModel()
    var reasonOfAppointment by rememberSaveable { mutableStateOf("") }
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val date = MyContent()
        val time = TimePickerDemo()
        OutlinedTextField(
            value = reasonOfAppointment,
            onValueChange = { reasonOfAppointment = it },
            modifier = Modifier.fillMaxWidth(),
            label = { Text(text = "Appointment Details") },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done)
        )
        Spacer(modifier = Modifier.height(10.dp))
        Button(
            onClick = {
                bookAppointmentViewModel.addAppointment(
                    reason = reasonOfAppointment,
                    caseId = caseId,
                    date = date,
                    time = time,
                    clientId = clientId
                )
                navigateBack()
            },

            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(7.dp),
        ) {

            Text(text = "Submit", fontSize = 16.sp)
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyContent(): String {

    // Fetching the Local Context
    val mContext = LocalContext.current
    var globalPosition = remember { mutableStateOf(Offset.Zero) }

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
            //          mDate.value = "$mDayOfMonth/${mMonth+1}/$mYear"
            // year-month-day
            mDate.value = "$mYear-${String.format("%02d", mMonth + 1)}-$mDayOfMonth"
        }, mYear, mMonth, mDay
    )
// correct code
//    Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
//
//        // Creating a button that on
//        // click displays/shows the DatePickerDialog
//        Button(onClick = {
//            mDatePickerDialog.show()
//        } ) {
//            Text(text = "Open Date Picker", color = Color.White)
//        }
////        , colors = ButtonDefaults.buttonColors(backgroundColor = Color(0XFF0F9D58))
//
//        // Adding a space of 100dp height
//        Spacer(modifier = Modifier.size(100.dp))
//
//        // Displaying the mDate value in the Text
//        Text(text = "Selected Date: ${mDate.value}", fontSize = 30.sp, textAlign = TextAlign.Center)
//    }
//    val selectedDate = remember { mutableStateOf<Date?>(null) }

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // Creating a TextField with an icon
        TextField(
            value = "Appointment Date" + " ${mDate.value}",
            onValueChange = { /* Handle text changes if needed */ },
            colors = TextFieldDefaults.textFieldColors(
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black,
//                containerColor = colorResource(id = R.color.light_sky_blue), // Set your desired background color

                focusedLabelColor = Color.Black,
                unfocusedLabelColor = Color.Black,
                cursorColor = Color.Black,
                focusedIndicatorColor = Color.Black,
                unfocusedIndicatorColor = Color.Black,
                disabledIndicatorColor = Color.Black,
                disabledLabelColor = Color.Black,
                disabledTextColor = Color.Black,
                disabledTrailingIconColor = Color.Black
            ),
            modifier = Modifier
                .clip(RoundedCornerShape(5.dp))
                .fillMaxWidth(),

//                .padding(16.dp),
            trailingIcon = {
                Icon(
                    imageVector = Icons.Default.CalendarMonth, // Replace with your icon resource
                    contentDescription = null,
                    modifier = Modifier
                        .clickable {
                            mDatePickerDialog.show()
                        }
                        .onGloballyPositioned { coordinates ->
                            // Update the absolute position when the button is positioned in the layout hierarchy
                            globalPosition.value = coordinates.positionInWindow()
                            println("globalPosition in root: x:" + globalPosition.value.x + " y : " + globalPosition.value.y)
                        },
                )
            },
            readOnly = true,
            enabled = false
        )

        // Adding a space of 16.dp height
//        Spacer(modifier = Modifier.size(16.dp))
//
//        // Displaying the selected date value in the Text
//        Text(
////            text = "Selected Date: ${selectedDate.value?.toString() ?: ""}",
//            text = "Selected Date: ${mDate.value}",
//            fontSize = 30.sp,
//            textAlign = TextAlign.Center
//        )
    }
    return mDate.value
}

@SuppressLint("DefaultLocale")
@Composable
fun TimePickerDemo(): String {
    var time by remember { mutableStateOf("") }
    val context = LocalContext.current

    val calendar = Calendar.getInstance()
    val hour = calendar.get(Calendar.HOUR_OF_DAY)
    val minute = calendar.get(Calendar.MINUTE)

    val timePickerDialog = TimePickerDialog(
        context,
        { _: TimePicker, selectedHour: Int, selectedMinute: Int ->
            time = String.format("%02d:%02d", selectedHour, selectedMinute)
        },
        hour,
        minute,
        true
    )

    Spacer(modifier = Modifier.height(10.dp))
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = time,
            modifier = Modifier.padding(start = 15.dp),
            fontWeight = FontWeight.Medium,
            fontSize = 16.sp,
            fontFamily = Inter,
            letterSpacing = (-0.3).sp
        )
        Button(
            onClick = { timePickerDialog.show() },
            shape = RoundedCornerShape(5.dp)

        ) {
            Text(text = "Pick Time")
        }
    }
    return time
}

@Preview(showBackground = true)
@Composable
fun BookAppointmentPreview() {
    BookAppointment()
}