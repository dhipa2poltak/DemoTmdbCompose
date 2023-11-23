package com.dpfht.demotmdbcompose.framework.commons.ui.components

import androidx.compose.material.icons.Icons.AutoMirrored.Filled
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import com.dpfht.demotmdbcompose.framework.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SharedTopAppBar(
  title: String,
  onNavigateBack: (() -> Unit)? = null
) {
  if (onNavigateBack == null) {
    TopAppBar(
      title = {
        Text(
          text = title,
          color = MaterialTheme.colorScheme.onPrimary,
          maxLines = 1,
          overflow = TextOverflow.Ellipsis
        )
      },
      colors = TopAppBarDefaults.topAppBarColors(
        containerColor = MaterialTheme.colorScheme.primary
      )
    )
  } else {
    TopAppBar(
      title = {
        Text(
          text = title,
          color = MaterialTheme.colorScheme.onPrimary,
          maxLines = 1,
          overflow = TextOverflow.Ellipsis
        )
      },
      colors = TopAppBarDefaults.topAppBarColors(
        containerColor = MaterialTheme.colorScheme.primary
      ),
      navigationIcon = {
        IconButton(
          onClick = {
            onNavigateBack()
          }
        ) {
          Icon(
            imageVector = Filled.ArrowBack,
            contentDescription = stringResource(id = R.string.framework_content_desc_back_button),
            tint = MaterialTheme.colorScheme.onPrimary
          )
        }
      }
    )
  }
}
