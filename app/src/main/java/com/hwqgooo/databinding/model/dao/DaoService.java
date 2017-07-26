package com.hwqgooo.databinding.model.dao;

import android.content.Context;

import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.rx.RxDao;
import org.greenrobot.greendao.rx.RxQuery;

import java.util.List;

import rx.Observable;

/**
 * Created by weiqiang on 2017/2/8.
 */

public class DaoService {
    public final static String TAG = DaoService.class.getSimpleName();
    RxDao<GalleryORM, String> rxVersionGradeDao;
    RxQuery<GalleryORM> rxVersionGradeQuery;
    DaoSession daoSession;

    static Context context;
    static DaoService daoService;

    public static void initContext(Context context) {
        DaoService.context = context;
        if (context == null) {
            daoService = null;
        }
    }

    public static DaoService getInstance() {
        if (daoService == null) {
            daoService = new DaoService(context);
        }
        return daoService;
    }

    private DaoService(Context context) {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(
                context.getApplicationContext(), "gallery");
        Database db = helper.getWritableDb();
        daoSession = new DaoMaster(db).newSession();
        rxVersionGradeDao = daoSession.getGalleryORMDao().rx();
        rxVersionGradeQuery = daoSession.getGalleryORMDao().queryBuilder().rx();
    }

    public Observable<List<GalleryORM>> getCovers() {
//        return Observable.create(new Observable.OnSubscribe<List<GalleryORM>>() {
//            @Override
//            public void call(Subscriber<? super List<GalleryORM>> subscriber) {
//                subscriber.onNext(new LinkedList<GalleryORM>());
//                subscriber.onCompleted();
//            }
//        });
        return rxVersionGradeQuery.list();
    }

    public Observable<GalleryORM> setRead(String url) {
        GalleryORM orm = new GalleryORM();
        orm.setGalleryUrl(url);
        return rxVersionGradeDao.insert(orm);
//        return Observable.create(new Observable.OnSubscribe<GalleryORM>() {
//            @Override
//            public void call(Subscriber<? super GalleryORM> subscriber) {
//                subscriber.onNext(new GalleryORM());
//                subscriber.onCompleted();
//            }
//        });
    }
}
