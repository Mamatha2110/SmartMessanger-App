package com.tutorialsee.ecommer.presentation;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.tutorialsee.ecommer.Database.DatabaseClient;
import com.tutorialsee.ecommer.HomeActivity;
import com.tutorialsee.ecommer.R;
import com.tutorialsee.ecommer.common.MyCustomDialog;
import com.tutorialsee.ecommer.modal.Login;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity implements MyCustomDialog.onSubmitListener{
    TextView uname_edit_text, password_edit_text;
    Button next_button, cancel_button;
    String str_uname, str_pwd;
    private List<Login> loginLists = new ArrayList<>();
    MyCustomDialog fragment_dialog;
    String str_match = "false";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        fragment_dialog = new MyCustomDialog();
        fragment_dialog.mListener = LoginActivity.this;

        uname_edit_text = (TextView) findViewById(R.id.uname_edit_text);
        password_edit_text = (TextView) findViewById(R.id.password_edit_text);
        next_button = (Button) findViewById(R.id.next_button);
        cancel_button = (Button) findViewById(R.id.cancel_button);

        LoginDataInsertion loginDataInsertion = new LoginDataInsertion();
        loginDataInsertion.execute();

        next_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                str_uname = uname_edit_text.getText().toString();
                str_pwd = password_edit_text.getText().toString();

                if (str_uname.length() == 0) {
                    uname_edit_text.setError("Please enter username");
                } else if (str_pwd.length() == 0) {
                    password_edit_text.setError("Please enter password");
                } else {
                    for (int i = 0; i < loginLists.size(); i++) {
                        if (str_uname.trim().equals(loginLists.get(i).getUserid().trim()) && str_pwd.equals(loginLists.get(i).getPassword())) {
                            str_match = "true";
                            Intent ii = new Intent(LoginActivity.this, HomeActivity.class);
                            startActivity(ii);
                        }
                    }
                    if (str_match.equalsIgnoreCase("false")) {
                        fragment_dialog.setDialog(R.layout.custom_dialog, LoginActivity.this, 0, 0, "Alert!", "Please enter valid username/password", "Ok", "");
                        fragment_dialog.show(getFragmentManager(), "");
                    }
                }

            }
        });
        cancel_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void setOnSubmitListener(int flag, int flag1) {
        if (flag == 1) {
            this.finish();
        }
    }

    class LoginDataInsertion extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            try {
                int count;
                count = DatabaseClient.getInstance(getApplicationContext()).getAppDatabase().loginDao().logincount();
                if (count == 0) {
                    DatabaseClient.getInstance(getApplicationContext()).getAppDatabase().loginDao().insertAll(Login.InsertData());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            GetData getData = new GetData();
            getData.execute();
        }
    }

    class GetData extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            try {
                loginLists = DatabaseClient.getInstance(getApplicationContext()).getAppDatabase().loginDao().getLoginDatils();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }

    /* Detect when the back button is pressed */
    @Override
    public void onBackPressed() {
        fragment_dialog.setDialog(R.layout.custom_dialog2, LoginActivity.this, 1, 0, "Alert!", "Are you sure want to close this application", "Yes", "No");
        fragment_dialog.show(LoginActivity.this.getFragmentManager(), "");
    }

}
