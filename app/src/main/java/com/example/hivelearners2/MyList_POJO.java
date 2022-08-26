package com.example.hivelearners2;

public class MyList_POJO {
    String title, subTitle, doc_id;

    public MyList_POJO(String title, String subTitle, String doc_id) {
        this.title = title;
        this.subTitle = subTitle;
        this.doc_id = doc_id;
    }

    public String getDoc_id() {
        return doc_id;
    }

    public void setDoc_id(String doc_id) {
        this.doc_id = doc_id;
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


}
