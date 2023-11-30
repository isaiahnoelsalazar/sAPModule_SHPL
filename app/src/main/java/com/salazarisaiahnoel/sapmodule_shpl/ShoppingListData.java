package com.salazarisaiahnoel.sapmodule_shpl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ShoppingListData extends SQLiteOpenHelper {

    private final static String database = "shoplist";

    public ShoppingListData(Context context){
        super(context, database, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String q = "CREATE TABLE shoppinglist(name text, number text, price text)";
        sqLiteDatabase.execSQL(q);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String q = "DROP TABLE IF EXISTS shoppinglist";
        sqLiteDatabase.execSQL(q);
        onCreate(sqLiteDatabase);
    }

    public void addData(String name, String number, String price){
        SQLiteDatabase db = getReadableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("name", name);
        cv.put("number", number);
        cv.put("price", price);

        db.insert("shoppinglist", null, cv);
    }

    public ArrayList<String> getName(){
        ArrayList<String> list = new ArrayList<>();
        SQLiteDatabase db = getWritableDatabase();
        String q = "SELECT * FROM shoppinglist";
        Cursor c = db.rawQuery(q, null);
        while (c.moveToNext()){
            list.add(c.getString(0));
        }
        c.close();
        return list;
    }
    public ArrayList<String> getNumber(){
        ArrayList<String> list = new ArrayList<>();
        SQLiteDatabase db = getWritableDatabase();
        String q = "SELECT * FROM shoppinglist";
        Cursor c = db.rawQuery(q, null);
        while (c.moveToNext()){
            list.add(c.getString(1));
        }
        c.close();
        return list;
    }
    public ArrayList<String> getPrice(){
        ArrayList<String> list = new ArrayList<>();
        SQLiteDatabase db = getWritableDatabase();
        String q = "SELECT * FROM shoppinglist";
        Cursor c = db.rawQuery(q, null);
        while (c.moveToNext()){
            list.add(c.getString(2));
        }
        c.close();
        return list;
    }

    public void clearData(){
        SQLiteDatabase db = getReadableDatabase();
        String q = "DROP TABLE IF EXISTS shoppinglist";
        db.execSQL(q);
        String qq = "CREATE TABLE shoppinglist(name text, number text, price text)";
        db.execSQL(qq);
    }

    public void deleteData(int position){
        SQLiteDatabase db = getReadableDatabase();
        List<String> a = getName();
        List<String> aa = getNumber();
        List<String> aaa = getPrice();
        a.remove(position);
        aa.remove(position);
        aaa.remove(position);
        ArrayList<StringTriple> list = new ArrayList<>();
        for (int i = 0; i < a.size(); i++){
            list.add(new StringTriple(a.get(i), aa.get(i), aaa.get(i)));
        }
        String q = "DROP TABLE IF EXISTS shoppinglist";
        db.execSQL(q);
        String qq = "CREATE TABLE shoppinglist(name text, number text, price text)";
        db.execSQL(qq);
        Collections.sort(list, new StringTripleComparator());
        for (int i = 0; i < list.size(); i++){
            String[] aaaaa = list.get(i).toString().split(", ");
            addData(aaaaa[0].substring(1), aaaaa[1], aaaaa[2].substring(0, aaaaa[2].length() - 1));
        }
    }

    public void rearrangePosition(){
        SQLiteDatabase db = getReadableDatabase();
        List<String> a = getName();
        List<String> aa = getNumber();
        List<String> aaa = getPrice();
        ArrayList<StringTriple> list = new ArrayList<>();
        for (int i = 0; i < a.size(); i++){
            list.add(new StringTriple(a.get(i), aa.get(i), aaa.get(i)));
        }
        String q = "DROP TABLE IF EXISTS shoppinglist";
        db.execSQL(q);
        String qq = "CREATE TABLE shoppinglist(name text, number text, price text)";
        db.execSQL(qq);
        Collections.sort(list, new StringTripleComparator());
        for (int i = 0; i < list.size(); i++){
            String[] aaaaa = list.get(i).toString().split(", ");
            addData(aaaaa[0].substring(1), aaaaa[1], aaaaa[2].substring(0, aaaaa[2].length() - 1));
        }
    }
}
