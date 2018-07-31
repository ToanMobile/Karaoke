package skymusic.com.vn.karaoke.data.model

data class GetSong(
        val song_id: Int = 0, // 1
        val file_sha1: String = "", // 8E785794EE9814FD8E4CAE3E3DA232574CE99B2D
        val song_name: String = "", // Chắc ai đó sẽ về
        val confidence: Int = 0, // 842
        val offset_seconds: Double = 0.0, // 5.43347
        val match_time: Double = 0.0, // 0.9910509586334229
        val offset: Int = 0, // 117
        val song_artist: String = "" // Sơn Tùng M-TP
)