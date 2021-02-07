package br.com.brunoccbertolini.easyandfastnotes_noads

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.brunoccbertolini.easyandfastnotes_noads.adapter.NoteListAdapter
import br.com.brunoccbertolini.easyandfastnotes_noads.databinding.MainFragmentBinding

class MainFragment : Fragment() {


    private lateinit var viewModel: MainViewModel

    private var _binding: MainFragmentBinding? = null
    private val binding: MainFragmentBinding get() = _binding!!
    private lateinit var adapter: NoteListAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = MainFragmentBinding.inflate(inflater,container, false)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        binding.rvMain.apply {
            setHasFixedSize(true)
            val divider = DividerItemDecoration(
                context, LinearLayoutManager(context).orientation
            )
            addItemDecoration(divider)
        }

        viewModel.notesList.observe(viewLifecycleOwner, Observer {
            Log.i("noteLogging", it.toString())
            adapter = NoteListAdapter(it)
            binding.rvMain.adapter = adapter
            binding.rvMain.layoutManager = LinearLayoutManager(activity)
        })

        return binding.root
    }



    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}