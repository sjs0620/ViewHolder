package com.sjs.viewholder;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class RecyclerAdapter
        extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder>{
    ArrayList<HashMap<String,Object>> arrayList = null;
//    public  RecyclerAdapter(ArrayList<HashMap<String, Object>> arrayList){
//        this.arrayList = new ArrayList<HashMap<String, Object>>();
//        this.arrayList = arrayList;
//    }
    private SQLiteDatabase mdb;

    public  RecyclerAdapter(SQLiteDatabase db){
        this.mdb = db;

        String query = "select * from item;";
        Cursor cursor = mdb.rawQuery(query,null);
        ArrayList<HashMap<String,Object>> arrayList = new ArrayList<HashMap<String, Object>>();
        HashMap<String,Object> hashMap = null;
        int rowcnt = 1;
        int[] image = {R.drawable.android_image_1,R.drawable.android_image_2,
                R.drawable.android_image_3,R.drawable.android_image_4,
                R.drawable.android_image_5,R.drawable.android_image_6,
                R.drawable.android_image_7,R.drawable.android_image_8};
        while (cursor.moveToNext()) {
            hashMap = new HashMap<String, Object>();
            hashMap.put("count", String.valueOf(cursor.getString(1)));
            hashMap.put("title", cursor.getString(2));
            hashMap.put("detail",cursor.getString(3));
            hashMap.put("image", image[rowcnt++%8]);
            int size = arrayList.size();
            arrayList.add(hashMap);
        }
        this.arrayList = arrayList;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflate = LayoutInflater.from(parent.getContext());
        View view = inflate.inflate(R.layout.item_cardlayout,parent,false);
        MyViewHolder viewHolder = new MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        HashMap<String, Object> hashMap = arrayList.get(position);
        holder.itemImage.setImageResource((Integer)hashMap.get("image"));
        holder.itemTitle.setText((String)hashMap.get("title"));
        holder.itemDetail.setText((String)hashMap.get("detail"));
        holder.itemCount.setText((String)hashMap.get("count"));

        holder.itemImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer count = Integer.parseInt(
                        ((TextView)holder.itemCount).getText().toString())+1;
                ((TextView)holder.itemCount).setText(String.valueOf(count));

                String title = ((TextView)holder.itemTitle).getText().toString();
                String detail = ((TextView)holder.itemDetail).getText().toString();
                ContentValues contentValues = new ContentValues();
                contentValues.put(ItemInfo.ItemEntry.COLUMN_NAME_COUNT,count);
                contentValues.put(ItemInfo.ItemEntry.COLUMN_NAME_TITLE,title);
                contentValues.put(ItemInfo.ItemEntry.COLUMN_NAME_DETAIL,detail);

                long newRowId = mdb.update(ItemInfo.ItemEntry.TABLE_NAME,contentValues,"detail=?",new String[] {detail});
                 if(newRowId == -1){
                    String msg= "";
                    Toast.makeText(v.getContext(),"저장에 문제가 발생하였습니다.",Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(v.getContext(), "저장하였습니다.", Toast.LENGTH_SHORT).show();
                    //long newRowId = mdb.insert(ItemInfo.ItemEntry.TABLE_NAME,null,contentValues);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        public ImageView itemImage;
        public TextView itemTitle;
        public TextView itemDetail;
        public TextView itemCount;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            itemImage = (ImageView)itemView.findViewById(R.id.item_image);
            itemTitle = (TextView) itemView.findViewById(R.id.item_title);
            itemDetail = (TextView) itemView.findViewById(R.id.item_detail);
            itemCount = (TextView) itemView.findViewById(R.id.item_count);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    removeItem(position,itemDetail.getText().toString());
                }
            });
        }
    }
    private  void removeItem(int position,String detail){
        long newRowId = mdb.delete(ItemInfo.ItemEntry.TABLE_NAME,"detail="+detail,null);

        arrayList.remove(position);
        notifyDataSetChanged();
    }


    public void addItem(int position, HashMap<String,Object> hashMap){
        Log.e("position","sjsjsjsjs");
        this.arrayList.add(hashMap);
        notifyItemInserted(position);
        notifyDataSetChanged();
    }
    public void addItemMethod(View v) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String detail = dateFormat.format(new Date());
        String title = "Add"+arrayList.size();
        int count = 0;
        ContentValues contentValues = new ContentValues();
        contentValues.put(ItemInfo.ItemEntry.COLUMN_NAME_COUNT,count);
        contentValues.put(ItemInfo.ItemEntry.COLUMN_NAME_TITLE,title);
        contentValues.put(ItemInfo.ItemEntry.COLUMN_NAME_DETAIL,detail);


        long newRowId = mdb.insert(ItemInfo.ItemEntry.TABLE_NAME,null,contentValues);

        if(newRowId == -1){
            Toast.makeText(v.getContext(),"저장에 문제가 발생하였습니다.",Toast.LENGTH_SHORT).show();
        } else{
//          Toast.makeText(v.getContext(),"저장하였습니다.",Toast.LENGTH_SHORT).show();

            HashMap<String,Object> hashMap = new HashMap<String, Object>();
            hashMap.put(ItemInfo.ItemEntry.COLUMN_NAME_COUNT,String.valueOf(count));
            hashMap.put(ItemInfo.ItemEntry.COLUMN_NAME_TITLE,title);
            hashMap.put(ItemInfo.ItemEntry.COLUMN_NAME_DETAIL,detail);

            addItem(1,hashMap);
        }


    }

}
