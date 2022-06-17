package com.kod.habittracker.ui.fragments.habitlist

import android.app.AlertDialog
import android.graphics.Color
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.kod.habittracker.R
import com.kod.habittracker.data.model.Habit
import com.kod.habittracker.databinding.FragmentHabitListBinding
import com.kod.habittracker.ui.viewmodels.habitviewmdodel.HabitViewModel

class HabitList : Fragment(R.layout.fragment_habit_list) {
    private var _binding: FragmentHabitListBinding? = null
    private val binding get() = _binding!!
    private lateinit var habitList: List<Habit>
    private lateinit var rvAdapter: HabitListAdapter
    private lateinit var myViewModel: HabitViewModel

    //This has to be put here
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHabitListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        myViewModel = ViewModelProvider(this)[HabitViewModel::class.java]
        readPrintAllData()
        swipetoRefresh()
        addBtnListener()
    }

    private fun readPrintAllData(){
        val recyclerView = binding.rvHabits
        myViewModel.readAllData.observe(viewLifecycleOwner) {
            habitList = it
            rvAdapter = HabitListAdapter(it)
            recyclerView.adapter = rvAdapter
            recyclerView.layoutManager = LinearLayoutManager(requireContext())

            if (it.isEmpty()) {
                binding.tvEmptyView.visibility = View.VISIBLE
                binding.addIcon.visibility = View.VISIBLE
                binding.rvHabits.visibility = View.GONE
            } else {
                binding.tvEmptyView.visibility = View.GONE
                binding.addIcon.visibility = View.GONE
                binding.rvHabits.visibility = View.VISIBLE
            }
        }
    }

    private fun swipetoRefresh(){
        binding.swipeToRefresh
            .setProgressBackgroundColorSchemeColor(Color.parseColor("#FFF45810"))
        binding.swipeToRefresh.setOnRefreshListener {
            rvAdapter = HabitListAdapter(habitList)
            binding.swipeToRefresh.isRefreshing = false
        }
    }

    private fun addBtnListener(){
        binding.addBtn.setOnClickListener{
            findNavController().navigate(R.id.action_habitList_to_createHabitItem)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.nav_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.delete ->
                AlertDialog.Builder(requireContext())
                    .setTitle("DELETE EVERYTHING ?").setMessage("Are you sure you want to delete everything?")
                    .setPositiveButton("YES"){
                            _, _ -> myViewModel.deleteAllHabits()
                        Toast.makeText(requireContext(), "dataBase cleared", Toast.LENGTH_SHORT).show()
                    }.setNegativeButton("NO"){ _,_ -> }.show()
        }
        return super.onOptionsItemSelected(item)
    }
}