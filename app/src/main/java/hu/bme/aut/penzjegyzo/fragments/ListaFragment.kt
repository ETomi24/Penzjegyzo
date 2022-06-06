package hu.bme.aut.penzjegyzo.fragments

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import hu.bme.aut.penzjegyzo.adapter.BejegyzesAdapter
import hu.bme.aut.penzjegyzo.data.Bejegyzes
import hu.bme.aut.penzjegyzo.data.BejegyzesDatabase
import hu.bme.aut.penzjegyzo.databinding.FragmentListaBinding
import hu.bme.aut.penzjegyzo.runOnUiThread
import kotlin.concurrent.thread

class ListaFragment : Fragment() , BejegyzesAdapter.BejegyzesekClickListener,
    NewBejegyzesItemDialogFragment.NewBejegyzesItemDialogListener{

    private lateinit var binding: FragmentListaBinding
    private lateinit var adapter : BejegyzesAdapter
    private lateinit var database: BejegyzesDatabase


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentListaBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        database = activity?.let { BejegyzesDatabase.getDatabase(it.applicationContext) }!!
        initRecyclerView()
        binding.fab.setOnClickListener(){
            NewBejegyzesItemDialogFragment().show(childFragmentManager,
                NewBejegyzesItemDialogFragment.TAG
            )
        }

    }
    private fun initRecyclerView() {
        adapter = BejegyzesAdapter(this)
        val mLayoutManager = LinearLayoutManager(activity)
        mLayoutManager.reverseLayout = true
        mLayoutManager.stackFromEnd = true
        binding.rvMain.layoutManager = mLayoutManager
        binding.rvMain.adapter = adapter
        loadItemsInBackground()
    }

    private fun loadItemsInBackground() {
        thread {
            val items = database.BejegyzesDao().getAll()
            runOnUiThread {
                adapter.update(items)
            }
        }
    }

    override fun onBejegyzesItemCreated(newItem: Bejegyzes) {
        thread {
            val insertId = database.BejegyzesDao().insert(newItem)
            newItem.id = insertId
            runOnUiThread {
                adapter.addItem(newItem)
                binding.rvMain.smoothScrollToPosition(insertId.toInt())
            }
        }
    }
    override fun onItemDeleted(item: Bejegyzes) {
        thread {
            database.BejegyzesDao().deleteItem(item)
            runOnUiThread {
                adapter.deleteItem(item)
            }
        }
    }
}