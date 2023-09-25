package dipl.project.loyaltyperks.app

import android.app.Application
import dipl.project.loyaltyperks.di.modules.startKoin

class LoyaltyPerksApplication:Application() {
    init {
        instance = this
    }

    companion object {
        private var instance: LoyaltyPerksApplication? = null

        fun applicationContext(): LoyaltyPerksApplication {
            return instance as LoyaltyPerksApplication
        }
    }

    override fun onCreate() {
        super.onCreate()
        startKoin(applicationContext())

    }
}