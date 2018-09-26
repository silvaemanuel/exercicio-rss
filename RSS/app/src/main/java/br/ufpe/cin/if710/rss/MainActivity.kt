package br.ufpe.cin.if710.rss

import android.app.Activity
import android.os.Bundle
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL

class MainActivity : Activity() {
    // UTILIZAR ESSA URL PELO STRINGS.XML private val RSS_FEED = "http://leopoldomt.com/if1001/g1brasil.xml"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        conteudoRSS.layoutManager = LinearLayoutManager(this)
    }

    override fun onStart() {
        super.onStart()
        try {
            //Usando doAsync para o passo 2
            doAsync {
                //Utilizando o Parser
                val feedXML = ParserRSS.parse(getRSSFeed(getString(R.string.rssfeed)))
                uiThread { conteudoRSS.adapter = RSSAdapter(feedXML, this@MainActivity)
                    conteudoRSS.addItemDecoration(DividerItemDecoration(this@MainActivity, LinearLayoutManager.VERTICAL))
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    @Throws(IOException::class)
    private fun getRSSFeed(feed: String): String {
        var inputStream: InputStream? = null
        var RSSFeed = ""
        try {
            val url = URL(feed)
            val conn = url.openConnection() as HttpURLConnection
            inputStream = conn.inputStream
            val out = ByteArrayOutputStream()
            val buffer = ByteArray(1024)
            var count = inputStream.read(buffer)
            while (count != -1) {
                out.write(buffer, 0, count)
                count = inputStream.read(buffer)
            }
            val response = out.toByteArray()
            RSSFeed = response.toString(charset("UTF-8"))
        } finally {
            inputStream?.close()
        }
        return RSSFeed
    }
}

