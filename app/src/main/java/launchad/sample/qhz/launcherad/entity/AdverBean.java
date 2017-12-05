package launchad.sample.qhz.launcherad.entity;

import java.io.Serializable;

/**
 * Created by redapplenet on 2017/12/1.
 */

public class AdverBean implements Serializable {
    private String advertUrl;// 广告资源地址
    private String advertType;//广告类型

    public AdverBean() {
    }

    public String getAdvertUrl() {
        return advertUrl;
    }

    public void setAdvertUrl(String advertUrl) {
        this.advertUrl = advertUrl;
    }

    public String getAdvertType() {
        return advertType;
    }

    public void setAdvertType(String advertType) {
        this.advertType = advertType;
    }
}
