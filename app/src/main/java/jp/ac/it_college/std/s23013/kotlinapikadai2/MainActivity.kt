package jp.ac.it_college.std.s23013.kotlinapikadai2

import android.os.Bundle
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.app.AlertDialog

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val listView: ListView = findViewById(R.id.listViewSpells)

        RetrofitClient.apiService.getSpells().enqueue(object : Callback<List<Spell>> {
            override fun onResponse(call: Call<List<Spell>>, response: Response<List<Spell>>) {
                if (response.isSuccessful) {
                    val spells = response.body()
                    if (spells != null && spells.isNotEmpty()) {
                        val spellNames = spells.map { it.name }
                        val adapter = ArrayAdapter(this@MainActivity, android.R.layout.simple_list_item_1, spellNames)
                        listView.adapter = adapter

                        // クリックリスナーの中でダイアログを表示
                        listView.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
                            val selectedSpell = spells[position]

                            val dialog = AlertDialog.Builder(this@MainActivity)
                                .setTitle(selectedSpell.name)
                                .setMessage(selectedSpell.description)
                                .setPositiveButton("OK", null)
                                .create()

                            dialog.show()
                        }
                    } else {
                        Toast.makeText(this@MainActivity, "データが空です", Toast.LENGTH_LONG).show()
                    }
                } else {
                    Toast.makeText(this@MainActivity, "エラー: ${response.message()}", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<List<Spell>>, t: Throwable) {
                Toast.makeText(this@MainActivity, "失敗: ${t.message}", Toast.LENGTH_LONG).show()
            }
        })
    }
}