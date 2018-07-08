package com.example.plquang.readqr;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Application;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.blikoon.qrcodescanner.QrCodeActivity;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_CODE_QR_SCAN = 101;
Button btn;

    private  static  final  int MY_PERMISSIONS_REQUEST_CAMERA=102;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         initPermission();

      btn=(Button) findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this,QrCodeActivity.class);

                startActivityForResult( i,REQUEST_CODE_QR_SCAN);

            }
        });

    }
 @Override
    public void onRequestPermissionsResult(int requestCode, @android.support.annotation.NonNull String[] permissions, @android.support.annotation.NonNull int[] grantResults) {
        if (requestCode == 1) {
            if (grantResults.length == 1 &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(MainActivity.this, "Bạn đã cấp quyền thành công !", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(MainActivity.this, "Bạn đã từ chối cấp quyền ! Vui lòng thử lại", Toast.LENGTH_SHORT).show();
 android.os.Process.killProcess(android.os.Process.myPid());
                    System.exit(1);
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
public void initPermission(){
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED||checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

                //Permisson don't granted
                if (shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)||shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    Toast.makeText(MainActivity.this, "Vui lòng đồng ý cấp quyền để ứng dụng hoạt động !", Toast.LENGTH_SHORT).show();

                }
                // Permisson don't granted and dont show dialog again.
                else {
                    Toast.makeText(MainActivity.this, "Vui lòng đồng ý cấp quyền để ứng dụng hoạt động ! ", Toast.LENGTH_SHORT).show();

                }
                //Register permission
                requestPermissions(new String[]{Manifest.permission.CAMERA,Manifest.permission.READ_EXTERNAL_STORAGE}, 1);

            }
        }
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {


            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.CAMERA)) {


            } else {



                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.CAMERA},
                        101);


            }
        }
               if(requestCode == REQUEST_CODE_QR_SCAN)
        {
            if(data==null)
            {
                return;
            }
            //Getting the passed result
            String result = data.getStringExtra("com.blikoon.qrcodescanner.got_qr_scan_relult");

//            Toast.makeText(this,"Have scan result in your app activity :"+result,Toast.LENGTH_SHORT).show();
if(result==null)
{
    AlertDialog alertDialog1 = new AlertDialog.Builder(MainActivity.this).create();
    alertDialog1.setTitle("Scan Fail!");
    alertDialog1.setMessage("Không có dữ liệu, vui lòng thử lại");
    alertDialog1.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
            new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });

    alertDialog1.show();
}
else {


    AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
    alertDialog.setTitle("Sucessfull!");
    alertDialog.setMessage(result);
    alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "OK",
            new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
    alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Exit",
            new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    android.os.Process.killProcess(android.os.Process.myPid());
                    System.exit(1);
                }
            });
    alertDialog.show();
    Toast.makeText(this,"Mật khẩu wifi có dạng P: \"Mật khẩu\"",Toast.LENGTH_LONG).show();
}
        }
    }

}


