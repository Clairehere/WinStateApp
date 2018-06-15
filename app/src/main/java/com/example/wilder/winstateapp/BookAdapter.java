package com.example.wilder.winstateapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.RelativeSizeSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.VideoView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.telenav.expandablepager.adapter.ExpandablePagerAdapter;
//import com.telenav.expandableviewpager.R;

public class BookAdapter extends ExpandablePagerAdapter<VideoModel> {
    public BookAdapter(List<VideoModel> items) {
        super(items);
    }

    boolean playVid = false;

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        final ViewGroup rootView = (ViewGroup) LayoutInflater.from(container.getContext()).inflate(R.layout.page, container, false);
        rootView.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT));
        ((TextView) rootView.findViewById(R.id.text)).setText(items.get(position).getDescription());
        ((TextView) rootView.findViewById(R.id.header_title)).setText(items.get(position).getTitle());

        TextView link = rootView.findViewById(R.id.textadress);
        link.setText(items.get(position).getLinkArticle());

        VideoView miniatureArticle = rootView.findViewById(R.id.header_img);
        miniatureArticle.setVideoURI(Uri.parse(items.get(position).getLinkVideo()));
        miniatureArticle.seekTo(100);


        link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(items.get(position).getLinkArticle()));
                rootView.getContext().startActivity(browserIntent);
            }
        });

        return attach(container, rootView, position);
    }

}