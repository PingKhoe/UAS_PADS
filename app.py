from flask import Flask, jsonify, request
from flask_sqlalchemy import SQLAlchemy

app = Flask(__name__)
app.config['SQLALCHEMY_DATABASE_URI'] = 'mysql+pymysql://root:@localhost/uas_pads'  # Replace with your MySQL database connection details
db = SQLAlchemy(app)


class Customer(db.Model):
    customer_id = db.Column(db.Integer, primary_key=True)
    customer_name = db.Column(db.String(255), nullable=False)
    sales_p_id = db.Column(db.Integer, nullable=False)
    orders = db.relationship('Orders', backref='customer', lazy='dynamic')


class Orders(db.Model):
    order_id = db.Column(db.Integer, primary_key=True)
    order_date = db.Column(db.Date, nullable=False)
    order_status = db.Column(db.String(255), nullable=False)
    order_address = db.Column(db.String(255), nullable=False)
    customer_id = db.Column(db.Integer, db.ForeignKey('customer.customer_id'), nullable=False)


class Products(db.Model):
    product_id = db.Column(db.Integer, primary_key=True)
    product_name = db.Column(db.String(255), nullable=False)
    product_category = db.Column(db.String(255), nullable=False)
    product_quantity = db.Column(db.Integer, nullable=False)


class SalesPerson(db.Model):
    sales_p_id = db.Column(db.Integer, primary_key=True)
    sales_name = db.Column(db.String(255), nullable=False)


@app.route('/customers', methods=['GET'])
def get_customers():
    customers = Customer.query.all()

    customer_list = []
    for customer in customers:
        customer_data = {
            'customer_id': customer.customer_id,
            'customer_name': customer.customer_name,
            'sales_p_id': customer.sales_p_id,
            'orders': [{
                'order_id': order.order_id,
                'order_date': str(order.order_date),
                'order_status': order.order_status,
                'order_address': order.order_address
            } for order in customer.orders]
        }
        customer_list.append(customer_data)

    return jsonify(customer_list), 200



@app.route('/orders', methods=['GET'])
def get_orders():
    orders = Orders.query.all()

    order_list = []
    for order in orders:
        order_data = {
            'order_id': order.order_id,
            'order_date': str(order.order_date),
            'order_status': order.order_status,
            'order_address': order.order_address,
            'customer_id': order.customer_id,
            'customer_name': order.customer.customer_name
        }
        order_list.append(order_data)

    return jsonify(order_list), 200



@app.route('/sales_person', methods=['GET'])
def get_sales_person():
    sales_person = SalesPerson.query.all()

    sales_person_list = []
    for salesperson in sales_person:
        salesperson_data = {
            'sales_p_id': salesperson.sales_p_id,
            'sales_name': salesperson.sales_name
        }
        sales_person_list.append(salesperson_data)

    return jsonify(sales_person_list), 200


@app.route('/customers/<int:sales_person_id>', methods=['GET'])
def get_customers_by_salesperson(sales_person_id):
    customers = Customer.query.filter_by(sales_p_id=sales_person_id).all()

    customer_list = []
    for customer in customers:
        customer_data = {
            'customer_id': customer.customer_id,
            'customer_name': customer.customer_name,
            'sales_p_id': customer.sales_p_id
        }
        customer_list.append(customer_data)

    return jsonify(customer_list), 200


@app.route('/add_orders', methods=['POST'])
def add_order():
    order_data = request.get_json()

    order_date = order_data.get('order_date')
    order_status = order_data.get('order_status')
    order_address = order_data.get('order_address', '')
    customer_id = order_data.get('customer_id', '')

    new_order = Orders(
        order_date=order_date,
        order_status=order_status,
        order_address=order_address,
        customer_id=customer_id
    )

    db.session.add(new_order)
    db.session.commit()

    return jsonify(message="Order created successfully"), 201


@app.route('/orders/<int:customer_id>', methods=['GET'])
def get_orders_by_customer(customer_id):
    customer = Customer.query.get(customer_id)
    if not customer:
        return jsonify({'error': 'Customer not found'}), 404

    orders = customer.orders.all()

    order_list = []
    for order in orders:
        order_data = {
            'order_id': order.order_id,
            'order_date': str(order.order_date),
            'order_status': order.order_status,
            'order_address': order.order_address,
            'customer_id': order.customer_id
        }
        order_list.append(order_data)

    return jsonify(order_list), 200


@app.route('/products', methods=['GET'])
def get_products():
    products = Products.query.all()

    products_list = []
    for item in products:
        item_data = {
            'product_id': item.product_id,
            'product_name': item.product_name,
            'product_category': item.product_category,
            'product_quantity': item.product_quantity
        }
        products_list.append(item_data)

    return jsonify(products_list), 200


if __name__ == '__main__':
    app.run()

