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
import com.hwqgooo.databinding.model.carton.LocalCartonChapterDataSource;
import com.hwqgooo.databinding.utils.recyclerview.ItemViewArg;
import com.hwqgooo.jetpack.utils.recyclerview.ItemViewClassSelector;
import com.hwqgooo.jetpack.utils.recyclerview.LayoutManagers;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by weiqiang on 2016/7/4.
 */
public class CartonChapterVM extends BaseGirlVM<Girl> {
    public static final String TAG = CartonChapterVM.class.getSimpleName();
    CompositeDisposable subscription;
    int id;

    public CartonChapterVM() {
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
        subscription = new CompositeDisposable();
    }

    public void setId(int id) {
        this.id = id;
        items = new LivePagedListBuilder<>(new DataSource.Factory<Integer, Girl>() {
            @Override
            public DataSource<Integer, Girl> create() {
                DataSource<Integer, Girl> dataSource = new LocalCartonChapterDataSource(id);
                return dataSource;
            }
        }, new PagedList.Config.Builder()
                .setPageSize(20)
                .setPrefetchDistance(5)
                .setEnablePlaceholders(true)
                .setInitialLoadSizeHint(20).build()
        ).build();
    }

    private void load(final boolean isRefresh) {
        isRefreshing.set(true);

        isRefreshing.set(false);
    }
}
