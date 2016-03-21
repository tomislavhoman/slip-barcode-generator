package eu.homan.slip.barcode.generator;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
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

        txtAmount.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    final EditText amountEditText = (EditText) v;
                    final String amountText = amountEditText.getText().toString();
                    if (TextUtils.isEmpty(amountText)) {
                        return;
                    }

                    if (!amountText.contains(".")) {
                        amountEditText.setText(amountText + ".00");
                    } else {
                        final int dotPosition = amountText.lastIndexOf(".");
                        final int clipNumber = amountText.length() - dotPosition - 3;
                        if (clipNumber == 0) {
                            return;
                        } else if (clipNumber == -2) {
                            amountEditText.setText(amountText + "00");
                        } else if (clipNumber == -1) {
                            amountEditText.setText(amountText + "0");
                        } else {
                            amountEditText.setText(amountText.substring(0, amountText.length() - clipNumber));
                        }
                    }
                }
            }
        });

        return root;
    }

    public String generateTransactionData() {
        final SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());

        return TransactionDataBuilder.create()
                .recipientName(preferences.getString("pref_recipient_name", ""))
                .recipientAddress1(preferences.getString("pref_recipient_address_1", ""))
                .recipientAddress2(preferences.getString("pref_recipient_address_2", ""))
                .recipientIban(preferences.getString("pref_recipient_iban", ""))
                .payerName(txtPayerName.getText().toString())
                .payerAddress1(txtPayerAddress1.getText().toString())
                .payerAddress2(txtPayerAddress2.getText().toString())
                .amount(txtAmount.getText().toString())
                .model(txtModel.getText().toString())
                .referenceNumber(txtReferenceNumber.getText().toString())
                .descriptionOfPayment(txtDescriptionOfPayment.getText().toString())
                .build();
    }

    private String generateDefaultReferenceNumber() {
        final Calendar calendar = Calendar.getInstance();
        final int day = calendar.get(Calendar.DAY_OF_MONTH);
        final int month = calendar.get(Calendar.MONTH);
        final int year = calendar.get(Calendar.YEAR);

        return String.format("%02d%02d%4d", day, month, year);
    }
}
