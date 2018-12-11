package com.myprojects.nicklasgilbertsson.meditation;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class YogaDetailActivity extends AppCompatActivity {

    ImageView mDetailImage;
    TextView mDetailTitle, mDetailDesc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yoga_detail);

        mDetailImage = (ImageView)findViewById(R.id.yogaDetailImage);
        mDetailTitle = (TextView)findViewById(R.id.yogaDetailTitle);
        mDetailDesc = (TextView)findViewById(R.id.yogaDetailDesc);

        byte[] bytes = getIntent().getByteArrayExtra("image");
        String title = getIntent().getStringExtra("title");
        String desc = getIntent().getStringExtra("desc");

        Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        mDetailTitle.setText(title);
        mDetailImage.setImageBitmap(bmp);
        mDetailDesc.setText(desc);
    }
}
