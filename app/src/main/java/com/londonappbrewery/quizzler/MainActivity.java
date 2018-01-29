package com.londonappbrewery.quizzler;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.accessibility.AccessibilityManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {



    // TODO: Declare member variables here:
        Button mtrueButton, mfalsebutton;
        TextView mQuestionTextView;
        int mIndex;
        int mQuestion;
        TextView mScoreTextView;
        ProgressBar mProgressBar;
        int mScore;
        Toast mToastMessage;


    // TODO: Uncomment to create question bank
    private TrueFalse[] mQuestionBank = new TrueFalse[] {
            new TrueFalse(R.string.question_1, true),
            new TrueFalse(R.string.question_2, true),
            new TrueFalse(R.string.question_3, true),
            new TrueFalse(R.string.question_4, true),
            new TrueFalse(R.string.question_5, true),
            new TrueFalse(R.string.question_6, false),
            new TrueFalse(R.string.question_7, true),
            new TrueFalse(R.string.question_8, false),
            new TrueFalse(R.string.question_9, true),
            new TrueFalse(R.string.question_10, true),
            new TrueFalse(R.string.question_11, false),
            new TrueFalse(R.string.question_12, false),
            new TrueFalse(R.string.question_13,true)
    };

    // TODO: Declare constants here

    final int PROGRESS_BAR_INCREAMNET = (int) Math.ceil(100.0 /mQuestionBank.length);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState != null){
            mScore = savedInstanceState.getInt("ScoreKey");
            mIndex = savedInstanceState.getInt("Index");
        }else {
            mScore = 0;
            mIndex = 0;
        }

        mtrueButton = (Button) findViewById(R.id.true_button);
        mfalsebutton = (Button) findViewById(R.id.false_button);
        mQuestionTextView =(TextView) findViewById(R.id.question_text_view);
        mScoreTextView = (TextView) findViewById(R.id.score);
        mProgressBar =(ProgressBar) findViewById(R.id.progress_bar);

        mQuestion = mQuestionBank[mIndex].getQuestionID();

        mQuestionTextView.setText(mQuestion);
        mScoreTextView.setText("Score" +mScore+ "/" + mQuestionBank.length);


        mtrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(true);
                upadteQuestion();
            }
        });

        mfalsebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(false);
                upadteQuestion();
            }
        });
    }


    private void upadteQuestion(){
        mIndex = (mIndex+1) % mQuestionBank.length;

        if (mIndex == 0){
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setTitle("Game Over");
            alert.setCancelable(false);
            alert.setMessage("Your Score " +mScore+ "points");
            alert.setPositiveButton("Close Application", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });
            alert.show();
        }

        mQuestion = mQuestionBank[mIndex].getQuestionID();
        mQuestionTextView.setText(mQuestion);
        mProgressBar.incrementProgressBy(PROGRESS_BAR_INCREAMNET);
        mScoreTextView.setText("Score" + mScore + "/" + mQuestionBank.length);

    }

    private void checkAnswer(boolean userSelection) {

        boolean correctAnswer = mQuestionBank[mIndex].isAnswer();

        if (mToastMessage != null) {
            mToastMessage.cancel();
        }

        if(userSelection == correctAnswer) {
            mToastMessage = Toast.makeText(getApplicationContext(), R.string.correct_toast, Toast.LENGTH_SHORT);
            mScore = mScore + 1;

        } else {
            mToastMessage = Toast.makeText(getApplicationContext(), R.string.incorrect_toast, Toast.LENGTH_LONG);
        }

        mToastMessage.show();

    }




   @Override
    public void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);

        outState.putInt("ScoreKey", mScore);
        outState.putInt("IndexKey", mIndex);
   }

}
