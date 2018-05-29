package skymusic.com.vn.karaoke.ui.home

import com.acrcloud.rec.sdk.IACRCloudListener
import com.toan_itc.core.base.CoreBaseDaggerFragment
import skymusic.com.vn.karaoke.R


class HomeFragment : CoreBaseDaggerFragment<HomeViewModel>(), IACRCloudListener {

    override fun getViewModel(): Class<HomeViewModel> {
        return HomeViewModel::class.java
    }

    override fun setLayoutResourceID(): Int {
        return R.layout.fragment_home
    }

    override fun initData() {

    }

    override fun initView() {

    }

    override fun onResult(p0: String?) {

    }

    override fun onVolumeChanged(p0: Double) {

    }

}
