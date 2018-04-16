package com.mbhonsle.batchresttemplate;

import android.util.Log;

import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class RestTemplateCollection extends RestTemplate {

    private List<String> urls = new ArrayList<>();
    private int batchSize = 10;

    public RestTemplateCollection() {
        super();
    }

    public RestTemplateCollection(ClientHttpRequestFactory requestFactory) {
        super(requestFactory);
    }

    public RestTemplateCollection(ClientHttpRequestFactory requestFactory, List<String> urls) {
        super(requestFactory);
        this.urls = urls;
    }

    public RestTemplateCollection(List<HttpMessageConverter<?>> messageConverters) {
        super(messageConverters);
    }

    public RestTemplateCollection(List<HttpMessageConverter<?>> messageConverters, List<String> urls) {
        super(messageConverters);
        this.urls = urls;
    }

    public void addAll(List<String> urls) {
        this.urls.addAll(urls);
    }

    public void setBatchSize(int batchSize) {
        this.batchSize = batchSize;
    }

    private <T> Observable<T> getObject(String url, Class<T> responseType) throws RestClientException {
        return Observable.just(getForObject(url, responseType));
    }

    /**
     * Returns a collection of REST call return values
     * @param responseType: required return type
     * @param <T>: return type
     * @return List<T> a list of objects of type responseType
     */
    public <T> List<T> getAll(final Class<T> responseType) {
        List<T> result = new ArrayList<>(this.urls.size());
        for (String url : this.urls) {
            getObject(url, responseType)
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<T>() {
                        @Override
                        public void onSubscribe(Disposable d) {
                            Log.w("RestTemplateCollection", "Not Implemented");
                        }

                        @Override
                        public void onNext(T t) {
                            result.add(t);
                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.e("RestTemplateCollection", e.getMessage(), e);
                        }

                        @Override
                        public void onComplete() {
                            Log.w("RestTemplateCollection", "Not Implemented");
                        }
                    });
        }
        return result;
    }
}
