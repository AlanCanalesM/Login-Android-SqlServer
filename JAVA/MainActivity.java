package com.example.alan.login;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MainActivity extends AppCompatActivity {

    EditText ettelefono;
    Button btnacceso;
    ProgressBar progressBar;


    //variables de conexion
    Connection con;
    String un,pass,db,ip;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ettelefono=(EditText)findViewById(R.id.ettel);
        btnacceso=(Button)findViewById(R.id.btnacceder);
        progressBar=(ProgressBar)findViewById(R.id.progressBar);

        progressBar.setVisibility(View.GONE);


        //Declaracion de variables de conexion

        ip = "10.0.1.15";
        db = "usuarios";
        un = "sa";
        pass = "alan123";


        btnacceso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckLogin checkLogin = new CheckLogin ();
                checkLogin.execute("");
            }
        });


    }

    public class CheckLogin extends AsyncTask<String,String,String>{


        String z= "";
        Boolean isSuccess = false;

        @Override
        protected void onPreExecute(){

            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(String... params) {

            String notelefono=ettelefono.getText().toString();

            if (notelefono.trim().equals("")){

                z ="Por favor ingresa tu numero de telefono";
            }
            else {
                try{

                    con = connectionclass(un,pass,db,ip);
                    if (con == null){
                        z = "Verifica tu conexion a internet";
                    }
                    else{
                        String query = "select * from nombres where telefono=" + notelefono.toString();
                        Statement stmt = con.createStatement();
                        ResultSet rs = stmt.executeQuery(query);


                        if (rs.next()){

                            Intent intent= new Intent(MainActivity.this, Main2Activity.class);
                            intent.putExtra("notel", ettelefono.getText().toString());
                            startActivity(intent);
                            z = "Inicio de sesion correcto";
                            isSuccess = true;
                            con.close();

                        }
                        else {

                            z = "No existe ese numero en la base de datos";
                            isSuccess = false;
                        }
                    }
                }catch (Exception ex){

                    isSuccess = false;
                    z = ex.getMessage();


                }
            }
            return z;
        }

        @Override
        protected void onPostExecute(String r){
             progressBar.setVisibility(View.GONE);
            Toast.makeText(MainActivity.this, r, Toast.LENGTH_SHORT).show();
            if (isSuccess){
                Toast.makeText(MainActivity.this, "Inicio de sesion correcto", Toast.LENGTH_SHORT).show();
            }

        }
    }

    @SuppressLint("NewApi")
    public Connection connectionclass(String user, String password, String database, String server){

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Connection connection = null;
        String ConnectionURL= null;

        try{
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            ConnectionURL= "jdbc:jtds:sqlserver://" + server + ";databaseName=" + database + ";user=" + user + ";password=" + password + ";";
            connection = DriverManager.getConnection(ConnectionURL);
        }catch (SQLException se){
            Log.e("error aqui 1 : ", se.getMessage());
        }catch (ClassNotFoundException e){
            Log.e("error aqui 2 : ", e.getMessage());
        }catch (Exception e){
            Log.e("error aqui 3 : ", e.getMessage());
        }
        return connection;
    }
}
