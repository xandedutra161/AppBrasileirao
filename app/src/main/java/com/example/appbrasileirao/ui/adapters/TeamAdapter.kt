package com.example.appbrasileirao.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.appbrasileirao.R
import com.example.appbrasileirao.data.model.team.Response
import com.example.appbrasileirao.databinding.ItemTeamBinding
import com.example.appbrasileirao.util.limitDescription
import com.example.appbrasileirao.util.loadImage

class TeamAdapter : RecyclerView.Adapter<TeamAdapter.TeamViewHolder>() {
    inner class TeamViewHolder(val binding: ItemTeamBinding) :
        RecyclerView.ViewHolder(binding.root)

    private val differCallback = object : DiffUtil.ItemCallback<Response>() {
        override fun areItemsTheSame(oldItem: Response, newItem: Response): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }

        override fun areContentsTheSame(oldItem: Response, newItem: Response): Boolean {
            return oldItem.team.id == newItem.team.id && oldItem.team.name == newItem.team.name && oldItem.team.code == newItem.team.code &&
                    oldItem.team.country == newItem.team.country && oldItem.team.founded == newItem.team.founded &&
                    oldItem.team.national == newItem.team.national && oldItem.team.logo == newItem.team.logo &&
                    oldItem.venue.id == newItem.venue.id && oldItem.venue.name == newItem.venue.name &&
                    oldItem.venue.address == newItem.venue.address && oldItem.venue.city == newItem.venue.city &&
                    oldItem.venue.capacity == newItem.venue.capacity && oldItem.venue.surface == newItem.venue.surface &&
                    oldItem.venue.image == newItem.venue.image
        }
    }

    private val differ = AsyncListDiffer(this, differCallback)

    var responses: List<Response>
        get() = differ.currentList
        set(value) = differ.submitList(value)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamViewHolder {
        return TeamViewHolder(
            ItemTeamBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun getItemCount(): Int = responses.size

    override fun onBindViewHolder(holder: TeamViewHolder, position: Int) {
        val response = responses[position]
        holder.binding.apply {
            tvNameCharacter.text = response.team.name
            if (response.venue.city == "") {
                tvDescriptionCharacter.text =
                    holder.itemView.context.getString(R.string.text_description_empty)
            } else {
                tvDescriptionCharacter.text = response.venue.city.limitDescription(100)
            }

            loadImage(
                imgCharacter,
                response.team.logo,
            )
        }

        holder.itemView.setOnClickListener {
            onItemClickListener?.let {
                it(response)
            }
        }
    }

    private var onItemClickListener: ((Response) -> Unit)? = null

    fun setOnClickListener(listener: (Response) -> Unit) {
        onItemClickListener = listener
    }

    fun getCharacterPosition(position: Int): Response {
        return responses[position]
    }


}