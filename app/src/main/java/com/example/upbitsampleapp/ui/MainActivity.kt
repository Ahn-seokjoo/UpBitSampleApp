package com.example.upbitsampleapp.ui

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.upbitsampleapp.R
import com.example.upbitsampleapp.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.subjects.BehaviorSubject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val compositeDisposable = CompositeDisposable()
    private val subject = BehaviorSubject.createDefault(0L)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)

        onDoubleBackPressed()
    }

    private fun onDoubleBackPressed() {
        subject.buffer(2, 1)
            .map {
                it[0] to it[1]
            }.subscribe {
                if (it.second - it.first < 2000L) {
                    super.onBackPressed()
                    finish()
                } else {
                    Toast.makeText(this, "뒤로 버튼을 한번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT).show()
                }
            }.addTo(compositeDisposable)
    }

    override fun onBackPressed() {
        subject.onNext(System.currentTimeMillis())
    }

    override fun onDestroy() {
        compositeDisposable.dispose()
        super.onDestroy()
    }
}
