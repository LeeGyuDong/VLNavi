package barreto.alessandro.feedlist;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Vector;

/**
 * Created by Lee K D on 2017-12-17.
 */

public class PathFinder3 {


    //ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ

    public class light
    {
        public int x;
        public int y;
        adj_node adj_list = null;

        public light(int x, int y)
        {
            this.x = x;
            this.y = y;
        }

    }

    public ArrayList<light> light_list = new ArrayList<>();

    public class adj_node
    {
        int vertex;
        int length;
        adj_node next_node;
    }

    ArrayList<adj_node> adj_list = new ArrayList<>();
    public Vector<Integer> result_list = new Vector<>();

    ArrayList<Integer> d = new ArrayList<>();
    ArrayList<Integer> p = new ArrayList<>();
    ArrayList<Integer> f = new ArrayList<>();

    CustomView customView = null;
    public int start_light;
    public int end_light;

    public int layout_Height = 1680;
    public int layout_Width = 1080;
    public int map_Height = 907;
    public int map_Width = 740;

    class heap_node
    {
        int left_vertex;
        int right_vertex;
        int length;
    }

    ArrayList<heap_node> heap = new ArrayList<>();

    /**
     * int로 오름차순(Asc) 정렬
     * @author Administrator
     *
     */
    static class CompareSeqAsc implements Comparator<heap_node> {
        @Override
        public int compare(heap_node o1, heap_node o2) {
            // TODO Auto-generated method stub
            return o1.length < o2.length ? -1 : o1.length > o2.length ? 1:0;
        }
    }

    //ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ인접 리스트
    adj_node New_Node(int vertex, int length)
    {
        adj_node new_node = new adj_node();
        new_node.length = length;
        new_node.vertex = vertex;
        new_node.next_node = null;

        return new_node;
    }

    public void Insert_Node(int left_vertex, int right_vertex, int length)
    {
        adj_node tail_node = null;

        //꼬리찾기
        try {
            tail_node = adj_list.get(left_vertex);
        }
        catch (IndexOutOfBoundsException e) {
            tail_node = null;
        }
        if (tail_node == null)
        {
            //cout << "머리에 넣기" << endl;
            adj_list.set(left_vertex, New_Node(right_vertex, length));
        }
        else
        {
            while (tail_node.next_node !=null)
                tail_node = tail_node.next_node;

            tail_node.next_node = New_Node(right_vertex, length);
        }
    }


    //ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ힙
    void Light_Adj_Matching()
    {
        for(int i = 0 ; i < light_list.size() ; i++)
            light_list.get(i).adj_list = adj_list.get(i);
    }

    heap_node Delete()
    {
        heap_node result;

        if (heap.size() <= 0)
            return null;

        result = heap.get(0);
        heap.remove(0);
        Collections.sort(heap, new CompareSeqAsc());

        Light_Adj_Matching();

        return result;
    }


    heap_node New_Heap_Node(int left_vertex, int right_vertex, int length)
    {
        heap_node temp_node = new heap_node();
        temp_node.left_vertex = left_vertex;
        temp_node.right_vertex = right_vertex;
        temp_node.length = length;

        return temp_node;
    }


    void Insert(heap_node input)
    {
        heap.add(input);
        Collections.sort(heap, new CompareSeqAsc());

        Light_Adj_Matching();
    }

    void Clean_Heap()
    {
        while (heap.size() != 0)
            Delete();

        Light_Adj_Matching();
    }


    //ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ
    void Initialize()
    {
        //초기화
        d.clear();
        p.clear();
        f.clear();
        result_list.clear();

        for (int i = 0; i < adj_list.size(); i++)
        {
            //adj_list[i] = null;
            d.add(0);
            p.add(1);
            f.add(0);
            //d[i] = 0;
            //p[i] = 1;
            //f[i] = 0;
        }

        //for (int i = 0; i < heap_max; i++)
        //    heap[i] = null;
    }

    void Process()
    {
        //힙에 넣자
        for (int i = 0; i < adj_list.size(); i++)
        {
            adj_node current_node = adj_list.get(i);
            if (current_node == null)
                continue;

            while (true)
            {
                if (f.get(i) != 0)//i은 방문했어야하고
                {
                    if (f.get(current_node.vertex) == 0) //i와 이어진 노드는 방문안했을때만
                        Insert(New_Heap_Node(i, current_node.vertex, current_node.length + d.get(i)));
                }
                current_node = current_node.next_node;

                if (current_node == null)
                    break;
            }
        }

        //힙에서 하나 가져오기
        heap_node top_node = Delete();

        //어느 버텍스의 부모를 바꾸어야하는지 찾기
        int target_vertex;
        int target_parent_vertex;

        if (f.get(top_node.left_vertex) == 0)
        {
            target_vertex = top_node.left_vertex;
            target_parent_vertex = top_node.right_vertex;
        }
        else
        {
            target_vertex = top_node.right_vertex;
            target_parent_vertex = top_node.left_vertex;
        }

        //찾은 대상의 부모와 거리, 플래그 변경
        d.set(target_vertex, top_node.length);
        p.set(target_vertex, target_parent_vertex);
        f.set(target_vertex, 1);

        //cout << "거리 : " << d[target_vertex] << endl;

        Clean_Heap();

    }

    public int Add_Light(int x, int y)
    {
        adj_list.add(null);

        light temp_light = new light(x, y);
        light_list.add(temp_light);
        return light_list.indexOf(temp_light);
    }


    public int Find_Path()
    {
        Initialize();

        //2번 위치에서 출발한다.
        d.set(start_light, 0);
        p.set(start_light, -1);
        f.set(start_light, 1);

        for (int i = 0; i < adj_list.size() - 1; i++)
            Process(); //최단거리 하나 찾아서 이어버리기

        //Print_Distance(vertex_num);
        //Print_Parent(vertex_num);

        //cout << endl;

        int current_parent = p.get(end_light);

        Log.d("치즈", " " + end_light + " " + light_list.get(end_light).x + "/" + light_list.get(end_light).y);
        result_list.add(end_light);

        while (true)
        {
            Log.d("치즈", " " + current_parent + " " + light_list.get(current_parent).x + "/" + light_list.get(current_parent).y);
            result_list.add(current_parent);
            if (current_parent == start_light)
                break;
            else
                current_parent = p.get(current_parent);
        }

        return 0;
    }

    public CustomView Get_View_And_Find_Path(Context context)
    {
        if(0 <= end_light && end_light != start_light)
            Find_Path();

        if(customView != null) {
            ViewGroup.LayoutParams params = customView.getLayoutParams();
            params.width = 0;
            params.height = 0;
            customView.setLayoutParams(params);
            //customView.setVisibility(View.GONE);
            //x.removeView(customView);
            //customView = null;

        }


        customView = new CustomView(context);

        if(0 <= end_light && end_light != start_light) {
            customView.Make_Start_Point(light_list.get(result_list.get(result_list.size() - 1)).x, light_list.get(result_list.get(result_list.size() - 1)).y);
            for (int i = result_list.size() - 2; i >= 0; i--)
                customView.Make_Path(light_list.get(result_list.get(i)).x, light_list.get(result_list.get(i)).y);
        }
        else
            end_light = -1;


        return customView;

    }


    public class CustomView extends View //화면에서 보여질 커스텀 뷰
    {
        Paint pathPaint;
        Path path;
        int light_length = 1;
        public CustomView(Context context) //생성자
        {
            super(context);

            pathPaint = new Paint();
            pathPaint.setAntiAlias(true);
            pathPaint.setColor(Color.RED);
            pathPaint.setStyle(Paint.Style.STROKE);
            pathPaint.setStrokeWidth(10.0f);
            //pathPaint.setStrokeCap(Paint.Cap.BUTT);
            //pathPaint.setStrokeJoin(Paint.Join.MITER);

            path = new Path();
        }

        public void Make_Start_Point(int x, int y)
        {

            path.moveTo(x * light_length  * layout_Width / map_Width, y * light_length  * layout_Height / map_Height);
        }



        public void Make_Path(int x, int y)
        {
            path.lineTo(x * light_length  * layout_Width / map_Width, y * light_length  * layout_Height / map_Height);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            canvas.drawPath(path, pathPaint);

            //if( 0 > end_light || end_light == start_light)
           // {
                Paint tempPaint = new Paint();
                tempPaint.setAntiAlias(true);
                tempPaint.setColor(Color.RED);
                tempPaint.setStyle(Paint.Style.STROKE);
                tempPaint.setStrokeWidth(10.0f);
                tempPaint.setStyle(Paint.Style.FILL);
                canvas.drawCircle(light_list.get(start_light).x * light_length  * layout_Width / map_Width, light_list.get(start_light).y * light_length  * layout_Height / map_Height, 12, tempPaint);

            //}
        }

    }


}
