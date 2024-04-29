package com.mentalhealth.eifie.ui.form

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Form() {

    val pagerState = rememberPagerState(pageCount = { 10 })
    
    HorizontalPager(
        state = pagerState,
        userScrollEnabled = false
    ) {
        
    }
    
}