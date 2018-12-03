package com.lll.flowlayout02;

import android.content.ContentValues;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText edit;
    private Button btn_search;
    private Button clear;
    private FlowLayout flow_history;
    private long insert;
    private Dao dao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //找控件
        edit = findViewById(R.id.edit_keys);
        flow_history = findViewById(R.id.flow_history_layout);
        btn_search = findViewById(R.id.btn_search);
        clear = findViewById(R.id.clear);
        btn_search.setOnClickListener(this);
        clear.setOnClickListener(this);
        //创建Dao层
        dao = new Dao(MainActivity.this);

        //查询数据库
        //创建Dao层
        Dao dao = new Dao(MainActivity.this);
        Cursor query = dao.query("fl", null, null, null, null, null, null);
        if (query.moveToFirst()){
            do {
                String name = query.getString(query.getColumnIndex("name"));
                flow_history.addTextView(name);
            }while (query.moveToNext());
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_search:
                String keys = edit.getText().toString().trim();
                if (keys.equals("")||keys == ""){
                    Toast.makeText(MainActivity.this,"输入框不能为空",Toast.LENGTH_LONG).show();
                    return;
                }
                flow_history.addTextView(keys);
                //点击搜索将内容添加到数据库
                ContentValues values = new ContentValues();
                values.put("name",keys);
                insert = dao.insert("fl", null, values);
                Toast.makeText(this,"添加成功",Toast.LENGTH_SHORT).show();
                break;
            case R.id.clear:
                long delete = dao.delete("fl", null, null);
                Toast.makeText(this,"删除条数为"+delete,Toast.LENGTH_SHORT).show();
                flow_history.removeAllViews();
                break;
        }
    }
}
