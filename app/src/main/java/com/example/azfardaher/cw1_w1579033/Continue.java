package com.example.azfardaher.cw1_w1579033;

import android.app.Activity;
import android.app.Activity;
import java.util.Random;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import android.content.SharedPreferences;

/**
 * Created by azfardaher on 12/03/2017.
 */

public class Continue extends Activity implements OnClickListener{


        private SharedPreferences gamePrefs;
        public static final String GAME_PREFS = "ArithmeticFile";
        private Random random;
        private int level = 0, answer = 0, operator = 0, operand1 = 0, operand2 = 0;
        private final int ADD_OPERATOR = 0, SUBTRACT_OPERATOR = 1, MULTIPLY_OPERATOR = 2, DIVIDE_OPERATOR = 3;
        private String[] operators = {"+", "-", "x", "/"};
        private int[][] levelMin = {
                {1, 11, 21, 31},
                {1, 5, 10, 15},
                {2, 5, 10, 15},
                {2, 3, 5, 10}};
        private int[][] levelMax = {
                {10, 25, 50, 75},
                {10, 20, 30, 50},
                {5, 10, 15, 25},
                {10, 50, 100, 200}};
        private TextView question, answerTxt, scoreTxt;
        private ImageView response;
        private Button btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btn0, hashBtn, cBtn; //hash= enter c= clear


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_playgame);
            gamePrefs = getSharedPreferences(GAME_PREFS, 0);

            question =  (TextView)findViewById(R.id.question);
            answerTxt = (TextView)findViewById(R.id.answer);
            response =  (ImageView)findViewById(R.id.response);
            scoreTxt =  (TextView)findViewById(R.id.score);
            response.setVisibility(View.INVISIBLE); //Ticks and Cross are invisible
            btn1 = (Button)findViewById(R.id.btn1);
            btn2 = (Button)findViewById(R.id.btn2);
            btn3 = (Button)findViewById(R.id.btn3);
            btn4 = (Button)findViewById(R.id.btn4);
            btn5 = (Button)findViewById(R.id.btn5);
            btn6 = (Button)findViewById(R.id.btn6);
            btn7 = (Button)findViewById(R.id.btn7);
            btn8 = (Button)findViewById(R.id.btn8);
            btn9 = (Button)findViewById(R.id.btn9);
            btn0 = (Button)findViewById(R.id.btn0);
            hashBtn = (Button)findViewById(R.id.enter);
            cBtn = (Button)findViewById(R.id.clear);

            btn1.setOnClickListener(this); // buttons 0-9 including # and clear buttons
            btn2.setOnClickListener(this);
            btn3.setOnClickListener(this);
            btn4.setOnClickListener(this);
            btn5.setOnClickListener(this);
            btn6.setOnClickListener(this);
            btn7.setOnClickListener(this);
            btn8.setOnClickListener(this);
            btn9.setOnClickListener(this);
            btn0.setOnClickListener(this);
            hashBtn.setOnClickListener(this);
            cBtn.setOnClickListener(this);

            if(savedInstanceState!=null){
                level=savedInstanceState.getInt("level");
                int exScore = savedInstanceState.getInt("score");
                scoreTxt.setText("Score: "+exScore);
            }
            else{
                Bundle extras = getIntent().getExtras();
                if(extras !=null)
                {
                    int passedLevel = extras.getInt("level", -1);
                    if(passedLevel>=0) level = passedLevel;
                }
            }
            random = new Random();
            chooseQuestion();



        }



        private void chooseQuestion(){
            answerTxt.setText("= ?");
            operator = random.nextInt(operators.length);
            operand1 = getOperand();
            operand2 = getOperand();

            if(operator == SUBTRACT_OPERATOR){
                while(operand2>operand1){
                    operand1 = getOperand();
                    operand2 = getOperand();
                }
            }
            else if(operator==DIVIDE_OPERATOR){
                while((((double)operand1/(double)operand2)%1 > 0) || (operand1==operand2))
                {
                    operand1 = getOperand();
                    operand2 = getOperand();
                }

            }

            switch(operator)
            {
                case ADD_OPERATOR:
                    answer = operand1+operand2;
                    break;
                case SUBTRACT_OPERATOR:
                    answer = operand1-operand2;
                    break;
                case MULTIPLY_OPERATOR:
                    answer = operand1*operand2;
                    break;
                case DIVIDE_OPERATOR:
                    answer = operand1/operand2;
                    break;
                default:
                    break;
            }

            question.setText(operand1+" "+operators[operator]+" "+operand2);

        }

        private int getOperand(){
            return random.nextInt(levelMax[operator][level] - levelMin[operator][level] + 1)
                    + levelMin[operator][level];
        }


        @Override
        public void onClick(View view){

            if(view.getId()==R.id.enter){
                String answerContent = answerTxt.getText().toString();

                if(!answerContent.endsWith("?")) {
                    int enteredAnswer = Integer.parseInt(answerContent.substring(2));
                    int exScore = getScore();    //answer

                    if (enteredAnswer == answer) {
                        chooseQuestion();
                        scoreTxt.setText("Score: " + (exScore + 1));
                        response.setImageResource(R.drawable.correct);
                        response.setVisibility(View.VISIBLE);
                    }
                    else {

                        response.setImageResource(R.drawable.cross);
                        response.setVisibility(View.VISIBLE);

                    }
                }
            }
            else if(view.getId()==R.id.clear){
                answerTxt.setText("= ?");
            }
            else {
                response.setVisibility(View.INVISIBLE);
                int enteredNum = Integer.parseInt(view.getTag().toString());
                if(answerTxt.getText().toString().endsWith("?"))
                    answerTxt.setText("= "+enteredNum);
                else
                    answerTxt.append(""+enteredNum);
            }
        }
        private int getScore(){
            String scoreStr = scoreTxt.getText().toString();
            return Integer.parseInt(scoreStr.substring(scoreStr.lastIndexOf(" ")+1));
        }

        private void setScore(){
            int exScore = getScore();
            String scores = gamePrefs.getString("highScores", "");
            SharedPreferences.Editor scoreEdit = gamePrefs.edit();
            if(exScore>0){


            }
            if(scores.length()>0){
                //we have existing scores
            }
            else{
                scoreEdit.putString("highScores", " - "+exScore);
                scoreEdit.commit();
            }
        }
        @Override
        public void onSaveInstanceState(Bundle savedInstanceState) {
            int exScore = getScore();
            savedInstanceState.putInt("score", exScore);
            savedInstanceState.putInt("level", level);
            super.onSaveInstanceState(savedInstanceState);
        }
    }


