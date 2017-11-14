package com.compscitutorials.basigarcia.navigationdrawervideotutorial;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


/**
 * A simple {@link Fragment} subclass.
 */
public class BlankFragment extends Fragment {

    String jsonString;
    JSONObject jsonObject;
    private String user_data;
    Snackbar sb;
    String doj;
    StringBuilder stringBuilder=new StringBuilder();

    TextView train_num,train_name,coach_type,boarding_code,boarding_station,depart_time,to_station_code,to_station,arrival_time;
    public BlankFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View RootView= inflater.inflate(R.layout.fragment_blank, container, false);
        //tv=(TextView) RootView.findViewById(R.id.res);
        train_num=(TextView)RootView.findViewById(R.id.train_num_blank);
        train_name=(TextView)RootView.findViewById(R.id.train_name_blank);
        coach_type=(TextView)RootView.findViewById(R.id.coach_type_blank);
        boarding_code=(TextView)RootView.findViewById(R.id.boarding_st_code_blank);
        boarding_station=(TextView)RootView.findViewById(R.id.boarding_st_blank);
        depart_time=(TextView)RootView.findViewById(R.id.depart_time_blank);
        to_station_code=(TextView)RootView.findViewById(R.id.to_st_code_blank);
        to_station=(TextView)RootView.findViewById(R.id.to_st_blank);
        arrival_time=(TextView)RootView.findViewById(R.id.arrival_time_blank);


        return RootView;
    }
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle=new Bundle();
        bundle=getArguments();
        jsonString=bundle.getString("string_json");
        try{
            jsonObject=new JSONObject(jsonString);
//            jsonArray=jsonObject.getJSONArray("train");
//            int count=0;
//            while (count<jsonArray.length()){
//                JSONObject jo=jsonArray.getJSONObject(count);
//
//            }
            int response_code=jsonObject.getInt("response_code");
            if(response_code==200) {
                System.out.println(jsonString+"json string");
                JSONObject train_obj = jsonObject.getJSONObject("train");
                System.out.println("train num "+train_obj.getString("number"));
                train_num.setText(train_obj.getString("number"));
                train_name.setText(train_obj.getString("name"));

                JSONObject journey_class = jsonObject.getJSONObject("journey_class");
                coach_type.setText(journey_class.getString("name"));

                JSONObject boarding_point = jsonObject.getJSONObject("boarding_point");
                boarding_code.setText(boarding_point.getString("code"));
                boarding_station.setText(boarding_point.getString("name"));

                JSONObject reservation_upto = jsonObject.getJSONObject("reservation_upto");
                to_station_code.setText(reservation_upto.getString("code"));
                to_station.setText(reservation_upto.getString("name"));

                doj=jsonObject.getString("doj");


                new train_info().execute();
            }
            else{
                Get_Pnr_frag fragment = new Get_Pnr_frag();
                android.support.v4.app.FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, fragment).addToBackStack(null);
                fragmentTransaction.commit();
                Snackbar.make(getActivity().findViewById(android.R.id.content), "Invalid Pnr", Snackbar.LENGTH_INDEFINITE).setAction("Ok", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                }).show();
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }
    class train_info extends AsyncTask<Void,Void,String>
    {

        String add_url;
        @Override
        protected void onPreExecute() {
            add_url="http://api.railwayapi.com/v2/route/train/"+train_num.getText()+"/apikey/9jj3hddb3m/";
        }

        @Override
        protected String doInBackground(Void... args) {
            String Nameb,Emailb,Passb,Phoneb;


            URL url= null;
            try {
                url = new URL(add_url);
                System.out.println(add_url);
                HttpURLConnection httpURLConnection=(HttpURLConnection) url.openConnection();
                // httpURLConnection.setRequestMethod("POST");
                //httpURLConnection.setDoOutput(true);
                InputStream inputStream=httpURLConnection.getInputStream();
                BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream));

                while ((user_data=bufferedReader.readLine())!=null){
                    stringBuilder.append(user_data + "\n");
                    //System.out.println(user_data+"\n");
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
           // System.out.println("route"+result);
            try {
                int day1=0;
                int day2=0;
                String t1="";
                String t2="";


                JSONObject train_data=new JSONObject(result);
                JSONArray trainArray=train_data.getJSONArray("route");
                int count=0;
                while (count<trainArray.length()){
                    JSONObject station_data=trainArray.getJSONObject(count);
                   // System.out.println("boarding "+boarding_code.getText().toString()+" "+station_data.getString("code")+" "+station_data.getString("schdep"));
                    if(station_data.getString("code").toString().equals(boarding_code.getText().toString())&&t1.equals("")){
                        System.out.println("in boarding");
                        day1=station_data.getInt("day");
                        t1=station_data.getString("schdep");
                        System.out.println(station_data.getString("code")+" "+station_data.getString("schdep"));


                    }
                    else if(station_data.getString("code").toString().equals(to_station_code.getText().toString())&&!t1.equals("")){
                        System.out.println("in to");
                        day2=station_data.getInt("day");
                        t2=station_data.getString("scharr");
                        System.out.println(station_data.getString("code")+" "+station_data.getString("schdep"));


                    }

                        count++;
                }
                System.out.println("day1 "+day1);
                System.out.println("day2 "+day2);
                System.out.println("t1 "+t1);
                System.out.println("t2 " + t2);
                String dates[]=doj.split("-");
                depart_time.setText(dates[0]+" "+get_month(Integer.parseInt(dates[1]))+" | "+t1);
                int arrival_timet=Integer.parseInt(dates[0])+day2-day1;
                arrival_time.setText(arrival_timet+" "+get_month(Integer.parseInt(dates[1]))+" | "+t2);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }
    public String get_month(int mon){
        String month="";
        switch (mon){
            case 1:month="Jan";
                break;
            case 2:month="Feb";
                break;
            case 3:month="Mar";
                break;
            case 4:month="Apr";
                break;
            case 5:month="May";
                break;
            case 6:month="Jun";
                break;
            case 7:month="Jul";
                break;
            case 8:month="Aug";
                break;
            case 9:month="Sep";
                break;
            case 10:month="Oct";
                break;
            case 11:month="Nov";
                break;
            case 12:month="Dec";
                break;
            default:
                month="";

        }
        return month;
    }

}
