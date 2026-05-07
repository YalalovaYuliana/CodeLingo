package com.yi_555555555.codelingo.presentation.screens.level

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.colintheshots.twain.MarkdownText
import com.yi_555555555.codelingo.R
import com.yi_555555555.codelingo.domain.model.Task
import com.yi_555555555.codelingo.domain.model.TaskType
import com.yi_555555555.codelingo.presentation.components.HSpacer
import com.yi_555555555.codelingo.presentation.components.Header
import com.yi_555555555.codelingo.presentation.components.OptionCheckbox
import com.yi_555555555.codelingo.presentation.components.PrimaryButton
import com.yi_555555555.codelingo.presentation.components.ScreenScaffold
import com.yi_555555555.codelingo.presentation.components.TopAppBar
import com.yi_555555555.codelingo.presentation.components.VSpacer
import com.yi_555555555.codelingo.presentation.components.WSpacer
import dev.snipme.highlights.Highlights
import dev.snipme.highlights.model.SyntaxLanguage
import dev.snipme.highlights.model.SyntaxTheme
import dev.snipme.highlights.model.SyntaxThemes
import dev.snipme.kodeview.view.material3.CodeEditText

@Composable
fun LevelScreen(
  onBackClick: () -> Unit,
  viewModel: LevelViewModel = hiltViewModel()
) {

  val state by viewModel.state.collectAsState()
  val snackbarHostState by viewModel.snackbarHostState.collectAsState()

  var showCloseDialog by remember { mutableStateOf(false) }

  if (showCloseDialog) {
    AlertDialog(
      onDismissRequest = { showCloseDialog = false },
      title = {
        Text(
          text = stringResource(R.string.close_level_dialog_title),
          style = MaterialTheme.typography.titleSmall
        )
      },
      text = { Text(stringResource(R.string.close_level_dialog_description)) },
      confirmButton = {
        PrimaryButton(
          text = stringResource(R.string.leave),
          isError = true,
          onClick = {
            showCloseDialog = false
            onBackClick()
          },
          minHeight = 0.dp
        )
      },
      dismissButton = {
        PrimaryButton(
          text = stringResource(R.string.stay),
          onClick = {
            showCloseDialog = false
          },
          minHeight = 0.dp
        )
      }
    )
  }

  ScreenScaffold(
    topBar = {
      TopAppBar(
        onBackClick = if (state is ViewState.Start) onBackClick else null,
        onCloseClick = if (state is ViewState.Input) {
          { showCloseDialog = true }
        } else null
      )
    },
    snackbarHostState = snackbarHostState
  ) { innerPadding ->
    Column(
      modifier = Modifier
        .fillMaxSize()
        .verticalScroll(state = rememberScrollState())
        .background(MaterialTheme.colorScheme.onPrimaryContainer)
        .padding(innerPadding)
        .imePadding()
        .padding(
          start = 24.dp,
          end = 24.dp,
          bottom = 32.dp
        ),
      verticalArrangement = Arrangement.Center,
      horizontalAlignment = Alignment.CenterHorizontally
    ) {
      when (val currentState = state) {
        is ViewState.Start -> {
          WSpacer()
          Header(text = currentState.title)
          VSpacer(40.dp)
          Image(
            modifier = Modifier.padding(horizontal = 50.dp),
            painter = painterResource(R.drawable.onboarding_screen),
            contentDescription = "onboarding"
          )
          WSpacer()
          PrimaryButton(
            modifier = Modifier
              .fillMaxWidth()
              .padding(horizontal = 26.dp),
            text = stringResource(R.string.start).uppercase(),
            onClick = { viewModel.startLevel() },
            isLoading = currentState.isLoading
          )
        }

        is ViewState.Input -> {
          BackHandler { }

          if (currentState.showTheory) {
            VSpacer(16.dp)
            MarkdownText(
              markdown = currentState.theory
            )
          } else {
            val task = currentState.currentTask
            Text(
              modifier = Modifier.fillMaxWidth(),
              text = task.title,
              style = MaterialTheme.typography.titleMedium,
              textAlign = TextAlign.Start
            )
            VSpacer(80.dp)
            Text(
              modifier = Modifier.fillMaxWidth(),
              text = task.description,
              style = MaterialTheme.typography.displayMedium,
              textAlign = TextAlign.Start
            )
            VSpacer(32.dp)
            TaskContent(
              task = currentState.currentTask,
              onOptionClick = { optionId ->
                viewModel.processCommand(Command.SelectOption(optionId))
              },
              onCodeChange = { newValue ->
                viewModel.processCommand(Command.InputCode(newValue))
              },
              onGapValueChange = { gap, newValue ->
                viewModel.processCommand(Command.InputGap(gap, newValue))
              }
            )
          }
          WSpacer()
          VSpacer(32.dp)
          PrimaryButton(
            isError = currentState.isError,
            modifier = Modifier
              .fillMaxWidth()
              .padding(horizontal = 26.dp),
            text = if (currentState.showTheory) {
              stringResource(R.string.next).uppercase()
            } else {
              stringResource(R.string.check).uppercase()
            },
            onClick = { viewModel.processCommand(Command.Submit) },
            isLoading = currentState.isLoading
          )
        }

        is ViewState.SuccessSubmitLevel -> {
          WSpacer()
          Image(
            modifier = Modifier.padding(horizontal = 50.dp),
            painter = painterResource(R.drawable.welcome_cat),
            contentDescription = null
          )
          VSpacer(56.dp)
          Text(
            text = stringResource(R.string.level_completed).uppercase(),
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.primaryContainer
          )
          if (!currentState.isBeforeCompleted) {
            VSpacer(40.dp)
            XpCard(
              value = currentState.xpAdded.toString()
            )
          }
          WSpacer()
          PrimaryButton(
            modifier = Modifier
              .fillMaxWidth()
              .padding(horizontal = 26.dp),
            text = stringResource(R.string.great).uppercase(),
            onClick = onBackClick
          )
        }
      }
    }
  }
}

@Composable
private fun TaskContent(
  task: Task,
  onOptionClick: (Int) -> Unit,
  onCodeChange: (String) -> Unit,
  onGapValueChange: (Task.Gap, String) -> Unit,
  modifier: Modifier = Modifier
) {
  when (task.type) {
    TaskType.Choice -> {
      val options = task.options
      Column(
        verticalArrangement = Arrangement.spacedBy(12.dp)
      ) {
        options?.forEach { option ->
          OptionCheckbox(
            text = option.text,
            isError = option.isError,
            selected = option.isChosen,
            onCheckedChange = {
              onOptionClick(option.id)
            }
          )
        }
      }
    }

    TaskType.Gap -> {
      VSpacer(16.dp)
      val gaps = task.gaps
      gaps?.forEach { gap ->
        VSpacer(4.dp)
        GapTemplate(
          modifier = modifier,
          gap = gap,
          onGapValueChange = { newValue ->
            onGapValueChange(gap, newValue)
          }
        )
      }
    }

    TaskType.Code -> {
      val code = task.code
      val highlights = remember(code?.isError, code?.userAnswer) {
        mutableStateOf(
          Highlights
            .Builder(code = code?.userAnswer.orEmpty())
            .language(code?.language ?: SyntaxLanguage.DEFAULT)
            .theme(
              if (code?.isError == true) errorTheme else SyntaxThemes.default()
            )
            .build()
        )
      }
      CodeEditText(
        modifier = Modifier
          .heightIn(min = 360.dp),
        isError = code?.isError == true,
        highlights = highlights.value,
        onValueChange = { newValue ->
          highlights.value = highlights.value.getBuilder()
            .code(newValue)
            .build()
          onCodeChange(newValue)
        }
      )
    }
  }
}

@Composable
private fun GapTemplate(
  gap: Task.Gap,
  onGapValueChange: (String) -> Unit,
  modifier: Modifier = Modifier
) {
  val separateGaps = gap.template.split(GAP_SEPARATOR)
  Row(
    modifier = modifier
      .fillMaxWidth()
      .padding(12.dp),
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.spacedBy(8.dp)
  ) {
    separateGaps.forEachIndexed { index, text ->
      Text(
        textAlign = TextAlign.Start,
        text = text,
        style = MaterialTheme.typography.displaySmall
      )
      if (index != separateGaps.size - 1) {
        TextField(
          modifier = Modifier
            .wrapContentWidth()
            .defaultMinSize(minWidth = 60.dp, minHeight = 40.dp)
            .widthIn(max = 120.dp),
          value = gap.userAnswer,
          singleLine = true,
          isError = gap.isError,
          onValueChange = onGapValueChange,
          textStyle = MaterialTheme.typography.displaySmall,
          colors = TextFieldDefaults.colors(
            errorTextColor = MaterialTheme.colorScheme.error
          ),
        )
      }
    }
  }
}

@Composable
private fun XpCard(
  value: String,
  modifier: Modifier = Modifier
) {
  Column(
    modifier = modifier
      .widthIn(max = 120.dp)
      .background(
        shape = RoundedCornerShape(24.dp),
        color = MaterialTheme.colorScheme.primaryContainer
      ),
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    Text(
      text = stringResource(R.string.xp).lowercase(),
      style = MaterialTheme.typography.bodyMedium,
      color = MaterialTheme.colorScheme.onPrimaryContainer
    )
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(4.dp)
        .background(
          shape = RoundedCornerShape(24.dp),
          color = MaterialTheme.colorScheme.onPrimaryContainer
        )
        .padding(
          vertical = 20.dp,
          horizontal = 26.dp
        ),
      horizontalArrangement = Arrangement.Center
    ) {
      Icon(
        painter = painterResource(R.drawable.ic_lightning),
        contentDescription = "xp",
        tint = Color.Unspecified
      )
      HSpacer(8.dp)
      Text(
        text = value,
        style = MaterialTheme.typography.titleSmall,
        color = MaterialTheme.colorScheme.outline
      )
    }
  }
}

private val errorTheme = SyntaxTheme(
  key = "error_theme",
  code = 0xFF4B4B,
  keyword = 0xFF4B4B,
  string = 0xFF4B4B,
  literal = 0xFF4B4B,
  comment = 0xFF4B4B,
  metadata = 0xFF4B4B,
  multilineComment = 0xFF4B4B,
  punctuation = 0xFF4B4B,
  mark = 0xFF4B4B
)

private const val GAP_SEPARATOR = "%g%"