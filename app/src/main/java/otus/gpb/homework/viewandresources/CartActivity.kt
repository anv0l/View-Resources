package otus.gpb.homework.viewandresources

import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.w3c.dom.Text

class CartActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        class CartItem(
            var image: Int,
            var name: String,
            var category: String,
            var extra: String,
            var price: Double = 0.0
        )

        fun formatPrice(price: Double): String {
            return "%.2f".format(price)
        }
        class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val itemName: TextView = itemView.findViewById(R.id.cart_item_name)
            val itemCategory: TextView = itemView.findViewById(R.id.cart_item_category)
            val itemExtra: TextView = itemView.findViewById(R.id.cart_item_extra)
            val itemPrice: TextView = itemView.findViewById(R.id.cart_item_price)
            val itemImage: ImageView = itemView.findViewById(R.id.cart_item_image)
        }

        class MyAdapter(private val data: List<CartItem>) :
            RecyclerView.Adapter<MyViewHolder>() {
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.cart_item, parent, false)
                return MyViewHolder(view)
            }

            override fun getItemCount(): Int {
                return data.size
            }

            override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
                holder.itemName.width = holder.itemName.measuredWidth
                holder.itemName.isSelected = true
                with(data[position]) {
                    holder.itemName.text = name
                    holder.itemPrice.text = formatPrice(price)
                    holder.itemCategory.text = category
                    holder.itemExtra.text = extra

                    holder.itemImage.setImageResource(image)
                }

            }

            override fun onViewAttachedToWindow(holder: MyViewHolder) {
                holder.itemName.isSelected = true
            }

            override fun onViewDetachedFromWindow(holder: MyViewHolder) {
                holder.itemName.isSelected = false
            }

        }



        val recyclerView = findViewById<RecyclerView>(R.id.item_list)

        val data: List<CartItem> = listOf(
            CartItem(R.drawable.eggs, "Куриные яйца", "Продукты", "Сорт С2", 12.0),
            CartItem(R.drawable.banana, "Бананы", "Продукты", "Ямайка", 2.0),
            CartItem(R.drawable.salt, "Соль пищевая", "Продукты", "Без ГМО", 1.0),
            CartItem(R.drawable.butter, "Сливочное масло", "Продукты", "72.5% жирности", 2.55),
            CartItem(R.drawable.bread, "Хлеб ржаной", "Продукты", "Собвестнного производства", 1.99),
            CartItem(R.drawable.sausage, "Сосиски молочные", "Продукты", "Без молока и мяса", 4.0)
        )

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = MyAdapter(data)

        findViewById<TextView>(R.id.cart_items_count).text = resources.getQuantityString(R.plurals.cart_items_count, data.count(), data.count())

        var subtotal = 0.0
        data.forEach { item ->
            subtotal += item.price
        }
        findViewById<TextView>(R.id.cart_subtotal_value).text = formatPrice(subtotal)
        val shipping = 25.0
        findViewById<TextView>(R.id.cart_shipping_value).text = formatPrice(shipping)
        val tax = 0.2
        val taxAmount = subtotal * tax
        findViewById<TextView>(R.id.cart_tax_value).text = formatPrice(taxAmount)
        val orderTotal = subtotal + taxAmount + shipping
        findViewById<TextView>(R.id.cart_order_total_value).text = formatPrice(orderTotal)
    }
}

