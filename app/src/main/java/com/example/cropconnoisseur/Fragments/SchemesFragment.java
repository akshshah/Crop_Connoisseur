package com.example.cropconnoisseur.Fragments;

import android.os.Bundle;
import androidx.fragment.app.Fragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.cropconnoisseur.HomeActivity;
import com.example.cropconnoisseur.R;

public class SchemesFragment extends Fragment {

    private WebView webView;
    String url = "https://krishijagran.com/agripedia/best-government-schemes-and-programmes-in-agriculture-for-farmers/";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_schemes, container, false);
        webView = view.findViewById(R.id.webView);

        webView.loadUrl(url);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        webView.setWebViewClient(new WebViewClient());

        webView.setOnKeyListener((View.OnKeyListener) (v, keyCode, event) -> {

            if(event.getAction() != KeyEvent.ACTION_DOWN)
                return true;
            if(keyCode == KeyEvent.KEYCODE_BACK){
                if(webView.canGoBack()){
                    webView.goBack();
                }
                else {
                    getActivity().onBackPressed();
                }
                return true;
            }
            return false;
        });

        return view;
    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Agricultural Schemes");
    }
}