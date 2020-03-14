package com.android.bluetoothapp;

import androidx.appcompat.app.AppCompatActivity;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    public  static  final int REQUEST_CONNECT_DEVICE=1;
    public  static  final  int REQUEST_BT=2;
    public  static  final UUID MY_UUID=UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    public  static BluetoothSocket mmSocket;
    public  static OutputStream  mmStream;
    public  static boolean CONNECT_STATUS=false;
    public BluetoothAdapter bluetoothAdapter;
    public TextView tv_rx;
    public EditText et_send;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv_rx=findViewById(R.id.receive_mes);
        et_send=findViewById(R.id.send_mes);

        Button blueConnect= findViewById(R.id.bt_conn);
        final Button  sendMes=findViewById(R.id.button_mes);
        Button  openL=findViewById(R.id.button_on);
        Button closeL=findViewById(R.id.button_off);
        Button  style1=findViewById(R.id.button_style1);
        Button style2=findViewById(R.id.button_style2);

        bluetoothAdapter=BluetoothAdapter.getDefaultAdapter();
        if (bluetoothAdapter==null){
            Toast.makeText(this,"wrong!",Toast.LENGTH_SHORT).show();
        }
      //========================================================================
        sendMes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mes=sendMes.getText().toString();
                if (CONNECT_STATUS){
                    write(mes);
                }else {
                    Toast.makeText(getApplicationContext(),"wrong!",Toast.LENGTH_SHORT).show();
                }
            }
        });

       openL.setOnClickListener(new  View.OnClickListener() {
           @Override
           public void onClick(View v) {
               if (CONNECT_STATUS){
                   write("ON");
               }else {
                   Toast.makeText(getApplicationContext(),"wrong!",Toast.LENGTH_SHORT).show();
               }

           }
       });

       closeL.setOnClickListener(new View.OnClickListener() {
       @Override
       public void onClick(View v) {
           if (CONNECT_STATUS){
               write("OFF");
           }
           else {
               Toast.makeText(getApplicationContext(),"wrong!",Toast.LENGTH_SHORT).show();
           }
       }
   });


       style1.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               if (CONNECT_STATUS){
                   write("THEME1");
               }else {
                   Toast.makeText(getApplicationContext(),"wrong!",Toast.LENGTH_SHORT).show();
               }
           }
       });

     style2.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             if (CONNECT_STATUS){
                 write("THEME2");
             }else {
                 Toast.makeText(getApplicationContext(),"wrong!",Toast.LENGTH_SHORT).show();
             }
         }
     });
      blueConnect.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              openOptionsMenu();
          }
      });
    }
    //=======================================================================
    public static void write(String str){
        if (CONNECT_STATUS){
            byte[] buffer= str.getBytes();
            try {
                mmStream=mmSocket.getOutputStream();
                mmStream.write(buffer);
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    //=====================================
    public void ConThread(BluetoothDevice device){
        try {
            mmSocket=device.createRfcommSocketToServiceRecord(MY_UUID);
            bluetoothAdapter.cancelDiscovery();
            mmSocket.connect();
            Toast.makeText(this,"连接成功",Toast.LENGTH_SHORT).show();
            CONNECT_STATUS=true;


        }catch (IOException e){
            e.printStackTrace();
        }

    }



}
