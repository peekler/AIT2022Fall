package hu.bme.aut.simplefragmentdemo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import hu.bme.aut.simplefragmentdemo.databinding.FragmentInputBinding

class InputFragment : Fragment() {

        companion object {
            const val TAG = "InputFragment"
        }

    lateinit var binding: FragmentInputBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentInputBinding.inflate(
            inflater, container, false)

        return binding.root
    }

    override fun onResume() {
        super.onResume()

        binding.btnOk.setOnClickListener {
            Toast.makeText(
                requireActivity(), "Demo fragment",
                Toast.LENGTH_LONG).show()
        }
    }

}