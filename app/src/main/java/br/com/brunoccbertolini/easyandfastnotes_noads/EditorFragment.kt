package br.com.brunoccbertolini.easyandfastnotes_noads

import android.app.Activity
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import br.com.brunoccbertolini.easyandfastnotes_noads.databinding.EditorFragmentBinding

class EditorFragment : Fragment() {

    private lateinit var viewModel: EditorViewModel
    private val args: EditorFragmentArgs by navArgs()
    private var _binding: EditorFragmentBinding? = null
    private val binding:EditorFragmentBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        (activity as AppCompatActivity).supportActionBar?.let {
            it.setHomeButtonEnabled(true)
            it.setDisplayShowHomeEnabled(true)
            it.setDisplayHomeAsUpEnabled(true)
            it.setHomeAsUpIndicator(R.drawable.ic_check)
        }
        setHasOptionsMenu(true)

        requireActivity().title =
            if (args.noteid == NEW_NOTE_ID){
                "New Note"
            }else{
                "Edit Note"
            }

        viewModel = ViewModelProvider(this).get(EditorViewModel::class.java)

        _binding = EditorFragmentBinding.inflate(inflater, container, false)
        binding.etTextNote.setText("")

        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true){
                override fun handleOnBackPressed() {
                    saveAndReturn()
                }

            }
        )
        viewModel.currentNote.observe(viewLifecycleOwner, {
            val savedString = savedInstanceState?.getString(NOTE_TEXT_KEY)
            val cursorPosition = savedInstanceState?.getInt(CURSOR_POSITION_KEY)?: 0
            binding.etTextNote.setText(savedString?: it.text)
            binding.etTextNote.setSelection(cursorPosition)
        })
        viewModel.getNoteById(args.noteid)
        return binding.root
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> saveAndReturn()
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun saveAndReturn(): Boolean {
        val imn = requireActivity()
            .getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imn.hideSoftInputFromWindow(binding.root.windowToken, 0)

        Log.i(TAG, "saveAndReturn: ${binding.etTextNote.text}")
        viewModel.currentNote.value?.text = binding.etTextNote.text.toString()
        viewModel.updateNote()

        findNavController().navigateUp()
        return true
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        binding.etTextNote.apply {
            outState.putString(NOTE_TEXT_KEY, text.toString())
            outState.putInt(CURSOR_POSITION_KEY, selectionStart)
        }
        super.onSaveInstanceState(outState)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}