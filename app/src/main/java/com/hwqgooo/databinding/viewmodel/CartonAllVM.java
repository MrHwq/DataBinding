package com.hwqgooo.databinding.viewmodel;

import android.arch.paging.DataSource;
import android.arch.paging.LivePagedListBuilder;
import android.arch.paging.PagedList;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;

import com.hwqgooo.databinding.BR;
import com.hwqgooo.databinding.R;
import com.hwqgooo.databinding.command.ReplyCommand;
import com.hwqgooo.databinding.model.bean.Girl;
import com.hwqgooo.databinding.model.carton.LocalCartonDataSource;
import com.hwqgooo.databinding.utils.recyclerview.ItemViewArg;
import com.hwqgooo.jetpack.utils.recyclerview.ItemViewClassSelector;
import com.hwqgooo.jetpack.utils.recyclerview.LayoutManagers;

import java.util.LinkedList;
import java.util.List;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by weiqiang on 2016/7/4.
 */
public class CartonAllVM extends BaseGirlVM<Girl> {
    public static final String TAG = CartonAllVM.class.getSimpleName();
    private CompositeDisposable cd;

    public CartonAllVM() {
        System.out.println("=====================");
        itemView = ItemViewArg.of(ItemViewClassSelector.builder()
                .put(Girl.class, BR.girl, R.layout.item_girl)
                .build());
        onRefresh = new ReplyCommand(() -> {
            if (isRefreshing.get()) {
                Log.d(TAG, "call: onRefresh is refreshing");
                return;
            }

            load(true);
        });
        onLoadMore = new ReplyCommand(() -> {
            if (isRefreshing.get()) {
                Log.d(TAG, "call: onLoadMore is refreshing");
                return;
            }
            load(false);
        });
        onItemClick = new ReplyCommand<>(integer -> {
            Log.d(TAG, "call: " + integer);
        });
        factory = LayoutManagers.staggeredGrid(2, LinearLayoutManager.VERTICAL);
//        factory = LayoutManagers.linear(LinearLayoutManager.HORIZONTAL, false);
        cd = new CompositeDisposable();
        items = new LivePagedListBuilder<>(new DataSource.Factory<Integer, Girl>() {
            @Override
            public DataSource<Integer, Girl> create() {
                DataSource<Integer, Girl> dataSource = new LocalCartonDataSource();
                return dataSource;
            }
        }, new PagedList.Config.Builder()
                .setPageSize(20)
                .setPrefetchDistance(5)
                .setEnablePlaceholders(true)
                .setInitialLoadSizeHint(20).build()).build();
    }


    @Override
    protected void onCleared() {
        super.onCleared();
    }

    private void load(final boolean isRefresh) {
        isRefreshing.set(true);
//        final Observable<GirlData> observable = girlService.getGirls(page);
        List<Girl> lists = new LinkedList<>();
//        lists.add(new Girl("1", "http://img4.imgtn.bdimg.com/it/u=221420021,4143011973&fm=200&gp=0.jpg"));

        isRefreshing.set(false);
    }
}
