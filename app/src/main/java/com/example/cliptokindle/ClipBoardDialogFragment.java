package com.example.cliptokindle;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

public class ClipBoardDialogFragment extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Add to website?");

        if (getArguments() != null) {
            builder.setMessage(getArguments().getString("content"))
                    .setPositiveButton("OK", (dialog, id) -> {
                        HttpApp.add(new Text(getArguments().getString("content")));
                    })
                    .setNegativeButton("Cancel", (dialog, id) -> {});
        } else {
            builder.setMessage("NOTHING");
        }



        return builder.create();
    }
}
