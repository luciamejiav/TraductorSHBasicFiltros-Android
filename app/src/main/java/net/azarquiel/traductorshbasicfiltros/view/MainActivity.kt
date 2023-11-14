package net.azarquiel.traductorshbasicfiltros.view

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import net.azarquiel.traductorshbasicfiltros.R
import net.azarquiel.traductorshbasicfiltros.adapter.CustomAdapter
import net.azarquiel.traductorshbasicfiltros.databinding.ActivityMainBinding
import net.azarquiel.traductorshbasicfiltros.model.Word
import net.azarquiel.traductorshbasicfiltros.util.Util


class MainActivity : AppCompatActivity(), SearchView.OnQueryTextListener {

    private lateinit var adapter: CustomAdapter
    private lateinit var words: java.util.ArrayList<Word>
    private lateinit var inglesSH: SharedPreferences
    private lateinit var espanolSH: SharedPreferences
    private lateinit var searchView: SearchView
    private lateinit var binding: ActivityMainBinding
    private var isSpFlag = true


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        Util.inyecta(this, "espanol.xml")
        Util.inyecta(this, "ingles.xml")

        espanolSH = getSharedPreferences("espanol", Context.MODE_PRIVATE)
        inglesSH = getSharedPreferences("ingles", Context.MODE_PRIVATE)

        //binding.cm.rvword //para poder hacer esto hemos creado en activity_main un include

        initRV()
        getAllWord()
        adapter.setWords(words)
    }

    private fun getAllWord() {
        words = ArrayList<Word>()
        var inglesAll = inglesSH.all
        //var espanolAll = espanolSH.all

        for ((key, value) in inglesAll) {
            var wordsp = espanolSH.getString(key, null) //vamos a buscar las palabras en espanol una a una segun la palabra que nos salga en ingles
            var word = Word(key, wordsp!!, value.toString()) //value es la palabra en ingles en este bucle y key es la palabra
            words.add(word)
        }
    }

    private fun initRV() {
        val rvpalabras = binding.cm.rvpalabras
        adapter = CustomAdapter(this, R.layout.row)
        rvpalabras.adapter = adapter
        rvpalabras.layoutManager = LinearLayoutManager(this)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        // ************* <Filtro> ************
        val searchItem = menu.findItem(R.id.search)
        searchView = searchItem.actionView as SearchView
        searchView.setQueryHint("Search...")
        searchView.setOnQueryTextListener(this)
        // ************* </Filtro> ************

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_flag -> {
                if (isSpFlag){
                    item.setIcon(R.drawable.flagi)
                }
                else{
                    item.setIcon(R.drawable.flage)
                }
                isSpFlag = !isSpFlag
                // pulsaron sobre la bandera. Actuar en consecuencia
                Toast.makeText(this, "pulsaste la bandera...",Toast.LENGTH_SHORT).show()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    // ************* <Filtro> ************
    override fun onQueryTextChange(query: String): Boolean {
        val original = ArrayList<Word>(words)
        if (isSpFlag){
            adapter.setWords(original.filter { palabra -> palabra.spWord.contains(query,true) })
        }
        else {
            adapter.setWords(original.filter { palabra -> palabra.enWord.contains(query,true) })
        }

        return false
    }

    override fun onQueryTextSubmit(text: String): Boolean {
        return false
    }
    // ************* </Filtro> ************

}