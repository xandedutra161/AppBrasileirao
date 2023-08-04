package com.example.appbrasileirao.ui.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appbrasileirao.R
import com.example.appbrasileirao.databinding.FragmentListTeamBinding
import com.example.appbrasileirao.ui.adapters.TeamAdapter
import com.example.appbrasileirao.ui.base.BaseFragment
import com.example.appbrasileirao.ui.state.ResourceState
import com.example.appbrasileirao.util.hide
import com.example.appbrasileirao.util.show
import com.example.appbrasileirao.util.toast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ListTeamFragment : BaseFragment<FragmentListTeamBinding, ListTeamViewModel>() {
    override val viewModel: ListTeamViewModel by viewModels()

    private val teamAdapter by lazy { TeamAdapter() }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        clickAdapter()
        collectObserver()
    }

    private fun collectObserver() = lifecycleScope.launch {
        viewModel.list.collect { resource ->
            when(resource){
                is ResourceState.Sucess -> {
                    resource.data?.let { values ->
                        binding.progressCircular.hide()
                        teamAdapter.responses = values.response.toList()
                    }
                }

                is ResourceState.Error -> {
                    binding.progressCircular.hide()
                    resource.message?.let{message ->
                        toast(getString(R.string.an_error_occurred))
                    }
                }

                is ResourceState.Loading -> {
                    binding.progressCircular.show()

                }
                else -> {}
            }
        }
    }


    private fun clickAdapter() {
        teamAdapter.setOnClickListener { Response ->
            val action = ListTeamFragmentDirections
                .actionListTeamFragmentToDetailsTeamFragment(Response)
            findNavController().navigate(action)
        }
    }

    private fun setupRecyclerView() = with(binding){
        rvCharacters.apply {
            adapter = teamAdapter
            layoutManager =  LinearLayoutManager(requireContext())
        }
    }

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentListTeamBinding = FragmentListTeamBinding.inflate(inflater, container, false)
}