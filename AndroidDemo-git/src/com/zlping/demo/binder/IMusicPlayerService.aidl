package com.zlping.demo.binder;

import android.os.Binder;

interface IMusicPlayerService{
    boolean start(String filePath);
    void stop();
}