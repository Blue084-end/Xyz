package com.example.networkguardian

import android.content.Context
import android.graphics.Color
import android.view.*
import android.widget.*
import java.text.SimpleDateFormat
import java.util.*

class NetworkLogAdapter(
    private val context: Context,
    private val logs: List<NetworkEvent>
) : BaseAdapter() {

    override fun getCount() = logs.size
    override fun getItem(position: Int) = logs[position]
    override fun getItemId(position: Int) = logs[position].id.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = convertView ?: LayoutInflater.from(context)
            .inflate(R.layout.network_log_item, parent, false)

        val log = logs[position]
        val riskColor = when (log.riskLevel) {
            "Nguy hiểm" -> Color.RED
            "Không rõ" -> Color.parseColor("#FFA500")
            else -> Color.BLACK
        }

        view.findViewById<TextView>(R.id.transportType).apply {
            text = log.transportType
            setTextColor(riskColor)
        }

        view.findViewById<TextView>(R.id.networkInfo).apply {
            text = log.networkInfo
            setTextColor(riskColor)
        }

        view.findViewById<TextView>(R.id.timestamp).text =
            SimpleDateFormat("HH:mm:ss dd/MM/yyyy", Locale.getDefault()).format(Date(log.timestamp))

        return view
    }
}
