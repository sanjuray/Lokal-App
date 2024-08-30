package com.practice.lokaljobs

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.practice.lokaljobs.databinding.ActivityMainBinding
import com.practice.lokaljobs.databinding.FragmentDashboardBinding
import com.practice.lokaljobs.fragments.dashboard.DashboardFragment
import com.practice.lokaljobs.utils.constants.Constants
import com.practice.lokaljobs.utils.errorLogs
import com.practice.lokaljobs.utils.showToast

class MainActivity : AppCompatActivity() {


    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadFragment(DashboardFragment.newInstance())
    }

    fun loadFragment(fragment: Fragment, bindingParent: Any? = null){
        try {
            val fragmentManager = supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            if(bindingParent == null){
                fragmentTransaction.replace(
                    binding.flContainerForFragmentsFragmentHost.id,
                    fragment
                )
                if(fragment !is DashboardFragment){
                    fragmentTransaction.addToBackStack(fragment.javaClass.canonicalName)
                }
            }else{
                fragmentTransaction.replace(
                    (bindingParent as FragmentDashboardBinding).flContainerForNavFragmentsFragmentHost.id,
                    fragment
                )
            }
            fragmentTransaction.commit()
        }catch (e: Exception){
            e.errorLogs("loadFragment@MainActivity")
        }
    }

    fun openAnyLink(url: String){
        try {
            val uri = Uri.parse(url)
            startActivity(Intent(Intent.ACTION_VIEW,uri))
        }catch (e: Exception){
            e.errorLogs("openAnyLink@MainActivity")
        }
    }

    fun shareContent(message: String){
        try {
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "text/plain"
            intent.putExtra(Intent.EXTRA_TEXT, message)
            startActivity(Intent.createChooser(intent, "Share Message via..."))
        }catch (e: Exception){
            e.errorLogs("shareContent@MainActivity")
        }
    }

    fun callingContent(number: String){
        try{
            if(checkPermission(Manifest.permission.CALL_PHONE, Constants.PHONE_PERMISSION_CODE)) {
                val intent = Intent(Intent.ACTION_CALL)
                intent.data = Uri.parse(number)
                startActivity(intent)
            }
        }catch (e: Exception){
            e.errorLogs("callingContent@MainActivity")
        }
    }

    private fun checkPermission(permission: String, requestCode: Int):Boolean{
        try {
            return if (ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_DENIED) {
                ActivityCompat.requestPermissions(this, arrayOf(permission), requestCode)
                false
            }else{
                true
            }
        }catch (e: Exception){
            e.errorLogs("checkPermission@MainActivity")
        }
        return false
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == Constants.PHONE_PERMISSION_CODE) {
            showToast(this,
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    "Call Permission Granted"
                }
                else {
                    "Call Permission Denied"
                }
            )

        }
    }
}