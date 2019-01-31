package com.developer.edu.app_arco;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.developer.edu.app_arco.conectionAPI.ConfigRetrofit;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import at.grabner.circleprogress.CircleProgressView;
import at.grabner.circleprogress.Direction;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MediaActivity extends AppCompatActivity {

    CircleProgressView mCircleView;

    public static List<String> list = new ArrayList<>();

    public static void clearList() {
        MediaActivity.list.clear();
    }

    public static void addList(String item) {
        MediaActivity.list.add(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media);

        mCircleView = (CircleProgressView) findViewById(R.id.circleView);

        list.add("2");
        list.add("3");

        Call<String> stringCall = ConfigRetrofit.getService().gerarMediaArcos(list);
        stringCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                if (response.code() == 200) {

         //value setting
                        mCircleView.setMaxValue(100);
                        mCircleView.setValue(0);
                        mCircleView.setValueAnimated(Float.parseFloat(response.body()));
                        mCircleView.setDirection(Direction.CW);
                        mCircleView.setAutoTextSize(true);
                        mCircleView.setUnitScale(0.9f);
                        mCircleView.setTextScale(0.9f);
                        mCircleView.setShowTextWhileSpinning(true);
                        mCircleView.setClickable(false);


                } else if (response.code() == 405) {
                    Toast.makeText(MediaActivity.this, response.body(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(MediaActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }


}
