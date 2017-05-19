package com.mywork.hch.work_parctice_fragments_two;


import android.content.Context;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListviewFragment extends Fragment {
    private View v;
    ListView listView;
    MyAdapter adapter;

    //가수 앨범을 담을 리스트(SingerItem 객체를 담아둘 ArrayList 생성)
    ArrayList<SingerItem> album;

    public ListviewFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_listview, container, false);

        listView = (ListView) v.findViewById(R.id.listView);
        album = ((MainActivity)getActivity()).getAlbum();


        //어댑터 생성
        adapter = new MyAdapter(getContext(), R.layout.singer_item, album);

        //리스트뷰에 어댑터 설정
        listView.setAdapter(adapter);


        //리스트뷰에서 아이템 클릭시 이벤트 처리
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            /* onItemClick() 메서드 params
               - parent : 클릭한 아이템을 포함하는 부모 뷰(ListView)
               - view : 클릭한 항목의 View
               - position : 클릭한 아이템의 Adepter에서의 위치값(0, 1, 2,...)
               - id : DB를 사용했을 때 Cursor의 id 값값
            */
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //번들을 이용하여 넘기고 싶지만 액티비티와 계속 정보를 주고 받고 싶기 때문에 번들을 쓰지 않았습니다.
                //대신 상위 액티비티의 메소드를 통해 데이터를 주고 받습니다.

                ((MainActivity)getActivity()).setSingerItem(album.get(position));
                ((MainActivity)getActivity()).setDetailFragment();

            }
        });//end of setOnItemClickListener
        // 필터링에 사용할 검색어는 입력상자에서 입력 받는다.
        EditText editTextSearch = (EditText) v.findViewById(R.id.editTextSearch);

        /* 입력상자(editTextSearch)의 내용이 변경될 때마다 리스트뷰의 필터링 텍스트를 변경 - 이벤트 처리
           - 입력상자의 내용이 변경될 때의 이벤트 리스너는 addTextChangedListener()를 통해 전달하며,
             TextWatcher 인터페이스를 implements하여 전달하면 됨
           - TextWatcher 인터페이스는 3개의 메서드를 가지고 있는데,
             입력상자의 텍스트가 변경되었을 때의 이벤트는 afterTextChanged() 메서드에서 처리함
         */

        //입력상자(EditText)의 텍스트 변경 이벤트 처리
        editTextSearch.addTextChangedListener(new TextWatcher() {
            //입력상자의 텍스트가 변경되었을 때의 자동 호출됨 - 필터링 수행
            @Override
            public void afterTextChanged(Editable edit) {
                //검색 키워드(필터링할 문자열)
                String filterText = edit.toString();

                //리스트뷰 아이템 필터링
                // 방법1) setFilterText() 메서드를 사용한 필터링 - 필터링 텍스트 팝업창이 뜬다.
                /*
                if (filterText.length() > 0) {
                    listView.setFilterText(filterText);//필터링
                } else {
                    listView.clearTextFilter();//필터링되어 표시된 결과를 초기상태로 clear
                }
                */

                /* 방법2) 리스트뷰를 통하지 않고 어댑터로부터 직접 filter() 객체를 가져와서
                   filter() 메서드로 필터링 수행
                   - setFilterText() 메서들 사용하면 필터링 텍스트 팝업창이 띄어지는 것을 피하기 위해서.
                */

                ((MyAdapter)listView.getAdapter()).getFilter().filter(filterText);
            }//end of afterTextChanged()

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }
        });//end of addTextChangedListener


        return v;
    }

    class MyAdapter extends BaseAdapter implements Filterable {
        Context mContext;//전달받은 Context 객체를 저장할 변수
        int singer_item;
        ArrayList<SingerItem> album;
        LayoutInflater inflater;
        Filter listFilter;
        private ArrayList<SingerItem> filteredItemList;

        //어댑터 생성자
        public MyAdapter(Context context, int singer_item, ArrayList<SingerItem> album) {
            mContext = context;
            this.singer_item = singer_item;
            
            filteredItemList = album;
            this.album = album;
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        /* 어댑터를 리스트뷰에 설정하면 리스트뷰(위젯)가 자동 호출함
                - 리스트뷰가 아댑터에게 요청하는 메서드들... */

        /* 어댑터에서 관리하고 있는 데이터(아이템)의 갯수를 반환
           (itemsList의 크기(size) 반환) */
        @Override
        public int getCount() {
            return filteredItemList.size();//리스트의 크기
        }

        //파라미터로 전달된 인덱스에 해당하는 데이터를 반환
        @Override
        public Object getItem(int position) {
            return filteredItemList.get(position);//리스트에서 아이템을 가져와 반환
        }

        //현재 아이템의 Id값을 인덱스값(position)을 반환
        @Override
        public long getItemId(int position) {
            return position;
        }

        //리스트에 아이템을 추가
        public void addItem(SingerItem item) {
            album.add(item);
        }

        //리스트의 모든 아이템을 삭제
        public void clear() {
            album.clear();
        }

        @Override//화면에 보일 아이템을 위한 뷰를 만들어 반환
        public View getView(int position, View convertView, ViewGroup parent) {
            //아이템을 위한 레이아웃 생성
            SingerLayout singerLayout = null;

            if (convertView == null) {
                singerLayout = new SingerLayout(mContext);
            } else {
                singerLayout = (SingerLayout) convertView;
            }
            

            //아이템의 인덱스값(position)을 이용해 리스트에 들어있는 아이템을 가져옴
            SingerItem items = filteredItemList.get(position);

            //아이템에서 이미지 리소스 id를 가져와, 레이아웃에 이미지 설정
            singerLayout.setImage(items.getResId());

            //아이템에서 이름을 가져와, 레이아웃에 이름 설정
            singerLayout.setNameText(items.getName());

            //아이템에서 소속을 가져와, 레이아웃에 소속 설정
            singerLayout.setCompany(items.getCompany());

            //아이템에서 노래를 가져와, 레이아웃에 노래 설정
            singerLayout.setSong(items.getSong());

            return singerLayout;//레이아웃을 리턴
        }//end of getView()

        //Filterable 인터페이스의 추상 메서드 - 필수 구현
        @Override
        public Filter getFilter() {
            if (listFilter == null) {
                //(7) =====================
                listFilter = new ListFilter();
            }
            return listFilter;
        }

        /* 필터링 기능을 구현하기 위해, Filter 클래스를 상속한 커스텀 Filter 클래스를 정의
            - performFiltering() 메서드와 publishResults() 메서드를 Override하여 구현
         */
        private  class ListFilter extends Filter {

            /* performFiltering() 메서드는 필터링을 수행하는 메서드
               - 필터링을 수행하는 루프를 구현한 다음, 필터링된 결과 리스트를 FilterResults에 담아서 리턴함
             */
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                //필터링 결과를 담을 FilterResults 객체 생성
                FilterResults results = new FilterResults();


                //(8) =====================
                if(constraint == null || constraint.length() == 0){
                    results.values = album;
                    results.count = album.size();
                }else{
                    ArrayList<SingerItem> itemList = new ArrayList<SingerItem>();
                    for(SingerItem item : album){
                        if(item.getName().toUpperCase().contains(constraint.toString().toUpperCase()) ||
                                item.getCompany().toUpperCase().contains(constraint.toString().toUpperCase())||
                                item.getSong().toUpperCase().contains(constraint.toString().toUpperCase())){
                            itemList.add(item);
                        }
                    }
                    results.values = itemList;
                    results.count = itemList.size();
                }
                return results;
            }//end of performFiltering()

            /* performFiltering() 메서드에서 필터링된 결과를 UI에 갱신시키는 역할을 수행
               - 커스텀 Adapter를 통한 ListView 갱신 작업을 구현하면 됨
             */
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                //필터링된 데이터 리스트를 filteredItemList(ArrayList)에 저장
                filteredItemList = (ArrayList<SingerItem>) results.values;

                //(9) =====================
                if(results.count > 0 ){
                    notifyDataSetChanged();
                }else{
                    notifyDataSetInvalidated();
                }

            }//end of publishResults()
        }//end of ListFilter 클래스
    }//end of MyAdapter class
}
