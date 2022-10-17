package com.most4dev.acquaintanceapp.fragments

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.most4dev.acquaintanceapp.R
import com.most4dev.acquaintanceapp.activity.MainActivity
import com.most4dev.acquaintanceapp.models.QuestionnaireModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_detail_questionnaire.*

class DetailQuestionnaireFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_detail_questionnaire, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (requireArguments().getSerializable("QuestionnaireModelItem") != null) {
            setData(requireArguments().getSerializable("QuestionnaireModelItem") as QuestionnaireModel)
        }

        appCompatButtonOpenChat.setOnClickListener {
            var bundle = Bundle()
            bundle.putSerializable(
                "QuestionnaireModelChat",
                requireArguments().getSerializable("QuestionnaireModelItem") as QuestionnaireModel
            )
            (requireActivity() as MainActivity).navController.navigate(
                R.id.action_detailQuestionnaireFragment_to_chatFragment,
                bundle
            )
        }

        actionButtonLike.setOnClickListener{
            actionButtonLike.setImageResource(R.drawable.ic_like_press)
            actionButtonLike.backgroundTintList = ColorStateList.valueOf(
                Color.parseColor("#9A2546")
            )
            Toast.makeText(requireContext(), getText(R.string.send_like), Toast.LENGTH_SHORT).show()
        }

        actionButtonStar.setOnClickListener{
            actionButtonStar.setImageResource(R.drawable.ic_press_star)
            actionButtonStar.backgroundTintList = ColorStateList.valueOf(
                Color.parseColor("#9A2546")
            )
            Toast.makeText(requireContext(), getText(R.string.send_star), Toast.LENGTH_SHORT).show()
        }


    }

    @SuppressLint("SetTextI18n")
    private fun setData(questionnaireModel: QuestionnaireModel) {
        Picasso.get()
            .load(questionnaireModel.image)
            .into(imageViewQuestionnaireItem)

        descriptionQuestionnaireItem.text =
            requireContext().getString(R.string.name) + ": " + questionnaireModel.name + "\n" + "\n" +
                    requireContext().getString(R.string.age) + ": " + questionnaireModel.age + "\n" + "\n" +
                    requireContext().getString(R.string.description) + ": " + questionnaireModel.description


    }
}