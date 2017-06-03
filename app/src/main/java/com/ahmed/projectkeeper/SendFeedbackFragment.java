package com.ahmed.projectkeeper;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class SendFeedbackFragment extends Fragment {


    private EditText edTxt;
    private Button sendFeedback;
    String feedbackTxt;

    public SendFeedbackFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_send_feedback, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        final Activity activity = getActivity();

        // Set title bar
        ((MainActivity) getActivity())
                .setActionBarTitle("Send Feedback");

        edTxt = (EditText)activity.findViewById(R.id.feedbackText);
        feedbackTxt = edTxt.getText().toString();
;

        sendFeedback = (Button)activity.findViewById(R.id.submitfeedback);
        sendFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edTxt.setText("");
                Toast.makeText(activity, "Thanks for your Feedback.", Toast.LENGTH_SHORT).show();
                InputMethodManager imm = (InputMethodManager)activity.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(edTxt.getWindowToken(), 0);
            }
        });


    }
}
