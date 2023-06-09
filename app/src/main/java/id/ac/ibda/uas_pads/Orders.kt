package id.ac.ibda.uas_pads

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import id.ac.ibda.uas_pads.databinding.FragmentOrdersBinding


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Orders.newInstance] factory method to
 * create an instance of this fragment.
 */
class Orders : Fragment() {
    private lateinit var binding: FragmentOrdersBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentOrdersBinding.inflate(layoutInflater)

        binding.customerBtn.setOnClickListener {
            findNavController().navigate(R.id.customersList)
        }

        binding.inventoryBtn.setOnClickListener {
            findNavController().navigate(R.id.inventory)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val salespersons = arrayOf("Giovanna", "Doppio", "Narancia")
        val salespersonSpinner: Spinner = binding.salespersonSpinner
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, salespersons)
        salespersonSpinner.adapter = adapter

        return binding.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Orders.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Orders().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

}