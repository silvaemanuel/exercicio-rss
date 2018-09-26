package br.ufpe.cin.if710.rss
/*
Baseado no PessoaAdapter exemplo do professor
 */

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import kotlinx.android.synthetic.main.itemlista.view.*

class RSSAdapter(private val listRSS: List<ItemRSS>, private val c: Context) : RecyclerView.Adapter<RSSAdapter.ViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(c).inflate(R.layout.itemlista, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listRSS.size
    }

    override fun onBindViewHolder(holder: RSSAdapter.ViewHolder, position: Int) {
        holder.bindModel(listRSS?.get(position))


    }

    inner class ViewHolder (item : View) : RecyclerView.ViewHolder(item), View.OnClickListener{
        var data: TextView? = null
        var titulo: TextView? = null
        var site: Uri? = null

        init {
            data = item.item_data
            titulo = item.item_titulo

            titulo!!.setOnClickListener(this)
        }

        fun bindModel(itemRSS: ItemRSS){
            data?.text = itemRSS?.pubDate
            titulo?.text = itemRSS?.title
            site = Uri.parse(itemRSS?.link)
        }

        override fun onClick(v: View?) {
            //Explicit Intent no evento do click
            val intent = Intent(Intent.ACTION_VIEW, site)
            c.startActivity(intent)
        }
    }
}