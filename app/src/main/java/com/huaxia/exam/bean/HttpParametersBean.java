package com.huaxia.exam.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.HashMap;

public class HttpParametersBean implements Parcelable {

    //进行网络请求的 Url
    private String url;

    //进行网络请求的参数
    private HashMap<String, String> map;

    //进行网络请求的类型
    private int type;  //1=get  2=post

    //本次任务的名称  便于标识
    private String name;

    //网络请求后  成功或者失败的状态码
    private int status;

    //成功或者失败后返回的String
    private String resultValue;


    public HttpParametersBean() {
    }


    @Override
    public String toString() {
        return "HttpParametersBean{" +
                "url='" + url + '\'' +
                ", map=" + map +
                ", type=" + type +
                ", name='" + name + '\'' +
                ", status=" + status +
                ", resultValue='" + resultValue + '\'' +
                '}';
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public HashMap<String, String> getMap() {
        return map;
    }

    public void setMap(HashMap<String, String> map) {
        this.map = map;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getResultValue() {
        return resultValue;
    }

    public void setResultValue(String resultValue) {
        this.resultValue = resultValue;
    }

    public HttpParametersBean(String url, HashMap<String, String> map, int type, String name, int status, String resultValue) {
        this.url = url;
        this.map = map;
        this.type = type;
        this.name = name;
        this.status = status;
        this.resultValue = resultValue;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.url);
        dest.writeSerializable(this.map);
        dest.writeInt(this.type);
        dest.writeString(this.name);
        dest.writeInt(this.status);
        dest.writeString(this.resultValue);
    }

    protected HttpParametersBean(Parcel in) {
        this.url = in.readString();
        this.map = (HashMap<String, String>) in.readSerializable();
        this.type = in.readInt();
        this.name = in.readString();
        this.status = in.readInt();
        this.resultValue = in.readString();
    }

    public static final Creator<HttpParametersBean> CREATOR = new Creator<HttpParametersBean>() {
        @Override
        public HttpParametersBean createFromParcel(Parcel source) {
            return new HttpParametersBean(source);
        }

        @Override
        public HttpParametersBean[] newArray(int size) {
            return new HttpParametersBean[size];
        }
    };
}
