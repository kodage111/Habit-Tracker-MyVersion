package com.kod.habittracker.ui.fragments.createhabit

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.TimePicker
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import com.kod.habittracker.R
import com.kod.habittracker.data.model.Habit
import com.kod.habittracker.databinding.FragmentCreateHabitItemBinding
import com.kod.habittracker.ui.viewmodels.habitviewmdodel.CreateHabitViewModel
import com.kod.habittracker.ui.viewmodels.habitviewmdodel.HabitViewModel
import com.kod.habittracker.ui.viewmodels.habitviewmdodel.HabitViewModelFactory
import com.kod.habittracker.utilities.Calculations
import com.kod.habittracker.utilities.Calculations.checkValues
import com.kod.habittracker.utilities.ListOfDrawables

class CreateHabitItem : Fragment(R.layout.fragment_create_habit_item), TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener{
    private var _binding: FragmentCreateHabitItemBinding? = null
    private val binding get() = _binding!!

    private lateinit var createHabitViewModel: CreateHabitViewModel
    private lateinit var habitViewModel: HabitViewModel
    private lateinit var adapter: DrawableAdapter

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentCreateHabitItemBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        createHabitViewModel = ViewModelProvider(this)[CreateHabitViewModel::class.java]
        val viewModelFactory = HabitViewModelFactory(requireActivity().application)
        this.habitViewModel = ViewModelProvider(this, viewModelFactory)[HabitViewModel::class.java]

        binding.btnConfirm.setOnClickListener {
            addHabitToDB()
        }
        adaptingDrawables()
        pickDateAndTime()
    }

    private fun adaptingDrawables(){
        adapter = DrawableAdapter()
        binding.rvHabitsItem.adapter = adapter
        binding.rvHabitsItem.layoutManager =
            LinearLayoutManager(requireActivity(), RecyclerView.HORIZONTAL, false)
    }

    private fun addHabitToDB(){
        createHabitViewModel.title = binding.etHabitTitle.text.toString()
        createHabitViewModel.description = binding.etHabitDescription.text.toString()
        createHabitViewModel.drawableSelected = adapter.getDrawable()

        val startTime = "${createHabitViewModel.cleanDate} ${createHabitViewModel.cleantime}"

        val isOK = checkValues(createHabitViewModel.title,
            createHabitViewModel.description,
            startTime,
            createHabitViewModel.drawableSelected)

        if(isOK){
            val habit = Habit(0,
                createHabitViewModel.title,
                createHabitViewModel.description,
                startTime,
                createHabitViewModel.drawableSelected)

            habitViewModel.addHabit(habit)
            snackBar("Habit added successfully")
            return2Main()
        }else{
            snackBar("Please fill all fields")
            return
        }
    }

    private fun return2Main(){
        findNavController().navigate(R.id.action_createHabitItem_to_habitList)
    }

    private fun drawableSelected(){

    }

    private fun pickDateAndTime(){
        binding.tvDateSelected.setOnClickListener {
            createHabitViewModel.getCurrentDateCalendar()
            DatePickerDialog(requireContext(), this,
                createHabitViewModel.year,
                createHabitViewModel.month,
                createHabitViewModel.day).show()
        }


        binding.tvTimeSelected.setOnClickListener {
            createHabitViewModel.getCurrentTimeCalendar()
            TimePickerDialog(requireContext(), this,
                createHabitViewModel.hour,
                createHabitViewModel.minute, true).show()
        }
    }

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        createHabitViewModel.cleantime = Calculations.cleanTime(hourOfDay, minute)
        createHabitViewModel.txTimeSelected = "Time: ${createHabitViewModel.cleantime}"
        binding.tvTimeSelected.setText(createHabitViewModel.txTimeSelected)
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        createHabitViewModel.cleanDate = Calculations.cleanDate(dayOfMonth, month, year)
        createHabitViewModel.txDateSelected = "Date: ${createHabitViewModel.cleanDate}"
        binding.tvDateSelected.setText(createHabitViewModel.txDateSelected)
    }

    private fun snackBar(message: String){
        Snackbar.make(requireView(), message, Snackbar.LENGTH_SHORT)
            .setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_SLIDE)
            .setTextColor(Color.parseColor(("#FFFFFF")))
            .show()
    }
}