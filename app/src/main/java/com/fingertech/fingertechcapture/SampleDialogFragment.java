package com.fingertech.fingertechcapture;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.os.Bundle;

public class SampleDialogFragment  extends DialogFragment {

    ProgressDialog progressDialog;

    public interface SampleDialogListener{

        public void onClickStopBtn(DialogFragment dialogFragment);

    }

    SampleDialogListener sampleDialogListener;



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
                    .setPositiveButton("STOP", (dialog, which) -> sampleDialogListener.onClickStopBtn(SampleDialogFragment.this));

            return builder.create();
        }

    }

}