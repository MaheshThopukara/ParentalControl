package com.mahesh.parentalcontrol.presentation.pin.forgot

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mahesh.parentalcontrol.domain.usecase.pin.GetSecurityQuestionsUseCase
import com.mahesh.parentalcontrol.domain.usecase.pin.VerifySecurityAnswersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecoverByQuestionsViewModel @Inject constructor(
    private val getQuestions: GetSecurityQuestionsUseCase,
    private val verifyAnswers: VerifySecurityAnswersUseCase
) : ViewModel() {
    private val _ui = MutableStateFlow(RecoverByQuestionsUiState())
    val ui: StateFlow<RecoverByQuestionsUiState> = _ui

    init {
        viewModelScope.launch {
            val qs = getQuestions()
            _ui.update { it.copy(questions = qs) }
        }
    }

    fun onAnswerChanged(key: String, value: String) {
        _ui.update { st ->
            st.copy(answers = st.answers.toMutableMap().apply { put(key, value) })
        }
    }

    fun onSubmit() {
        val st = _ui.value
        if (st.answers.isEmpty()) {
            _ui.update { it.copy(error = "Please answer at least ${st.minRequired} questions.") }
            return
        }
        viewModelScope.launch {
            _ui.update { it.copy(isSubmitting = true, error = null) }
            val ok = verifyAnswers(st.answers, st.minRequired)
            _ui.update {
                it.copy(
                    isSubmitting = false,
                    proceedNext = ok,
                    error = if (!ok) "At least ${st.minRequired} answers must match." else null
                )
            }
        }
    }
}