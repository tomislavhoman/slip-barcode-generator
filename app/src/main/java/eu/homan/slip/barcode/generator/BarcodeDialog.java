package eu.homan.slip.barcode.generator;

import android.app.DialogFragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public final class BarcodeDialog extends DialogFragment {

    private static final String KEY_TRANSACTION_DATA = "transaction_data";
    private static final String KEY_BARCODE_SIZE = "barcode_size";

    public static BarcodeDialog create(final String transactionData, final int barcodeSize) {

        final BarcodeDialog dialog = new BarcodeDialog();
        final Bundle arguments = new Bundle();
        arguments.putString(KEY_TRANSACTION_DATA, transactionData);
        arguments.putInt(KEY_BARCODE_SIZE, barcodeSize);
        dialog.setArguments(arguments);
        return dialog;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View root = inflater.inflate(R.layout.layout_barcode_dialog, container, false);
        final ImageView imgBarcode = (ImageView) root.findViewById(R.id.img_barcode);
        final ImageButton btnShare = (ImageButton) root.findViewById(R.id.btn_share);

        final Bundle arguments = getArguments();
        if (arguments != null) {
            final String transactionData = arguments.getString(KEY_TRANSACTION_DATA, "");
            final int barcodeSize = arguments.getInt(KEY_BARCODE_SIZE, -1);
            if (!TextUtils.isEmpty(transactionData) && barcodeSize > -1) {
                final Bitmap barcodeBitmap = new BitmapEncoder(transactionData, barcodeSize, barcodeSize).encode();
                imgBarcode.setImageBitmap(barcodeBitmap);

                btnShare.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final String path = saveBitmap(barcodeBitmap);
                        if (!TextUtils.isEmpty(path)) {
                            shareBitmap(Uri.parse(path));
                        }
                    }
                });
            }
        }
        return root;
    }

    private String saveBitmap(final Bitmap bitmap) {
        final File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        if (!path.exists()) {
            if (!path.mkdirs()) {
                return "";
            }
        }
        final File imageFile = new File(path, "barcode_" + System.currentTimeMillis() + ".png");
        FileOutputStream fileOutPutStream = null;
        try {
            fileOutPutStream = new FileOutputStream(imageFile);
            bitmap.compress(Bitmap.CompressFormat.PNG, 80, fileOutPutStream);
            fileOutPutStream.flush();
            return "file://" + imageFile.getAbsolutePath();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Toast.makeText(getActivity(), R.string.external_storage_permission_error, Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fileOutPutStream != null) {
                try {
                    fileOutPutStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return "";
    }

    private void shareBitmap(final Uri path) {
        Intent sendIntent = new Intent(Intent.ACTION_SEND);
        sendIntent.setType("image/jpeg");
        sendIntent.putExtra(Intent.EXTRA_STREAM, path);
        sendIntent.putExtra(Intent.EXTRA_TEXT, "");
        startActivityForResult(Intent.createChooser(sendIntent, getResources().getString(R.string.share)), 42);
    }
}
