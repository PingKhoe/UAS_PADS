package id.ac.ibda.uas_pads

import MyClass
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import id.ac.ibda.uas_pads.databinding.FragmentCLCustomersBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [CL_Customers.newInstance] factory method to
 * create an instance of this fragment.
 */
class CL_Customers : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var binding: FragmentCLCustomersBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        binding = FragmentCLCustomersBinding.inflate(layoutInflater)

        binding.orderBtn.setOnClickListener {
            findNavController().navigate(R.id.orders)
        }

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
        binding = FragmentCLCustomersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val salesPersonId = arguments?.getInt("salesPersonId")

        // Fetch salesperson data and update inventoryTextViewCL
        GlobalScope.launch(Dispatchers.Main) {
            val salesPersons = try {
                val myClass = MyClass()
                myClass.getSalesPersons()
            } catch (e: Exception) {
                emptyList()
            }

            if (salesPersons.isNotEmpty() && salesPersonId != null) {
                val salesPerson = salesPersons.find { it.sales_p_id == salesPersonId }
                val salesPersonText = "${salesPerson?.sales_name} - ${salesPerson?.sales_p_id}"
                binding.inventoryTextViewCL.text = salesPersonText
            }
        }

        binding.orderBtn.setOnClickListener {
            findNavController().navigate(R.id.orders)
        }

        binding.customerBtn.setOnClickListener {
            findNavController().navigate(R.id.customersList)
        }

        binding.inventoryBtn.setOnClickListener {
            findNavController().navigate(R.id.inventory)
        }
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment CL_Customers.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CL_Customers().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}