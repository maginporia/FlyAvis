package com.flyavis.android.data;

import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.annotation.WorkerThread;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

// ResultType: Type for the Resource data.
// RequestType: Type for the API response.
public abstract class NetworkBoundResource<ResultType, RequestType> {
    private Flowable<Resource<ResultType>> result;

    @MainThread
    NetworkBoundResource() {
        Flowable<Resource<ResultType>> source;
        if (shouldFetch()) {
            source = createCall()
                    .doOnNext(this::saveCallResult)
                    .flatMap(apiResponse -> loadFromDb().toObservable().map(Resource::success))
                    .doOnError(this::onFetchFailed)
                    .onErrorResumeNext(t -> {
                        return loadFromDb()
                                .toObservable()
                                .map(Resource::success);

                    })
                    .toFlowable(BackpressureStrategy.LATEST);
        } else {
            source = loadFromDb()
                    .subscribeOn(Schedulers.io())
                    .map(Resource::success);
        }
        result = Flowable.concat(loadFromDb()
                        .map(Resource::loading)
                        .take(1),
                source)
                .subscribeOn(Schedulers.io());
    }

    // Called to save the result of the API response into the database.
    @WorkerThread
    protected abstract void saveCallResult(@NonNull RequestType item);

    // Called with the data in the database to decide whether to fetch
    // potentially updated data from the network.
    @MainThread
    protected abstract boolean shouldFetch();

    // Called to get the cached data from the database.
    @NonNull
    @MainThread
    protected abstract Flowable<ResultType> loadFromDb();

    // Called to create the API call.
    @NonNull
    @MainThread
    protected abstract Observable<RequestType> createCall();

    // Called when the fetch fails. The child class may want to reset components
    // like rate limiter.
    @MainThread
    protected void onFetchFailed(Throwable t) {
        Timber.e(t);
    }

    // Returns a LiveData object that represents the resource that's implemented
    // in the base class.
    public final LiveData<Resource<ResultType>> getAsLiveData() {
        return LiveDataReactiveStreams.fromPublisher(result);
    }
}
