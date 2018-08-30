package barreto.alessandro.feedlist;

import android.content.Context;
import android.os.Handler;
import com.physicaloid.lib.Physicaloid;
import com.physicaloid.lib.usb.driver.uart.ReadLisener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
/**
 * Created by Lee K D on 2017-11-13.
 */

public class SerialConnection{

    Physicaloid m_Physicaloid;
    Handler m_Handler = new Handler();
    List m_Light = new ArrayList();          //"000164e599f796d40102030405060708090ab0890001";
    ArrayList<Integer> m_Light_Position = new ArrayList();
    List m_Buf = new ArrayList();

    int m_Prev_Light_Num = -1;
    int m_Current_Light_Num = -1;

    //Get
    public int Get_Prev_Light_Num()
    {
        return m_Prev_Light_Num;
    }

    //Get
    public int Get_Current_Light_Num()
    {
        return m_Current_Light_Num;
    }

    //생성자
    public SerialConnection()
    {
        m_Light.add("0001000264e599f796d40102030405060708090abab5");
        m_Light_Position.add(15);
    }

    //1. 커넥션 생성
    public void Create_Connection(Context context)
    {
        m_Physicaloid = new Physicaloid(context);
    }

    //2. 커넥션 열기
    public boolean Open_Connection()
    {
        if (m_Physicaloid.open()) {
            //Toast.makeText(((Main2Activity)Main2Activity.mContext), "mPhysicaloid 열림", Toast.LENGTH_LONG).show();
            //Toast.makeText(this, "mPhysicaloid 열림", Toast.LENGTH_LONG).show();

            m_Physicaloid.addReadListener(new ReadLisener() {
                @Override
                public void onRead(int size) {
                    if (size > 0) {
                        byte[] temp_buf = new byte[size];
                        m_Physicaloid.read(temp_buf, size);

                        Get_Light_Number(asHex(temp_buf));
                    }
                }
            });

            return true;
        } else {
            //Toast.makeText(((Main2Activity)Main2Activity.mContext), "Can not open", Toast.LENGTH_LONG).show();
            return false;
        }

    }

    //3. 수신 레이트 세팅
    public void Set_Baudrate(int x)
    {
        m_Physicaloid.setBaudrate(x);
    }

    //4. 커넥션 닫기
    public void Close_Connection() {	//when close button is pressed
        if(m_Physicaloid.close()) { 	//close the connection to arduino
            m_Physicaloid.clearReadListener();	//clear read listener
            //Toast.makeText(this, "Close", Toast.LENGTH_LONG).show();
        }
    }

    //열렸는지 검사
    public boolean Is_Opened() {
        return m_Physicaloid.isOpened();
    }


    //16바이트로 표현된 숫자의 스트링을 얻어 계산
    private void Get_Light_Number(CharSequence text) {
        final CharSequence ftext = text;
        m_Handler.post(new Runnable() {
            @Override
            public void run() {
                //리스트 길어지면 지우고
                if(m_Buf.size() >= 10)
                    m_Buf.clear();
                //리스트에 추가
                m_Buf.add(ftext.toString());

                //리스트 돌면서 검사
                String temp_str = m_Buf.toString().replaceAll(",", "").replaceAll("\n", "").replaceAll(" ", "");

                for(int i = 0, n = m_Light.size(); i < n; i++) {
                    if (temp_str.contains(m_Light.get(i).toString())) {
                            m_Buf.clear();
                            m_Prev_Light_Num = m_Current_Light_Num;
                            m_Current_Light_Num = m_Light_Position.get(i);
                            // ((Main2Activity)Main2Activity.mContext).change_edit(m_Current_Light_Num);
                            break;
                    } else {
                        if(m_Current_Light_Num != -1) {
                            m_Prev_Light_Num = m_Current_Light_Num;
                            m_Current_Light_Num = -1;
                            //((Main2Activity)Main2Activity.mContext).change_edit(m_Current_Light_Num);
                        }
                        //textView.setText("아무것도 못 찾았다." + buf_list.toString().replaceAll(",", "").replaceAll("\n", "").replaceAll(" ", ""));
                    }
                }
            }
        });
    }

    //바이트를 16진수로 바꾸어 문자열 함수
    public static String asHex(byte[] bytes) {
        StringBuffer sb = new StringBuffer(bytes.length * 2);
        for (int i = 0; i < bytes.length; i++) {
            if (((int) bytes[i] & 0xff) < 0x10) {
                sb.append('0');
            }
            sb.append(Integer.toString(bytes[i] & 0xff, 16));
        }
        return sb.toString();
    }


}
