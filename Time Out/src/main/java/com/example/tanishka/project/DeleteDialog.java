package com.example.tanishka.project;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;

/**
 * Created by Tanishka on 05-06-2016.
 */
public class DeleteDialog extends DialogFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public DeleteDialog() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *

     * @return A new instance of fragment MyDialog.
     */
    // TODO: Rename and change types and number of parameters
    public static DeleteDialog newInstance() {
        DeleteDialog fragment = new DeleteDialog();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new AlertDialog.Builder(getActivity()).setTitle("Alert!").setMessage("Delete Selected Items?").setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(getTargetFragment()==null)
                    return;
                 sendResult(Activity.RESULT_OK);
            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(getTargetFragment()==null)
                    return;
               sendResult(Activity.RESULT_CANCELED);
            }
        }).create();
    }

public  void  sendResult(int resultCode)
{  if (resultCode==Activity.RESULT_OK)
    getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode,null);
   else if (resultCode==Activity.RESULT_CANCELED)
    getTargetFragment().onActivityResult(getTargetRequestCode(),resultCode,null);
}

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}
