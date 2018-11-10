package com.sjs.viewholder;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    RecyclerAdapter adapter;
    ArrayList<HashMap<String,Object>> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton floatingActionButton = findViewById(R.id.addItemAction);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap<String,Object> hashMap = new HashMap<String, Object>();
                hashMap.put("title","Chapter Oone");
                hashMap.put("detail","Detail Oone");
                hashMap.put("count","0");
                hashMap.put("image",R.drawable.android_image_1);
                adapter.addItem(1,hashMap);
            }
        });
        dataSetting();

        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        layoutManager = new LinearLayoutManager(this);
//        layoutManager = new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new RecyclerAdapter(arrayList);
        recyclerView.setAdapter(adapter);
    }

    private void dataSetting(){
        arrayList = new ArrayList<HashMap<String, Object>>();
        HashMap<String,Object> hashMap = null;
        int count = 0;
        int[] image = {R.drawable.android_image_1,R.drawable.android_image_2,
                R.drawable.android_image_3,R.drawable.android_image_4,
                R.drawable.android_image_5,R.drawable.android_image_6,
                R.drawable.android_image_7,R.drawable.android_image_8};
        for(int i=1; i<=8; i++) {
            hashMap = new HashMap<String, Object>();
            hashMap.put("title", "Chapter " + String.valueOf(i));
            hashMap.put("detail", "Item "+ String.valueOf(i)+ " details");
            hashMap.put("count", String.valueOf(count));
            hashMap.put("image", image[i%8]);
            arrayList.add(hashMap);
        }

    }
}
