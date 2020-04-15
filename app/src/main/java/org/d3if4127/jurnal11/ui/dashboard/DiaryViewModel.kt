package org.d3if4127.jurnal11.ui.dashboard

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import kotlinx.coroutines.*
import org.d3if4127.jurnal11.database.Diary
import org.d3if4127.jurnal11.database.DiaryDAO
import org.d3if4127.jurnal11.database.DiaryDB
import java.text.SimpleDateFormat
import java.util.*

class DiaryViewModel(application: Application) : AndroidViewModel(application) {

    private lateinit var database: DiaryDAO
    lateinit var list_diary : LiveData<List<Diary>>

    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    init {
        database = DiaryDB.getInstance(application).DiaryDao
        list_diary = database.get()
    }

    private fun dateNow(): String {
        return SimpleDateFormat("dd/MMMM/yyyy").format(Date())
    }

    fun onPush(content: String){
        uiScope.launch {
            val diary = Diary(content = content, lastUpdate = dateNow())
            insert(diary)
        }
    }
    fun onUpdate(id: Long, newContent: String) {
        uiScope.launch {
            update(id, newContent)
        }
    }
    fun onDelete(id: Long) {
        uiScope.launch {
            delete(id)
        }
    }
    fun onClear() {
        uiScope.launch {
            clear()
        }
    }

    private suspend fun insert(diary: Diary) {
        withContext(Dispatchers.IO) {
            database.insert(diary)
        }
    }
    private suspend fun delete(id: Long) {
        withContext(Dispatchers.IO) {
            database.delete(id)
        }
    }
    private suspend fun clear() {
        withContext(Dispatchers.IO) {
            database.clear()
        }
    }
    private suspend fun update(id: Long, newContent: String) {
        withContext(Dispatchers.IO) {
            database.update(id = id, newContent = newContent, lastUpdate = dateNow())
        }
    }

}