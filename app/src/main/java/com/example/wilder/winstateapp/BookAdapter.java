package com.example.wilder.winstateapp;

import android.content.Context;
import android.net.Uri;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.RelativeSizeSpan;
import android.view.LayoutInflater;
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

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        final ViewGroup rootView = (ViewGroup) LayoutInflater.from(container.getContext()).inflate(R.layout.page, container, false);
        rootView.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT));
        ((TextView) rootView.findViewById(R.id.text)).setText(items.get(position).getDescription());
        ((TextView) rootView.findViewById(R.id.header_title)).setText(items.get(position).getTitle());
        ((TextView) rootView.findViewById(R.id.textadress)).setText(items.get(position).getLinkArticle());

        VideoView miniatureArticle = rootView.findViewById(R.id.header_img);
        miniatureArticle.setVideoURI(Uri.parse(items.get(position).getLinkVideo()));
        miniatureArticle.seekTo(100);



        return attach(container, rootView, position);
    }

    private void setSpan(TextView textView, String pattern) {
        float relativeSize = 1.5f;
        Pattern pat = Pattern.compile(pattern);
        Matcher m = pat.matcher(textView.getText());
        if (m.find()) {
            SpannableString span = new SpannableString(textView.getText());
            span.setSpan(new RelativeSizeSpan(relativeSize), 0, m.group(0).length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            textView.setText(span);
        }
    }
}