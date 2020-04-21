package com.tutorialsee.ecommer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.Toast;

import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.tutorialsee.ecommer.adapters.ListbaseAdapter;
import com.tutorialsee.ecommer.common.MyCustomDialog;
import com.tutorialsee.ecommer.modal.Beanclasses;

import java.util.ArrayList;
import java.util.HashMap;

public class HomeActivity extends AppCompatActivity implements BaseSliderView.OnSliderClickListener {
    SliderLayout mDemoSlider;

    private ListView list;
    private ArrayList<Beanclasses> Bean;
    private ListbaseAdapter baseAdapter;

    private int[] IMAGE = {R.drawable.slidertea, R.drawable.slider2, R.drawable.slider3,};

    private String[] TITLE = {"", "", ""};

    private String[] SUBTITLE = {"", "", ""};

    private String[] SHOP = {"Add Product", "Search Product", "Log Out"};

    MyCustomDialog fragment_dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homeactivity);

        list = (ListView)findViewById(R.id.list);


        //*****listview*******
        Bean = new ArrayList<Beanclasses>();

        for (int i= 0; i< TITLE.length; i++){

            Beanclasses bean = new Beanclasses(IMAGE[i], TITLE[i], SUBTITLE[i], SHOP[i]);
            Bean.add(bean);

        }

        baseAdapter = new ListbaseAdapter(HomeActivity.this, Bean) {

        };

        list.setAdapter(baseAdapter);

        //******slider***********
        mDemoSlider = (SliderLayout)findViewById(R.id.slider);

        HashMap<String,Integer> file_maps = new HashMap<String, Integer>();
        file_maps.put("1", R.drawable.s1);
        file_maps.put("2",R.drawable.s2);
        file_maps.put("3",R.drawable.s3);


        for(String name : file_maps.keySet()){
            TextSliderView textSliderView = new TextSliderView(this);
            // initialize a SliderLayout
            textSliderView
                    //  .description(name)
                    .image(file_maps.get(name))
                    .setScaleType(BaseSliderView.ScaleType.CenterCrop)
                    .setOnSliderClickListener(this);


            textSliderView.bundle(new Bundle());
            textSliderView.getBundle().putString("extra", name);

            mDemoSlider.addSlider(textSliderView);
        }
        mDemoSlider.setPresetTransformer(SliderLayout.Transformer.Default);
        mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        mDemoSlider.setCustomAnimation(new ChildAnimationExample());
        mDemoSlider.setDuration(4000);
        mDemoSlider.addOnPageChangeListener(this);
    }

    @Override
    public void onSliderClick(BaseSliderView slider) {

    }

}