package dipl.project.loyaltyperks.ui.mainUser

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import dipl.project.loyaltyperks.R
import dipl.project.loyaltyperks.databinding.FragmentMyCardsBinding


class MyCardsFragment : Fragment() {

    // Binding
    private lateinit var binding:FragmentMyCardsBinding

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


        return binding.root
    }

}