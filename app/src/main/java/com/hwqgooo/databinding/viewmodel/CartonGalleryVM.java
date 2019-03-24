package com.hwqgooo.databinding.viewmodel;

import android.arch.paging.DataSource;
import android.arch.paging.LivePagedListBuilder;
import android.arch.paging.PagedList;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;

import com.hwqgooo.databinding.BR;
import com.hwqgooo.databinding.R;
import com.hwqgooo.databinding.command.ReplyCommand;
import com.hwqgooo.databinding.model.carton.LocalCartonGalleryDataSource;
import com.hwqgooo.databinding.utils.recyclerview.ItemViewArg;
import com.hwqgooo.jetpack.utils.recyclerview.ItemViewClassSelector;
import com.hwqgooo.jetpack.utils.recyclerview.LayoutManagers;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by weiqiang on 2016/7/4.
 */
public class CartonGalleryVM extends BaseGirlVM<String> {
    public static final String TAG = CartonGalleryVM.class.getSimpleName();
    CompositeDisposable cd;

    int id;
    int chapter;

    public CartonGalleryVM() {
        itemView = ItemViewArg.of(ItemViewClassSelector.builder()
                .put(String.class, BR.girl, R.layout.item_carton)
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
        factory = LayoutManagers.linear(LinearLayoutManager.VERTICAL, false);
        cd = new CompositeDisposable();
    }

    public void setGallery(int id, int chapter) {
        this.id = id;
        items = new LivePagedListBuilder<>(new DataSource.Factory<Integer, String>() {
            @Override
            public DataSource<Integer, String> create() {
                DataSource<Integer, String> dataSource = new LocalCartonGalleryDataSource(id, chapter);
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
//        isRefreshing.set(true);
//        List<String> lists = new LinkedList<>();
//        for (int i = 0; i < 20; ++i) {
//            lists.add(baseUrl + "images/mh/data/" + id + "/" + chapter + "/" + page + ".jpg");
//            ++page;
//        }
//        isRefreshing.set(false);
    }
}
