package com.most4dev.acquaintanceapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.most4dev.acquaintanceapp.activity.MainActivity
import com.most4dev.acquaintanceapp.R
import com.most4dev.acquaintanceapp.adapters.QuestionnaireAdapter
import com.most4dev.acquaintanceapp.viewModels.AcquaintanceViewModel
import kotlinx.android.synthetic.main.fragment_list_questionnaire.*
import java.util.ArrayList

class ListQuestionnaireFragment : Fragment() {

    private lateinit var acquaintanceViewModel: AcquaintanceViewModel
    private lateinit var adapterQuestionnaire: QuestionnaireAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        acquaintanceViewModel = ViewModelProvider(this)[AcquaintanceViewModel::class.java]
        return inflater.inflate(R.layout.fragment_list_questionnaire, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        createAdapter()

        acquaintanceViewModel.getListQuestionnaire()!!.observe(viewLifecycleOwner) {
            adapterQuestionnaire.setItems(it)
        }
    }

    private fun createAdapter() {
        adapterQuestionnaire = QuestionnaireAdapter(
            ArrayList()
        )

        adapterQuestionnaire.clickURL = {
            val bundle = Bundle()
            bundle.putSerializable("QuestionnaireModelItem", it)
            (requireActivity() as MainActivity).navController.navigate(
                R.id.action_listQuestionnaire_to_detailQuestionnaireFragment,
                bundle
            )
        }

        val recyclerQuestionnaireHome: RecyclerView = recyclerQuestionnaire
        recyclerQuestionnaireHome.layoutManager = GridLayoutManager(requireContext(), 2)
        recyclerQuestionnaireHome.adapter = adapterQuestionnaire
    }
}