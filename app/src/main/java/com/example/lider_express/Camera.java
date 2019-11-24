package com.example.lider_express;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.ImageFormat;
import android.graphics.SurfaceTexture;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CameraMetadata;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.TotalCaptureResult;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.media.Image;
import android.media.ImageReader;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;
import android.util.Size;
import android.util.SparseIntArray;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Arrays;

public class Camera extends AppCompatActivity {
    CameraService myCamera;

    private CameraManager mCameraManager    = null;
    private String camera1;

    private Button tackPhoto;
    private Button flash;
    private TextureView mImageView = null;
    private HandlerThread mBackgroundThread;
    private Handler mBackgroundHandler = null;
    private Size imageDimension;


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);

        if ( checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED ||
                (ContextCompat.checkSelfPermission(Camera.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
        ) // if
        {
            requestPermissions(new String[]{Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
        }


        tackPhoto =  findViewById(R.id.btnCapture);
        flash =  findViewById(R.id.btnFlash);
        mImageView = findViewById(R.id.textureView);

        tackPhoto.setOnClickListener(new View.OnClickListener() {                          /** Listener mButtonOpenCamera1 **/
        @Override
        public void onClick(View v) {
            if (myCamera.isOpen()) myCamera.makePhoto();
        }
        });

        mCameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);



        try {
            camera1 = mCameraManager.getCameraIdList()[0];
        } catch (CameraAccessException ex) {
            ex.printStackTrace();
        }
                // создаем обработчик для камеры
        myCamera = new CameraService(mCameraManager,camera1);

        CameraCharacteristics characteristics = null;
        try {
            characteristics = mCameraManager.getCameraCharacteristics(camera1);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
        StreamConfigurationMap map = characteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP);
        assert map != null;
        imageDimension = map.getOutputSizes(SurfaceTexture.class)[0];

        if (myCamera != null) {
            if (!myCamera.isOpen()) myCamera.openCamera();
        }

    } //  --- OnCreate ---



    public class CameraService {                                                                    /** Class CameraService **/

    private File mFile = new File(Environment.
                    getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM), "test1.jpg");

        private String mCameraID;
        private CameraDevice mCameraDevice = null;
        private CameraCaptureSession mCaptureSession;
        private ImageReader mImageReader;

        public CameraService(CameraManager cameraManager, String cameraID) {
            mCameraManager = cameraManager;
            mCameraID = cameraID;
        }

        public void makePhoto (){                                                                   /** makePhoto **/
            try {
                // This is the CaptureRequest.Builder that we use to take a picture.
                final CaptureRequest.Builder captureBuilder =
                        mCameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_STILL_CAPTURE);

                captureBuilder.addTarget(mImageReader.getSurface());

                CameraCaptureSession.CaptureCallback CaptureCallback = new CameraCaptureSession.CaptureCallback() {

                    @Override
                    public void onCaptureCompleted(@NonNull CameraCaptureSession session,
                                                   @NonNull CaptureRequest request,
                                                   @NonNull TotalCaptureResult result) {
                    }
                }; // --- CaptureCallback ---

                mCaptureSession.stopRepeating();
                mCaptureSession.abortCaptures();
                mCaptureSession.capture(captureBuilder.build(), CaptureCallback, mBackgroundHandler);

            } catch (CameraAccessException e)
            {
                e.printStackTrace();
            }

        }  // --- MakePhoto ---


        private final ImageReader.OnImageAvailableListener mOnImageAvailableListener                /** ImageReader **/
                = new ImageReader.OnImageAvailableListener() {

            @Override
            public void onImageAvailable(ImageReader reader) {
                mBackgroundHandler.post(new ImageSaver(reader.acquireNextImage(), mFile));
            }

        };


        private CameraDevice.StateCallback mCameraCallback = new CameraDevice.StateCallback() {     /** CameraDevice.StateCallback **/

        @Override
        public void onOpened(CameraDevice camera) {
            mCameraDevice = camera;
            createCameraPreviewSession();
        }

            @Override
            public void onDisconnected(CameraDevice camera) {
                mCameraDevice.close();
                mCameraDevice = null;
            }

            @Override
            public void onError(CameraDevice camera, int error) {
            }
        }; // --- CameraDevice.StateCallback ---


        private void createCameraPreviewSession() {

            mImageReader = ImageReader.newInstance(imageDimension.getWidth(),imageDimension.getHeight(), ImageFormat.JPEG,1);
            mImageReader.setOnImageAvailableListener(mOnImageAvailableListener, null);

            SurfaceTexture texture = mImageView.getSurfaceTexture();

            if(texture == null){
                Log.i("Null", "Null");
            }

            texture.setDefaultBufferSize(imageDimension.getWidth(),imageDimension.getHeight());

            Surface surface = new Surface(texture);

            try {
                final CaptureRequest.Builder builder =
                        mCameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW);

                builder.addTarget(surface);

                mCameraDevice.createCaptureSession(Arrays.asList(surface,mImageReader.getSurface()),
                        new CameraCaptureSession.StateCallback() {
                            // =========================================
                            @Override
                            public void onConfigured(CameraCaptureSession session) {
                                mCaptureSession = session;
                                try {
                                    mCaptureSession.setRepeatingRequest(builder.build(),null,mBackgroundHandler);
                                } catch (CameraAccessException e) {
                                    e.printStackTrace();
                                }
                            } // onConfigured

                            @Override
                            public void onConfigureFailed(CameraCaptureSession session) {

                            }
                            // ============================================
                        }, // end StateCallback,
                        mBackgroundHandler);
            } catch (CameraAccessException e) {
                e.printStackTrace();
            }

        } // --- createCameraPreviewSession ---


        public boolean isOpen() {
            if (mCameraDevice == null) {
                return false;
            } else {
                return true;
            }
        }


        @RequiresApi(api = Build.VERSION_CODES.M)                                                      /** Request API 23 **/

        public void openCamera() {
            try {

                if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                    mCameraManager.openCamera(mCameraID,mCameraCallback,mBackgroundHandler);

                }
            } catch (CameraAccessException e) {
            }
        }

        public void closeCamera() {
            if (mCameraDevice != null) {
                mCameraDevice.close();
                mCameraDevice = null;
            }
        }


    } // ------- CameraService -------------


    @Override
    public void onPause() {
        if(myCamera.isOpen()){myCamera.closeCamera();}
        stopBackgroundThread();
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        startBackgroundThread();
    }


    private static class ImageSaver implements Runnable {                                           /** ImageSaver **/

        /**
         * The JPEG image
         */
        private final Image mImage;
        /**
         * The file we save the image into.
         */
        private final File mFile;

        ImageSaver(Image image, File file) {
            mImage = image;
            mFile = file;
        }

        @Override
        public void run() {
            ByteBuffer buffer = mImage.getPlanes()[0].getBuffer();
            byte[] bytes = new byte[buffer.remaining()];
            buffer.get(bytes);
            FileOutputStream output = null;
            try {
                output = new FileOutputStream(mFile);
                output.write(bytes);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                mImage.close();
                if (null != output) {
                    try {
                        output.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } // - if -
            } // - finally -
        } // -- run() --

    } // --- ImageSaver ---

    private void startBackgroundThread() {
        mBackgroundThread = new HandlerThread("CameraBackground");
        mBackgroundThread.start();
        mBackgroundHandler = new Handler(mBackgroundThread.getLooper());
    }

    private void stopBackgroundThread() {
        mBackgroundThread.quitSafely();
        try {
            mBackgroundThread.join();
            mBackgroundThread = null;
            mBackgroundHandler = null;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

} // ---- Main ----


