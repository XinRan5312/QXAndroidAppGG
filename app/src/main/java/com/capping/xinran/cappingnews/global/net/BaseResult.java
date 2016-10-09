package com.capping.xinran.cappingnews.global.net;

import java.io.Serializable;

/**
 * Created by qixinh on 16/9/22.
 */
public class BaseResult<D> implements Serializable {
    private static final long serialVersionUID = 1L;
    public int state;
    public String message;
    public D data;
}
