package skymusic.com.vn.karaoke.ui.details

import android.databinding.DataBindingComponent
import com.toan_itc.core.base.CoreBaseDaggerFragment
import com.toan_itc.core.binding.FragmentDataBindingComponent
import skymusic.com.vn.karaoke.R

class DetailsFragment : CoreBaseDaggerFragment<DetailsViewModel>() {
  //  var dataBindingComponent: DataBindingComponent = FragmentDataBindingComponent(this)
    override fun getViewModel(): Class<DetailsViewModel> {
        return DetailsViewModel::class.java
    }

    override fun setLayoutResourceID(): Int {
        return R.layout.fragment_details
    }

    override fun initData() {

    }

    override fun initView() {

    }

}
