package com.mywork.hch.work_parctice_fragments_two;

import android.content.res.Configuration;
import android.icu.text.UnicodeSetSpanner;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Surface;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private Fragment listviewFragment;
    private Fragment detailFragment;
    private SingerItem singerItem;
    private FragmentManager manager;
    private ArrayList<SingerItem> album;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //화면에 출력할 리스트 데이터 입력 리스트 관리
        album = new ArrayList<SingerItem>();
        album.add(new SingerItem(R.drawable.img01, "정은지", "에이핑크", "너란 봄"));
        album.add(new SingerItem(R.drawable.img02, "트와이스", "JYP엔터테인먼트", "KNOCK KNOCK"));
        album.add(new SingerItem(R.drawable.img03, "하이라이트", "어라운드어스", "얼굴 찌프리지 말아요"));
        album.add(new SingerItem(R.drawable.img04, "워너", "YG엔터테인먼트", "REALLY REALLY"));
        album.add(new SingerItem(R.drawable.img05, "걸스데이", "드림티엔터테인먼트", "I'LL BE YOURS"));
        album.add(new SingerItem(R.drawable.img06, "이엑스아이디", "바나나컬쳐엔터테인먼트", "낮 보다는 밤"));
        album.add(new SingerItem(R.drawable.img07, "지코", "세븐시즌스", "SHES A BABY"));

        //세로 -> 가로 넘어갈때 정보
        singerItem = null;

        //초기화
        listviewFragment = new ListviewFragment();
        detailFragment = new DetailFragment();
        manager = getSupportFragmentManager();

        //앱 화면에 따른 화면 표시 셋팅
        if(getWindowManager().getDefaultDisplay().getRotation() == Surface.ROTATION_90){
            setContentView(R.layout.activity_main_land);

            setListviewFragment();
            setDetailFragment();

        }else{
            setContentView(R.layout.activity_main);
            setListviewFragment();
        }
    }

    @Override
    protected void onStart(){
        super.onStart();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig){
        super.onConfigurationChanged(newConfig);
        //화면 가로 세로 변경시 화면 셋팅
        if(newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE){
            setContentView(R.layout.activity_main_land);
            Toast.makeText(getApplication(), "가로", Toast.LENGTH_SHORT).show();
            //가로 화면은 리스트와 디테일 프래그먼트 동시에 보여준다.
            setDetailFragment();
            setListviewFragment();
        }else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
            setContentView(R.layout.activity_main);
            Toast.makeText(getApplication(), "세로", Toast.LENGTH_SHORT).show();
            //세로 화면은 변동시 리스트를 보여주는 프래그먼트만 보여준다.
            setListviewFragment();
        }
    }

    public void setListviewFragment(Fragment listviewFragment) {
        this.listviewFragment = listviewFragment;
    }

    public void setDetailFragment(Fragment detailFragment) {
        this.detailFragment = detailFragment;
    }

    public Fragment getListviewFragment() {
        return listviewFragment;
    }

    public Fragment getDetailFragment() {
        return detailFragment;
    }

    public SingerItem getSingerItem() {
        return singerItem;
    }

    public void setSingerItem(SingerItem singerItem) {
        this.singerItem = singerItem;
    }

    public ArrayList<SingerItem> getAlbum() {
        return album;
    }

    public void setAlbum(ArrayList<SingerItem> album) {
        this.album = album;
    }

    public void setDetailFragment(){
        //화면을 변동하기 위한 프래그먼트
        Fragment changefragement = new DetailFragment();

        //첫 앱 실행시 아무 리스트를 클릭하지 않으면 1번 리스트의 내용을 보여준다.를 초기화하여 출력한다.
        if (singerItem == null){
            singerItem = album.get(0);
        }

        Bundle bundle = new Bundle();
        bundle.putInt("img", singerItem.getResId());
        bundle.putString("name", singerItem.getName());
        bundle.putString("company", singerItem.getCompany());
        bundle.putString("song", singerItem.getSong());
        changefragement.setArguments(bundle);
        //화면에 따른 프래그먼트 변동 코드
        if(getWindowManager().getDefaultDisplay().getRotation() == Surface.ROTATION_90){
            this.detailFragment = changefragement;
            manager.beginTransaction().replace(R.id.Main_view_fram2, changefragement).commit();
        }else{
            this.detailFragment = changefragement;
            manager.beginTransaction().replace(R.id.Main_view_fram, changefragement).commit();
        }
    }

    public void setListviewFragment(){
        //화면이 세로 가로 상관없이 같은 ID 값을 갖는다. 고로 같은 코드로 작성
        Fragment changefragment = new ListviewFragment();
        manager.beginTransaction().replace(R.id.Main_view_fram, changefragment).commit();
        listviewFragment = changefragment;
    }


}
