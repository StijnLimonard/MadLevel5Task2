package com.example.madlevel5task2.ui

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.madlevel5task2.R
import com.example.madlevel5task2.databinding.FragmentGameBacklogBinding
import com.example.madlevel5task2.model.Game
import com.example.madlevel5task2.viewmodel.GameViewModel
import com.google.android.material.snackbar.Snackbar

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class GameBacklogFragment : Fragment() {
    private lateinit var binding: FragmentGameBacklogBinding

    private val viewModel: GameViewModel by viewModels()

    private val gamesList = arrayListOf<Game>()
    private val gameBacklogAdapter = GameBacklogAdapter(gamesList)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)

        // Inflate the layout for this fragment
        binding = FragmentGameBacklogBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeRecyclerView()

        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(false)

        observeGameList()

        binding.fabAddGame.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.findItem(R.id.action_delete_all).isVisible = true
        requireActivity().title = getString(R.string.game_backlog)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_delete_all -> {
                viewModel.deleteAllGames()
                return true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }

    private fun observeGameList() {
        // Make an observer which will get notified by the LiveData observable data holder class state has changed
        viewModel.gamesList.observe(viewLifecycleOwner, { gamesList ->
            this@GameBacklogFragment.gamesList.clear()
            this@GameBacklogFragment.gamesList.addAll(gamesList)
            this@GameBacklogFragment.gamesList.sortBy { it.date }
            gameBacklogAdapter.notifyDataSetChanged()
        })
    }

    private fun initializeRecyclerView() {
        val viewManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)

        binding.rvGames.apply {
            setHasFixedSize(true) // true because the ViewHolder's do not affect the RecyclerView's size. This is done for optimization purposes
            layoutManager = viewManager
            adapter = gameBacklogAdapter
        }

        createItemTouchHelperSwipe().attachToRecyclerView(binding.rvGames)
    }

    private fun createItemTouchHelperSwipe(): ItemTouchHelper {

        // Callback which is used to create the ItemTouch helper. Only enables left swipe.
        // Use ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) to also enable right swipe.
        val callback = object :
            ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

            // Enables or Disables the ability to move items up and down.
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            // Callback triggered when a user swiped an item.
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val gameToDelete = gamesList[position]
                viewModel.deleteGame(gameToDelete)
                Snackbar.make(
                    view!!,
                    getString(R.string.successfully_deleted_game),
                    Snackbar.LENGTH_LONG
                )
                    .setAction(getString(R.string.undo)) {
                        viewModel.insertGame(
                            gameToDelete.title,
                            gameToDelete.platform,
                            gameToDelete.date
                        )
                    }
                    .show()
            }
        }
        return ItemTouchHelper(callback)
    }
}