package commonadapter.sample;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.ListView;

import com.squareup.picasso.Picasso;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import commonadapter.library.DefaultAdapter;
import commonadapter.library.DefaultViewHolder;
import commonadapter.library.DefaultViewHolder.ImageBinder;

public class MainActivity extends AppCompatActivity {

    private String[] imgSites = {
            "http://image.baidu.com/",
            "http://www.22mm.cc/",
            "http://www.moko.cc/",
            "http://eladies.sina.com.cn/photo/",
            "http://www.youzi4.com/"
    };

    private DefaultAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView lv_main = (ListView) findViewById(R.id.lv_main);

        for (String url : imgSites) {
            loadImgList(url);
        }

        adapter = new DefaultAdapter<String>(this, new ArrayList<String>(), R.layout.item_simple) {

            private ImageBinder mImageBinder = new ImageBinder() {

                private ImageOptions mImageOptions = new ImageOptions.Builder()
                        .setSize(300 / 3, 168 / 3)
                        .setLoadingDrawableId(R.mipmap.ic_launcher)
                        .setFailureDrawableId(R.mipmap.ic_launcher)
                        .setImageScaleType(ScaleType.CENTER_CROP)
                        //.setFadeIn(true)
                        .build();

                @Override
                public void bind(@NonNull ImageView imageView, @NonNull String url) {
                    //x.image().bind(imageView, url, mImageOptions);
                    Picasso.with(imageView.getContext())
                            .load(url)
                            .placeholder(R.mipmap.ic_launcher)
                            .error(R.mipmap.ic_launcher)
                            .resize(300 / 3, 168 / 3)
                            //.fit()
                            .into(imageView);
                }
            };

            @SuppressLint("DefaultLocale")
            @Override
            protected void convert(DefaultViewHolder holder, int position) {
                String url = getItem(position);
                holder.bindImage(R.id.iv_icon, url, mImageBinder)
                        .setText(R.id.tv_num, url);
            }
        };

        if (lv_main != null) {
            lv_main.setAdapter(adapter);
        }
    }

    private void loadImgList(String url) {
        x.http().get(new RequestParams(url), new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                adapter.addAll(getImgSrcList(result));
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
            }

            @Override
            public void onCancelled(CancelledException cex) {
            }

            @Override
            public void onFinished() {
            }
        });
    }

    /**
     * 得到网页中图片的地址
     */
    public static List<String> getImgSrcList(String htmlStr) {
        List<String> pics = new ArrayList<>();
        String regEx_img = "<img.*?src=\"http://(.*?).jpg\""; // 图片链接地址
        Pattern p_image = Pattern.compile(regEx_img, Pattern.CASE_INSENSITIVE);
        Matcher m_image = p_image.matcher(htmlStr);
        while (m_image.find()) {
            String src = m_image.group(1);
            if (src.length() < 100) {
                pics.add("http://" + src + ".jpg");
            }
        }
        return pics;
    }

}
