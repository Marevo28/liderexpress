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
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.TotalCaptureResult;
import android.media.Image;
import android.media.ImageReader;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Range;
import android.util.SparseIntArray;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.SeekBar;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.lider_express.Instruments.RWClass;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Arrays;

public class Camera extends AppCompatActivity {

    private CameraService myCamera = null;
    private CameraManager mCameraManager    = null;
    public AppCompatActivity appCompatActivity;
    private HandlerThread mBackgroundThread;
    private Handler mBackgroundHandler = null;
    public Context context;

    private String Papka;
    private String Zakazchik;
    private String position;
    private String NameTu;
    private String cameraID;

    private final int CAMERA1   = 0;
    private static final int REQUEST_CAMERA_PERMISSION = 200;

    private boolean mFlashFlag = false;
    private boolean mFlashFlagCount = false;
    private boolean mBrightnessFlagCount = true;

    private Button mButtonTakePhoto = null;
    private ToggleButton mButtonFlash = null;
    private ToggleButton mButtonBrightness = null;
    private FrameLayout mFrameLayoutSeekBar = null;
    private SeekBar seekBar = null;
    private TextureView mTextureView = null;

    private static final SparseIntArray ORIENTATIONS = new SparseIntArray();

    static{ ORIENTATIONS.append(Surface.ROTATION_0,90);
            ORIENTATIONS.append(Surface.ROTATION_90,0);
            ORIENTATIONS.append(Surface.ROTATION_180,270);
            ORIENTATIONS.append(Surface.ROTATION_270,180);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);

        appCompatActivity = this;
        context = this;

        mButtonTakePhoto =  findViewById(R.id.btnCapture);
        mButtonFlash =  findViewById(R.id.btnFlash);
        mButtonBrightness = findViewById(R.id.btnBrightness);
        mFrameLayoutSeekBar = findViewById(R.id.frameLayoutSeekBar);
        mFrameLayoutSeekBar.setVisibility(View.INVISIBLE);
        seekBar = findViewById(R.id.seekBar);
        mTextureView = findViewById(R.id.textureView);

        mCameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);

        try{
            cameraID = mCameraManager.getCameraIdList()[CAMERA1];
            // обработчик для камеры
            myCamera = new CameraService(mCameraManager,cameraID);
        } catch(CameraAccessException e){
            e.printStackTrace();
        }

        assert mTextureView != null;
        mTextureView.setSurfaceTextureListener(textureListener);

        mButtonTakePhoto.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                myCamera.makePhoto();
            }
        });

        mButtonFlash.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if(mButtonFlash.isChecked()){
                    mFlashFlag = true;

                }else{
                    mFlashFlag = false;
                }
                mFlashFlagCount = true;
            }
        });

        mButtonBrightness.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if(mButtonBrightness.isChecked()){
                    mFrameLayoutSeekBar.setVisibility(View.VISIBLE);
                    mBrightnessFlagCount = false;
                    myCamera.setBrightness(seekBar.getProgress());
                }else{
                    mFrameLayoutSeekBar.setVisibility(View.INVISIBLE);
                    mBrightnessFlagCount = true;
                    myCamera.setBrightness(0);
                }
            }
        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                myCamera.setBrightness(progress);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) { }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        Bundle arguments = getIntent().getExtras();
        Papka = arguments.getString("Papka");
        Zakazchik = arguments.getString("Zakazchik");
        position = arguments.getString("position");
        NameTu = arguments.getString("Name");
    } //  --- OnCreate ---

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == REQUEST_CAMERA_PERMISSION)
        {
            if(grantResults[0] != PackageManager.PERMISSION_GRANTED)
            {
                Toast.makeText(this, "You can't use camera without permission", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    /** ===================================== CLASS CameraService ============================== **/
    public class CameraService {

        private CameraDevice mCameraDevice = null;
        private CameraCaptureSession mCaptureSession;
        private ImageReader mImageReader;
        private CaptureRequest.Builder mBuilder;
        private RWClass rwClass = new RWClass();

        private String mCameraID;

        private int mImageWidth;
        private int mImageHeight;
        private int minCompensationRange = 0;
        private int maxCompensationRange = 10;

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

                if(mFlashFlagCount) {
                    if (mFlashFlag) {
                        mBuilder.set(CaptureRequest.FLASH_MODE, CaptureRequest.FLASH_MODE_SINGLE);
                        mCaptureSession.setRepeatingRequest(mBuilder.build(), null, null);
                        mFlashFlagCount = false;
                    } else {
                        mBuilder.set(CaptureRequest.FLASH_MODE, CaptureRequest.FLASH_MODE_OFF);
                        mCaptureSession.setRepeatingRequest(mBuilder.build(), null, null);
                        mFlashFlagCount = false;
                    }
                }

                if(mBrightnessFlagCount) {
                    captureBuilder.set(CaptureRequest.CONTROL_AE_TARGET_FPS_RANGE, getRange());
                }

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
                File mFile = new File(Environment.
                        getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM)
                           + "/Job"
                           + "/" + Zakazchik + "/" + position + "_" + NameTu + "/" + Papka);
                mFile.mkdirs();

                rwClass.setPath(mFile.toString());  // Указываем путь

                int countPhoto = 0;
                try {
                    countPhoto = rwClass.getCountPhoto();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                File mFileWrite = new File(mFile, "Photo" + countPhoto + ".jpg");

                String fullPath = "DCIM" + "/Job" + "/" + Zakazchik + "/" + position + "_" + NameTu + "/"
                        + Papka + "Photo" + countPhoto + ".jpg";

                try {
                    rwClass.writeCountPhoto(countPhoto); //Записываем в текст файл
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Toast toast = Toast.makeText(getApplicationContext(),
                        fullPath, Toast.LENGTH_LONG);
                toast.show();

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
                mBuilder = mCameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW);
                mBuilder.addTarget(surface);
                if(mBrightnessFlagCount) {
                    mBuilder.set(CaptureRequest.CONTROL_AE_TARGET_FPS_RANGE, getRange());
                }
                mCameraDevice.createCaptureSession(Arrays.asList(surface,mImageReader.getSurface()),
                        new CameraCaptureSession.StateCallback() {
                            // =========================================
                            @Override
                            public void onConfigured(CameraCaptureSession session) {
                                mCaptureSession = session;
                                try {
                                    mCaptureSession.setRepeatingRequest(mBuilder.build(),null,mBackgroundHandler);
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

        /** ==================================== Range ======================================== **/
        private Range<Integer> getRange() {
            CameraCharacteristics chars = null;
            try {
                chars = mCameraManager.getCameraCharacteristics(cameraID);
                Range<Integer>[] ranges = chars.get(CameraCharacteristics.CONTROL_AE_AVAILABLE_TARGET_FPS_RANGES);
                Range<Integer> result = null;
                for (Range<Integer> range : ranges) {
                    int upper = range.getUpper();
                    // 10 - min range upper for my needs
                    if (upper >= 10) {
                        if (result == null || upper < result.getUpper().intValue()) {
                            result = range;
                        }
                    }
                }
                if (result == null) {
                    result = ranges[0];
                }
                return result;
            } catch (CameraAccessException e) {
                e.printStackTrace();
                return null;
            }
        }
        /** ------------------------------------------ Range ----------------------------------- **/

        /** ==================================== setBrightness ================================= **/
        public void setBrightness(int value) {
            int brightness = (int) (minCompensationRange + (maxCompensationRange - minCompensationRange) * (value / 100f));
            mBuilder.set(CaptureRequest.CONTROL_AE_EXPOSURE_COMPENSATION, brightness);
            try {
                mCaptureSession.setRepeatingRequest(mBuilder.build(), null, null);
            } catch (CameraAccessException e) {
                e.printStackTrace();
            }
        }
        /** --------------------------------- setBrightness ----------------------------------- **/


        public boolean isOpen() {
            if (mCameraDevice == null) {
                return false;
            } else {
                return true;
            }
        }

        /** ====================================== openCamera ================================= **/
        @RequiresApi(api = Build.VERSION_CODES.M)                                                      /** Request API 23 **/
        public void openCamera() {
            try {

                if(ActivityCompat.checkSelfPermission(context, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
                {
                    ActivityCompat.requestPermissions(appCompatActivity,new String[]{
                            Manifest.permission.CAMERA,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE
                    },REQUEST_CAMERA_PERMISSION);
                    return;
                }
                    mCameraManager.openCamera(mCameraID,mCameraCallback,mBackgroundHandler);

            } catch (CameraAccessException e) {
            }
        } // - openCamera -
        /** --------------------------------- openCamera ----------------------------------- **/

        public void closeCamera() {
            if (mCameraDevice != null) {
                mCameraDevice.close();
                mCameraDevice = null;
            }
        } // - closeCamera -

    } // - CameraService -
    /** -------------------------------------class CameraService ------------------------------ **/


    @Override
    public void onPause() {
        if(myCamera.isOpen()){myCamera.closeCamera();}
        stopBackgroundThread();
        super.onPause();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onResume() {
        super.onResume();
        startBackgroundThread();
        if(mTextureView.isAvailable())
            myCamera.openCamera();
        else
            mTextureView.setSurfaceTextureListener(textureListener);
    }



    /** ================================== textureListener ====================================== **/
    TextureView.SurfaceTextureListener textureListener = new TextureView.SurfaceTextureListener() {
        @RequiresApi(api = Build.VERSION_CODES.M)
        @Override
        public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int i, int i1) {
            myCamera.openCamera();
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
    /** ------------------------------------ textureListener ----------------------------------- **/


    /** ==================================== ImageSaver ======================================== **/
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
    /** ------------------------------------ ImageSaver --------------------------------------- **/

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


