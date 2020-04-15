package org.d3if4127.jurnal11.ui.write

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import org.d3if4127.jurnal11.R
import org.d3if4127.jurnal11.ui.dashboard.DiaryViewModel
import org.d3if4127.jurnal11.databinding.FragmentWriteBinding

/**
 * A simple [Fragment] subclass.
 */
class writeFragment : Fragment() {

    private lateinit var binding: FragmentWriteBinding
    private lateinit var diaryVM: DiaryViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_write, container, false)
        diaryVM = ViewModelProviders.of(this).get(DiaryViewModel::class.java)
        binding.setLifecycleOwner(this)
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_write, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.writeDiary -> _writeDiary()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun _writeDiary() {
        try {
            var content = binding.inputDiary.text.toString()
            diaryVM.onPush(content)
            Toast.makeText(this.context, "Sukses Tambah Diary", Toast.LENGTH_SHORT).show()
            this.view?.findNavController()?.navigate(R.id.action_writeFragment_to_dashboardFragment)
        }catch (e: Exception) {
            Toast.makeText(this.context, "Ada Kesalahan", Toast.LENGTH_SHORT).show()
        }
    }

}
