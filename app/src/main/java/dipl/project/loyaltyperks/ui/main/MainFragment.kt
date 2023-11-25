package dipl.project.loyaltyperks.ui.main

import androidx.fragment.app.Fragment
import dipl.project.loyaltyperks.databinding.FragmentMainBinding


class MainFragment : Fragment() {

    // Binding
    private lateinit var binding: FragmentMainBinding

    override fun getContext(): MainActivity? {
        return activity as MainActivity
    }

}