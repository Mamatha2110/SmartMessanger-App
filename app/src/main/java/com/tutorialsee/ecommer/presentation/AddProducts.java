package com.tutorialsee.ecommer.presentation;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.tutorialsee.ecommer.Database.DatabaseClient;
import com.tutorialsee.ecommer.R;
import com.tutorialsee.ecommer.common.MyCustomDialog;
import com.tutorialsee.ecommer.modal.Productdetails;

public class AddProducts extends AppCompatActivity implements MyCustomDialog.onSubmitListener{

    Spinner sp_category;
    EditText et_name,et_disc;
    Button btn_add;
    String str_cate,str_name,str_disc;
    MyCustomDialog fragment_dialog;
    Productdetails productDet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_products);

        fragment_dialog = new MyCustomDialog();
        fragment_dialog.mListener = AddProducts.this;

        sp_category = (Spinner)findViewById(R.id.sp_category);
        et_name = (EditText)findViewById(R.id.et_name);
        et_disc = (EditText)findViewById(R.id.et_disc);
        btn_add = (Button)findViewById(R.id.btn_add);

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isValidData();
            }
        });
    }

    boolean isValidData() {
        boolean valid = true;

        str_cate = sp_category.getSelectedItem().toString();
        str_name = et_name.getText().toString();
        str_disc = et_disc.getText().toString();

        if (str_cate.equals("Select")) {
            valid = false;
            fragment_dialog.setDialog(R.layout.custom_dialog, AddProducts.this, 0, 0, "Alert!", "Please select Category", "Ok", "");
            fragment_dialog.show(getFragmentManager(), "");
        }  else if (str_name.length() == 0) {
            valid = false;
            fragment_dialog.setDialog(R.layout.custom_dialog, AddProducts.this, 0, 0, "Alert!", "Please enter Product Name", "Ok", "");
            fragment_dialog.show(getFragmentManager(), "");
        } else if (str_cate.length() == 0) {
            valid = false;
            fragment_dialog.setDialog(R.layout.custom_dialog, AddProducts.this, 0, 0, "Alert!", "Please enter Discription", "Ok", "");
            fragment_dialog.show(getFragmentManager(), "");
        } else{
            SaveTask saveTask = new SaveTask();
            saveTask.execute();
        }

        return valid;
    }

    @Override
    public void setOnSubmitListener(int flag, int flag1) {

    }

    private class SaveTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                productDet = new Productdetails();

                productDet.setCategory(str_cate);
                productDet.setName(str_name);
                productDet.setDiscription(str_disc);
                DatabaseClient.getInstance(AddProducts.this).getAppDatabase().loginDao().insertProducts(productDet);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Toast.makeText(AddProducts.this, "Product added sucessfully", Toast.LENGTH_LONG).show();
        }
    }
}
