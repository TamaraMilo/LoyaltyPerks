package dipl.project.loyaltyperks.ui.mainUser

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import dipl.project.loyaltyperks.R
import dipl.project.loyaltyperks.databinding.FragmentMyCardsBinding
import dipl.project.loyaltyperks.model.CardViewModel
import dipl.project.loyaltyperks.ui.mainUser.adapters.GridViewAdapter
import org.koin.android.ext.android.inject


class MyCardsFragment : Fragment() {

    // Binding
    private lateinit var binding: FragmentMyCardsBinding

    private val cardViewModel: CardViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentMyCardsBinding.inflate(inflater, container, false)

        binding.bAddCard.setOnClickListener {
            val transaction = activity?.supportFragmentManager?.beginTransaction()
            transaction?.replace(R.id.mainActivityLayout, ScanUserFragment())
            transaction?.commit()
        }

        cardViewModel.returnUserCards {
            it?.let { cards ->
                context?.let {con->
                    val adapter = GridViewAdapter(con,cards)
                    binding.gridViewCards.adapter = adapter
                }

            }
        }





        return binding.root
    }


}