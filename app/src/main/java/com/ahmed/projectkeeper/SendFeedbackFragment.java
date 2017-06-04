package com.ahmed.projectkeeper;


import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
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



        sendFeedback = (Button)activity.findViewById(R.id.submitfeedback);
        sendFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                feedbackTxt = edTxt.getText().toString();
                new RetrieveFeedTask().execute(feedbackTxt);
                edTxt.setText("");
                Toast.makeText(getActivity(), "Thanks for your feedback.", Toast.LENGTH_LONG).show();
                InputMethodManager imm = (InputMethodManager)activity.getSystemService(activity.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(edTxt.getWindowToken(), 0);

            }
        });

    }

    class RetrieveFeedTask extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... params) {

           String feedbackTxt = params[0];

            GMailSender m = new GMailSender("projectkeeperfeedback@gmail.com", "ulker300317$$");

            String[] toArr = {"projectkeeperfeedback@gmail.com"};
            m.setTo(toArr);
            m.setFrom("projectkeeperfeedback@gmail.com");
            m.setSubject("Android App User Feedback.");

            m.setBody(feedbackTxt);



            try {
//                    m.addAttachment("/sdcard/filelocation");
                if(m.send()) {

                } else {
                }
            } catch(Exception e) {
                Handler handler =  new Handler(getActivity().getMainLooper());
                handler.post( new Runnable(){
                    public void run(){
                Toast.makeText(getActivity(), "There was a problem sending the email.", Toast.LENGTH_LONG).show();
                }
            });
                Log.e("MailApp", "Could not send email", e);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }

}
