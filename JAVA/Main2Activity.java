package com.example.alan.login;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Main2Activity extends AppCompatActivity {

    Button btnsalir;
    ImageButton btniamlo, btnipeña, btnitrump;
    TextView tvid;



    Connection con;
    String un,pass,db,ip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

btnsalir= (Button)findViewById(R.id.btnsalir);
tvid = (TextView)findViewById(R.id.tvid);
btniamlo = (ImageButton)findViewById(R.id.btnamlo);
btnipeña = (ImageButton)findViewById(R.id.btnpeña);
btnitrump = (ImageButton)findViewById(R.id.btntrump);


        ip = "10.0.1.15";
        db = "usuarios";
        un = "sa";
        pass = "alan123";

        btniamlo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                amlo();
            }
        });

        btnipeña.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                peña();
            }
        });

        btnitrump.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                trump();
            }
        });

btnsalir.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        salir();
    }
});
    }

    public void amlo(){

        String numtel = getIntent().getStringExtra("notel");

        try{
            con = connectionclass(un,pass,db,ip);
            if (con==null){

                Toast.makeText(getApplicationContext(), "Verifica tu conexion a internet", Toast.LENGTH_SHORT).show();
            }
            else{


                Statement stm = con.createStatement();
                ResultSet rs = stm.executeQuery("select * from nombres where estado='Inactivo(a)' AND telefono=" + numtel.toString());
                if (rs.next()){


                    String id = rs.getString(1).toString();
                    String amlo = "AMLO";

                    Toast.makeText(getApplicationContext(), "Tu voto es por AMLO", Toast.LENGTH_SHORT).show();
                    PreparedStatement myStmt = con.prepareStatement("insert into votos values(?,?)");


                    myStmt.setString(1, id.toString());
                    myStmt.setString(2, amlo.toString());

                    myStmt.executeUpdate();

                    PreparedStatement myStmt2 = con.prepareStatement("update nombres set estado='Voto_Si' where telefono=" + numtel.toString());
                    int rowsAffected = myStmt2.executeUpdate();



                }
                else{
                    Toast.makeText(getApplicationContext(), "No se pudo ingresar tu voto", Toast.LENGTH_SHORT).show();
                }


            }
        }catch (Exception e){

            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }


    }

    public void peña(){

        String numtel = getIntent().getStringExtra("notel");

        try{
            con = connectionclass(un,pass,db,ip);
            if (con==null){

                Toast.makeText(getApplicationContext(), "Verifica tu conexion a internet", Toast.LENGTH_SHORT).show();
            }
            else{


                Statement stm = con.createStatement();
                ResultSet rs = stm.executeQuery("select * from nombres where estado='Inactivo(a)' AND telefono=" + numtel.toString());
                if (rs.next()){


                    String id = rs.getString(1).toString();
                    String pena = "PEÑA";

                    Toast.makeText(getApplicationContext(), "Tu voto es por PEÑA", Toast.LENGTH_SHORT).show();
                    PreparedStatement myStmt = con.prepareStatement("insert into votos values(?,?)");


                    myStmt.setString(1, id.toString());
                    myStmt.setString(2, pena.toString());

                    myStmt.executeUpdate();

                    PreparedStatement myStmt2 = con.prepareStatement("update nombres set estado='Voto_Si' where telefono=" + numtel.toString());
                    int rowsAffected = myStmt2.executeUpdate();



                }
                else{
                    Toast.makeText(getApplicationContext(), "No se pudo ingresar tu voto", Toast.LENGTH_SHORT).show();
                }


            }
        }catch (Exception e){

            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }



    }

    public void trump(){

        String numtel = getIntent().getStringExtra("notel");

        try{
            con = connectionclass(un,pass,db,ip);
            if (con==null){

                Toast.makeText(getApplicationContext(), "Verifica tu conexion a internet", Toast.LENGTH_SHORT).show();
            }
            else{


                Statement stm = con.createStatement();
                ResultSet rs = stm.executeQuery("select * from nombres where estado='Inactivo(a)' AND telefono=" + numtel.toString());
                if (rs.next()){


                    String id = rs.getString(1).toString();
                    String trump = "TRUMP";

                    Toast.makeText(getApplicationContext(), "Tu voto es por TRUMP", Toast.LENGTH_SHORT).show();
                    PreparedStatement myStmt = con.prepareStatement("insert into votos values(?,?)");


                    myStmt.setString(1, id.toString());
                    myStmt.setString(2, trump.toString());

                    myStmt.executeUpdate();

                    PreparedStatement myStmt2 = con.prepareStatement("update nombres set estado='Voto_Si' where telefono=" + numtel.toString());
                    int rowsAffected = myStmt2.executeUpdate();



                }
                else{
                    Toast.makeText(getApplicationContext(), "No se pudo ingresar tu voto", Toast.LENGTH_SHORT).show();
                }


            }
        }catch (Exception e){

            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }


    }
//BOTON SALIR METODO.
    public void salir() {
        String numtel = getIntent().getStringExtra("notel");

        try{
            con = connectionclass(un,pass,db,ip);
            if (con==null){

                Toast.makeText(getApplicationContext(), "Verifica tu conexion a internet", Toast.LENGTH_SHORT).show();
            }
            else{


                Intent intent= new Intent(Main2Activity.this, MainActivity.class);
                startActivity(intent);
                Toast.makeText(getApplicationContext(), "Sesion cerrada correctamente", Toast.LENGTH_SHORT).show();
            }
        }catch (Exception e){

            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
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
