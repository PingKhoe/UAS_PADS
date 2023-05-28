import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Body
import retrofit2.http.POST

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
    val customer_id: String
)

data class Product(
    val product_category: String,
    val product_id: Int,
    val product_name: String,
    val product_quantity: Int
)

data class SalesPerson(
    val sales_p_id: Int,
    val sales_name: String
)

interface ApiService {
    @GET("/customers")
    suspend fun getCustomers(): List<Customer>

    @GET("/orders")
    suspend fun getOrders(): List<Order>

    @GET("/sales_person")
    suspend fun getSalesPersons(): List<SalesPerson>

    @GET("/customers/{sales_person_id}")
    suspend fun getCustomersBySalesPerson(@Path("sales_person_id") salesPersonId: Int): List<Customer>

    @POST("/add_orders")
    suspend fun addOrder(@Body orderData: Order)

    @GET("/orders/{customer_id}")
    suspend fun getOrdersByCustomer(@Path("customer_id") customerId: Int): List<Order>

    @GET("/products")
    suspend fun getProducts(): List<Product>
}

class MyClass {
    private val retrofit = Retrofit.Builder()
        .baseUrl("http://127.0.0.1:5000")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val apiService = retrofit.create(ApiService::class.java)

    suspend fun addOrder(orderData: Order) {
        apiService.addOrder(orderData)
    }

    suspend fun getCustomers(): List<Customer> {
        return apiService.getCustomers()
    }

    suspend fun getOrders(): List<Order> {
        return apiService.getOrders()
    }

    suspend fun getSalesPersons(): List<SalesPerson> {
        return apiService.getSalesPersons()
    }

    suspend fun getCustomersBySalesPerson(salesPersonId: Int): List<Customer> {
        return apiService.getCustomersBySalesPerson(salesPersonId)
    }

    suspend fun getOrdersByCustomer(customerId: Int): List<Order> {
        return apiService.getOrdersByCustomer(customerId)
    }

    suspend fun getProducts(): List<Product> {
        return apiService.getProducts()
    }
}
