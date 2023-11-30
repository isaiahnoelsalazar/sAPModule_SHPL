package com.salazarisaiahnoel.sapmodule_shpl;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ShoppingListAdapter.OnItemClickListener, ShoppingListAdapter.OnItemLongClickListener {

    RecyclerView rv;
    LinearLayoutManager llm;
    ShoppingListAdapter sla;

    ShoppingListData sd;
    List<String> name;
    List<String> number;
    List<String> price;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        sd = new ShoppingListData(this);

        rv = findViewById(R.id.rv);
        llm = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rv.setLayoutManager(llm);

        refreshData();

        rv.addItemDecoration(new RecyclerViewVerticalSpace(6));
    }

    @Override
    protected void onResume() {
        super.onResume();

        refreshData();
    }

    void refreshData(){
        name = sd.getName();
        number = sd.getNumber();
        price = sd.getPrice();

        BigInteger totalAmount = BigInteger.ZERO;
        BigDecimal totalPrice = BigDecimal.ZERO;

        for (int a = 0; a < name.size(); a++){
            totalAmount = totalAmount.add(new BigInteger(number.get(a)));
            totalPrice = totalPrice.add(new BigDecimal(price.get(a)).multiply(new BigDecimal(number.get(a))));
        }

        name.add("Total");
        number.add(totalAmount.toString());
        price.add(totalPrice.toString());

        sla = new ShoppingListAdapter(this, name, number, price, this, this);
        rv.setAdapter(sla);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.add){
            LayoutInflater li = LayoutInflater.from(this);
            View view1 = li.inflate(R.layout.shoplist_add_data, null);
            final EditText et = view1.findViewById(R.id.edittitle);
            final EditText et1 = view1.findViewById(R.id.edittitle1);
            final EditText et2 = view1.findViewById(R.id.edittitle2);
            final Button b21 = view1.findViewById(R.id.done);
            final Button b31 = view1.findViewById(R.id.cancel);
            AlertDialog.Builder adb = new AlertDialog.Builder(this)
                    .setView(view1);
            AlertDialog ad = adb.create();
            ad.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            ad.show();
            b21.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (TextUtils.isEmpty(et.getText().toString()) || TextUtils.isEmpty(et1.getText().toString()) || TextUtils.isEmpty(et2.getText().toString())){
                        if (TextUtils.isEmpty(et.getText().toString())){
                            et.setError("Cannot be empty.");
                        }
                        if (TextUtils.isEmpty(et1.getText().toString())){
                            et1.setError("Cannot be empty.");
                        }
                        if (TextUtils.isEmpty(et2.getText().toString())){
                            et2.setError("Cannot be empty.");
                        }
                    } else {
                        sd.addData(et.getText().toString(), et1.getText().toString(), et2.getText().toString());
                        sd.rearrangePosition();
                        refreshData();
                        ad.cancel();
                    }
                }
            });
            b31.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ad.cancel();
                }
            });
        }
        if (item.getItemId() == R.id.clear){
            LayoutInflater li = LayoutInflater.from(this);
            View view1 = li.inflate(R.layout.shoplist_clear_data, null);
            final Button b2 = view1.findViewById(R.id.yes);
            final Button b3 = view1.findViewById(R.id.cancel1);
            AlertDialog.Builder adb = new AlertDialog.Builder(this)
                    .setView(view1);
            AlertDialog ad = adb.create();
            ad.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            ad.show();
            b2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    sd.clearData();
                    refreshData();
                    ad.cancel();
                }
            });
            b3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ad.cancel();
                }
            });
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(int position) {
        if (position < name.size() - 1){
            LayoutInflater li = LayoutInflater.from(this);
            View view1 = li.inflate(R.layout.shoplist_edit_data, null);
            final EditText et = view1.findViewById(R.id.edittitleed);
            final EditText et1 = view1.findViewById(R.id.edittitle1ed);
            final EditText et2 = view1.findViewById(R.id.edittitle2ed);
            final Button b21 = view1.findViewById(R.id.doneed);
            final Button b31 = view1.findViewById(R.id.canceled);
            et.setText(name.get(position));
            et1.setText(number.get(position));
            et2.setText(price.get(position));
            AlertDialog.Builder adb = new AlertDialog.Builder(this)
                    .setView(view1);
            AlertDialog ad = adb.create();
            ad.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            ad.show();
            b21.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (TextUtils.isEmpty(et.getText().toString()) || TextUtils.isEmpty(et1.getText().toString()) || TextUtils.isEmpty(et2.getText().toString())){
                        if (TextUtils.isEmpty(et.getText().toString())){
                            et.setError("Cannot be empty.");
                        }
                        if (TextUtils.isEmpty(et1.getText().toString())){
                            et1.setError("Cannot be empty.");
                        }
                        if (TextUtils.isEmpty(et2.getText().toString())){
                            et2.setError("Cannot be empty.");
                        }
                    } else {
                        sd.deleteData(position);
                        sd.addData(et.getText().toString(), et1.getText().toString(), et2.getText().toString());
                        sd.rearrangePosition();
                        refreshData();
                        ad.cancel();
                    }
                }
            });
            b31.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ad.cancel();
                }
            });
        }
    }

    @Override
    public void onItemLongClick(int position) {
        if (position < name.size() - 1){
            LayoutInflater li = LayoutInflater.from(this);
            View view1 = li.inflate(R.layout.shoplist_delete_data, null);
            final Button b21 = view1.findViewById(R.id.yes1);
            final Button b31 = view1.findViewById(R.id.cancel2);
            AlertDialog.Builder adb = new AlertDialog.Builder(this)
                    .setView(view1);
            AlertDialog ad = adb.create();
            ad.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            ad.show();
            b21.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    sd.deleteData(position);
                    sd.rearrangePosition();
                    refreshData();
                    ad.cancel();
                }
            });
            b31.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ad.cancel();
                }
            });
        }
    }
}