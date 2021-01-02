package com.example.madlevel5task2.ui

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.madlevel5task2.R
import com.example.madlevel5task2.databinding.FragmentAddGameBinding
import com.example.madlevel5task2.viewmodel.GameViewModel
import java.util.*

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class AddGameFragment : Fragment() {
    private lateinit var binding: FragmentAddGameBinding

    private val viewModel: GameViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)

        // Inflate the layout for this fragment
        binding = FragmentAddGameBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Get a support ActionBar corresponding to this toolbar and enable the Up button
        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        binding.fabSaveGame.setOnClickListener {
            onAddGame()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        requireActivity().title = getString(R.string.add_game)
        // If Up button is clicked, pop the fragment off the Backstack
        activity?.findViewById<Toolbar>(R.id.toolbar)!!.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun onAddGame() {
        val title = binding.etTitle.text.toString()
        val platform = binding.etPlatform.text.toString()
        val day = binding.etDay.text.toString()
        val month = binding.etMonth.text.toString()
        val year = binding.etYear.text.toString()
        val date = "$day $month $year"

        if (title.isNotBlank()) {
            if (date.isNotBlank()) {
                viewModel.insertGame(title, platform, date)
                findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
            } else {
                Toast.makeText(context, "Please fill in a valid date.", Toast.LENGTH_SHORT)
                    .show()
            }
        } else {
            Toast.makeText(context, getString(R.string.please_fill_in_a_title), Toast.LENGTH_SHORT)
                .show()
        }
    }

    private fun dateIsValid(day: String, month: String, year: String): Boolean {
        if (day.isNotBlank() && month.isNotBlank() && year.isNotBlank()) {
            return day.toInt() in 1..31 && month.toInt() in 1..12 && year.toInt() > Calendar.getInstance()
                .get(Calendar.YEAR)
        }
        return false
    }
}