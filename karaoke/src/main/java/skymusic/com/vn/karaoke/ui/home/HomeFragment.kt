package skymusic.com.vn.karaoke.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.acrcloud.rec.sdk.IACRCloudListener
import skymusic.com.vn.karaoke.R

class HomeFragment : Fragment(), IACRCloudListener {

    private lateinit var viewModel: HomeViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)

    }

    override fun onResult(p0: String?) {

    }

    override fun onVolumeChanged(p0: Double) {

    }

}
