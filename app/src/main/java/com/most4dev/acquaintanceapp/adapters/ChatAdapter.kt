package com.most4dev.acquaintanceapp.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.most4dev.acquaintanceapp.databinding.ItemMessageChatBinding
import com.most4dev.acquaintanceapp.managers.PhotoManager
import com.most4dev.acquaintanceapp.managers.SharedPreferenceManager
import com.most4dev.acquaintanceapp.models.MessageModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_message_chat.view.*

class ChatAdapter(
    private var context: Context,
    private var listMessage: List<MessageModel>
): RecyclerView.Adapter<ChatAdapter.ChatViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ChatAdapter.ChatViewHolder {
        val binding = ItemMessageChatBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return ChatViewHolder(binding)
    }

    @SuppressLint("ClickableViewAccessibility", "SetTextI18n")
    override fun onBindViewHolder(
        holder: ChatAdapter.ChatViewHolder,
        position: Int
    ) {

        val msg: MessageModel = listMessage[position]
        when (msg.type) {
            MessageModel.TYPE_RECEIVED -> {
                holder.binding.constraintLayoutLeft.visibility = View.VISIBLE
                holder.binding.constraintLayoutRight.visibility = View.GONE
                holder.binding.leftMsg.text =
                    listMessage[position].questionnaireModel.name +"\n\n"+
                            msg.content
                Picasso.get()
                    .load(listMessage[position].questionnaireModel.image)
                    .fit()
                    .into(holder.binding.circleImageViewLeft)

            }
            MessageModel.TYPE_SENT -> {
                holder.binding.constraintLayoutLeft.visibility = View.GONE
                holder.binding.constraintLayoutRight.visibility = View.VISIBLE
                holder.binding.rightMsg.text =
                    SharedPreferenceManager(context)
                        .getPersonModel().name + "\n\n"+
                            msg.content
                holder
                    .binding
                    .leftLayout
                    .left_msg
                    .setTextColor(Color.WHITE)
                holder.binding.circleImageViewRight.setImageBitmap(
                    PhotoManager.stringToBitmap(
                        SharedPreferenceManager(context)
                            .getPersonModel()
                            .imageCode
                    )
                )
            }
        }

    }

    override fun getItemCount() = listMessage.size

    @SuppressLint("NotifyDataSetChanged")
    fun setItems(list: List<MessageModel>) {
        listMessage = list
        notifyDataSetChanged()
    }

    fun getItems(): List<MessageModel>{
        return listMessage
    }

    inner class ChatViewHolder(val binding: ItemMessageChatBinding) :
        RecyclerView.ViewHolder(binding.root)

}