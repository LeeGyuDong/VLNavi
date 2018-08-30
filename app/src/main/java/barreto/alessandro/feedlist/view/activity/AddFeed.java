package barreto.alessandro.feedlist.view.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import barreto.alessandro.feedlist.MainActivity;
import barreto.alessandro.feedlist.PathFinder3;
import barreto.alessandro.feedlist.R;
import barreto.alessandro.feedlist.SerialConnection;
import pl.polidea.view.ZoomView;
import uk.co.senab.photoview.PhotoViewAttacher;

public class AddFeed extends AppCompatActivity {

    //멤버 변수
    public int testnum = 0;
    private ImageView map;
    private FloatingActionButton fab;
    private FloatingActionButton fab2;
    private ListView lvDrawerList;
    private String[] drawerItemList = {"주변", "후기"};
    SerialConnection serial_Connection;
    PathFinder3 pathFinder3;
    Handler thread_Handler;
    //커스텀 뷰용 레이아웃(빨간줄 그은 그림 + 배경)
    LinearLayout.LayoutParams layoutParams;
    //커스텀 뷰를 담는 드래그, 확대축소 라이브러리
    ZoomView zoomView;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 0)
        {
            if(resultCode == RESULT_OK)
            {
                pathFinder3.end_light = data.getIntExtra("search", -1);
            }
        }
    }

    //OnCreate
    @SuppressLint("ClickableViewAccessibility")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_feed);

        //멤버변수 등록
        map = findViewById(R.id.map);
        fab = findViewById(R.id.fab);
        fab2 = findViewById(R.id.fab2);

        //인스턴스 생성
        serial_Connection = new SerialConnection();
        pathFinder3 = new PathFinder3();
        layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);


        //Drawer 어뎁터와 이벤트리스너 등록
        lvDrawerList = findViewById(R.id.lv_drawer_list);
        lvDrawerList.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, drawerItemList));
        lvDrawerList.setOnItemClickListener(new DrawerItemClickListener());


        //시리얼 커넥션 생성
        serial_Connection.Create_Connection(getApplicationContext());
        serial_Connection.Set_Baudrate(115200);

        if (serial_Connection.Open_Connection() == true) {
            serial_Connection.Set_Baudrate(115200);
        } else
            Toast.makeText(getApplicationContext(), "Can not create serial connection", Toast.LENGTH_SHORT).show();


        //점 추가
        pathFinder3.Add_Light(340, 284); //0 화니
        pathFinder3.Add_Light(412, 284); //1 평양면옥
        pathFinder3.Add_Light(530, 284); //2 밀화
        pathFinder3.Add_Light(303, 334); //3
        pathFinder3.Add_Light(340, 334);     //4
        pathFinder3.Add_Light(412, 334);    //5
        pathFinder3.Add_Light(449, 334); //6
        pathFinder3.Add_Light(530, 334);    //7
        pathFinder3.Add_Light(660, 334); //8
        pathFinder3.Add_Light(374, 402); //9 살바토레쿠오모
        pathFinder3.Add_Light(558, 389); //10 덕인관
        pathFinder3.Add_Light(449, 417); //11
        pathFinder3.Add_Light(558, 417);    //12
        pathFinder3.Add_Light(660, 417); //13
        pathFinder3.Add_Light(682, 427);    //14
        pathFinder3.Add_Light(205, 448); //15
        pathFinder3.Add_Light(303, 448); //16
        pathFinder3.Add_Light(374, 448);   //17
        pathFinder3.Add_Light(410, 448); //18
        pathFinder3.Add_Light(449, 448); //19
        pathFinder3.Add_Light(558, 448);   //20
        pathFinder3.Add_Light(601, 470); //21
        pathFinder3.Add_Light(660, 470); //22
        pathFinder3.Add_Light(682, 470);    //23
        pathFinder3.Add_Light(205, 483);    //24
        pathFinder3.Add_Light(378, 503); //25 꼴라주
        pathFinder3.Add_Light(525, 503); //26 타마린드
        pathFinder3.Add_Light(234, 535); //27
        pathFinder3.Add_Light(303, 535); //28
        pathFinder3.Add_Light(326, 535);    //29
        pathFinder3.Add_Light(378, 535);    //30
        pathFinder3.Add_Light(451, 535);   //31
        pathFinder3.Add_Light(525, 535);   //32
        pathFinder3.Add_Light(573, 535);   //33
        pathFinder3.Add_Light(601, 535); //34
        pathFinder3.Add_Light(234, 637);   //35
        pathFinder3.Add_Light(326, 597); //36 호경도
        pathFinder3.Add_Light(451, 597); //37 히바린
        pathFinder3.Add_Light(573, 597); //38 아오키


        //점 사이의 연결 추가
        pathFinder3.Insert_Node(0, 4, 1);
        pathFinder3.Insert_Node(4, 0, 1);

        pathFinder3.Insert_Node(1, 5, 1);
        pathFinder3.Insert_Node(5, 1, 1);

        pathFinder3.Insert_Node(2, 7, 1);
        pathFinder3.Insert_Node(7, 2, 1);

        pathFinder3.Insert_Node(3, 4, 1);
        pathFinder3.Insert_Node(4, 3, 1);

        pathFinder3.Insert_Node(4, 5, 1);
        pathFinder3.Insert_Node(5, 4, 1);

        pathFinder3.Insert_Node(5, 6, 1);
        pathFinder3.Insert_Node(6, 5, 1);

        pathFinder3.Insert_Node(6, 7, 1);
        pathFinder3.Insert_Node(7, 5, 1);

        pathFinder3.Insert_Node(7, 8, 1);
        pathFinder3.Insert_Node(8, 7, 1);

        pathFinder3.Insert_Node(3, 16, 1);
        pathFinder3.Insert_Node(16, 3, 1);

        pathFinder3.Insert_Node(15, 16, 1);
        pathFinder3.Insert_Node(16, 15, 1);

        pathFinder3.Insert_Node(16, 17, 1);
        pathFinder3.Insert_Node(17, 16, 1);

        pathFinder3.Insert_Node(9, 17, 1);
        pathFinder3.Insert_Node(17, 9, 1);

        pathFinder3.Insert_Node(17, 18, 1);
        pathFinder3.Insert_Node(18, 17, 1);

        pathFinder3.Insert_Node(18, 19, 1);
        pathFinder3.Insert_Node(19, 18, 1);

        pathFinder3.Insert_Node(11, 19, 1);
        pathFinder3.Insert_Node(19, 11, 1);

        pathFinder3.Insert_Node(6, 11, 1);
        pathFinder3.Insert_Node(11, 6, 1);

        pathFinder3.Insert_Node(19, 20, 1);
        pathFinder3.Insert_Node(20, 19, 1);

        pathFinder3.Insert_Node(11, 12, 1);
        pathFinder3.Insert_Node(12, 11, 1);

        pathFinder3.Insert_Node(12, 20, 1);
        pathFinder3.Insert_Node(20, 12, 1);

        pathFinder3.Insert_Node(12, 10, 1);
        pathFinder3.Insert_Node(10, 12, 1);

        pathFinder3.Insert_Node(12, 13, 1);
        pathFinder3.Insert_Node(13, 12, 1);

        pathFinder3.Insert_Node(8, 13, 1);
        pathFinder3.Insert_Node(13, 8, 1);

        pathFinder3.Insert_Node(13, 22, 1);
        pathFinder3.Insert_Node(22, 13, 1);

        pathFinder3.Insert_Node(21, 22, 1);
        pathFinder3.Insert_Node(22, 21, 1);

        pathFinder3.Insert_Node(22, 23, 1);
        pathFinder3.Insert_Node(23, 22, 1);

        pathFinder3.Insert_Node(14, 23, 1);
        pathFinder3.Insert_Node(23, 14, 1);

        pathFinder3.Insert_Node(15, 24, 1);
        pathFinder3.Insert_Node(24, 15, 1);

        pathFinder3.Insert_Node(16, 28, 1);
        pathFinder3.Insert_Node(28, 16, 1);

        pathFinder3.Insert_Node(27, 28, 1);
        pathFinder3.Insert_Node(28, 27, 1);

        pathFinder3.Insert_Node(27, 35, 1);
        pathFinder3.Insert_Node(35, 27, 1);

        pathFinder3.Insert_Node(28, 29, 1);
        pathFinder3.Insert_Node(29, 28, 1);

        pathFinder3.Insert_Node(29, 36, 1);
        pathFinder3.Insert_Node(36, 29, 1);

        pathFinder3.Insert_Node(29, 30, 1);
        pathFinder3.Insert_Node(30, 29, 1);

        pathFinder3.Insert_Node(25, 30, 1);
        pathFinder3.Insert_Node(30, 25, 1);

        pathFinder3.Insert_Node(30, 31, 1);
        pathFinder3.Insert_Node(31, 30, 1);

        pathFinder3.Insert_Node(19, 31, 1);
        pathFinder3.Insert_Node(31, 19, 1);

        pathFinder3.Insert_Node(31, 37, 1);
        pathFinder3.Insert_Node(37, 31, 1);

        pathFinder3.Insert_Node(32, 31, 1);
        pathFinder3.Insert_Node(31, 32, 1);

        pathFinder3.Insert_Node(26, 32, 1);
        pathFinder3.Insert_Node(32, 26, 1);

        pathFinder3.Insert_Node(32, 33, 1);
        pathFinder3.Insert_Node(33, 32, 1);

        pathFinder3.Insert_Node(33, 38, 1);
        pathFinder3.Insert_Node(38, 33, 1);

        pathFinder3.Insert_Node(33, 34, 1);
        pathFinder3.Insert_Node(34, 33, 1);

        pathFinder3.Insert_Node(21, 34, 1);
        pathFinder3.Insert_Node(34, 21, 1);


        //시작점, 끝점 설정
       // Intent intent = getIntent(); //이 액티비티를 부른 인텐트를 받는다.
        //pathFinder3.end_light = intent.getIntExtra("search", -1);
        pathFinder3.end_light = -1;
        pathFinder3.start_light = 24;

        //스레드의 현재 빛 업데이트 메시지를 받는 핸들러 설정
        thread_Handler = new Handler() {
            public void handleMessage(Message msg) {
                if( testnum <= 30 || testnum > 70)
                {
                    if (serial_Connection.Is_Opened() == true) {//시리얼 열렸을때

                        if (serial_Connection.Get_Current_Light_Num() >= 0) {//조명이 인식됬다면 0이상 값 받음
                            //현재 조명받는 빛의 숫자를 가져옴
                            int new_light_num = serial_Connection.Get_Current_Light_Num();

                            //if 이전과 같은 위치라면 갱신안함
                            if (pathFinder3.start_light != new_light_num) {
                                //길을 찾아서 커스텀뷰를 만들어 빨간줄 긋고 배경설정후, 현재 레이아웃 컨테이너에 추가
                                pathFinder3.start_light = new_light_num;
                                Show_Custom_View_With_Path();
                            }
                        } else {
                            int new_light_num = 24;
                            Show_Custom_View_With_Path();
                            //조명이 안들어오고 있다면 일단 아무것도 안함

                        }
                    } else {//시리얼 안열렸을때
                        //아무 빛이나 일단 초기화하기
                        int new_light_num = 24;
                        //if 이전과 같은 위치라면 갱신안함
                        if (pathFinder3.start_light != new_light_num) {
                            //길을 찾아서 커스텀뷰를 만들어 빨간줄 긋고 배경설정후, 현재 레이아웃 컨테이너에 추가
                            pathFinder3.start_light = new_light_num;
                            Show_Custom_View_With_Path();
                        }
                    }
                }
                else
                {
                    if(testnum > 45 && testnum <= 55)
                    {
                        pathFinder3.start_light = 9;
                    }
                    else if( (testnum <= 45 &&testnum > 40) || (testnum > 55 && testnum <= 60))
                    {
                        pathFinder3.start_light = 17;
                    }
                    else if( (testnum <= 40 &&testnum > 35) || (testnum > 60 && testnum <= 65))
                    {
                        pathFinder3.start_light = 16;
                    }
                    else if( (testnum <= 35 &&testnum > 30) || (testnum > 65 && testnum <= 70))
                    {
                        pathFinder3.start_light = 15;
                    }
                    else
                    {
                        pathFinder3.start_light = 24;
                    }
                    Show_Custom_View_With_Path();
                }


                if (0 > pathFinder3.end_light || pathFinder3.end_light == pathFinder3.start_light) {
                    findViewById(R.id.fab2).setVisibility(View.INVISIBLE);
                } else {
                    findViewById(R.id.fab2).setVisibility(View.VISIBLE);
                }

                if( testnum <= 70)
                {
                    testnum++;
                }

            }
        };


        //확대 드래그를 위한 줌뷰생성
        zoomView = new ZoomView(this);
        zoomView.setLayoutParams(layoutParams);
        //zoomView.setMiniMapEnabled(true); // 좌측 상단 검은색 미니맵 설정
        zoomView.setMaxZoom(4f); // 줌 Max 배율 설정  1f 로 설정하면 줌 안됩니다.
        //zoomView.setMiniMapCaption("Mini Map Test"); //미니 맵 내용
        //zoomView.setMiniMapCaptionSize(20); // 미니 맵 내용 글씨 크기 설정
        // zoomView.zoomTo(2.0f, 10, 10);


        //디버깅용 : 스레드를 껐을때, 빛 감지를 현재 안하므로 2번 위치를 시작점
        //pathFinder3.start_light = 2;

        //디버깅용, 스레드를  껏을때, 길을 찾아서 커스텀뷰를 만들어 빨간줄 긋고 배경설정후, 현재 레이아웃 컨테이너에 추가
        //Show_Custom_View_With_Path();


        //빛을 감지하여 시작점을 설정하는 스레드 실행 ------------------------ 디버깅용으로  안하게할려면 .start(); 제거
        new Thread(new Runnable() {
            @Override
            public void run() { // TODO Auto-generated method stub
                while (true) {

                    try { // 1초에 한번씩 핸들러의 함수 실행
                        thread_Handler.sendMessage(thread_Handler.obtainMessage());
                        Thread.sleep(1000); // 1초간 Thread를 잠재운다
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

        //버튼 이벤트
        fab.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplication(), SearchActivity.class);
                intent.putExtra("USERNAME_KEY", "jizard"); //키 - 보낼 값(밸류)
                intent.putExtra("BIRTHDAY_KEY", 119);
                startActivityForResult(intent, 0);
            }
        });

        fab2.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                pathFinder3.end_light = -1;
                findViewById(R.id.fab2).setVisibility(View.INVISIBLE);
                Show_Custom_View_With_Path();
            }
        });


    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {

        View layoutMainView = this.findViewById(R.id.fl_container);

        Log.w("Layout Width - ", String.valueOf(layoutMainView.getWidth()));
        Log.w("Layout Height - ", String.valueOf(layoutMainView.getHeight()));
        pathFinder3.layout_Height = layoutMainView.getHeight();
        pathFinder3.layout_Width = layoutMainView.getWidth();

        Show_Custom_View_With_Path();
    }


    //길을 찾아서 커스텀뷰를 만들어 빨간줄 긋고 배경설정후, 현재 레이아웃 컨테이너에 추가
    public void Show_Custom_View_With_Path() {

        //레이아웃에서 그림을 담는 컨테이너를 가져옴
        LinearLayout map_Con = findViewById(R.id.mapCon);


        //길찾고, 빨간 줄이 그인 커스텀뷰를 생성하여 리턴받음
        PathFinder3.CustomView customView = pathFinder3.Get_View_And_Find_Path(getApplicationContext());
        //커스텀뷰 배경설정
        customView.setBackgroundResource(R.drawable.map5);
        //레이아웃 설정
        zoomView.setLayoutParams(layoutParams);

        zoomView.removeAllViews();
        //줌뷰에 커스텀뷰를 추가하구
        zoomView.addView(customView);


        map_Con.removeAllViews();
        //줌뷰를 레이아웃에 추가
        map_Con.addView(zoomView);
    }

    //Drawer 이벤트 리스너
    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
            switch (position) {
                case 0:

                    break;
                case 1:
                    startActivity(new Intent(AddFeed.this, MainActivity.class));
                    break;
            }
        }
    }


}



