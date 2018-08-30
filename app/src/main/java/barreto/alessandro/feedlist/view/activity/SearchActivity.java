package barreto.alessandro.feedlist.view.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;

import barreto.alessandro.feedlist.R;

public class SearchActivity extends AppCompatActivity {

    String[] items = { "타마린드 ( 쌀국수 )", "덕인관 ( 떡갈비 )", "남자 화장실", "에스컬레이터", "꼴라주 ( 프렌치 카페 )", "히바린 ( 카츠 / 누들 )", "아오키 ( 일식 )", "호경전 ( 모던 차이니즈 푸드 )",
    "여자 화장실", "밀화 ( 샤브샤브 / 전골 )", "평양면옥 ( 냉면 / 불고기 )", "화니 ( 한정식 / 비빔밥 )", "동쪽 엘리베이터", "서쪽 엘리베이터", "살바토레쿠오모 ( 이탈리안 피자 )"};
    Integer[] items_Position = {26, 10, 35, 20, 25, 37, 38, 36, 14, 2, 1, 0, 24, 23, 9};

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        AutoCompleteTextView edit =  findViewById(R.id.searchView);
        edit.setAdapter(new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, items));

        //버튼 이벤트
        findViewById(R.id.button11).setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(getApplication(), AddFeed.class);
                Intent intent = new Intent();
                intent.putExtra("search",26); //키 - 보낼 값(밸류)
                setResult(RESULT_OK, intent);
                finish();
                //startActivity(intent);
            }
        });

        //버튼 이벤트
        findViewById(R.id.button9).setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(getApplication(), AddFeed.class);
                Intent intent = new Intent();
                intent.putExtra("search",10); //키 - 보낼 값(밸류)
                setResult(RESULT_OK, intent);
                finish();
                //startActivity(intent);
            }
        });

        //버튼 이벤트
        findViewById(R.id.button3).setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(getApplication(), AddFeed.class);
                Intent intent = new Intent();
                intent.putExtra("search",35); //키 - 보낼 값(밸류)
                setResult(RESULT_OK, intent);
                finish();
                //startActivity(intent);
            }
        });

        //버튼 이벤트
        findViewById(R.id.button2).setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(getApplication(), AddFeed.class);
                Intent intent = new Intent();
                intent.putExtra("search",20); //키 - 보낼 값(밸류)
                setResult(RESULT_OK, intent);
                finish();
                //startActivity(intent);
            }
        });

        //버튼 이벤트
        findViewById(R.id.button10).setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(getApplication(), AddFeed.class);
                Intent intent = new Intent();
                intent.putExtra("search",25); //키 - 보낼 값(밸류)
                setResult(RESULT_OK, intent);
                finish();
                //startActivity(intent);
            }
        });

        //버튼 이벤트
        findViewById(R.id.button13).setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(getApplication(), AddFeed.class);
                Intent intent = new Intent();
                intent.putExtra("search",37); //키 - 보낼 값(밸류)
                setResult(RESULT_OK, intent);
                finish();
                //startActivity(intent);
            }
        });

        //버튼 이벤트
        findViewById(R.id.button14).setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(getApplication(), AddFeed.class);
                Intent intent = new Intent();
                intent.putExtra("search",38); //키 - 보낼 값(밸류)
                setResult(RESULT_OK, intent);
                finish();
                //startActivity(intent);
            }
        });

        //버튼 이벤트
        findViewById(R.id.button12).setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(getApplication(), AddFeed.class);
                Intent intent = new Intent();
                intent.putExtra("search",36); //키 - 보낼 값(밸류)
                setResult(RESULT_OK, intent);
                finish();
                //startActivity(intent);
            }
        });

        //버튼 이벤트
        findViewById(R.id.button4).setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(getApplication(), AddFeed.class);
                Intent intent = new Intent();
                intent.putExtra("search",14); //키 - 보낼 값(밸류)
                setResult(RESULT_OK, intent);
                finish();
                //startActivity(intent);
            }
        });


        //버튼 이벤트
        findViewById(R.id.button7).setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(getApplication(), AddFeed.class);
                Intent intent = new Intent();
                intent.putExtra("search",2); //키 - 보낼 값(밸류)
                setResult(RESULT_OK, intent);
                finish();
                //startActivity(intent);
            }
        });

        //버튼 이벤트
        findViewById(R.id.button6).setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
               //Intent intent = new Intent(getApplication(), AddFeed.class);
                Intent intent = new Intent();
                intent.putExtra("search",1); //키 - 보낼 값(밸류)
                setResult(RESULT_OK, intent);
                finish();
                //startActivity(intent);
            }
        });

        //버튼 이벤트
        findViewById(R.id.button5).setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Intent intent = new Intent(getApplication(), AddFeed.class);
                Intent intent = new Intent();
                intent.putExtra("search",0); //키 - 보낼 값(밸류)
                setResult(RESULT_OK, intent);
                finish();
                //startActivity(intent);
            }
        });

        //버튼 이벤트
        findViewById(R.id.button1).setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(getApplication(), AddFeed.class);
                Intent intent = new Intent();
                intent.putExtra("search",24); //키 - 보낼 값(밸류)
                setResult(RESULT_OK, intent);
                finish();
                //startActivity(intent);
            }
        });


        //버튼 이벤트
        findViewById(R.id.button24).setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(getApplication(), AddFeed.class);
                Intent intent = new Intent();
                intent.putExtra("search",23); //키 - 보낼 값(밸류)
                setResult(RESULT_OK, intent);
                finish();
                //startActivity(intent);
            }
        });


        //버튼 이벤트
        findViewById(R.id.button8).setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(getApplication(), AddFeed.class);
                Intent intent = new Intent();
                intent.putExtra("search",9); //키 - 보낼 값(밸류)
                setResult(RESULT_OK, intent);
                finish();
                //startActivity(intent);
            }
        });

        edit.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AutoCompleteTextView edit =  findViewById(R.id.searchView);
                for(int i = 0 ; i < items.length ; i++)
                {
                    if(items[i].contains(edit.getText().toString()) == true)
                    {
                        //Intent intent = new Intent(getApplication(), AddFeed.class);
                        Intent intent = new Intent();
                        intent.putExtra("search",items_Position[i]); //키 - 보낼 값(밸류)
                        setResult(RESULT_OK, intent);
                        finish();
                        //startActivity(intent);
                        break;
                    }
                }
            }
        });



    }


}
