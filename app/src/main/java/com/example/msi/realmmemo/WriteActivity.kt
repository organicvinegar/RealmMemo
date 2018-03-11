package com.example.msi.realmmemo

import android.app.Activity
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.vicpin.krealmextensions.query
import com.vicpin.krealmextensions.queryAll
import com.vicpin.krealmextensions.queryLast
import com.vicpin.krealmextensions.save
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_write.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import java.text.SimpleDateFormat
import java.util.*

class WriteActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_write)
        Realm.init(this)    // realm 사용을 위한 초기화
        saveBtn.onClick {   // 저장 버튼에 이벤트 추가
            //  현재 시각을 아래 포맷으로 받는다
            val dayTime = SimpleDateFormat("yyyy년 MM월 dd일 hh:mm:ss")
            val date = dayTime.format(Date(System.currentTimeMillis()))

            /*
            realm DB에 있는 Memo 형 중 마지막 데이터의 id 값이 null 일 경우
            지금 저장하는 Memo 는 id를 0, null이 아닐 경우 가장 마지막 값 + 1
             */
            val id = Memo().queryLast()?.id.let {
                if(it == null) 0 else it + 1
            }

            // id와 date 는 위의 변수이고 제목과 내용은 EditText에서 받아온 뒤
            // Memo 형 변수를 만든 다음 .save() 를 통해 db에 저장
            Memo(id, titleEdt.text.toString(), textEdt.text.toString(), date).save()
            setResult(Activity.RESULT_OK)
            finish() //	저장을 했으니 닫아주자.
        }
    }
}