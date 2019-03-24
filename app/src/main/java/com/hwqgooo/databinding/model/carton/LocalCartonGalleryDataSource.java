package com.hwqgooo.databinding.model.carton;

import android.arch.paging.PageKeyedDataSource;
import android.support.annotation.NonNull;

import java.util.LinkedList;
import java.util.List;

public class LocalCartonGalleryDataSource extends PageKeyedDataSource<Integer, String> {
    int id;
    int chapter;

    public LocalCartonGalleryDataSource(int id, int chapter) {
        super();
        this.id = id;
        this.chapter = chapter;
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull LoadInitialCallback<Integer, String> callback) {
        callback.onResult(getGirls(0), null, 0);
    }

    private List<String> getGirls(final int page) {
        List<String> lists = new LinkedList<>();
        final int size = 8;
        String baseUrl = "http://img.elodm.com/";
        for (int i = 0; i < size; ++i) {
            int index = page * size + i;
            lists.add(baseUrl + "images/mh/data/" + id + "/" + chapter + "/" + index + ".jpg");
            System.out.println(lists.get(i));
        }
        return lists;
    }

    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, String> callback) {
        System.out.println("=========before:" + (params.key - 1));
        callback.onResult(getGirls(params.key - 1), params.key - 1);
    }

    @Override
    public void loadAfter(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, String> callback) {
        System.out.println("=========after:" + (params.key + 1));
        callback.onResult(getGirls(params.key + 1), params.key > 6 ? null : params.key + 1);
    }
}
