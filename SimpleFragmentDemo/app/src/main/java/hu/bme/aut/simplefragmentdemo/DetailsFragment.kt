package hu.bme.aut.simplefragmentdemo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import hu.bme.aut.simplefragmentdemo.databinding.FragmentDetailsBinding
import hu.bme.aut.simplefragmentdemo.databinding.FragmentInputBinding

class DetailsFragment : Fragment() {

    companion object {
        const val TAG = "DetailsFragment"
    }

    lateinit var binding: FragmentDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailsBinding.inflate(
            inflater, container, false)

        return binding.root
    }

}