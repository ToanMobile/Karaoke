package skymusic.com.vn.karaoke.ui.home

import androidx.navigation.fragment.findNavController
import com.orhanobut.logger.Logger
import com.toan_itc.core.architecture.observer
import com.toan_itc.core.base.CoreBaseDataFragment
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import skymusic.com.vn.karaoke.BuildConfig
import skymusic.com.vn.karaoke.R
import skymusic.com.vn.karaoke.event.InfoSongEvent


class HomeFragment : CoreBaseDataFragment<HomeViewModel>() {
   // var dataBindingComponent: DataBindingComponent = FragmentDataBindingComponent(this)

    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }

    override fun getViewModel(): Class<HomeViewModel> {
        return HomeViewModel::class.java
    }

    override fun setLayoutResourceID(): Int {
        return R.layout.fragment_home
    }

    override fun initData() {

    }

    override fun initView() {
        viewModel.startService(context)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onInfoSongEvent(infoSongEvent: InfoSongEvent) {
        viewModel.setInfoSong(infoSongEvent.songName,infoSongEvent.singer,BuildConfig.OP,BuildConfig.CLIENT_KEY,BuildConfig.CLIENT_SECRET)
        viewModel.checkSong.observer(this){resource->
            Logger.e("checkSong="+resource.data.toString())
            findNavController().navigate(R.id.details_dest)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.stopService(context)
    }
}
