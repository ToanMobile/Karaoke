package skymusic.com.vn.karaoke.ui.home

import android.arch.lifecycle.ViewModelProvider
import android.databinding.DataBindingComponent
import com.toan_itc.core.architecture.AppExecutors
import com.toan_itc.core.base.CoreBaseDaggerFragment
import com.toan_itc.core.binding.FragmentDataBindingComponent
import dagger.android.AndroidInjection
import dagger.android.support.AndroidSupportInjection
import skymusic.com.vn.karaoke.R
import skymusic.com.vn.karaoke.data.service.ApiService
import javax.inject.Inject


class HomeFragment : CoreBaseDaggerFragment<HomeViewModel>() {
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
