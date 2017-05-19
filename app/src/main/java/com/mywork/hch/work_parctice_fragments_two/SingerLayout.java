package com.mywork.hch.work_parctice_fragments_two;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/* 아이템을 위한 XML 레이아웃은 LinearLayout과 같은
   레이아웃 클래스를 상속하는 클래스를 만들어 설정함 */
public class SingerLayout extends LinearLayout {
	Context mContext;
    LayoutInflater inflater;

    ImageView imageView;
	TextView nameTextView;
	TextView companyTextView;
    TextView songTextView;

	//생성자-1
	public SingerLayout(Context context) {
		super(context);
		mContext = context;

		//객체가 생성될 때 초기화
		init();
	}

	//생성자-2
	public SingerLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;

		//객체가 생성될 때 초기화
		init();
	}

	//초기화 메서드
	private void init() {
        // 아이템의 화면을 구성한 XML 레이아웃(singer_item.xml)을 인플레이션
		inflater = (LayoutInflater) mContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.singer_item, this, true);

		//부분화면 레이아웃에 정의된 객체 참조
        imageView = (ImageView) findViewById(R.id.imageView);
		nameTextView = (TextView) findViewById(R.id.nameTextView);
		companyTextView = (TextView) findViewById(R.id.companyTextView);
        songTextView = (TextView) findViewById(R.id.songTextView);
    }//end of init()

    public void setImage(int resId) {//이미지 리소스 id 설정
        imageView.setImageResource(resId);
    }

	public void setNameText(String name) {//이름 설정
		nameTextView.setText(name);
	}
	
	public void setCompany(String company) {//소속 설정
		companyTextView.setText(company);
	}

    public void setSong(String song) {//노래 설정
        songTextView.setText(song);
    }
}
