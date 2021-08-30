package com.hafizcode.moviesandtv.ui.bookmark.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.hafizcode.moviesandtv.R
import com.hafizcode.moviesandtv.databinding.ActivityBookmarkBinding
import com.hafizcode.moviesandtv.ui.home.content.helper.DataViewModel
import com.hafizcode.moviesandtv.ui.home.view.SectionPagerAdapter
import com.hafizcode.moviesandtv.viewmodel.ViewModelFactory

class BookmarkActivity : AppCompatActivity() {

    private lateinit var dataViewModel: DataViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val activityBookmarkBinding = ActivityBookmarkBinding.inflate(layoutInflater)
        setContentView(activityBookmarkBinding.root)

        val factory = ViewModelFactory.getInstance(applicationContext)
        dataViewModel = ViewModelProvider(this, factory)[DataViewModel::class.java]

        val bookmarkSectionPagerAdapter = BookmarkSectionPagerAdapter(this, supportFragmentManager)
        activityBookmarkBinding.viewPagerBookmark.adapter = bookmarkSectionPagerAdapter
        activityBookmarkBinding.tabsBookmark.setupWithViewPager(activityBookmarkBinding.viewPagerBookmark)
    }
}