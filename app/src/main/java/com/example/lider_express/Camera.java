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
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.FileAttribute;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;
import java.util.Arrays;
import java.util.Set;

public class Camera extends AppCompatActivity {

    CameraService myCameras = null;

    private CameraManager mCameraManager    = null;
    private final int CAMERA1   = 0;
    private String cameraID;

    private Button mButtonTakePhoto = null;
    private Button mButtonFlash = null;
    private Button mButtonBrightness = null;
    private TextureView mTextureView = null;
    private HandlerThread mBackgroundThread;
    private Handler mBackgroundHandler = null;

    String Papka;
    String Zakazchik;
    String position;
    String NameTu;

    private static final SparseIntArray ORIENTATIONS = new SparseIntArray();
    static{
        ORIENTATIONS.append(Surface.ROTATION_0,90);
        ORIENTATIONS.append(Surface.ROTATION_90,0);
        ORIENTATIONS.append(Surface.ROTATION_180,270);
        ORIENTATIONS.append(Surface.ROTATION_270,180);
    }

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

        mButtonTakePhoto =  findViewById(R.id.btnCapture);
       // mButtonFlash =  findViewById(R.id.btnFlash);
        mButtonBrightness =findViewById(R.id.btnBrightness);
        mTextureView = findViewById(R.id.textureView);

        mCameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);

        try{
            cameraID = mCameraManager.getCameraIdList()[CAMERA1];
            // обработчик для камеры
            myCameras = new CameraService(mCameraManager,cameraID);
        } catch(CameraAccessException e){
            e.printStackTrace();
        }

        assert mTextureView != null;
        mTextureView.setSurfaceTextureListener(textureListener);

      //  if (myCameras != null) {
      //      if (!myCameras.isOpen()) myCameras.openCamera();
      //  }

        mButtonTakePhoto.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                myCameras.makePhoto();
            }
        });

        Bundle arguments = getIntent().getExtras();
        Papka = arguments.getString("Papka");
        Zakazchik = arguments.getString("Zakazchik");
        position = arguments.getString("position");
        NameTu = arguments.getString("Name");
    } //  --- OnCreate ---






    public class CameraService {                                                                    /** Class CameraService **/

    int i = 0;

        private String mCameraID;
        private CameraDevice mCameraDevice = null;
        private CameraCaptureSession mCaptureSession;
        private ImageReader mImageReader;
        private int mImageWidth;
        private int mImageHeight;

        public CameraService(CameraManager cameraManager, String cameraID) {
            mCameraManager = cameraManager;
            mCameraID = cameraID;
        }

        /** ========================================= MAKE PHOTO ============================== **/
        public void makePhoto (){                                                                   /** makePhoto **/
            try {
                final CaptureRequest.Builder captureBuilder =
                        mCameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_STILL_CAPTURE);

                captureBuilder.addTarget(mImageReader.getSurface());

                int rotation = getWindowManager().getDefaultDisplay().getRotation();
                captureBuilder.set(CaptureRequest.JPEG_ORIENTATION,ORIENTATIONS.get(rotation));

                CameraCaptureSession.CaptureCallback CaptureCallback = new CameraCaptureSession.CaptureCallback() {
                    @Override
                    public void onCaptureCompleted(@NonNull CameraCaptureSession session,
                                                   @NonNull CaptureRequest request,
                                                   @NonNull TotalCaptureResult result) {
                    }
                }; // --- CaptureCallback ---


              //  mCaptureSession.stopRepeating();
              //  mCaptureSession.abortCaptures();
                mCaptureSession.capture(captureBuilder.build(), CaptureCallback, mBackgroundHandler);


            } catch (CameraAccessException e)
            {
                e.printStackTrace();
            }

        }  // --- MakePhoto ---
        /** ----------------------------------------- MAKE PHOTO ------------------------------ **/

        /** ============================== ImageAvailableListener ============================= **/
        private final ImageReader.OnImageAvailableListener mOnImageAvailableListener                /** ImageReader **/
                = new ImageReader.OnImageAvailableListener() {

            @Override
            public void onImageAvailable(ImageReader reader) {
                i++;
                File mFile = new File(Environment.
                        getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM)
                           + "/Job"
                           + "/" + Zakazchik + "/" + position + "_" + NameTu + "/" + Papka);
                mFile.mkdirs();
                File mFileWrite = new File(mFile, "Photo" + String.valueOf(i) + ".jpg");
                mBackgroundHandler.post(new ImageSaver(reader.acquireNextImage(), mFileWrite));
            }
        };
        /** ------------------------------ ImageAvailableListener ----------------------------- **/

        /** ============================== StateCallback mCameraCallback ====================== **/
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
        /** ---------------------------- StateCallback mCameraCallback ------------------------ **/


        /** ============================== createCameraPreviewSession ========================= **/
        private void createCameraPreviewSession() {


            mImageWidth = MainActivity.getDisplayWidth();
            mImageHeight = MainActivity.getDisplayHeight();

            mImageReader = ImageReader.newInstance(mImageWidth,mImageHeight, ImageFormat.JPEG,1);

            mImageReader.setOnImageAvailableListener(mOnImageAvailableListener, null);

            SurfaceTexture texture = mTextureView.getSurfaceTexture();

            texture.setDefaultBufferSize(mImageWidth,mImageHeight);
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
                            public void onConfigureFailed(CameraCaptureSession session) {}
                            // ============================================
                        }, // end StateCallback,
                        mBackgroundHandler);
            } catch (CameraAccessException e) {
                e.printStackTrace();
            }

        } // --- createCameraPreviewSession ---
        /** ------------------------------ createCameraPreviewSession ------------------------- **/


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
        } // - openCamera -

        public void closeCamera() {
            if (mCameraDevice != null) {
                mCameraDevice.close();
                mCameraDevice = null;
            }
        } // - closeCamera -
    } // ------- CameraService -------------


    @Override
    public void onPause() {
        if(myCameras.isOpen()){myCameras.closeCamera();}
        stopBackgroundThread();
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        startBackgroundThread();
    }




    TextureView.SurfaceTextureListener textureListener = new TextureView.SurfaceTextureListener() {
        @RequiresApi(api = Build.VERSION_CODES.M)
        @Override
        public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int i, int i1) {
            myCameras.openCamera();
        }
        @Override
        public void onSurfaceTextureSizeChanged(SurfaceTexture surfaceTexture, int i, int i1) {
        }
        @Override
        public boolean onSurfaceTextureDestroyed(SurfaceTexture surfaceTexture) {
            return false;
        }
        @Override
        public void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {
        }
    };


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


