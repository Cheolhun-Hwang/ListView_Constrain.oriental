package com.mywork.hch.work_parctice_fragments_two;


import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class DetailFragment extends Fragment  {
    private View v;

    ImageView imageView;
    TextView nameTextView, companyTextView, songTextView;
    Button backBtn;

    FragmentManager manager;

    public DetailFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        if(getActivity().getWindowManager().getDefaultDisplay().getRotation() == Surface.ROTATION_90){
            v= inflater.inflate(R.layout.fragment_detail_land, container, false);
        }else{
            v= inflater.inflate(R.layout.fragment_detail, container, false);
        }
        manager = getFragmentManager();

        //부분화면 레이아웃에 정의된 객체 참조
        imageView = (ImageView) v.findViewById(R.id.imageView);
        nameTextView = (TextView) v.findViewById(R.id.nameTextView);
        companyTextView = (TextView) v.findViewById(R.id.companyTextView);
        songTextView = (TextView) v.findViewById(R.id.songTextView);

        //이전 프래그먼트 정보를 가져와서 저장
        //Parcelable을 SingerItem에 추상클래스로 상속받아 putParcelable을 통해
        //데이터 전달 가능, 현재는 기존 Intent 코드를 계속쓰기 위해 각자 받음.

        int image = getArguments().getInt("img", 0);
        String name = getArguments().getString("name");
        String company = getArguments().getString("company");
        String song = getArguments().getString("song");

        imageView.setImageResource(image);
        nameTextView.setText(name);
        companyTextView.setText(company);
        songTextView.setText(song);

        //화면이 가로 일때와 세로일때 버튼이 필요 없고 필요있고 가 나뉩니다.
        //가로 시 버튼을 표시하지 않습니다. 세로시 버튼을 표시하고 이벤트를 설정합니다.
        if (getActivity().getWindowManager().getDefaultDisplay().getRotation() == Surface.ROTATION_90){
        }else{
            backBtn = (Button) v.findViewById(R.id.backBtn);

            //Back 버튼 클릭시 리턴 프래그먼트
            backBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    manager.beginTransaction().replace(R.id.Main_view_fram, ((MainActivity)getActivity()).getListviewFragment()).commit();
                }
            });//end of setOnClickListener
        }

        return v;
    }

}
