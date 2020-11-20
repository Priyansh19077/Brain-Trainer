package com.example.braintrainer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    CountDownTimer timer;
    Button start_button;
    TextView question;
    TextView remaining_time;
    Button button1;
    Button button2;
    Button button3;
    Button button4;
    int current_ans;
    int location_correct_ans;
    TextView correct;
    TextView wrong;
    CountDownTimer final_timer;
    int starting=0;
    int total_questions=0;
    int score=0;
    TextView score_view;
    ArrayList<Integer> possible_answer=new ArrayList<Integer>();
    public void onOptionSelect(View view)
    {
        if(current_ans==-1)
        {
            final Toast toast=Toast.makeText(getApplicationContext(),"FIRST START THE GAME", Toast.LENGTH_SHORT);
            CountDownTimer toastCountDown;
            toastCountDown = new CountDownTimer(300, 100 /*Tick duration*/) {
                public void onTick(long millisUntilFinished) {
                    toast.show();
                }
                public void onFinish() {
                    toast.cancel();
                }
            };
            toastCountDown.start();
        }
        else
        {
            Log.i("BUTTON",view.getTag().toString());
            String ans_chosen=view.getTag().toString();
            String actual_correct_ans="button"+Integer.toString(location_correct_ans+1);
            if(ans_chosen.equals(actual_correct_ans)) {
                wrong.setVisibility(View.INVISIBLE);
                correct.setVisibility(View.VISIBLE);
                score++;
            }
            else {
                correct.setVisibility(View.INVISIBLE);
                wrong.setVisibility(View.VISIBLE);
            }
            update_score();
            set_question();
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        correct=(TextView)findViewById(R.id.correct);
        wrong=(TextView)findViewById(R.id.wrong);
        current_ans=-1;
        remaining_time=(TextView)findViewById(R.id.time123);
        location_correct_ans=-1;
        score_view=(TextView)findViewById(R.id.score);
        button1=(Button)findViewById(R.id.button1);
        button2=(Button)findViewById(R.id.button2);
        button3=(Button)findViewById(R.id.button3);
        button4=(Button)findViewById(R.id.button4);
        start_button=(Button) findViewById(R.id.start_quit);
        question=(TextView)findViewById(R.id.question);
        start_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(starting==0) {
                    wrong.setText("WRONG :(");
                    starting=1;
                    score_view.setText("0/0");
                    start_button.setText("QUIT");
                    set_timer1();
                    wrong.setVisibility(View.INVISIBLE);
                    wrong.setTextSize(40);
                    correct.setTextSize(40);
                    correct.setVisibility(View.INVISIBLE);
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"GAME EXITED",Toast.LENGTH_SHORT).show();
                    return_to_initial_state();
                }
            }
        });
    }
    public void set_question()
    {
        total_questions++;
        Random rand=new Random();
        int a=rand.nextInt(41);
        int b=rand.nextInt(41);
        int c=rand.nextInt(4);
        location_correct_ans=c;
        current_ans=a+b;
        possible_answer=new ArrayList<Integer>();
        question.setText(Integer.toString(a)+" + "+Integer.toString(b));
        for(int i=0;i<4;i++)
        {
            if(i==c)
                possible_answer.add(a+b);
            else {
                int x=rand.nextInt(81);
                while(x==a+b) {
                    x = rand.nextInt(81);
                }
                possible_answer.add(x);
            }
        }
        button1.setText(Integer.toString(possible_answer.get(0)));
        button2.setText(Integer.toString(possible_answer.get(1)));
        button3.setText(Integer.toString(possible_answer.get(2)));
        button4.setText(Integer.toString(possible_answer.get(3)));
    }
    public void update_score()
    {
        if(total_questions>10)
            score_view.setTextSize(30);
        score_view.setText(Integer.toString(score)+"/"+Integer.toString(total_questions));
    }
    public void set_timer1()
    {
        CountDownTimer t1=new CountDownTimer(3000+100,1000) {
            @Override
            public void onTick(long l) {
                correct.setTextSize(100);
                correct.setText(Integer.toString((int)l/1000));
                correct.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFinish() {
                correct.setVisibility(View.INVISIBLE);
                correct.setTextSize(40);
                correct.setText("CORRECT :)");
                set_timer();
                set_question();
                return;
            }
        };
        t1.start();
    }
    public void set_timer()
    {
        timer=new CountDownTimer(30000+100,1000) {
            @Override
            public void onTick(long l) {
                int x=(int)l;
                remaining_time.setText(Integer.toString((int)(x/1000)));
            }

            @Override
            public void onFinish() {
                current_ans=-1;
                location_correct_ans=-1;
                start_button.setText("PLAY AGAIN");
                starting=0;
                score_view.setText("0/0");
                wrong.setText(Integer.toString(score)+" / "+Integer.toString(total_questions));
                total_questions=0;
                score=0;
                wrong.setVisibility(View.VISIBLE);
                correct.setVisibility(View.INVISIBLE);
                button1.setText("1");
                button2.setText("2");
                button3.setText("3");
                button4.setText("4");
                Toast.makeText(getApplicationContext(),"GAME HAS ENDED",Toast.LENGTH_SHORT).show();
            }
        };
        timer.start();
    }
    public void return_to_initial_state()
    {
        timer.cancel();
        current_ans=-1;
        location_correct_ans=-1;
        start_button.setText("PLAY AGAIN");
        starting=0;
        score_view.setText("0/0");
        wrong.setText("GAME WAS EXITED");
        total_questions=0;
        score=0;
        wrong.setVisibility(View.VISIBLE);
        correct.setVisibility(View.INVISIBLE);
        button1.setText("1");
        button2.setText("2");
        button3.setText("3");
        button4.setText("4");
    }
}