package skymusic.com.vn.karaoke

import android.Manifest.permission.*
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.toan_itc.core.richutils.runDelayed
import com.toan_itc.core.utils.addFragment
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import kotlinx.android.synthetic.main.activity_main.*
import permissions.dispatcher.NeedsPermission
import permissions.dispatcher.RuntimePermissions
import skymusic.com.vn.karaoke.main.MainFragment
import javax.inject.Inject

@RuntimePermissions
class MainActivity : AppCompatActivity(), HasSupportFragmentInjector {
    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

    override fun supportFragmentInjector() = dispatchingAndroidInjector

    private var doubleBackToExitPressedOnce: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        startFragmentWithPermissionCheck()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        addFragment(MainFragment.newInstance(), R.id.container)
    }

    @NeedsPermission(RECORD_AUDIO, INTERNET, ACCESS_NETWORK_STATE, READ_EXTERNAL_STORAGE, WRITE_EXTERNAL_STORAGE, VIBRATE, READ_PHONE_STATE)
    fun startFragment() {}

    override fun onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            finish()
        }
        doubleBackToExitPressedOnce = true
        Snackbar.make(container, R.string.msg_exit, Snackbar.LENGTH_SHORT).show()
        runDelayed({ doubleBackToExitPressedOnce = false }, 2000)
    }
}
