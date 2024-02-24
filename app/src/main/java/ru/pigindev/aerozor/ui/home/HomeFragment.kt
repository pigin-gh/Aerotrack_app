package ru.pigindev.aerozor.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import ru.pigindev.aerozor.R
import ru.pigindev.aerozor.data.DataFetcher
import ru.pigindev.aerozor.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var dataFetcher: DataFetcher

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dataFetcher = DataFetcher(this)
    }

    fun updateCO2(co2: Double) {
        binding.eco2ValueTv.text = getString(R.string.co2_value, co2)
    }

    fun updateHum(hum: Double) {
        binding.humValueTv.text = getString(R.string.hum_value, hum)
    }

    fun updateTemp(temp: Double) {
        binding.tempValueTv.text = getString(R.string.temp_value, temp)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        dataFetcher.cancelFetch()
        _binding = null
    }
}