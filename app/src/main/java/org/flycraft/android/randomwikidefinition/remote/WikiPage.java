package org.flycraft.android.randomwikidefinition.remote;

import android.os.Parcel;
import android.os.Parcelable;

public class WikiPage implements Parcelable {

    private long id;
    private String title;
    private String extract;

    public WikiPage(long id, String title, String extract) {
        this.id = id;
        this.title = title;
        this.extract = extract;
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getExtract() {
        return extract;
    }

    @Override
    public String toString() {
        return "{" + id + "/" + title + "/" + extract.substring(0, extract.length() / 6) + "}";
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeString(this.title);
        dest.writeString(this.extract);
    }

    protected WikiPage(Parcel in) {
        this.id = in.readLong();
        this.title = in.readString();
        this.extract = in.readString();
    }

    public static final Parcelable.Creator<WikiPage> CREATOR = new Parcelable.Creator<WikiPage>() {
        public WikiPage createFromParcel(Parcel source) {
            return new WikiPage(source);
        }

        public WikiPage[] newArray(int size) {
            return new WikiPage[size];
        }
    };
}
