package hu.bme.aut.penzjegyzo.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import hu.bme.aut.penzjegyzo.R
import hu.bme.aut.penzjegyzo.data.Valuta
import hu.bme.aut.penzjegyzo.databinding.FragmentValtoBinding
import hu.bme.aut.penzjegyzo.network.NetworkManager
import hu.bme.aut.penzjegyzo.runOnUiThread
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.math.BigDecimal

class ValtoFragment: Fragment(), AdapterView.OnItemSelectedListener {

    private lateinit var binding: FragmentValtoBinding
    private var valuta: Valuta? = null
    private var from = "USD"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentValtoBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.let {
            ArrayAdapter.createFromResource(
                requireContext(),
                R.array.valutak,
                R.layout.spinner_item
            )
        }.also {adapter ->
            adapter?.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.spinner3.adapter = adapter
        }
        binding.spinner3.onItemSelectedListener = this

        binding.btnValto.setOnClickListener(){
            loadValutaData(binding.editTextNumber.text.toString().toDouble(),from)
        }
    }


    private fun loadValutaData(amount : Double, to : String){
        NetworkManager.getValuta(amount,to).enqueue(object : Callback<Valuta?>{
            override fun onResponse(call: Call<Valuta?>, response: Response<Valuta?>) {
                if(response.isSuccessful){
                    runOnUiThread {
                        valuta = response.body()
                        val a = valuta?.rates?.HUF
                        binding.tvConverted.text = a?.let { BigDecimal.valueOf(it).toPlainString() }
                    }
                    Log.d("jo", "onResponse: " + response.code())
                    Log.d("jo", "onResponse: " + (response.body()?.amount))
                    Log.d("jo", "onResponse: " + (response.body()?.date))
                    Log.d("jo", "onResponse: " + (response.body()?.base))
                    Log.d("jo", "onResponse: " + (response.body()?.rates?.HUF))
                }
                else{
                    Toast.makeText(this@ValtoFragment.context, "Error: " + response.message(), Toast.LENGTH_LONG).show()
                }
            }
            override fun onFailure(call: Call<Valuta?>, t: Throwable) {
                t.printStackTrace()
                Toast.makeText(this@ValtoFragment.context, "Network request error occured, check LOG", Toast.LENGTH_LONG).show()
            }
        })
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

        if (p0 != null) {
            from = p0.getItemAtPosition(p2).toString()
        }
        Log.d("helo", "onSelected $from")

    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
    }
}