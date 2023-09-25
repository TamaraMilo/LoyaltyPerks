package dipl.project.loyaltyperks.data

data class payloadData(var iss:String,
                        var typ: String,
                       var iat:Int,
                       var payload: String
                       )