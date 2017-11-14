package com.compscitutorials.basigarcia.navigationdrawervideotutorial;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class Get_Pnr_frag extends Fragment {

    EditText pnr,train_number;
    Button pnr_submit;
    String pnr_string,train_string,pnr_no;
    private String user_data;
    TextView tv;

    StringBuilder stringBuilder=new StringBuilder();
     View RootView;
    SharedPreferences sp;

    public Get_Pnr_frag() {
        // Required empty public constructor
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         RootView=inflater.inflate(R.layout.fragment_get__pnr_frag, container, false);


        return RootView;


    }
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        pnr=(EditText) RootView.findViewById(R.id.pnr_text);

        // train_number=(EditText)findViewById(R.id.train_text);
        pnr_submit=(Button)RootView.findViewById(R.id.pnr_submit);
        sp=getActivity().getSharedPreferences("login",Context.MODE_PRIVATE);

        //if SharedPreferences contains username and password then directly redirect to Home activity
        if(sp.contains("pnr")){

          pnr_no=sp.getString("pnr","empty");
            System.out.println("Shared pref Contains Pnr"+pnr_no);
            new Backgroundtask_pnr().execute();
                    }
        else{

            tv=(TextView)RootView.findViewById(R.id.sr);

            System.out.println("shared pref does not contain pnr");
            pnr_submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    getpnr_no();
                    System.out.println("pnr no "+pnr_no);
                    get_pnr_data();


                }
            });
        }
    }
    private void get_pnr_data(){
        if (!validate_pnr_number()) {
            return;
        }

        saveinfo();


    }
    public void getpnr_no(){
pnr_no=pnr.getText().toString();
    }
    private boolean validate_train_number() {
        if (train_number.getText().toString().trim().isEmpty()||(train_number.getText().length()!=5)) {
            train_number.setError(getString(R.string.err_msg_password));
            requestFocus(train_number);
            return false;
        } else {
            //pass.setErrorEnabled(false);
        }

        return true;
    }
    private boolean validate_pnr_number() {
        if (pnr.getText().toString().trim().isEmpty()||(pnr.getText().length()!=10)) {
            pnr.setError("invalid pnr");
            requestFocus(pnr);
            return false;
        } else {
            //pass.setErrorEnabled(false);
        }

        return true;
    }
    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
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
                case R.id.pnr_text:
                    validate_pnr_number();

            }
        }


    }
    public void saveinfo(){
        pnr_string=pnr.getText().toString();
        // train_string=train_number.getText().toString();


    }
    public void set_shared_pref(){
        SharedPreferences.Editor editor=getActivity().getSharedPreferences("login", Context.MODE_PRIVATE).edit();
        editor.putString("pnr",pnr_no);
        editor.apply();

        SharedPreferences sp=getContext().getSharedPreferences("login",Context.MODE_PRIVATE);
        String pass=sp.getString("pnr",null);
        System.out.println("pass"+pass);
                System.out.println("sp saved in "+pnr_no);

    }
    class Backgroundtask_pnr extends AsyncTask<Void,Void,String>
    {

        String add_url;
        @Override
        protected void onPreExecute() {

            add_url="http://api.railwayapi.com/v2/pnr-status/pnr/"+pnr_no+"/apikey/9jj3hddb3m/";
            System.out.println("add url "+add_url);
        }

        @Override
        protected String doInBackground(Void... args) {
            String Nameb,Emailb,Passb,Phoneb;


            URL url= null;
            try {
                url = new URL(add_url);
                HttpURLConnection httpURLConnection=(HttpURLConnection) url.openConnection();
                // httpURLConnection.setRequestMethod("POST");
                //httpURLConnection.setDoOutput(true);
                InputStream inputStream=httpURLConnection.getInputStream();
                BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream));

                while ((user_data=bufferedReader.readLine())!=null){
                    stringBuilder.append(user_data+"\n");
                    System.out.println(user_data+"\n");
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
//
//                JSONObject jsonObject=new JSONObject(stringBuilder.toString().trim());
//                JSONArray jsonArray=jsonObject.getJSONArray("train_num");
//                System.out.println(jsonArray.length());

            } catch (Exception e) {
                e.printStackTrace();
            }

            return stringBuilder.toString();
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String result) {


            if (result == null) {

                Snackbar.make(getActivity().findViewById(android.R.id.content), "Invalid Pnr", Snackbar.LENGTH_INDEFINITE).setAction("Ok", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                }).show();
            }
            else{
                try {
                    JSONObject jsonObject=new JSONObject(result);
                    int json_res=jsonObject.getInt("response_code");
                    System.out.println("json_response"+json_res);
                    if(json_res==200){
                        set_shared_pref();
                        BlankFragment fragment = new BlankFragment();
                        Bundle bundle=new Bundle();
                        android.support.v4.app.FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                        bundle.putString("string_json",result);
                        fragment.setArguments(bundle);
                        fragmentTransaction.replace(R.id.fragment_container, fragment).addToBackStack(null);
                        fragmentTransaction.commit();
                    }
                    else{
                        Snackbar.make(getActivity().findViewById(android.R.id.content), "Invalid Pnr", Snackbar.LENGTH_INDEFINITE).setAction("Ok", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                            }
                        }).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }



}

