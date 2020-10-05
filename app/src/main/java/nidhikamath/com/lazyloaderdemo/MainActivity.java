package nidhikamath.com.lazyloaderdemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import nidhikamath.com.lazyloaderdemo.databinding.ActivityMainBinding;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<String> mStringList;
    private int mLoadedItems = 0;
    private SampleAdapter mSampleAdapter;
    private ActivityMainBinding mActivityMainBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MainActivity.this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mActivityMainBinding.recyclerView.setLayoutManager(linearLayoutManager);
        mActivityMainBinding.recyclerView.addItemDecoration(new DividerItemDecoration(MainActivity.this, DividerItemDecoration.VERTICAL));
        mStringList = new ArrayList<>();
        mSampleAdapter = new SampleAdapter(mStringList);
        mActivityMainBinding.recyclerView.setAdapter(mSampleAdapter);
        addDataToList();

        mActivityMainBinding.recyclerView.addOnScrollListener(new EndlessRecyclerOnScrollListener() {
            @Override
            public void onLoadMore() {
                addDataToList();
            }
        });
    }

    private void addDataToList() {
        mActivityMainBinding.itemProgressBar.setVisibility(View.VISIBLE);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i <= 30; i++) {
                    mStringList.add("SampleText : " + mLoadedItems);
                    mLoadedItems++;
                }
                mSampleAdapter.notifyDataSetChanged();
                mActivityMainBinding.itemProgressBar.setVisibility(View.GONE);
            }
        }, 1500);

    }
}
