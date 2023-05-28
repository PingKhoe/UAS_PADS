import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import android.widget.Button
import id.ac.ibda.uas_pads.R

class OrdersFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the fragment layout XML
        return inflater.inflate(R.layout.fragment_orders, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Example: Set click listeners for buttons
        view.findViewById<Button>(R.id.orderBtn).setOnClickListener {
            // Do nothing
        }

        view.findViewById<Button>(R.id.customerBtn).setOnClickListener {
            val customerFragment = CustomerFragment()
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, customerFragment)
                .addToBackStack(null)
                .commit()
        }

        view.findViewById<Button>(R.id.inventoryBtn).setOnClickListener {
            // Replace the current fragment with the InventoryFragment
            val inventoryFragment = InventoryFragment()
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, inventoryFragment)
                .addToBackStack(null)
                .commit()
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
}