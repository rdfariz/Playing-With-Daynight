package org.d3if4127.jurnal11.ui.dashboard

import android.content.res.Configuration
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.d3if4127.jurnal11.R
import org.d3if4127.jurnal11.databinding.FragmentDashboardBinding

/**
 * A simple [Fragment] subclass.
 */
class dashboardFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    private lateinit var binding: FragmentDashboardBinding
    private lateinit var diaryVM: DiaryViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_dashboard, container, false)
        diaryVM = ViewModelProviders.of(this).get(DiaryViewModel::class.java)
        binding.diaryVM = diaryVM
        binding.setLifecycleOwner(this)

        viewManager = LinearLayoutManager(context)
        diaryVM.list_diary.observe(viewLifecycleOwner, Observer {
            viewAdapter = diaryAdapter(it, diaryAdapter.OnClickListener {
                var bundle = bundleOf("content" to it.content, "id" to it.id)
                view?.findNavController()?.navigate(R.id.action_dashboardFragment_to_updateFragment, bundle)
            })
            recyclerView = binding.rvDiary.apply {
                layoutManager = viewManager
                adapter = viewAdapter
            }
        })

        binding.toWriteDiary.setOnClickListener {view: View ->
            view.findNavController().navigate(R.id.action_dashboardFragment_to_writeFragment)
        }

        return binding.root
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.clearData -> clearData()
            R.id.changeTheme -> {
                if (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK == Configuration.UI_MODE_NIGHT_YES) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                }else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.overflow,menu)
    }

    private fun clearData() {
        diaryVM.onClear()
    }

}
