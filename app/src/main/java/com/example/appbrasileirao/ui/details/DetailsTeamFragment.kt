package com.example.appbrasileirao.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.appbrasileirao.R
import com.example.appbrasileirao.data.model.team.Response
import com.example.appbrasileirao.databinding.FragmentDetailsTeamBinding
import com.example.appbrasileirao.ui.adapters.PlayerAdapter
import com.example.appbrasileirao.ui.base.BaseFragment
import com.example.appbrasileirao.ui.state.ResourceState
import com.example.appbrasileirao.util.hide
import com.example.appbrasileirao.util.limitDescription
import com.example.appbrasileirao.util.show
import com.example.appbrasileirao.util.toast
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class DetailsTeamFragment : BaseFragment<FragmentDetailsTeamBinding, DetailsTeamViewModel>() {
    override val viewModel: DetailsTeamViewModel by viewModels()

    private val args: DetailsTeamFragmentArgs by navArgs()
    private val playerAdapter by lazy { PlayerAdapter() }
    private lateinit var response: Response

    override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        response = args.response
        viewModel.fetch(response.team.id)
        setupRecycleView()
        onLoadedTeam(response)
        collectObserver()
        binding.tvDescriptionTeamDetails.setOnClickListener{
            onShowDialog(response)
        }
    }
    
    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentDetailsTeamBinding =
        FragmentDetailsTeamBinding.inflate(inflater, container, false)

    private fun setupRecycleView() = with(binding){
        rvPlayers.apply {
            adapter = playerAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    private fun onShowDialog(response: Response) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(response.team.name)
            .setMessage(response.team.name)
            .setNegativeButton(getString(R.string.close_dialog)){ dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    private fun collectObserver() = lifecycleScope.launch {
        viewModel.details.collect { result ->
            when(result) {
                is ResourceState.Sucess -> {
                    binding.progressBarDetail.hide()
                    result.data?.let { values ->
                        if (values.response.isNotEmpty()){
                            playerAdapter.players = values.response.toList()
                        } else {
                            toast(getString(R.string.empty_list_players))
                        }
                    }
                }
                is ResourceState.Error -> {
                    binding.progressBarDetail.hide()
                    result.message?.let { message ->
                        Timber.tag("DetailsTeam").e("Error -> $message")
                        toast(getString(R.string.an_error_occurred))
                    }
                }
                is ResourceState.Loading -> {
                    binding.progressBarDetail.show()
                }
                else -> {
                }
            }
        }
    }

    private fun onLoadedTeam(response: Response) = with(binding){
        tvNameTeamDetails.text = response.team.name
        if(response.team.name.isEmpty()) {
            tvDescriptionTeamDetails.text =
                requireContext().getString(R.string.text_description_empty)
        } else {
            tvDescriptionTeamDetails.text = response.venue.name.limitDescription(100)
            tvCapacity.text = "Capacidade de torcedores " + response.venue.capacity.toString()
        }
        Glide.with(requireContext())
            .load(response.venue.image)
            .into(imgTeamDetails)
    }
    
}
