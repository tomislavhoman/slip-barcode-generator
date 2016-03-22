package eu.homan.slip.barcode.generator;

import android.app.DialogFragment;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

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

        // TODO - add share button

        final Bundle arguments = getArguments();
        if (arguments != null) {
            final String transactionData = arguments.getString(KEY_TRANSACTION_DATA, "");
            final int barcodeSize = arguments.getInt(KEY_BARCODE_SIZE, -1);
            if (!TextUtils.isEmpty(transactionData) && barcodeSize > -1) {
                final Bitmap barcodeBitmap = new BitmapEncoder(transactionData, barcodeSize, barcodeSize).encode();
                imgBarcode.setImageBitmap(barcodeBitmap);
            }
        }
        return root;
    }
}
