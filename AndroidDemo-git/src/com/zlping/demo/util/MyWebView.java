package com.zlping.demo.util;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebView;
/**
 * 自定义webview类
 * @author zhangy
 *
 */
public class MyWebView extends WebView {
	/**
	 * 用于监听消息loading框的自定义listener
	 */
	private OnContentShowListener mOnContentShowListener;
	/**
	 * 用于监听加载网页出错的listener
	 */
	private OnErrorListener mOnErrorListener;
	/**
	 * 用于监听网页加载成功的listener
	 */
	private OnPageFinishedListener mOnPageFinishedListener;
	/**
	 * 用于监听网页加载成功的listener
	 */
	private OnSuccessFinishedListener mOnPageSuccessFinishedListener;
	  /**
     * 用于监听网页开始加载的listener
     */
	private OnPageStartListener mOnPageStartListener;
	private boolean mInTouching;
	
	private String url;
    private final static String TAG =MyWebView.class.getSimpleName();
    
	public MyWebView(Context context) {
		super(context);
		/*让进度条显示在网页内容之上一层而不是和内容平行*/
		this.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
	}
	
	public MyWebView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
	}
	
	public MyWebView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
	}
	@Deprecated	
	public interface OnContentShowListener {
		public void onContentShow(WebView view);
	}
	
	@Deprecated
	public void setOnContentShowListener (OnContentShowListener listener) {
		mOnContentShowListener = listener;
	}
	
	@Deprecated
	public OnContentShowListener getContentShowListener(){
		return mOnContentShowListener;
	}
	
	@Deprecated
	public void contentShow(){
		if(mOnContentShowListener!=null)
		mOnContentShowListener.onContentShow(MyWebView.this);
	}
	
	public interface OnSuccessFinishedListener{
	    public void onSuccessFinished(WebView view);
	}
	
	public interface OnErrorListener {
		public void onError(WebView view);
	}
	
	public interface OnPageStartListener{
		public void onPageStart(WebView view);
	}
	
	public interface OnPageFinishedListener{
		public void onPageFinished(WebView view);
	}
	
	public void setOnErrorListener (OnErrorListener listener) {
		mOnErrorListener = listener;
	}
    
	public void setPageOnFinishedListener (OnPageFinishedListener listener) {
		mOnPageFinishedListener = listener;
	}
	
	public OnPageStartListener getOnPageStartLinstener(){
	    return this.mOnPageStartListener;
	}
	
	public void setOnPageStartLinstener(OnPageStartListener listener){
	    this.mOnPageStartListener = listener;
	}
	
	public void setOnSuccessFinishedListener(OnSuccessFinishedListener listener){
	    this.mOnPageSuccessFinishedListener = listener;
	}
	
	public void contentLoadError(){
		if(mOnErrorListener != null){
			mOnErrorListener.onError(MyWebView.this);
		}
	}
	
    public void pageFinish(){
		if(mOnPageFinishedListener!=null){
			mOnPageFinishedListener.onPageFinished(MyWebView.this);
		}
	}
    
    public void pageSuccess(){
		if(mOnPageSuccessFinishedListener!=null){
			mOnPageSuccessFinishedListener.onSuccessFinished(MyWebView.this);
		}
	}
    
    @Override
    public boolean performLongClick() {
    	return false;
    }

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	/**
	 * 添加收藏JS
	 */
	public void addtofav(){
	    this.loadUrl("javascript:$.infodetail.addtofav();");
	}
	
	@Override
    public void scrollTo (int x, int y) {
        if (!mInTouching) {
        	if (y <= 2 && getScrollY() > 2) {
        		return;
        	}
        }
    	super.scrollTo(x, y);
    }
    
    @Override
    public boolean onTouchEvent (MotionEvent ev) {
    	final int action = ev.getAction();
    	final float y = ev.getY();
    	switch (action) {
    	case MotionEvent.ACTION_DOWN:
    		mInTouching = true;
    	    break;
    	case MotionEvent.ACTION_CANCEL:
    	case MotionEvent.ACTION_UP:
    		mInTouching = false;
    	    break;
    	}
    	return super.onTouchEvent(ev);
    }
	
}