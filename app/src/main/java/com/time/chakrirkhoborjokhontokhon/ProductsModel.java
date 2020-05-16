package com.time.chakrirkhoborjokhontokhon;

public class ProductsModel {
    private String name;
    private String price;
    private String lastD;
    private String quan;
    private String des;
    private String source;
    private String link;
    private boolean expanded;

    public ProductsModel() {
    }

    public boolean isExpanded() {
        return expanded;
    }

    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }

    public String getLastD() {
        return lastD;
    }

    public void setLastD(String lastD) {
        this.lastD = lastD;
    }

    public String getQuan() {
        return quan;
    }

    public void setQuan(String quan) {
        this.quan = quan;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public ProductsModel(String name, String price, String des, String lastD, String quan, String source) {
        this.name = name;
        this.price = price;
        this.des = des;
        this.lastD = lastD;
        this.quan = quan;
        this.source = source;
        this.link = link;
        this.expanded = false;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }


}
