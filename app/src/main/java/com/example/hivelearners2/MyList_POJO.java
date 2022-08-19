package com.example.hivelearners2;

public class MyList_POJO {
    String title, subTitle, pushKey;

    public MyList_POJO(String title, String subTitle, String pushKey) {
        this.title = title;
        this.subTitle = subTitle;
        this.pushKey = pushKey;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public String getPushKey() {
        return pushKey;
    }

    public void setPushKey(String pushKey) {
        this.pushKey = pushKey;
    }
}
