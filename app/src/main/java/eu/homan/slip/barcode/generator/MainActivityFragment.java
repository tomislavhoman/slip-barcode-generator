package eu.homan.slip.barcode.generator;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import java.util.Calendar;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    private EditText txtPayerName;
    private EditText txtPayerAddress1;
    private EditText txtPayerAddress2;
    private EditText txtAmount;
    private EditText txtModel;
    private EditText txtReferenceNumber;
    private EditText txtDescriptionOfPayment;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.fragment_main, container, false);

        txtPayerName = (EditText) root.findViewById(R.id.txt_payer_name);
        txtPayerAddress1 = (EditText) root.findViewById(R.id.txt_payer_address_1);
        txtPayerAddress2 = (EditText) root.findViewById(R.id.txt_payer_address_2);
        txtAmount = (EditText) root.findViewById(R.id.txt_amount);
        txtModel = (EditText) root.findViewById(R.id.txt_model);
        txtReferenceNumber = (EditText) root.findViewById(R.id.txt_reference_number);
        txtDescriptionOfPayment = (EditText) root.findViewById(R.id.txt_description_of_payment);

        txtModel.setText(R.string.default_model);
        txtAmount.setText(R.string.default_amount);
        txtReferenceNumber.setText(generateDefaultReferenceNumber());

        return root;
    }

    public void makeBarCode() {

    }

    private String generateDefaultReferenceNumber() {
        final Calendar calendar = Calendar.getInstance();
        final int day = calendar.get(Calendar.DAY_OF_MONTH);
        final int month = calendar.get(Calendar.MONTH);
        final int year = calendar.get(Calendar.YEAR);

        return String.format("%02d%02d%4d", day, month, year);
    }
}
