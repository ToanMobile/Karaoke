package skymusic.com.vn.karaoke.data.model

data class PlayEnt(var merchant_id: Long = 1,//id dia diem phat
                   var username: String? = "Sky",//ten dang nhap
                   var song_name: String? = "",//ten bai hat detect
                   var singer: String? = "",
                   var artist: String? = "",
                   var sky_key: String? = "",
                   var timestamp: Long = 0,
                   var ip: String? = "",
                   var device_id: String? = "",
                   var app_version: String? = "",
                   var relatedRight: Boolean = false, //true
                   var copyRight: Boolean = false, //false
                   var vcpmcRight: Boolean = false) {
    override fun toString(): String {
        return "PlayEnt(merchant_id=$merchant_id, username=$username, song_name=$song_name, singer=$singer, artist=$artist, sky_key=$sky_key, timestamp=$timestamp, ip=$ip, device_id=$device_id, app_version=$app_version, relatedRight=$relatedRight, copyRight=$copyRight, vcpmcRight=$vcpmcRight)"
    }
}
