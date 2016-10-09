package com.capping.xinran.cappingnews.third;

import com.capping.xinran.cappingnews.BuildConfig;
import com.github.moduth.blockcanary.BlockCanaryContext;

/**
 * Created by qixinh on 16/9/18.
 */
public class EgBockCanaryContext extends BlockCanaryContext {
    public String providePath() {
        return "/blockcanary/englory";
    }
    public int provideBlockThreshold() {
        return 500;
    }
    public boolean displayNotification() {
        return BuildConfig.DEBUG;
    }
}
