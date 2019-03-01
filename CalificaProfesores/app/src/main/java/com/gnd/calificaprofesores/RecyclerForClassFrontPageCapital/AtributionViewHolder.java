package com.gnd.calificaprofesores.RecyclerForClassFrontPageCapital;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.gnd.calificaprofesores.R;

import org.w3c.dom.Text;

public class AtributionViewHolder extends RecyclerView.ViewHolder {
    View view;
    public AtributionViewHolder(View _view){
        super(_view);
        view = _view;
    }
    public void setDetails(String html){
        WebView content = view.findViewById(R.id.WebView);

        String mime = "text/html";
        String encoding = "utf-8";

        //content.getSettings().setJavaScriptEnabled(true);
        content.loadDataWithBaseURL(null, html, mime, encoding, null);

    }
}
