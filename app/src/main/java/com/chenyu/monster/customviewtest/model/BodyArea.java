package com.chenyu.monster.customviewtest.model;

import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Region;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenyu on 16/6/22.
 */
public class BodyArea implements Parcelable {
    /**
     * 区域id
     */
    public String areaId;
    /**
     * 区域名称
     */
    public String areaTitle;
    /**
     * 区域介绍
     */
    public String desc;
    /***
     * 区域点坐标
     */
    public int[] pts;
    public List<BodyArea> areas = new ArrayList<BodyArea>();

    public Path path;
    public boolean isRectF;

    public BodyArea() {
    }

    protected BodyArea(Parcel in) {
        areaId = in.readString();
        areaTitle = in.readString();
        desc = in.readString();
        pts = in.createIntArray();
        areas = in.createTypedArrayList(BodyArea.CREATOR);
    }

    public static final Creator<BodyArea> CREATOR = new Creator<BodyArea>() {
        @Override
        public BodyArea createFromParcel(Parcel in) {
            return new BodyArea(in);
        }

        @Override
        public BodyArea[] newArray(int size) {
            return new BodyArea[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(areaId);
        dest.writeString(areaTitle);
        dest.writeString(desc);
        dest.writeIntArray(pts);
        dest.writeTypedList(areas);
    }

    public void setPts(String[] pts) {
        setStrArrayToIntArray(pts);
    }

    private void setStrArrayToIntArray(String[] pts) {
        if (null != pts && 0 != pts.length) {
            int len = pts.length;
            this.pts = new int[len];
            for (int i = 0; i < len; ++i) {
                try {
                    this.pts[i] = Integer.parseInt(pts[i]);
                } catch (Exception e) {
                    this.pts[i] = 0;
                    Log.e("SmartPit", e.getMessage());
                }
            }
        }
    }

    public void setPts(String pts, String split) {
        if (!TextUtils.isEmpty(pts) && !TextUtils.isEmpty(split)) {
            String[] points = pts.split(split);
            setPts(points);
        }
    }

    public CheckArea getCheckArea() {
        CheckArea checkArea = null;
        if (pts != null) {
            checkArea = new CheckArea(pts);
        }
        return checkArea;
    }

    /**
     * 写内部类的原因在于在Activity之间传递时，
     * 不能传未实现Serializable接口的类
     */
    public class CheckArea {
        private final Path path;
        //当前处理是从点的个数来判断是矩形 还是多边形,这两种的方式对点的位置判断不太一样
        private final boolean isRectF;

        private CheckArea(int[] pts) {
            this.path = new Path();
            int len = pts.length;
            isRectF = len == 4;
            Log.e("TAG", "len================" + len);
            for (int i = 0; i < len; ) {
                if (i == 0) {
                    this.path.moveTo(pts[i++], pts[i++]);
                } else {
                    this.path.lineTo(pts[i++], pts[i++]);
                }
            }
            this.path.close();
        }

        public Path getPath() {
            return this.path;
        }

        /**
         * 检测是否在区域范围内
         *
         * @param rectf 从外部传可以重用
         * @param x
         * @param y
         * @return
         */
        public boolean isInArea(RectF rectf, float x, float y) {
            boolean resStatus = false;
            if (this.path != null) {
                rectf.setEmpty();
                path.computeBounds(rectf, true);
                if (isRectF) {
                    //当是矩形时
                    resStatus = rectf.contains(x, y);
                } else {
                    //如果是多边形时
                    Region region = new Region();
                    region.setPath(path, region);
                    region.setPath(path, new Region((int) rectf.left, (int) rectf.top, (int) rectf.right, (int) rectf.bottom));
                    resStatus = region.contains((int) x, (int) y);
                }
            }
            return resStatus;
        }
    }
}
