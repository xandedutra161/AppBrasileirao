package com.example.appbrasileirao.ui.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appbrasileirao.data.model.player.PlayerResponse
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
class DetailsTeamViewModel @Inject constructor(
    private val repository: BrasileiraoRepository
): ViewModel(){
    private val _details = MutableStateFlow<ResourceState<PlayerResponse>>(ResourceState.Loading())
    val details: StateFlow<ResourceState<PlayerResponse>> = _details

    fun fetch(teamId: Int) = viewModelScope.launch  {
        safeFetch(teamId)
    }

    private suspend fun safeFetch(teamId: Int){
        _details.value = ResourceState.Loading()
        try {
            val response = repository.getPlayers(teamId)
            _details.value = handleResponse(response)
        }catch (t: Throwable){
            when(t){
                is IOException -> _details.value = ResourceState.Error("Erro de conexão com a internet")
                else -> _details.value = ResourceState.Error("Erro na conversão")
            }
        }
    }

    private fun handleResponse(response: Response<PlayerResponse>): ResourceState<PlayerResponse> {
        if(response.isSuccessful){
            response.body()?.let {values ->
                return ResourceState.Sucess(values)
            }
        }
        return ResourceState.Error(response.message())
    }
}