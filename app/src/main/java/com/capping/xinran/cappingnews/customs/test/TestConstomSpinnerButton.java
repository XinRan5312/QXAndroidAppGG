package com.capping.xinran.cappingnews.customs.test;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.capping.xinran.cappingnews.R;
import com.capping.xinran.cappingnews.customs.views.ConstomSpinerButton;

/**
 * Created by houqixin on 2016/11/2.
 */
public class TestConstomSpinnerButton extends Activity {

    private ConstomSpinerButton mSpinnerBtn;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_custom_spinner_button_act);

        this.mSpinnerBtn = (ConstomSpinerButton) this
                .findViewById(R.id.spinner_btn);
        mSpinnerBtn.setResIdAndViewCreatedListener(R.layout.spinner_dropdown_items,
                new ConstomSpinerButton.ViewCreatedListener() {
                    @Override
                    public void onViewCreated(View v) {
                        if(v instanceof LinearLayout){
                            LinearLayout linearLayout= (LinearLayout) v;
                            int childCount=linearLayout.getChildCount();
                            Log.e("ConstomSpinerButton", "childCount" + childCount);
                            for(int i=0;i<childCount;i++){
                                linearLayout.getChildAt(i).setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        handleClick(((TextView) view).getText().toString());
                                    }
                                });
                            }
                        }
                    }

                });
    }

    private void handleClick(String text){
        mSpinnerBtn.dismiss();
        mSpinnerBtn.setText(text);
        mSpinnerBtn.startAnimation(0);
    }
}
