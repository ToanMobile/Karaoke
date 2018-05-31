package skymusic.com.vn.karaoke.ui.home

import com.toan_itc.core.base.CoreBaseDataFragment
import skymusic.com.vn.karaoke.R


class HomeFragment : CoreBaseDataFragment<HomeViewModel>() {
   // var dataBindingComponent: DataBindingComponent = FragmentDataBindingComponent(this)
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

}
