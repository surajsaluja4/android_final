package com.compscitutorials.basigarcia.navigationdrawervideotutorial;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class Signup extends AppCompatActivity {
    Button sign;
    EditText name,email,phone,pass,confirm;
    String Names,Emails,Phones,Passs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);
        sign=(Button)findViewById(R.id.signup);
        name=(EditText)findViewById(R.id.Name);
        email=(EditText)findViewById(R.id.email);
        phone=(EditText)findViewById(R.id.Phone);
        pass=(EditText)findViewById(R.id.password);
        confirm=(EditText)findViewById(R.id.confirm);


        name.addTextChangedListener(new MyTextWatcher(name));
        email.addTextChangedListener(new MyTextWatcher(email));
        pass.addTextChangedListener(new MyTextWatcher(pass));
        phone.addTextChangedListener(new MyTextWatcher(phone));
        confirm.addTextChangedListener(new MyTextWatcher(confirm));

        sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                submitForm();

            }
        });
    }
    private void submitForm() {
        if (!validateName()) {
            return;
        }

        if (!validateEmail()) {
            return;
        }

        if (!validatePassword()) {
            return;
        }
        if (!validatephone()) {
            return;
        }
        if(!validateconfirmpass()){
            return;
        }

        saveinfo();
        Toast.makeText(getApplicationContext(), "Thank You!", Toast.LENGTH_SHORT).show();
        Intent i=new Intent(Signup.this,Resetpass.class);
        startActivity(i);
    }

    private boolean validateName() {
        if (name.getText().toString().trim().isEmpty()) {
            name.setError(getString(R.string.err_msg_name));
            requestFocus(name);
            return false;
        } else {
            //name.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validateconfirmpass() {
        String pass1 = pass.getText().toString().trim();
        String cpass1=confirm.getText().toString();

        if (cpass1.isEmpty() || !isValidconfirm(cpass1,pass1)) {
            confirm.setError(getString(R.string.err_msg_confirm));
            requestFocus(confirm);
            return false;
        } else {
            //inputLayoutEmail.setErrorEnabled(false);
        }

        return true;
    }
    private boolean validateEmail() {
        String emails = email.getText().toString().trim();

        if (emails.isEmpty() || !isValidEmail(emails)) {
            email.setError(getString(R.string.err_msg_email));
            requestFocus(email);
            return false;
        } else {
            //inputLayoutEmail.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validatephone() {

        String phno=phone.getText().toString();

        if (phno.isEmpty() || !isValidphoneno(phno)) {
            phone.setError(getString(R.string.err_msg_phone));
            requestFocus(phone);
            return false;
        } else {
            //inputLayoutEmail.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validatePassword() {
        if (pass.getText().toString().trim().isEmpty()) {
            pass.setError(getString(R.string.err_msg_password));
            requestFocus(pass);
            return false;
        } else {
            //pass.setErrorEnabled(false);
        }

        return true;
    }

    private static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private static boolean isValidconfirm(String con,String pass) {
        boolean r=!TextUtils.isEmpty(con) && con.equals(pass);
        return r;
    }
    private static boolean isValidphoneno(String phno) {
        return !TextUtils.isEmpty(phno) && Patterns.PHONE.matcher(phno).matches();
    }
    public void saveinfo(){
        Names=name.getText().toString();
        Emails=email.getText().toString();
        Phones=phone.getText().toString();
        Passs=pass.getText().toString();
        Backgroundtask bt=new Backgroundtask();
        bt.execute(Names, Emails, Phones, Passs);
        finish();

    }
    public void save_offline(){
        SharedPreferences sharedPref = getSharedPreferences("userinfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("usernname",Names);
        editor.putString("password",Passs);
        editor.commit();
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    private class MyTextWatcher implements TextWatcher {

        private View view;

        private MyTextWatcher(View view) {
            this.view = view;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void afterTextChanged(Editable editable) {
            switch (view.getId()) {
                case R.id.Name:
                    validateName();
                    break;
                case R.id.email:
                    validateEmail();
                    break;
                case R.id.password:
                    validatePassword();
                    break;
                case R.id.Phone:
                    validatephone();
                    break;
                case R.id.confirm:
                    validateconfirmpass();

            }
        }


    }

    class Backgroundtask extends AsyncTask<String,Void,String>
    {

        String add_url;
        @Override
        protected void onPreExecute() {
            add_url="https://www.piettechies.com/android/add_data.php";
        }

        @Override
        protected String doInBackground(String... args) {
            String Nameb,Emailb,Passb,Phoneb;
            Nameb=args[0];
            Emailb=args[1];
            Phoneb=args[2];
            Passb=args[3];

            URL url= null;
            try {
                url = new URL(add_url);
                HttpURLConnection httpURLConnection=(HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                OutputStream outputStream=httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));

                String data= URLEncoder.encode("name", "UTF-8")+"="+URLEncoder.encode(Nameb,"UTF-8")+"&"+
                        URLEncoder.encode("email","UTF-8")+"="+URLEncoder.encode(Emailb,"UTF-8")+"&"+
                        URLEncoder.encode("phone","UTF-8")+"="+URLEncoder.encode(Phoneb,"UTF-8")+"&"+
                        URLEncoder.encode("password","UTF-8")+"="+URLEncoder.encode(Passb,"UTF-8");
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream=httpURLConnection.getInputStream();
                inputStream.close();
                httpURLConnection.disconnect();
                return "One Row Inserted...";
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String result) {
            Snackbar.make(findViewById(android.R.id.content), result, Snackbar.LENGTH_INDEFINITE).setAction("Ok", new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            }).show();
        }
    }
}
