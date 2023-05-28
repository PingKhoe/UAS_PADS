import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import id.ac.ibda.uas_pads.R
import id.ac.ibda.uas_pads.databinding.FragmentInventoryBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class InventoryFragment : Fragment() {

    private var _binding: FragmentInventoryBinding? = null
    private val binding get() = _binding!!
    private lateinit var apiService: ApiService
    private var products: List<Product>? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentInventoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Create Retrofit instance
        val retrofit = Retrofit.Builder()
            .baseUrl("http://127.0.0.1:5000") // Replace with your base URL
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        // Create ApiService instance
        apiService = retrofit.create(ApiService::class.java)

        // Example: Set click listeners for buttons
        binding.orderBtn.setOnClickListener {
            // Do nothing
        }

        binding.customerBtn.setOnClickListener {
            val customerFragment = CustomerFragment()
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.frameLayout, customerFragment)
                .addToBackStack(null)
                .commit()
        }

        binding.inventoryBtn.setOnClickListener {
            // Do nothing since we're already on the inventory screen
        }

        // Fetch inventory data and update inventoryTextView
        fetchInventoryData()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun fetchInventoryData() {
        GlobalScope.launch(Dispatchers.Main) {
            try {
                products = apiService.getProducts()
                val inventoryText = products?.joinToString("\n") { it.product_name }
                binding.inventoryTextView.text = inventoryText // Update inventoryTextView with the fetched inventory data
                binding.inventoryTextView.visibility = View.VISIBLE // Set the visibility of inventoryTextView
            } catch (e: Exception) {
                e.printStackTrace()
                showError("Failed to fetch inventory data")
            }
        }
    }

    private fun showError(errorMessage: String) {
        // Handle the error case
    }
}
