import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import android.widget.Button
import android.widget.TextView
import id.ac.ibda.uas_pads.R
import retrofit2.Response


class InventoryFragment : Fragment() {
    private lateinit var productsResponse: Response<List<Product>> // Assume you have the productsResponse available

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the fragment layout XML
        return inflater.inflate(R.layout.fragment_inventory, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Example: Set click listeners for buttons
        view.findViewById<Button>(R.id.orderBtn).setOnClickListener {
            // Replace the current fragment with the OrdersFragment
            val ordersFragment = OrdersFragment()
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, ordersFragment)
                .addToBackStack(null)
                .commit()
        }

        view.findViewById<Button>(R.id.customerBtn).setOnClickListener {
            // Replace the current fragment with the CustomerFragment
            val customerFragment = CustomerFragment()
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, customerFragment)
                .addToBackStack(null)
                .commit()
        }

        view.findViewById<Button>(R.id.inventoryBtn).setOnClickListener {
            // Handle the button click
            // Add your code here
            // For example, display productsResponse in the TextView
            val textView = view.findViewById<TextView>(R.id.inventoryTextView)
            textView.text = formatProductsResponse(productsResponse)
        }

        view.findViewById<Button>(R.id.salesperson1).setOnClickListener {
            // Handle the button click
            // Add your code here
        }

        view.findViewById<Button>(R.id.salesperson2).setOnClickListener {
            // Handle the button click
            // Add your code here
        }

        view.findViewById<Button>(R.id.salesperson3).setOnClickListener {
            // Handle the button click
            // Add your code here
        }
    }

    private fun formatProductsResponse(productsResponse: Response<List<Product>>): String {
        return if (productsResponse.isSuccessful) {
            val products = productsResponse.body()
            // Format the products data as a table string
            val stringBuilder = StringBuilder()
            products?.forEachIndexed { index, product ->
                stringBuilder.append("Product ${index + 1}: ${product.product_name}\n")
                stringBuilder.append("Product ID: ${product.product_id}\n")
                stringBuilder.append("Category: ${product.product_category}\n")
                stringBuilder.append("Quantity: ${product.product_quantity}\n\n")
            }
            stringBuilder.toString()
        } else {
            "Error occurred while fetching products"
        }
    }
}
