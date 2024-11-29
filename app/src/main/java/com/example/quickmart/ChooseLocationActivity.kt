package com.example.quickmart

import android.content.pm.PackageManager
import android.location.Geocoder
import android.os.Bundle
import android.widget.Adapter
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.quickmart.databinding.ActivityChooseLocationBinding
import com.example.quickmart.databinding.ActivitySignUpBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import java.util.Locale

class ChooseLocationActivity : AppCompatActivity() {
    lateinit var binding: ActivityChooseLocationBinding

    companion object {
        const val LOCATION_REQUEST_CODE = 100
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityChooseLocationBinding.inflate(layoutInflater)
        setContentView(binding.root)
//        val locationList = arrayListOf(
//            "Andhra Pradesh",
//            "Arunachal Pradesh",
//            "Assam",
//            "Bihar",
//            "Chhattisgarh",
//            "Goa",
//            "Gujarat",
//            "Haryana",
//            "Himachal Pradesh",
//            "Jharkhand",
//            "Karnataka",
//            "Kerala",
//            "Madhya Pradesh",
//            "Maharashtra",
//            "Manipur",
//            "Meghalaya",
//            "Mizoram",
//            "Nagaland",
//            "Odisha",
//            "Punjab",
//            "Rajasthan",
//            "Sikkim",
//            "Tamil Nadu",
//            "Telangana",
//            "Tripura",
//            "Uttar Pradesh",
//            "Uttarakhand",
//            "West Bengal"
//        )
//        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, locationList)
//        val autoCompleteTextView = binding.listOfLocation
//        autoCompleteTextView.setAdapter(adapter)


        getCurrentLocation()

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray,
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode== LOCATION_REQUEST_CODE){
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                getCurrentLocation()
            }else{
                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()

            }
        }

    }

    fun checkLocationPermission():Boolean {
        if (ActivityCompat.checkSelfPermission(this,android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(this,android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){

            ActivityCompat.requestPermissions(
                this, arrayOf(android.Manifest.permission.ACCESS_COARSE_LOCATION),
                LOCATION_REQUEST_CODE
            )
            return false
        }
        return true
    }

    fun getCurrentLocation(){
        if (checkLocationPermission()){
            val fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
            fusedLocationProviderClient.lastLocation
                .addOnSuccessListener {
                    if (it != null) {
                        val latitude = it.latitude
                        val longitude = it.longitude
                        val geocoder = Geocoder(this, Locale.getDefault())
                        val addresses = geocoder.getFromLocation(latitude, longitude, 1)

                        if (!addresses.isNullOrEmpty()) {
                            val address = addresses[0].getAddressLine(0)

                            binding.listOfLocation.setText(address)
                        }
                        Toast.makeText(this, "Latitude: $latitude, Longitude: $longitude", Toast.LENGTH_SHORT).show()
                    }else{
                        Toast.makeText(this, "Unable to fetch location", Toast.LENGTH_SHORT).show()
                    }
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Failed to get location: ${it.message}", Toast.LENGTH_SHORT).show()
                }
        }
    }

    private fun checkGPSEnabled(){
        val loactionRequest=com.google.android.gms.location.LocationRequest.create().apply {
            priority=com.google.android.gms.location.Priority.PRIORITY_HIGH_ACCURACY
        }
        val builder= LocationSettingsRequest.Builder().addLocationRequest(loactionRequest)
        val settingsClient=LocationServices.getSettingsClient(this)
        val task=settingsClient.checkLocationSettings(builder.build())
        task.addOnSuccessListener {
            getCurrentLocation()
        }
        task.addOnFailureListener {exception ->
            if (exception is com.google.android.gms.common.api.ResolvableApiException) {
                try {
                    exception.startResolutionForResult(this, LOCATION_REQUEST_CODE)
                } catch (e: Exception) {
                    Toast.makeText(this, "Unable to request GPS enable", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

}