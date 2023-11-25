package dipl.project.loyaltyperks.di.modules

import dipl.project.loyaltyperks.viewmodel.CardViewModel
import dipl.project.loyaltyperks.viewmodel.UserViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val ViewModelModule = module {
    viewModel { UserViewModel() }
    viewModel { CardViewModel() }
}
