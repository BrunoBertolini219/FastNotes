package br.com.brunoccbertolini.easyandfastnotes_noads

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.brunoccbertolini.easyandfastnotes_noads.adapter.NoteListAdapter
import br.com.brunoccbertolini.easyandfastnotes_noads.data.NoteEntity
import br.com.brunoccbertolini.easyandfastnotes_noads.databinding.MainFragmentBinding

class MainFragment : Fragment(), NoteListAdapter.ListItemListener {


    private lateinit var viewModel: MainViewModel

    private var _binding: MainFragmentBinding? = null
    private val binding: MainFragmentBinding get() = _binding!!
    private lateinit var adapter: NoteListAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (activity as AppCompatActivity)
            .supportActionBar?.setDisplayHomeAsUpEnabled(false)
        setHasOptionsMenu(true)

        requireActivity().title = getString(R.string.app_name)

        _binding = MainFragmentBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        binding.rvMain.apply {
            setHasFixedSize(true)
            val divider = DividerItemDecoration(
                context, LinearLayoutManager(context).orientation
            )
            addItemDecoration(divider)
        }

        viewModel.notesList?.observe(viewLifecycleOwner, {
            adapter = NoteListAdapter(it, this)
            binding.rvMain.adapter = adapter
            binding.rvMain.layoutManager = LinearLayoutManager(activity)

            val selectedNotes =
                savedInstanceState?.getParcelableArrayList<NoteEntity>(SELECTED_NOTES_KEY)
            adapter.selectedNotes.addAll(selectedNotes ?: emptyList())

        })

        binding.fabAddNote.setOnClickListener {
            editNote(NEW_NOTE_ID)
        }

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        val menuId = if (this::adapter.isInitialized && adapter.selectedNotes.isNotEmpty()) {
            R.menu.menu_main_selected_items
        } else {
            R.menu.menu_main
        }
        inflater.inflate(menuId, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {

            R.id.action_sample_data -> addSampleData()
            R.id.action_delete -> deleteSelectedNotes()
            R.id.action_delete_all -> deleteAllNotes()

            else -> super.onOptionsItemSelected(item)
        }

    }

    private fun deleteAllNotes(): Boolean {
        viewModel.deleteAllNotes()
        return true
    }

    private fun addSampleData(): Boolean {
        viewModel.addSampleData()
        return true
    }

    private fun deleteSelectedNotes(): Boolean {
        viewModel.deleteNotes(adapter.selectedNotes)
        Handler(Looper.getMainLooper()).postDelayed({
            adapter.selectedNotes.clear()
            requireActivity().invalidateOptionsMenu()
        }, 50)
        return true
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun editNote(noteId: Int) {
        val action = MainFragmentDirections.actionMainFragmentToEditorFragment(noteId)
        findNavController().navigate(action)
    }

    override fun onItemSelectionChanged() {
        requireActivity().invalidateOptionsMenu()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        if (this::adapter.isInitialized) {
            outState.putParcelableArrayList(SELECTED_NOTES_KEY, adapter.selectedNotes)
        }
        super.onSaveInstanceState(outState)
    }
}