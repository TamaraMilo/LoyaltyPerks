package dipl.project.loyaltyperks.ui.mainUser.adapters

import android.util.SparseArray
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import dipl.project.loyaltyperks.ui.mainUser.MyCardsFragment
import dipl.project.loyaltyperks.ui.mainUser.ProfileFragment

//class ViewPageAdapter(supportFragmentManager: FragmentManager ): FragmentPagerAdapter(supportFragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
//
//    private val mFragmentList = ArrayList<Fragment>()
//    private val mFragmentTitleList = ArrayList<String>()
//
//    override fun getCount(): Int {
//        return mFragmentList.size
//    }
//
//    override fun getItem(position: Int): Fragment {
//        return mFragmentList[position]
//    }
//
//    override fun getPageTitle(position: Int): CharSequence? {
//        return mFragmentTitleList[position]
//    }
//
//    fun addFragment(fragment: Fragment, title:String) {
//        mFragmentList.add(fragment)
//        mFragmentTitleList.add(title)
//    }
//}

class ViewPageAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int = NUM_ITEMS

    private val pageReferenceMap = SparseArray<Fragment>()

    override fun createFragment(position: Int): Fragment {
        val fragment = when(position) {
            FIRST_FRAGMENT -> MyCardsFragment()
            SECOND_FRAGMENT -> ProfileFragment()
            else -> MyCardsFragment()
        }
        pageReferenceMap.put(position, fragment)
        return fragment
    }


    companion object {
        private const val FIRST_FRAGMENT = 0
        private const val SECOND_FRAGMENT = 1
        private const val NUM_ITEMS = 2
    }


}