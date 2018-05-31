package skymusic.com.vn.karaoke

import android.Manifest
import android.support.v4.app.Fragment
import com.blankj.utilcode.util.PermissionUtils
import com.orhanobut.logger.Logger
import com.toan_itc.core.base.CoreBaseActivity
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject

class MainActivity : CoreBaseActivity(), HasSupportFragmentInjector{
    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

    override fun supportFragmentInjector() = dispatchingAndroidInjector

    override fun setLayoutResourceID(): Int {
        return R.layout.activity_main
    }

    override fun initViews() {
        permission()
    }

    override fun initData() {

    }

    private fun permission() {
        PermissionUtils.permission(Manifest.permission.RECORD_AUDIO,
                Manifest.permission.ACCESS_NETWORK_STATE,
                Manifest.permission.INTERNET,
                Manifest.permission.ACCESS_WIFI_STATE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.VIBRATE)
                .callback(object : PermissionUtils.FullCallback {
                    override fun onGranted(permissionsGranted: MutableList<String>?) {

                    }

                    override fun onDenied(permissionsDeniedForever: MutableList<String>?, permissionsDenied: MutableList<String>?) {
                        Logger.e("permissionsDeniedForever=" + permissionsDeniedForever.toString() + "permissionsDenied=" + permissionsDenied.toString())
                    }
                })
                .request()
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 0) {
            supportFragmentManager.popBackStack()
        } else {
            finish()
        }
    }
}
