package com.example.appbrasileirao.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appbrasileirao.data.model.team.TeamResponse
import com.example.appbrasileirao.repository.BrasileiraoRepository
import com.example.appbrasileirao.ui.state.ResourceState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class ListTeamViewModel @Inject constructor(
    private val repository: BrasileiraoRepository
): ViewModel() {
    private val _list = MutableStateFlow<ResourceState<TeamResponse>>(ResourceState.Loading())
    val list: StateFlow<ResourceState<TeamResponse>> = _list

    init {
        fech()
    }

    private fun fech() = viewModelScope.launch {
        safeFetch()
    }

    private suspend fun safeFetch() {
        try {
            val response = repository.listTeams()
            _list.value = handleResponse(response)
        } catch (t: Throwable){
            when(t){
                is IOException -> _list.value = ResourceState.Error("Erro de conexão")
                else -> _list.value = ResourceState.Error("Falha na conversão de dados")
            }
        }
    }

    private fun handleResponse(response: Response<TeamResponse>): ResourceState<TeamResponse> {
        if(response.isSuccessful) {
            response.body()?.let { values ->
                return ResourceState.Sucess(values)
            }
        }
        return ResourceState.Error(response.message())
    }

}