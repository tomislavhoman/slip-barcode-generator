package eu.homan.slip.barcode.generator;

import android.app.DialogFragment;
import android.content.res.Resources;
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

    public static BarcodeDialog create(final String transactionData) {

        final BarcodeDialog dialog = new BarcodeDialog();
        final Bundle arguments = new Bundle();
        arguments.putString(KEY_TRANSACTION_DATA, transactionData);
        dialog.setArguments(arguments);
        return dialog;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View root = inflater.inflate(R.layout.layout_barcode_dialog, container, false);
        final ImageView imgBarcode = (ImageView) root.findViewById(R.id.img_barcode);
        // TODO - set size to match screen width
        // TODO - add share button

        final Bundle arguments = getArguments();
        if (arguments != null) {
            final String transactionData = arguments.getString(KEY_TRANSACTION_DATA, "");
            if (!TextUtils.isEmpty(transactionData)) {
                final Resources resources = getResources();
                // TODO - set size to match screen width
                final int barcodeWidth = resources.getDimensionPixelSize(R.dimen.barcode_size);
                final int barcodeHeight = resources.getDimensionPixelSize(R.dimen.barcode_size);
                final Bitmap barcodeBitmap = new BitmapEncoder(transactionData, barcodeWidth, barcodeHeight).encode();
                imgBarcode.setImageBitmap(barcodeBitmap);
            }
        }
        return root;
    }
}
