package kz.kmg.qorgau.domain.services.glide;

import android.app.Application;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;

import dagger.Module;
import dagger.Provides;


@Module
public class GlideModule {

    @Provides
    static RequestOptions provideRequestOptions() {
        return RequestOptions
                .placeholderOf(null);
                //.error(R.drawable.big_placeholder_sauna);
    }

    @Provides
    static RequestManager provideGlideInstance(Application application, RequestOptions requestOptions) {
        return Glide.with(application)
                .setDefaultRequestOptions(requestOptions);
    }


}
