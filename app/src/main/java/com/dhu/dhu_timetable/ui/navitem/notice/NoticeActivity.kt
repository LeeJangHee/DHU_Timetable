package com.dhu.dhu_timetable.ui.navitem.notice

import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.SimpleItemAnimator
import com.dhu.dhu_timetable.R
import com.dhu.dhu_timetable.databinding.ActivityNoticeBinding
import java.util.*

class NoticeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNoticeBinding

    private val noticeAdapter: NoticeAdapter by lazy { NoticeAdapter() }
    private val noticeViewModel: NoticeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNoticeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
            it.title = getString(R.string.nav_notice_title)
        }

        noticeApi()
        setRecyclerView()

        noticeViewModel.noticeData.observe(this) { noticeModels ->
            noticeAdapter.setNoticeList(noticeModels)
        }
    }

    private fun setRecyclerView() {
        binding.noticeRecyclerview.apply {
            adapter = noticeAdapter.also { adapter ->
                adapter.setHasStableIds(true)
            }
            itemAnimator.also { animator ->
                when(animator) {
                    is SimpleItemAnimator -> animator.supportsChangeAnimations = false
                }
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun noticeApi() {
        noticeViewModel.setNoticeData()
    }

    companion object {
        private const val TAG = "jaemin"
    }
}