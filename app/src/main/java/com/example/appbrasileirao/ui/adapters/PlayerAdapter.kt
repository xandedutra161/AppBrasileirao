package com.example.appbrasileirao.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.appbrasileirao.data.model.player.Response
import com.example.appbrasileirao.databinding.ItemPlayerBinding
import java.text.SimpleDateFormat

class PlayerAdapter : RecyclerView.Adapter<PlayerAdapter.PlayerViewHolder>() {

    inner class PlayerViewHolder(val binding: ItemPlayerBinding) :
        RecyclerView.ViewHolder(binding.root)


    private val differCallback = object : DiffUtil.ItemCallback<Response>() {
        override fun areItemsTheSame(oldItem: Response, newItem: Response): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }

        override fun areContentsTheSame(oldItem: Response, newItem: Response): Boolean {
            return oldItem.player.id == newItem.player.id && oldItem.player.age == newItem.player.age &&
                    oldItem.player.firstname == newItem.player.firstname && oldItem.player.height == newItem.player.height &&
                    oldItem.player.injured == newItem.player.injured && oldItem.player.lastname == newItem.player.lastname &&
                    oldItem.player.name == newItem.player.name && oldItem.player.nationality == newItem.player.nationality &&
                    oldItem.player.photo == newItem.player.photo && oldItem.player.weight == newItem.player.weight &&
                    oldItem.player.birth.country == newItem.player.birth.country && oldItem.player.birth.date == newItem.player.birth.date &&
                    oldItem.player.birth.place == newItem.player.birth.place
        }
    }

    private val differ = AsyncListDiffer(this, differCallback)

    var players: List<Response>
        get() = differ.currentList
        set(value) = differ.submitList(value)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayerViewHolder {
        return PlayerViewHolder(
            ItemPlayerBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun getItemCount(): Int = players.size

    override fun onBindViewHolder(holder: PlayerViewHolder, position: Int) {
        val player = players[position]
        holder.binding.apply {
            val convertData = convertDateToBrazilianFormat(player.player.birth.date)

            val age = convertNullToEmptyString(player.player.age.toString())
            val birth = convertNullToEmptyString(convertData)
            val height_player = convertNullToEmptyString(player.player.height)
            val weight_player = convertNullToEmptyString(player.player.weight)
            val nationality = convertNullToEmptyString(player.player.nationality)


            tvNamePlayer.text = player.player.name + " " + player.player.lastname
            tvDescriptionPlayer.text =
                "Idade: " + age + "\n" +
                "Nascimento: " + birth + "\n" +
                "Altura: " + height_player + "\n" +
                "KG: " + weight_player + "\n" +
                "Nacionalidade: " + nationality
            Glide.with(holder.itemView.context)
                .load(player.player.photo)
                .into(imgPlayer)
        }
    }

    private fun convertDateToBrazilianFormat(dateString: String): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd")
        val date = dateFormat.parse(dateString)
        val brazilianDateFormat = SimpleDateFormat("dd/MM/yyyy")
        return brazilianDateFormat.format(date)
    }

    fun convertNullToEmptyString(text: String?): String {
        return text ?: ""
    }
}