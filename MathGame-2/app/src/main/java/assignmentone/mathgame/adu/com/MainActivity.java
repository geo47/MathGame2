package assignmentone.mathgame.adu.com;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private static final Character[] OPERATORS = {'+', '-', 'x', '÷'};
    private static final String[]    POSITIVE_COMMENTS = {
            "Very good!",
            "Excellent!",
            "Nice work!",
            "Keep up the good work!"
    };
    private static final String[]    NEGATIVE_COMMENTS = {
            "No, Please try again.",
            "Wrong, Try once more.",
            "Don't give up!",
            "No, Keep trying."
    };

    private Character   operator;
    private int         operandLeft;
    private int         operandRight;
    private int         answer;

    private EditText        etAnswer;
    private TextView        tvStartGame;
    private TextView        tvQuestion1, tvQuestion2, tvQuestion3;
    private TextView        tvSubmit, tvComments;
    private RelativeLayout  relativeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etAnswer    = (EditText) findViewById(R.id.editText_Answer);
        tvStartGame = (TextView) findViewById(R.id.textView_StartGame);
        tvQuestion1  = (TextView) findViewById(R.id.textView_Question_1);
        tvQuestion2  = (TextView) findViewById(R.id.textView_Question_2);
        tvQuestion3  = (TextView) findViewById(R.id.textView_Question_3);
        tvSubmit  = (TextView) findViewById(R.id.textView_Submit);
        tvComments  = (TextView) findViewById(R.id.textView_Comment);
        relativeLayout  = (RelativeLayout) findViewById(R.id.textView_Comment_Layout);

        tvSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String text = etAnswer.getText().toString();

                if(!"".equals(text)){
                    int ans = Integer.parseInt(text);
                    if(answer == ans){

                        tvComments.setText(randComment(true));
                        relativeLayout.setVisibility(View.VISIBLE);
                        new Handler().postDelayed(new Runnable() {
                            public void run() {
                                refreshQuestion();
                                relativeLayout.setVisibility(View.GONE);
                            }
                        }, 2000);
                    }else{
                        tvComments.setText(randComment(false));
                        relativeLayout.setVisibility(View.VISIBLE);
                        new Handler().postDelayed(new Runnable() {
                            public void run() {
                                relativeLayout.setVisibility(View.GONE);
                            }
                        }, 2000);
                    }
                }
            }
        });

        tvStartGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                refreshQuestion();
            }
        });
    }

    private void refreshQuestion(){
        etAnswer.setEnabled(true);
        tvSubmit.setEnabled(true);
        getQuestion();
        etAnswer.setText("");
    }

    private String getQuestion(){

        String question = null;

        operator  = OPERATORS[(int)(Math.random() * OPERATORS.length)];

        switch (operator){
            case '+':
                operandLeft = randInt(1, 9);
                operandRight = randInt(1, 9);
                answer = operandLeft + operandRight;

                tvQuestion1.setText(String.valueOf(operandLeft));
                tvQuestion2.setText(String.valueOf(operator));
                tvQuestion3.setText(String.valueOf(operandRight));
                break;
            case '-':
                operandLeft = randInt(1, 9);
                operandRight = randInt(1, 9);
                while(operandLeft < operandRight){
                    operandLeft = randInt(1, 9);
                }

                answer = operandLeft - operandRight;

                tvQuestion1.setText(String.valueOf(operandLeft));
                tvQuestion2.setText(String.valueOf(operator));
                tvQuestion3.setText(String.valueOf(operandRight));
                break;
            case 'x':
                operandLeft = randInt(1, 9);
                operandRight = randInt(1, 9);
                answer = operandLeft * operandRight;

                tvQuestion1.setText(String.valueOf(operandLeft));
                tvQuestion2.setText(String.valueOf(operator));
                tvQuestion3.setText(String.valueOf(operandRight));
                break;
            case '÷':
                operandLeft = randInt(1, 9);
                Log.d("TEST", "operandLeft: "+operandLeft+" operandRight: "+operandRight);
                operandRight = randDivisibleInt(operandLeft);
                answer = operandLeft / operandRight;

                tvQuestion1.setText(String.valueOf(operandLeft));
                tvQuestion2.setText(String.valueOf(operator));
                tvQuestion3.setText(String.valueOf(operandRight));
                break;
        }

        return question;
    }

    /**
     * Returns a psuedo-random number between min and max, inclusive.
     * The difference between min and max can be at most
     * <code>Integer.MAX_VALUE - 1</code>.
     *
     * @param min Minimim value
     * @param max Maximim value.  Must be greater than min.
     * @return Integer between min and max, inclusive.
     * @see java.util.Random#nextInt(int)
     */
    public static int randInt(int min, int max) {

        // Usually this can be a field rather than a method variable
        Random rand = new Random();

        // nextInt is normally exclusive of the top value,
        // so add 1 to make it inclusive
        int randomNum = rand.nextInt((max - min) + 1) + min;

        return randomNum;
    }

    private static int randDivisibleInt(int number){
        ArrayList<Integer> divisibleList = new ArrayList<>();
        Integer[] tempArray;

        for(int i=1; i<= number; i++){
            if(number % i == 0)
                divisibleList.add(i);
        }

        tempArray = divisibleList.toArray(new Integer[divisibleList.size()]);
        for(int temp:tempArray)
            Log.d("TEST", "TEST: "+temp);
        int random = (int)(Math.random()*divisibleList.toArray().length);
        return tempArray[random];
    }

    private static String randComment(boolean isTrue){
        String comment = null;
        int random = 0;

        if(isTrue){
            random = (int)(Math.random()*POSITIVE_COMMENTS.length);
            comment = POSITIVE_COMMENTS[random];
        }else{
            random = (int)(Math.random()*NEGATIVE_COMMENTS.length);
            comment = NEGATIVE_COMMENTS[random];
        }
        return comment;
    }
}
