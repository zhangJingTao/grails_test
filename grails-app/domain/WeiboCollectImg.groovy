class WeiboCollectImg {

    static constraints = {
        downloadDate(nullable: true)
        diskPath(nullable: true)
    }


    String imgUrl
    WeiboCollect collect
    Integer sort
    String diskPath
    Boolean downloaded
    Boolean needDownload
    Date downloadDate


    @Override
    public String toString() {
         return imgUrl
    }
}