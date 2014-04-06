package com.tatuas.android.bitmapallocationsample;

import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

public class MainActivity extends ActionBarActivity {
  int mCurrentIndex = 0;
  final int[] imageIDs = {
      R.drawable.a, R.drawable.b, R.drawable.c, R.drawable.d, R.drawable.e
  };
  Bitmap mCurrentBitmap;
  BitmapFactory.Options mBitmapOptions;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    Toast.makeText(this, "On Create", Toast.LENGTH_SHORT).show();
    super.onCreate(savedInstanceState);

    if (savedInstanceState == null) {
      Toast.makeText(this, "Bundle is null.", Toast.LENGTH_SHORT).show();
    }

    setContentView(R.layout.activity_main);

    final CheckBox checkBox = (CheckBox) findViewById(R.id.checkBox);
    final TextView durationTextView = (TextView) findViewById(R.id.durationTextView);
    final ImageView imageView = (ImageView) findViewById(R.id.imageView);

    // Create option instance
    mBitmapOptions = new BitmapFactory.Options();

    // Load drawble images profile (not load image bitmap)
    mBitmapOptions.inJustDecodeBounds = true;
    BitmapFactory.decodeResource(getResources(), R.drawable.a, mBitmapOptions);

    // Create Bitmap frame by option value (this does not have fact image)
    mCurrentBitmap = Bitmap.createBitmap(mBitmapOptions.outWidth,
        mBitmapOptions.outHeight, Bitmap.Config.ARGB_8888);

    // Create bitmap from resources in fact
    mBitmapOptions.inJustDecodeBounds = false;
    mBitmapOptions.inBitmap = mCurrentBitmap;
    mBitmapOptions.inSampleSize = 4;
    BitmapFactory.decodeResource(getResources(), R.drawable.a, mBitmapOptions);

    // Set to imageview
    imageView.setImageBitmap(mCurrentBitmap);

    imageView.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        mCurrentIndex = (mCurrentIndex + 1) % imageIDs.length;
        Bitmap onClickBitmap;
        BitmapFactory.Options onClickBitmapOptions;

        long startTime = System.currentTimeMillis();

        if (checkBox.isChecked()) {
          onClickBitmapOptions = mBitmapOptions;
          onClickBitmap = mCurrentBitmap;
          onClickBitmapOptions.inBitmap = onClickBitmap;
          BitmapFactory.decodeResource(getResources(), imageIDs[mCurrentIndex],
              onClickBitmapOptions);
        } else {
          onClickBitmap = null;
          onClickBitmapOptions = new BitmapFactory.Options();

          // Load drawble images profile (not load image bitmap)
          onClickBitmapOptions.inJustDecodeBounds = true;
          BitmapFactory.decodeResource(getResources(), imageIDs[mCurrentIndex],
              onClickBitmapOptions);

          // Create Bitmap frame by option value (this does not have fact image)
          onClickBitmap = Bitmap.createBitmap(onClickBitmapOptions.outWidth,
              onClickBitmapOptions.outHeight, Bitmap.Config.ARGB_8888);

          // Create bitmap from resources in fact
          onClickBitmapOptions.inJustDecodeBounds = false;
          onClickBitmapOptions.inBitmap = onClickBitmap;
          onClickBitmapOptions.inSampleSize = 4;
          BitmapFactory.decodeResource(getResources(), imageIDs[mCurrentIndex],
              onClickBitmapOptions);
        }

        // Set to imageview
        imageView.setImageBitmap(onClickBitmap);

        durationTextView.setText("Load took "
            + (System.currentTimeMillis() - startTime));
      }
    });
  }
}
