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
import android.view.SurfaceHolder;
import android.view.TextureView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

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

    private CameraManager mCameraManager    = null;
    private String mCameraID;

    private int countFlash = 1;
    private int mNumberPhoto = 1;

    private ImageView mImageFlash;
    private Button mButtonTakePhoto = null;
    private Button mButtonFlashOnOff = null;
    private TextureView mImageView;
    private HandlerThread mBackgroundThread;
    private Handler mBackgroundHandler = null;
    CameraCharacteristics characteristics;
    StreamConfigurationMap map;
    Size mPreviewSize;

    private CameraDevice mCameraDevice = null;
    private CameraCaptureSession mCaptureSession;
    private ImageReader mImageReader;

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
        )
        {
            requestPermissions(new String[]{Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
        }

        mButtonTakePhoto =  findViewById(R.id.btnCapture);
        mButtonFlashOnOff =  findViewById(R.id.btnFlash);
        mImageFlash = findViewById(R.id.imageFlash);
        mImageView = findViewById(R.id.textureView);

        mCameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);

        try{
            mCameraID = mCameraManager.getCameraIdList()[0];
        } catch(CameraAccessException e){
            e.printStackTrace();
        }

        try {
            characteristics = mCameraManager.getCameraCharacteristics(mCameraID);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
        map = characteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP);
        mPreviewSize = map.getOutputSizes(SurfaceTexture.class)[0];

            if (mCameraDevice == null) {
                openCamera();
            }

        mButtonTakePhoto.setOnClickListener(new View.OnClickListener() {                            /** Listener mButtonTakePhoto **/
        @Override
        public void onClick(View v) {
            if (mCameraDevice == null) {
                makePhoto();
            }
        }
        });

        mButtonFlashOnOff.setOnClickListener(new View.OnClickListener() {                           /** Listener mButtonFlashOnOff **/
        @Override
        public void onClick(View v) {
         /**   switch (countFlash) {
                case 0: try {
                    mCameraManager.setTorchMode(mCameraID, false);
                } catch (CameraAccessException e) {
                    e.printStackTrace();
                }
                mImageFlash.setImageResource(R.drawable.ic_flash_off_white_64dp);
                countFlash = 1;
                break;
                case 1: try {
                    mCameraManager.setTorchMode(mCameraID, true);
                } catch (CameraAccessException e) {
                    e.printStackTrace();
                }
                mImageFlash.setImageResource(R.drawable.ic_flash_on_white_64dp);
                countFlash = 0;
                break;
            } **/
        }
        });

        Bundle arguments = getIntent().getExtras();
        Papka = arguments.getString("Papka");
        Zakazchik = arguments.getString("Zakazchik");
        position = arguments.getString("position");
        NameTu = arguments.getString("Name");
    } //  --- OnCreate ---



       /** public CameraService(CameraManager cameraManager, String cameraID) {
            mCameraManager = cameraManager;
            mCameraID = cameraID;
        } **/

        public void makePhoto (){                                                                   /** makePhoto **/
            try {
                final CaptureRequest.Builder captureBuilder =
                        mCameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_STILL_CAPTURE);
                captureBuilder.addTarget(mImageReader.getSurface());
                captureBuilder.set(CaptureRequest.CONTROL_MODE, CameraMetadata.CONTROL_MODE_AUTO);
                captureBuilder.set(CaptureRequest.CONTROL_AWB_MODE, CaptureRequest.CONTROL_AWB_MODE_WARM_FLUORESCENT);
                captureBuilder.set(CaptureRequest.CONTROL_AF_MODE, CaptureRequest.CONTROL_AF_TRIGGER_CANCEL);
                //Check orientation base on device
                int rotation = getWindowManager().getDefaultDisplay().getRotation();
                captureBuilder.set(CaptureRequest.JPEG_ORIENTATION,ORIENTATIONS.get(rotation));

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

            String mFileName = "Photo_" + mNumberPhoto + ".jpg";
            File mFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM), mFileName);

            @Override
            public void onImageAvailable(ImageReader reader) {
                mBackgroundHandler.post(new ImageSaver(reader.acquireNextImage(), mFile));
                mNumberPhoto++;
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

            mImageReader = ImageReader.newInstance(mPreviewSize.getWidth(),mPreviewSize.getHeight(), ImageFormat.JPEG,1);
            mImageReader.setOnImageAvailableListener(mOnImageAvailableListener, null);

            SurfaceTexture texture = mImageView.getSurfaceTexture();

            texture.setDefaultBufferSize(mPreviewSize.getWidth(),mPreviewSize.getHeight());

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

        TextureView.SurfaceTextureListener textureListener = new TextureView.SurfaceTextureListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int i, int i1) {
                openCamera();
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


    @Override
    public void onPause() {
        if (mCameraDevice == null) {
            closeCamera();
        }
        stopBackgroundThread();
        super.onPause();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onResume() {
        super.onResume();
        startBackgroundThread();
        if(mImageView.isAvailable()) {
            openCamera();
        }
        else {
            mImageView.setSurfaceTextureListener(textureListener);
        }
    }


    private static class ImageSaver implements Runnable {                                           /** ImageSaver **/

        private final Image mImage;
        private File mFileName;

        ImageSaver(Image image, File  fileName) {
            mImage = image;
            mFileName = fileName;
        }

        @Override
        public void run() {
            ByteBuffer buffer = mImage.getPlanes()[0].getBuffer();
            byte[] bytes = new byte[buffer.remaining()];
            buffer.get(bytes);
            FileOutputStream output = null;
            try {
                output =  new FileOutputStream(mFileName);
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


