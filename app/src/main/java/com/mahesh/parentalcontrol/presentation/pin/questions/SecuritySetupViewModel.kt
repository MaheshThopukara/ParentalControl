package com.mahesh.parentalcontrol.presentation.pin.questions

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mahesh.parentalcontrol.domain.usecase.pin.GetSecurityQuestionsUseCase
import com.mahesh.parentalcontrol.domain.usecase.pin.SaveSecurityAnswersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SecuritySetupViewModel @Inject constructor(
    private val getQuestions: GetSecurityQuestionsUseCase,
    private val saveAnswers: SaveSecurityAnswersUseCase
) : ViewModel() {

    private val _ui = MutableStateFlow(SecuritySetupUiState())
    val ui: StateFlow<SecuritySetupUiState> = _ui

    init {
        viewModelScope.launch {
            val qs = getQuestions()
            _ui.update { it.copy(questions = qs) }
        }
    }

    fun onAnswerChanged(id: String, value: String) {
        _ui.update { it.copy(answers = it.answers.toMutableMap().apply { put(id, value) }) }
    }

    fun onSave() {
        val st = _ui.value
        // Require all 5 answers non-blank
        if (st.questions.any { q -> st.answers[q.key].isNullOrBlank() }) {
            _ui.update { it.copy(error = "Please answer all questions") }
            return
        }
        viewModelScope.launch {
            _ui.update { it.copy(isSaving = true, error = null) }
            try {
                saveAnswers(st.answers)
                _ui.update { it.copy(isSaving = false, proceedNext = true) }
            } catch (t: Throwable) {
                _ui.update { it.copy(isSaving = false, error = t.message ?: "Failed to save answers") }
            }
        }
    }
}