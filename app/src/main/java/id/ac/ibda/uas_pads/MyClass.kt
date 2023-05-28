import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import kotlinx.coroutines.runBlocking

data class Customer(
    val customer_id: Int,
    val customer_name: String,
    val sales_p_id: Int,
    val orders: List<Order>
)

data class Order(
    val order_date: String,
    val order_status: String,
    val order_address: String,
    val customer_id: Int,
    val customer_name: String
)

data class Product(
    val product_id: Int,
    val product_name: String,
    val product_category: String,
    val product_quantity: Int
)

data class SalesPerson(
    val sales_p_id: Int,
    val sales_name: String
)

interface ApiService {
    @GET("/customers")
    suspend fun getCustomers(): Response<List<Customer>>

    @GET("/orders")
    suspend fun getOrders(): Response<List<Order>>

    @GET("/sales_person")
    suspend fun getSalesPersons(): Response<List<SalesPerson>>

    @GET("/customers/{sales_person_id}")
    suspend fun getCustomersBySalesPerson(@Path("sales_person_id") salesPersonId: Int): Response<List<Customer>>

    @POST("/add_orders")
    suspend fun addOrder(@Body orderData: Order): Response<Unit>

    @GET("/orders/{customer_id}")
    suspend fun getOrdersByCustomer(@Path("customer_id") customerId: Int): Response<List<Order>>

    @GET("/products")
    suspend fun getProducts(): Response<List<Product>>
}

fun main() {
    runBlocking {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://127.0.0.1:5000")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiService = retrofit.create(ApiService::class.java)

        // Get all customers
        val customersResponse = apiService.getCustomers()
        if (customersResponse.isSuccessful) {
            val customers = customersResponse.body()
            // Handle the list of customers
        } else {
            // Handle the error case
        }

        // Get all orders
        val ordersResponse = apiService.getOrders()
        if (ordersResponse.isSuccessful) {
            val orders = ordersResponse.body()
            // Handle the list of orders
        } else {
            // Handle the error case
        }

        // Get all salespersons
        val salesPersonsResponse = apiService.getSalesPersons()
        if (salesPersonsResponse.isSuccessful) {
            val salesPersons = salesPersonsResponse.body()
            // Handle the list of salespersons
        } else {
            // Handle the error case
        }

        // Get customers by salesperson ID
        val salesPersonId = 1
        val customersBySalesPersonResponse = apiService.getCustomersBySalesPerson(salesPersonId)
        if (customersBySalesPersonResponse.isSuccessful) {
            val customersBySalesPerson = customersBySalesPersonResponse.body()
            // Handle the list of customers by salesperson
        } else {
            // Handle the error case
        }

        // Add an order
        val orderData = Order(order_date = "2023-05-27", order_status = "Sent", order_address = "Sample Address", customer_id = 1, customer_name = "John Doe")
        val addOrderResponse = apiService.addOrder(orderData)
        if (addOrderResponse.isSuccessful) {
            // Order added successfully
        } else {
            // Handle the error case
        }

        // Get orders by customer ID
        val customerId = 1
        val ordersByCustomerResponse = apiService.getOrdersByCustomer(customerId)
        if (ordersByCustomerResponse.isSuccessful) {
            val ordersByCustomer = ordersByCustomerResponse.body()
            // Handle the list of orders by customer
        } else {
            // Handle the error case
        }

        // Get all products
        val productsResponse = apiService.getProducts()
        if (productsResponse.isSuccessful) {
            val products = productsResponse.body()
            // Handle the list of products
        } else {
            // Handle the error case
        }
    }
}
