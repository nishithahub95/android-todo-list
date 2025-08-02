package com.example.todolist

import android.content.DialogInterface
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    lateinit var item :EditText
    lateinit var add :Button
    lateinit var listView:ListView
    var arraList=ArrayList<String>()
    var fileHelper=FileHelper()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        item=findViewById(R.id.editText)
        add=findViewById(R.id.button)
        listView=findViewById(R.id.listView)
        arraList=fileHelper.readData(this)
        var adapter=ArrayAdapter(this,android.R.layout.simple_list_item_1,android.R.id.text1,arraList)
        listView.adapter=adapter
        add.setOnClickListener {
            var itemName:String=item.text.toString()
            arraList.add(itemName)
            item.setText("")
            fileHelper.writeData(arraList,applicationContext)
            adapter.notifyDataSetChanged()

        }
        listView.setOnItemClickListener { adapterView, view, position, l ->

            var alert=AlertDialog.Builder(this)
            alert.setTitle("Delete")
            alert.setMessage("Do you want to delete this item")
            alert.setCancelable(false)
            alert.setNeutralButton("No", DialogInterface.OnClickListener { dialogInterface, i ->
                dialogInterface.cancel()
            })
            alert.setPositiveButton("Yes", DialogInterface.OnClickListener { dialogInterface, i ->

                arraList.removeAt(position)
                adapter.notifyDataSetChanged()
                fileHelper.writeData(arraList,applicationContext)
            })
            alert.create().show()
        }
    }
}