package com.fingertech.fingertechcapture;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;



public class SampleDialogFragment  extends DialogFragment {

    ProgressDialog progressDialog;
    SampleDialogListener sampleDialogListener;
    View view;
    Activity activity;

    public interface SampleDialogListener{

        public void onClickStopBtn();

    }


    public void setView(SampleDialogListener sampleDialogListener){
        this.sampleDialogListener = sampleDialogListener;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try{
            sampleDialogListener = (SampleDialogListener)context;
        }catch(ClassCastException e){
            e.printStackTrace();
        }
    }

    @Override
    public void onAttach(Activity activity) {
        // TODO Auto-generated method stub
        super.onAttach(activity);
        try{
            sampleDialogListener = (SampleDialogListener)activity;
        }catch(ClassCastException e){
            e.printStackTrace();
        }

    }

    /* (non-Javadoc)
     * @see android.app.DialogFragment#onCreateDialog(android.os.Bundle)
     */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        if("DIALOG_TYPE_PROGRESS".equals(this.getTag())){

            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMessage("Loading...");
            progressDialog.setIndeterminate(true);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setCancelable(false);
            return progressDialog;
        }else{
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder
                    .setTitle("AUTO ON")
                    .setPositiveButton("STOP", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    sampleDialogListener.onClickStopBtn();
                                }
                            }

                    );
            return builder.create();
        }

    }

}