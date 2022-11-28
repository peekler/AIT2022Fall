package hu.bme.aut.navgraphfragmentdemo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import hu.bme.aut.navgraphfragmentdemo.databinding.FragmentDetailsBinding


class DetailsFragment : Fragment() {

    lateinit var binding: FragmentDetailsBinding
    val args: DetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailsBinding.inflate(inflater,
            container, false)

        binding.tvData.text =
            """
                Name: ${args.shopItem.name} 
                Price: ${args.shopItem.price}
            """.trimIndent()


        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            DetailsFragment().apply {
            }
    }
}