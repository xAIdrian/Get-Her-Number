package androidtitancom.cuteapp;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.telephony.SmsManager;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileWriter;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MyAwesomeDialogFragment extends DialogFragment {

    String name;
    String cellNumber;
    String textMessage;

    @Bind(R.id.input_name)
    EditText text1;

    @Bind(R.id.input_cell)
    EditText text2;

    @Bind(R.id.sendBtn)
    TextView sender;


    public MyAwesomeDialogFragment() {
        // Required empty public constructor
    }

    public static MyAwesomeDialogFragment newInstance() {
        MyAwesomeDialogFragment fragment = new MyAwesomeDialogFragment();
//        Bundle args = new Bundle();
//        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            textMessage = getArguments().getInt("month")
                    + getArguments().getInt("day")+ "/"
                    + "/" + getArguments().getInt("year");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_dialog, container, false);
        ButterKnife.bind(this, v);

        text2.setInputType(InputType.TYPE_CLASS_NUMBER);

        sender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = text1.getText().toString();
                cellNumber = text2.getText().toString();

                try {
                    SmsManager smsManager = SmsManager.getDefault();
                    // Send a text based SMS
                    smsManager.sendTextMessage(cellNumber, null,
                            "Congrats!  You\'re getting a drink with Adrian on " + textMessage,
                            null, null);
                } catch (Exception e) {
                    Toast.makeText(getActivity(), "Failed", Toast.LENGTH_LONG).show();
                }


                if(Environment.getExternalStorageState() != null) {
                    try {
                        File root = new File(Environment.getExternalStorageDirectory(), "girlsNumber");
                        if (!root.exists()) {
                            root.mkdirs();
                        }
                        File gpxfile = new File(root, "numbers");
                        FileWriter writer = new FileWriter(gpxfile, false);
                        writer.append(name + ": " + cellNumber);
                        writer.close();
                        Toast.makeText(getActivity(), "Success!", Toast.LENGTH_LONG).show();

                        startActivity(new Intent(getActivity(), MainActivity.class));
                        getActivity().finish();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                getDialog().dismiss();


            }
        });

        return v;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


}
