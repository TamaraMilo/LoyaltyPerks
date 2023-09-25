package dipl.project.loyaltyperks.di.modules

import dipl.project.loyaltyperks.model.UserViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val ViewModelModule = module{
    viewModel{UserViewModel()}
}
