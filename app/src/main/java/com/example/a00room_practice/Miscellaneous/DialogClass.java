package com.example.a00room_practice.Miscellaneous;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.example.a00room_practice.R;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class DialogClass extends DialogFragment {
public EditText editText;
    @NonNull
    @Override
    public android.app.Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        LayoutInflater inflater = requireActivity().getLayoutInflater();
        Intent intent = new Intent();
        View view = inflater.inflate(R.layout.dialog_layout,null);
        editText = view.findViewById(R.id.dl_edit_text);
        listener.Edit_text(editText);
        MaterialAlertDialogBuilder materialAlertDialogBuilder = new MaterialAlertDialogBuilder
                (getActivity(),R.style.ThemeOverlay_App_MaterialAlertDialog);

        materialAlertDialogBuilder.setView(view)
                .setTitle("Category Title")
                        .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                listener.onDialogPositiveClick(DialogClass.this);
                                editText.getText();




                            }
                        }).setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        listener.onDialogNegativeClick(DialogClass.this);

                    }
                });

        AlertDialog dialog = materialAlertDialogBuilder.create();
        dialog.show();
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);

// OR you can use here setOnShowListener to disable button at first time.

// Now set the textchange listener for edittext
        editText.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {;

            }

            @Override
            public void afterTextChanged(Editable s) {

                // Check if edittext is empty
                if (TextUtils.isEmpty(s) || s.toString().trim().isEmpty()) {
                    // Disable ok button
                    dialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);

                } else {
                    // Something into edit text. Enable the button.
                    dialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(true);

                }

            }
        });

        //return materialAlertDialogBuilder.create();
        return dialog;


    }

    public interface CategoryDialogListener {
         void onDialogPositiveClick(DialogClass dialog);
         void onDialogNegativeClick(DialogClass dialog);
         void Edit_text(EditText input);
    }

    // Use this instance of the interface to deliver action events
    CategoryDialogListener listener;

    // Override the Fragment.onAttach() method to instantiate the NoticeDialogListener

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            listener = (CategoryDialogListener) context;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException("Something"
                    + " must implement CategoryDialogListener");
        }
    }


}
