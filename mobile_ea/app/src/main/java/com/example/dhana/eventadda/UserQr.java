package com.example.dhana.eventadda;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
public class UserQr extends AppCompatActivity {
    ImageView imageView;
    Button button;
    EditText editText;
    String qr;
    Thread thread;
    public final static int QRcodeWidth = 500;
    Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_qr);
        qr="Hello App";
        imageView = (ImageView) findViewById(R.id.imageViewQR);
        try {
            bitmap = TextToImageEncode(qr);
        } catch (WriterException e) {
            e.printStackTrace();
        }
        imageView.setImageBitmap(bitmap);
    }
    private Bitmap TextToImageEncode(String editTextValue) throws WriterException {
        BitMatrix bitMatrix = null;

        try {
            bitMatrix = new MultiFormatWriter().encode(editTextValue,
                    BarcodeFormat.DATA_MATRIX.QR_CODE,
                    QRcodeWidth, QRcodeWidth, null
            );

        } catch (IllegalArgumentException Illegalargumentexception) {

            return null;
        } catch (WriterException e) {
            e.printStackTrace();
        }

        int bitMatrixWidth = bitMatrix.getWidth();
        int bitMatrixHeight = bitMatrix.getHeight();

        int[] pixels = new int[bitMatrixWidth * bitMatrixHeight];

        for (int y = 0; y < bitMatrixHeight; y++) {
            int offset = y * bitMatrixWidth;

            for (int x = 0; x < bitMatrixWidth; x++) {

                pixels[offset + x] = bitMatrix.get(x, y) ?
                        getResources().getColor(R.color.QRCodeBlackColor) : getResources().getColor(R.color.QRCodeWhiteColor);
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(bitMatrixWidth, bitMatrixHeight, Bitmap.Config.ARGB_4444);

        bitmap.setPixels(pixels, 0, 500, 0, 0, bitMatrixWidth, bitMatrixHeight);
        return bitmap;
    }
}
