package vote_app_test.com.supermercado;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

import java.io.IOException;
import java.net.MalformedURLException;

import vote_app_test.com.supermercado.R;

public class DialogBuy extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.dialog_buy_product)
                .setPositiveButton(R.string.fire, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // FIRE ZE MISSILES!
                        try {
                            listener.onYesButton();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                        listener.onNoButton();
                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }
    public interface MyCustomObjectListener {
        void onYesButton() throws IOException;
        void onNoButton();
    }
    private MyCustomObjectListener listener;
    public void setClickListener(MyCustomObjectListener listener) {
        this.listener = listener;
    }
}