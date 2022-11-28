package hu.bme.aut.navgraphfragmentdemo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import hu.bme.aut.navgraphfragmentdemo.databinding.FragmentMainBinding


class MainFragment : Fragment() {

    lateinit var binding: FragmentMainBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate(
            inflater, container, false
        )
        return binding.root
    }

    override fun onResume() {
        super.onResume()

        binding.btnSend.setOnClickListener {
            //.. navigate to details fragment
            findNavController().navigate(
                MainFragmentDirections.actionMainFragmentToDetailsFragment2(
                    Item(
                        binding.etItemName.text.toString(),
                        binding.etItemPrice.text.toString().toInt())
                )
            )
        }
    }


    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MainFragment().apply {
            }
    }
}