package com.yi_555555555.codelingo.presentation.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import coil3.ImageLoader
import coil3.network.okhttp.OkHttpNetworkFetcherFactory
import coil3.svg.SvgDecoder

@Composable
fun rememberImageLoader(): ImageLoader {
  val context = LocalContext.current
  return remember(context) {
    ImageLoader.Builder(context)
      .components {
        add(OkHttpNetworkFetcherFactory())
        add(SvgDecoder.Factory())
      }
      .build()
  }
}