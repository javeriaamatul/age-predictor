package com.javeria.agepredictor

import `in`.zapr.panel.viewmodel.BaseViewModel
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.javeria.agepredictor.data.remote.Status
import com.javeria.agepredictor.data.remote.model.UserDetails

class MainActivity : AppCompatActivity(), View.OnClickListener, TextWatcher {

    private var mainViewModel: MainViewModel? = null
    private var etName: EditText? = null
    private var btnPredictAge: Button? = null
    private var name = ""
    private var tvAge: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        etName = findViewById(R.id.et_name)
        btnPredictAge = findViewById(R.id.btn_age)
        tvAge = findViewById(R.id.tv_age)
        setListener()
        initializeViewModel()
        observeUserDetails()
    }

    private fun setListener() {
        btnPredictAge?.setOnClickListener(this)
        etName?.addTextChangedListener(this)
    }
    private fun initializeViewModel() {
        mainViewModel =
            ViewModelProvider(this, BaseViewModel()).get(MainViewModel::class.java)
    }

    override fun onClick(v: View?) {
       when(v?.id) {
           R.id.btn_age -> {
                if (name.length > 0)
                    getAge()
                else
                    Toast.makeText(this, "Enter valid name", Toast.LENGTH_LONG).show()
           }
       }
    }

    override fun afterTextChanged(p0: Editable?) {
        name = etName?.text.toString()
    }
    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
    }

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
    }

    private fun getAge() {
        if (isInternetAvailable(this) ){
            mainViewModel?.getUserAge(name)
        }
        else {
            Toast.makeText(this, "No internet", Toast.LENGTH_LONG).show()
        }
    }


    private fun observeUserDetails() {
        mainViewModel?.userDetails?.observe(this, Observer { res ->
            if (Status.SUCCESS == res.status ) {
                val userDetails =  res.data as UserDetails
                val age = userDetails.age.toString()
                tvAge?.text = resources.getString(R.string.your_age_is) + " "+age
                createDialog(age)
            }
        })
    }

    private fun isInternetAvailable(context: Context): Boolean {
        var result = false
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val networkCapabilities = connectivityManager.activeNetwork ?: return false
            val actNw =
                connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false
            result = when {
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                else -> false
            }
        } else {
            connectivityManager.run {
                connectivityManager.activeNetworkInfo?.run {
                    result = when (type) {
                        ConnectivityManager.TYPE_WIFI -> true
                        ConnectivityManager.TYPE_MOBILE -> true
                        ConnectivityManager.TYPE_ETHERNET -> true
                        else -> false
                    }

                }
            }
        }

        return result
    }

    private fun createDialog(msg: String) {
        val builder: AlertDialog.Builder? = this?.let {
            AlertDialog.Builder(it)
        }
        builder?.setMessage(msg)?.setTitle("Your age is ")
        val dialog: AlertDialog? = builder?.create()
        dialog?.show()
    }
}