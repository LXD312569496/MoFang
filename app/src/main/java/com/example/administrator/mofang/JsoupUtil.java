package com.example.administrator.mofang;

import android.content.Context;
import android.util.Log;

import com.example.administrator.mofang.common.HttpUtil;
import com.example.administrator.mofang.query.competitor.Competitor;
import com.example.administrator.mofang.query.competitor.SpinnerBean;
import com.example.administrator.mofang.query.match.Match;
import com.example.administrator.mofang.query.rank.Rank;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by Administrator on 2017/1/24.
 */

public class JsoupUtil {

    private static Context mContext;
    private static OkHttpClient client;
    private static Retrofit retrofit;
    private static MoFangInterface mInterface;

    private static String BASE_URL = "https://cubingchina.com";

    public static void getInstance(Context context) {
        mContext = context;

        client = new OkHttpClient.Builder()
                .cache(new Cache(new File(mContext
                        .getCacheDir().getAbsolutePath(), "CaseCache"), 20 * 1024 * 1024))
                .addInterceptor(interceptor)
                .addNetworkInterceptor(interceptor)
                .build();

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        mInterface = retrofit.create(MoFangInterface.class);
    }


    public static OkHttpClient getOkHttpClient() {
        return client;
    }


    private static Interceptor interceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            if (HttpUtil.isNetWorkConnected()) {
                //网络已连接

            } else {
                //网络未连接,获取缓存数据
                //在20秒缓存有效，此处测试用，实际根据需求设置具体缓存有效时间
//                CacheControl cacheControl = new CacheControl.Builder()
//                        .maxStale(20, TimeUnit.SECONDS)
//                        .build();
                request = request.newBuilder()
                        .cacheControl(CacheControl.FORCE_CACHE)
                        .url(request.url())
                        .build();
            }
            Response response = chain.proceed(request);
            if (HttpUtil.isNetWorkConnected()) {
                int maxAge = 60 * 60; // read from cache for 1 minute
                response.newBuilder()
                        .removeHeader("Pragma")
                        .header("Cache-Control", "public, max-age=" + maxAge)
                        .build();
            } else {
                int maxStale = 15 * 60 * 24 * 8; // tolerate 2 -day stale
                response.newBuilder()
                        .removeHeader("Pragma")
                        .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                        .build();
            }
            return response;
        }
    };


    public static MoFangInterface getInterFace() {
        return mInterface;
    }


    public static List<Match> getMatch(String result) {
        List<Match> list = new ArrayList<>();
        Document document = Jsoup.parse(result);

        //抓取未过期的比赛
        Elements links = document.select("div.table-responsive").select("tbody").select("tr");
        for (Element e : links) {
            String url = e.select("a").attr("href");
            String date = e.select("td").first().text();
            String name = e.select("a").text();

            StringBuilder isDanger = new StringBuilder();
            Boolean isOut;


            if (e.attr("class").equals("warning") || e.attr("class").equals("danger") || e.attr("class").equals("info")) {
                //说明未过期
                isOut = false;
                if (e.attr("class").equals("warning") || e.attr("class").equals("danger")) {
                    Log.d("test",e.toString());
                    String day = e.select("div.text.days").text();
                    String hours = e.select("div.text.hours").text();
                    isDanger.append(day + "天" + hours + "小时");

                }
                Log.d("test", isDanger.toString());

            } else {
                isOut = true;
            }
            list.add(new Match(url, date, name, isOut, isDanger.toString()));
        }
        //抓取今天正在进行的比赛
//        Elements links1 = document.select("tr.success");
//        for (Element e : links1) {
//            Log.d("test", e.toString());
//            String url = e.select("a").attr("href");
//            String date = e.select("td").first().text();
//            String name = e.select("a").text();
//            Boolean isOut = false;
//            list.add(new Match(url, date, name, isOut));
//        }

        //抓取过期的比赛
//        Elements links2 = document.select("tr.active");
//        for (Element e : links2) {
////            Log.d("test", e.toString());
//            String url = e.select("a").attr("href");
//            String date = e.select("td").first().text();
//            String name = e.select("a").text();
//            Boolean isOut = true;
//            list.add(new Match(url, date, name, isOut));
//        }

        return list;
    }


    //获取选手的列表
    public static List<Competitor> getCompetitor(String result) {
        List<Competitor> list = new ArrayList<>();
        Document document = Jsoup.parse(result);

        Elements links = document.select("table.table.table-bordered.table-condensed.table-hover.table-boxed")
                .select("tbody").select("tr");

        for (Element e : links) {
//            Log.d("test", e.toString());
            String name = e.select("a").first().text();
            String url = "https://cubingchina.com" + e.select("a").first().attr("href");
            String wcaId = e.select("td.region").first().text();
            String country = e.select("td.region").get(1).text();
            String cnuntryPic = e.select("td.region").get(1).select("img").attr("src");

            list.add(new Competitor(url, name, wcaId, country, cnuntryPic));
        }
        return list;
    }

    //获取所有的地区，用于spinner下拉列表显示
    public static List<SpinnerBean> getRegion(String result) {
        List<SpinnerBean> list = new ArrayList<>();
        Document document = Jsoup.parse(result);
        Element link = document.select("select.form-control").first();

        list.add(new SpinnerBean(link.select("option").first().attr("value"), link.select("option").first().text()));

        Elements links = link.select("optgroup");
        for (Element e : links) {
            String name = e.attr("label");

            list.add(new SpinnerBean("", name));

            Elements countrys = e.select("option");
            for (Element e1 : countrys) {
                list.add(new SpinnerBean(e1.attr("value"), e1.text()));
            }
        }

        return list;
    }

    //获取魔方类型，用于spinner下拉列表
    public static List<SpinnerBean> getType(String result) {
        List<SpinnerBean> list = new ArrayList<>();
        Document document = Jsoup.parse(result);

        Elements links = document.select("select#event.form-control").select("option");

        for (Element e : links) {
            String value = e.attr("value");
            String name = e.text();
            list.add(new SpinnerBean(value, name));
        }
        return list;
    }


    //获取排名的列表
    public static List<Rank> getRankList(String result) {
        List<Rank> list = new ArrayList<>();
        Document document = Jsoup.parse(result);

        Elements links = document.select("table.table.table-bordered.table-condensed.table-hover.table-boxed")
                .select("tbody").select("tr");

        for (Element e : links) {
//            Log.d("test", e.toString());
            String name = e.select("a").first().text();
            String url = "https://cubingchina.com" + e.select("a").first().attr("href");
            String country = e.select("td.region").first().text();
            String cnuntryPic = e.select("td.region").first().select("img").attr("src");
            String score = e.select("td").get(4).text();

            list.add(new Rank(url, name, cnuntryPic, country, score));
        }

        return list;
    }


//    /**
//     * 要先抓取公式的类型,比如oll,pll
//     */
//    public static List<SpinnerBean> getCaseSpinner(){
//        List<SpinnerBean> list=new ArrayList<>();
//
//
//
//        return list;
//    }


}
