package com.example.msi.realmmemo

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.example.msi.realmmemo.databinding.ItemMemoBinding
import com.github.nitrico.lastadapter.LastAdapter
import com.vicpin.krealmextensions.delete
import com.vicpin.krealmextensions.queryAll
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.startActivityForResult

class MainActivity : AppCompatActivity() {

    var mList = ArrayList<Memo>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Realm.init(this)

        loadMemoList()
        addNoteBtn.onClick {
            startActivityForResult<WriteActivity>(100)
        }

        memoRecycler.layoutManager = LinearLayoutManager(this)
        LastAdapter(mList, BR.item)
                .map<Memo, ItemMemoBinding>(R.layout.item_memo){
                    onLongClick{
                        var memo = it.binding.item
                        Memo().delete { it.equalTo("id", memo?.id) }
                        loadMemoList()
                    }
                }
                .into(memoRecycler)
    }

    private fun loadMemoList() {
        mList.clear()
        Memo().queryAll().forEach { mList.add(it) }
        memoRecycler.adapter?.notifyDataSetChanged()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == 100 && resultCode == Activity.RESULT_OK){
            loadMemoList()
        }
    }
}