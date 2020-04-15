package org.d3if4127.jurnal11.ui.update

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import org.d3if4127.jurnal11.R
import org.d3if4127.jurnal11.ui.dashboard.DiaryViewModel
import org.d3if4127.jurnal11.databinding.FragmentUpdateBinding

/**
 * A simple [Fragment] subclass.
 */
class updateFragment : Fragment() {

    private lateinit var binding: FragmentUpdateBinding
    private lateinit var diaryVM: DiaryViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val id = arguments?.getLong("id")
        val content = arguments?.getString("content")
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_update, container, false)
        diaryVM = ViewModelProviders.of(this).get(DiaryViewModel::class.java)
        binding.setLifecycleOwner(this)
        binding.inputDiary.setText(content)
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_update, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.updateDiary -> _updDiary()
            R.id.deleteDiary -> _deleteDiary()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun _deleteDiary() {
        try {
            val id = arguments?.getLong("id")
            id?.let { diaryVM.onDelete(id = it) }
            Toast.makeText(this.context, "Diary ${id} berhasil dihapus", Toast.LENGTH_SHORT).show()
            this.view?.findNavController()?.navigate(R.id.action_updateFragment_to_dashboardFragment)
        }catch (e: Exception) {
            Toast.makeText(this.context, "Ada kesalahan", Toast.LENGTH_SHORT).show()
        }
    }
    private fun _updDiary() {
        try {
            val id = arguments?.getLong("id")
            var content = binding.inputDiary.text.toString()
            id?.let { diaryVM.onUpdate(id = it, newContent = binding.inputDiary.text.toString()) }
            Toast.makeText(this.context, "Diary ${id} berhasil diupdate", Toast.LENGTH_SHORT).show()
            this.view?.findNavController()?.navigate(R.id.action_updateFragment_to_dashboardFragment)
        }catch (e: Exception) {
            Toast.makeText(this.context, "Ada kesalahan", Toast.LENGTH_SHORT).show()
        }
    }
}
