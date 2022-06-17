package com.kod.habittracker.ui.fragments.updatehabit

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.graphics.Color
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.DatePicker
import android.widget.TimePicker
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import com.kod.habittracker.R
import com.kod.habittracker.data.model.Habit
import com.kod.habittracker.databinding.FragmentUpdateHabitItemBinding
import com.kod.habittracker.ui.fragments.createhabit.DrawableAdapter
import com.kod.habittracker.ui.viewmodels.habitviewmdodel.CreateHabitViewModel
import com.kod.habittracker.ui.viewmodels.habitviewmdodel.HabitViewModel
import com.kod.habittracker.ui.viewmodels.habitviewmdodel.HabitViewModelFactory
import com.kod.habittracker.utilities.Calculations
import com.kod.habittracker.utilities.ListOfDrawables

class UpdateHabitItem : Fragment(R.layout.fragment_update_habit_item), ListOfDrawables, TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener{

    private var _binding: FragmentUpdateHabitItemBinding? = null
    private val binding get() = _binding!!
    private lateinit var createHabitViewModel: CreateHabitViewModel
    private lateinit var habitViewModel: HabitViewModel
    private val args by navArgs<UpdateHabitItemArgs>()
    private lateinit var adapter: DrawableAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUpdateHabitItemBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        createHabitViewModel = ViewModelProvider(this)[CreateHabitViewModel::class.java]
        val viewModelFactory = HabitViewModelFactory(requireActivity().application)
        this.habitViewModel = ViewModelProvider(this, viewModelFactory)[HabitViewModel::class.java]
        binding.etHabitTitleUpdate.setText(args.selectedHabit.title)
        binding.etHabitDescriptionUpdate.setText(args.selectedHabit.description)

        adaptingDrawables()
        pickDateAndTime()
        binding.btnConfirmUpdate.setOnClickListener {
            updateHabitInDB()
        }
    }

    private fun adaptingDrawables(){
        adapter = DrawableAdapter()
        binding.rvHabitsItemUpdate.adapter = adapter
        binding.rvHabitsItemUpdate.layoutManager =
            LinearLayoutManager(requireActivity(), RecyclerView.HORIZONTAL, false)
    }

    private fun pickDateAndTime(){
        binding.tvDateSelectedUpdate.setOnClickListener {
            createHabitViewModel.getCurrentDateCalendar()
            DatePickerDialog(requireContext(), this,
                createHabitViewModel.year,
                createHabitViewModel.month,
                createHabitViewModel.day).show()
        }

        binding.tvTimeSelectedUpdate.setOnClickListener {
            createHabitViewModel.getCurrentTimeCalendar()
            TimePickerDialog(requireContext(), this,
                createHabitViewModel.hour,
                createHabitViewModel.minute, true).show()
        }
    }

    private fun updateHabitInDB(){
        createHabitViewModel.title = binding.etHabitTitleUpdate.text.toString()
        createHabitViewModel.description = binding.etHabitDescriptionUpdate.text.toString()
        createHabitViewModel.drawableSelected = adapter.getDrawable()
        val startTime = "${createHabitViewModel.cleanDate} ${createHabitViewModel.cleantime}"

        val isOK = Calculations.checkValues(
            createHabitViewModel.title,
            createHabitViewModel.description,
            startTime,
            createHabitViewModel.drawableSelected
        )

        if(isOK){
            val habit = Habit(args.selectedHabit.id,
                createHabitViewModel.title,
                createHabitViewModel.description,
                startTime,
                createHabitViewModel.drawableSelected)

            habitViewModel.updateHabit(habit)
            snackBar("Habit updated successfully")
            return2Main()
        }else{
            snackBar("Please fill all fields")
            return
        }
    }

    private fun return2Main(){
        findNavController().navigate(R.id.action_updateHabitItem_to_habitList)
    }

    private fun deleteHabit(habit: Habit){
        habitViewModel.deleteHabit(habit)
    }

    private fun snackBar(message: String){
        Snackbar.make(requireView(), message, Snackbar.LENGTH_SHORT)
            .setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_SLIDE)
            .setTextColor(Color.parseColor(("#FFFFFF")))
            .show()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        createHabitViewModel.cleantime = Calculations.cleanTime(hourOfDay, minute)
        createHabitViewModel.txTimeSelected = "Time: ${createHabitViewModel.cleantime}"
        binding.tvTimeSelectedUpdate.setText(createHabitViewModel.txTimeSelected)
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        createHabitViewModel.cleanDate = Calculations.cleanDate(dayOfMonth, month, year)
        createHabitViewModel.txDateSelected = "Date: ${createHabitViewModel.cleanDate}"
        binding.tvDateSelectedUpdate.setText(createHabitViewModel.txDateSelected)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.delete_single_item -> {
                deleteHabit(args.selectedHabit)
                snackBar("Item Deleted Successfully")
                return2Main()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.single_item_menu, menu)
    }
}