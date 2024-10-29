package gt.edu.umg.bliss;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class MainActivity2 extends AppCompatActivity {

    private static final int REQUEST_CAMERA_PERMISSION = 200;

    private VideoView backgroundVideoView;
    private Uri videoUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        backgroundVideoView = findViewById(R.id.backgroundVideoView);
        videoUri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.playita);

        setupVideoView();

        // Configurar el botón para abrir la cámara
        Button cameraButton = findViewById(R.id.button2);
        cameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(MainActivity2.this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(MainActivity2.this, new String[]{android.Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
                } else {
                    openCamera();
                }
            }
        });


        // Configurar el botón para abrir MainActivity3
        Button openMainActivity3Button = findViewById(R.id.button3);
        openMainActivity3Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMainActivity3();
            }
        });




        // Configurar el botón para abrir MainActivity4
        Button openMainActivity4Button = findViewById(R.id.button4);
        openMainActivity4Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMainActivity4();
            }
        });
    }

    private void setupVideoView() {
        backgroundVideoView.setVideoURI(videoUri);
        backgroundVideoView.setOnPreparedListener(mp -> {
            mp.setLooping(true);
            backgroundVideoView.start();
        });
    }

    private void openCamera() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (cameraIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(cameraIntent);
        }
    }

    private void openMainActivity3() {
        Intent intent = new Intent(MainActivity2.this, MainActivity3.class);
        startActivity(intent);
    }

    private void openMainActivity4() {
        Intent intent = new Intent(MainActivity2.this, MainActivity4.class);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        backgroundVideoView.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        backgroundVideoView.pause();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CAMERA_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openCamera();
            } else {
                // Permiso denegado
            }
        }
    }
}