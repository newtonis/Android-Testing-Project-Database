package com.gnd.calificaprofesores.NetworkNewsHandler;

import com.gnd.calificaprofesores.RecyclerForClassFrontPageCapital.NewsItemData;

import java.util.List;

public interface GotNewsListener {
    void onGotNews(List<NewsItemData> news);
}
