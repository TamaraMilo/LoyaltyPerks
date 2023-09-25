package dipl.project.loyaltyperks.di.modules

import dipl.project.loyaltyperks.app.LoyaltyPerksApplication
import dipl.project.loyaltyperks.di.modules.ViewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidFileProperties
import org.koin.core.context.startKoin

fun startKoin(application:LoyaltyPerksApplication ) {
    startKoin {
        androidContext(application)
        androidFileProperties()
        modules(ViewModelModule)
    }
}