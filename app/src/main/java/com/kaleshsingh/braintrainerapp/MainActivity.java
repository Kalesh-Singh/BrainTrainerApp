package com.kaleshsingh.braintrainerapp;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

import static java.util.Arrays.asList;


public class MainActivity extends AppCompatActivity {
    TextView goTextView;
    TextView timerTextView;
    TextView questionTextView;
    TextView scoreTextView;
    TextView answer1TextView;
    TextView answer2TextView;
    TextView answer3TextView;
    TextView answer4TextView;
    TextView resultTextView;
    RelativeLayout playingScreenRelativeLayout;
    CountDownTimer countDownTimer;
    Button playAgainButton;

    int firstNumber;
    int secondNumber;
    int sum;

    Random rand;

    int correctAnswers;
    int questionsAttempted;

    public String secondsToTime(int timeInSeconds){
        int minutes = timeInSeconds / 60;
        int seconds = timeInSeconds % 60;

        String minuteString =
                (minutes < 10) ? "0" + Integer.toString(minutes) : Integer.toString(minutes);

        String secondString =
                (seconds < 10) ? "0" + Integer.toString(seconds) : Integer.toString(seconds);

        return minuteString + ":" + secondString;
    }

    public void generateQuestion(){
        rand = new Random();
        firstNumber = rand.nextInt(20) + 1;
        rand = new Random();
        secondNumber = rand.nextInt(20) + 1;
        sum = firstNumber + secondNumber;
        questionTextView.setText
                (Integer.toString(firstNumber) + " + " + Integer.toString(secondNumber));
    }

    public void generateOptions(){
        rand = new Random();
        String num1 = Integer.toString(rand.nextInt(40) + 1);
        rand = new Random();
        String num2 = Integer.toString(rand.nextInt(40) + 1);
        rand = new Random();
        String num3 = Integer.toString(rand.nextInt(40) + 1);
        rand = new Random();
        String num4 = Integer.toString(rand.nextInt(40) + 1);
        rand = new Random();
        int index = rand.nextInt(4);

        ArrayList<String> options = new ArrayList<>(
          asList(num1, num2, num3, num4)
        );

        options.set(index, Integer.toString(sum));

        answer1TextView.setText(options.get(0));
        answer2TextView.setText(options.get(1));
        answer3TextView.setText(options.get(2));
        answer4TextView.setText(options.get(3));
    }

    public void updateScore(View view){
        TextView selectedOption = (TextView) view;

        int userAnswer = Integer.parseInt(selectedOption.getText().toString());

        if(userAnswer == sum){
            correctAnswers += 1;
        }

        questionsAttempted += 1;

        scoreTextView.setText
                (Integer.toString(correctAnswers) + "/" +Integer.toString(questionsAttempted));
        Log.i("Option", "Answer selected!");

        generateQuestion();
        generateOptions();
    }

    public void showScore(){
        resultTextView.setText("Your Score: " +
                Integer.toString(correctAnswers) + "/" + Integer.toString(questionsAttempted));

        playAgainButton.setVisibility(View.VISIBLE);

    }

    public void go(View view){
        playingScreenRelativeLayout.setVisibility(View.VISIBLE);
        goTextView.setVisibility(View.INVISIBLE);
        playAgainButton.setVisibility(View.INVISIBLE);

        correctAnswers = 0;
        questionsAttempted = 0;
        resultTextView.setText("");
        scoreTextView.setText("0/0");

        generateQuestion();
        generateOptions();


        countDownTimer = new CountDownTimer(30050, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timerTextView.setText(secondsToTime((int) (millisUntilFinished / 1000)));
            }

            @Override
            public void onFinish() {
                timerTextView.setText("00:00");
                showScore();
            }
        }.start();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        goTextView = (TextView) findViewById(R.id.goTextView);
        playingScreenRelativeLayout =
                (RelativeLayout) findViewById(R.id.playingScreenRelativeLayout);
        timerTextView = (TextView) findViewById(R.id.timerTextView);
        questionTextView = (TextView) findViewById(R.id.questionTextView);

        answer1TextView = (TextView) findViewById(R.id.answer1TextView);
        answer2TextView = (TextView) findViewById(R.id.answer2TextView);
        answer3TextView = (TextView) findViewById(R.id.answer3TextView);
        answer4TextView = (TextView) findViewById(R.id.answer4TextView);

        resultTextView = (TextView) findViewById(R.id.resultTextView);

        scoreTextView = (TextView) findViewById(R.id.scoreTextView);

        playAgainButton = (Button) findViewById(R.id.playAgainButton);

        goTextView.setVisibility(View.VISIBLE);
        playingScreenRelativeLayout.setVisibility(View.INVISIBLE);
        timerTextView.setText("00:30");
    }
}
