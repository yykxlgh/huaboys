package com.example.pulldownview;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ListView;

import com.example.widget.PullDownListView;

public class MainActivity extends Activity  implements PullDownListView.OnRefreshListioner{

	private PullDownListView mPullDownView;
	private ListView mListView;
	private List<String> list = new ArrayList<String>();
	private MyAdapter adapter;
	private Handler mHandler = new Handler();
	private int maxAount = 20;//设置了最大数据值
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    	mPullDownView = (PullDownListView) findViewById(R.id.sreach_list);
		mPullDownView.setRefreshListioner(this);
		mListView = mPullDownView.mListView;
		addLists(10);
	    adapter = new MyAdapter(this,list);	
	    mPullDownView.setMore(true);//这里设置true表示还有更多加载，设置为false底部将不显示更多
	    mListView.setAdapter(adapter);
    }
    
    private void addLists(int n){
    		
    	 n += list.size();
    	 for(int i=list.size();i<n;i++){
	        list.add("选项"+i);
	     }
    }
    
    /**
     * 刷新，先清空list中数据然后重新加载更新内容
     */
	public void onRefresh() {
		
		mHandler.postDelayed(new Runnable() {
			
			public void run() {
				list.clear();
				addLists(10);
				mPullDownView.onRefreshComplete();//这里表示刷新处理完成后把上面的加载刷新界面隐藏
				mPullDownView.setMore(true);//这里设置true表示还有更多加载，设置为false底部将不显示更多
				adapter.notifyDataSetChanged();	
				
			}
		}, 1500);
		
		
	}
	
	/**
	 * 加载更多，在原来基础上在添加新内容
	 */
	public void onLoadMore() {
		
		mHandler.postDelayed(new Runnable() {
			public void run() {
				addLists(5);//每次加载五项新内容
				mPullDownView.onLoadMoreComplete();//这里表示加载更多处理完成后把下面的加载更多界面（隐藏或者设置字样更多）
				if(list.size()<maxAount)//判断当前list中已添加的数据是否小于最大值maxAount，是那么久显示更多否则不显示
					mPullDownView.setMore(true);//这里设置true表示还有更多加载，设置为false底部将不显示更多
				else
					mPullDownView.setMore(false);
				adapter.notifyDataSetChanged();	
				
			}
		}, 1500);
	}


    
}
