package dipl.project.loyaltyperks.model

data class QRCodeData(var programName: String,
                      var loyaltyClass: String,
                      var storeName: String,
                      var cardColor:String,
                      var imageUrl: String,
                      var balance:Int
                      )
