package com.dpfht.demotmdbcompose.feature_error_message

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBottomSheet(
  msg: String,
  onDismiss: () -> Unit
) {
  val modalBottomSheetState = rememberModalBottomSheetState()

  ModalBottomSheet(
    onDismissRequest = { onDismiss() },
    sheetState = modalBottomSheetState,
    dragHandle = { BottomSheetDefaults.DragHandle() }
  ) {
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 10.dp)
    ) {
      Text(
        text = stringResource(id = R.string.error_message_text_message),
        fontWeight = FontWeight.Bold
      )
      Spacer(
        modifier = Modifier.size(10.dp)
      )
      Text(
        text = msg
      )
      Spacer(
        modifier = Modifier.size(30.dp)
      )
      Button(
        onClick = { onDismiss() },
        modifier = Modifier.align(Alignment.CenterHorizontally)
      ) {
        Text(
          text = stringResource(id = R.string.error_message_text_ok)
        )
      }
      Spacer(
        modifier = Modifier.size(30.dp)
      )
    }
  }
}
