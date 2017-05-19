package com.mywork.hch.work_parctice_fragments_two;

import android.graphics.drawable.Drawable;

//  리스트뷰의 아이템에 표시할 테이터 클래스를 정의
public class ListViewItem {
    private Drawable icon;//이미지
    private String title;//타이틀(팀명)
    private String description;//설명(소속사)

    public void setIcon(Drawable icon) {
        this.icon = icon;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Drawable getIcon() {
        return this.icon;
    }

    public String getTitle() {
        return this.title;
    }

    public String getDescription() {
        return this.description;
    }
}
