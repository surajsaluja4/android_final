package com.compscitutorials.basigarcia.navigationdrawervideotutorial;


import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
/**
 * A simple {@link Fragment} subclass.
 */
public class GalleryFragment extends Fragment {
    Button camera;

    public GalleryFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View Rootview = inflater.inflate(R.layout.fragment_gallery, container, false);
        final Button cambutton = (Button) Rootview.findViewById(R.id.cam);
        final ImageView i = (ImageView) Rootview.findViewById(R.id.e1);
        cambutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                startActivityForResult(i, 0);
            }
        });

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        Bitmap b=(Bitmap)data.getExtras().get("data");
//        i.setImageBitmap(b);
//    }
//
//
//
//        return Rootview;
//
//    }

        return Rootview;
    }
}
