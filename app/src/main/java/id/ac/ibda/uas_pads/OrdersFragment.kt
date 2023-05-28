import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import id.ac.ibda.uas_pads.databinding.FragmentOrdersBinding

class OrdersFragment : Fragment() {
    // ...

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentOrdersBinding.bind(view)

        binding.submitBtn.setOnClickListener {
            submitForm()
        }
    }

    private fun submitForm() {
        val binding = FragmentOrdersBinding.bind(requireView())

        val customerID = binding.customerID.text.toString()
        val orderDate = binding.orderDate
        val year = orderDate.year
        val month = orderDate.month
        val day = orderDate.dayOfMonth
        val customerAddress = binding.customerAddress.text.toString()
        val salespersonId = binding.salespersonSpinner.selectedItemId.toInt()

        val orderData = Order(
            order_date = "$month-$day-$year",
            order_status = "Sent",
            order_address = customerAddress,
            customer_id = customerID
        )

        GlobalScope.launch(Dispatchers.Main) {
            val success = try {
                addOrder(orderData)
                true
            } catch (e: Exception) {
                false
            }

            if (success) {
                showToast("Order created successfully")
            } else {
                showToast("Failed to create order")
            }
        }
    }

    private suspend fun addOrder(orderData: Order) {
        val myClass = MyClass()
        myClass.addOrder(orderData)
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

}
