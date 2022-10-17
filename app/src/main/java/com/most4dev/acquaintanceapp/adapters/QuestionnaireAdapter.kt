package com.most4dev.acquaintanceapp.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.most4dev.acquaintanceapp.databinding.ItemQuestionnaireBinding
import com.most4dev.acquaintanceapp.models.QuestionnaireModel
import com.squareup.picasso.Picasso

class QuestionnaireAdapter(
    private var listQuestionnaire: List<QuestionnaireModel>
) : RecyclerView.Adapter<QuestionnaireAdapter.QuestionnaireViewHolder>() {

    var clickURL: ((QuestionnaireModel) -> Unit)? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): QuestionnaireAdapter.QuestionnaireViewHolder {
        val binding = ItemQuestionnaireBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return QuestionnaireViewHolder(binding)
    }

    @SuppressLint("ClickableViewAccessibility", "SetTextI18n")
    override fun onBindViewHolder(
        holder: QuestionnaireAdapter.QuestionnaireViewHolder,
        position: Int
    ) {

        Picasso.get()
            .load(listQuestionnaire[position].image)
            .fit()
            .into(holder.binding.imageViewQuestionnaire)

        holder.binding.textViewNameQuestionnaire.text = listQuestionnaire[position].name
        holder.binding.textViewAgeQuestionnaire.text = listQuestionnaire[position].age

    }

    override fun getItemCount() = listQuestionnaire.size

    @SuppressLint("NotifyDataSetChanged")
    fun setItems(list: List<QuestionnaireModel>) {
        listQuestionnaire = list
        notifyDataSetChanged()
    }

    inner class QuestionnaireViewHolder(val binding: ItemQuestionnaireBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.cardViewQuestionnaire.setOnClickListener {
                clickURL?.invoke(
                    listQuestionnaire[adapterPosition]
                )
            }
        }
    }

}