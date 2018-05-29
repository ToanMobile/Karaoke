package skymusic.com.vn.karaoke.data.model


data class CheckSong(
        val songId: Int = 0, //45397
        val songName: String = "", //Niệm Khúc Cuối
        val songKey: String = "", //QQA0oRzfqNWh
        val singer: String = "", //Nhật Tinh Anh, Vy Oanh
        val artist: String = "", //Ngô Thụy Miên
        val relatedRight: Boolean = false, //true
        val copyRight: Boolean = false, //false
        val type: String = "", //AUDIO
        val viewOnline: Int = 0, //0
        val viewOffline: Int = 0, //0
        val exclusive: Boolean = false //false
)