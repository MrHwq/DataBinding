package com.hwqgooo.databinding.model.carton;

import android.arch.paging.PageKeyedDataSource;
import android.support.annotation.NonNull;

import com.hwqgooo.databinding.model.bean.Girl;

import java.util.LinkedList;
import java.util.List;

public class LocalCartonChapterDataSource extends PageKeyedDataSource<Integer, Girl> {
    int id;

    public LocalCartonChapterDataSource(int id) {
        super();
        this.id = id;
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull LoadInitialCallback<Integer, Girl> callback) {
        callback.onResult(getGirls(0), null, 0);
    }

    private List<Girl> getGirls(int page) {
        List<Girl> lists = new LinkedList<>();
        final int size = 8;
        String baseUrl = "http://img.elodm.com/";
        for (int i = 0; i < size; ++i) {
            int index = page * size + i + 1;
            lists.add(new Girl(String.valueOf(page * size + i + 1),
                    baseUrl + "images/mh/data/" + id + "/" + index + "/cover.jpg"));
            System.out.println(lists.get(i));
        }
        return lists;
    }

    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, Girl> callback) {
        System.out.println("=========before:" + (params.key - 1));
        callback.onResult(getGirls(params.key - 1), params.key - 1);
    }

    @Override
    public void loadAfter(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, Girl> callback) {
        System.out.println("=========after:" + (params.key + 1));
        callback.onResult(getGirls(params.key + 1), params.key > 6 ? null : params.key + 1);
    }
}
