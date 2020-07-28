package com.example.lab4.tasks;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.lab4.R;

/**
 * A simple {@link //Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnCallDialogInteractionListener} interface
 * to handle interaction events.
 */
public class CallDialog extends DialogFragment {


    private String mName;
    private String mSurname;
    private OnCallDialogInteractionListener mListener;

    public CallDialog(String full_name,String full_surname) {
        this.mName = full_name;
        this.mSurname = full_surname;
    }
    public static CallDialog newInstance(String full_name, String full_surname){
        return new CallDialog(full_name,full_surname);
    }



    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        // AlertDialog.Builder will be used to create the dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Set the message displayed in the dialog
        builder.setMessage(getString(R.string.call_msg) + " " + mName + " " + mSurname + "?");
        // Set the text and action for the positive button click
        builder.setPositiveButton(getString(R.string.dialog_confirm), new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Notify the OnDeleteDialogInteractionListener interface of positive button click
                mListener.onCallDialogPositiveClick(CallDialog.this,mName,mSurname);
            }
        });
        // Set the text and action for the negative button click
        builder.setNegativeButton(getString(R.string.dialog_cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Notify the OnDeleteDialogInteractionListener interface of negative button click
                mListener.onCallDialogNegativeClick(CallDialog.this,mName,mSurname);
            }
        });
        return builder.create();
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnCallDialogInteractionListener) {
            mListener = (OnCallDialogInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnDeleteDialogInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnCallDialogInteractionListener {
        void onCallDialogPositiveClick(DialogFragment dialog,String imie, String nazwisko);
        void onCallDialogNegativeClick(DialogFragment dialog,String imie,String nazwisko);
    }
}
