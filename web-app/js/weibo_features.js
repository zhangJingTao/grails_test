var max = 10//分页大小
$(document).ready(function () {
    var intervalId = window.setInterval(function () {
        var width = parseInt(100 * parseInt($(".progress-bar-warning").css("width")) / parseInt($("#loadingProgress").css("width"))) + 100
        $(".progress-bar-warning").css("width", width + "%")
        if (width > 120) {
            $("#loadingProgress").hide()
            $("#list").show()
            window.clearInterval(intervalId)
        }
    }, 1000)

    var navigation = responsiveNav("#nav", {customToggle: "#nav-toggle"});

    $._messengerDefaults = {
        extraClasses: 'messenger-fixed messenger-theme-future messenger-on-bottom'
    }

    getFeatures(1)
    var speed = 1000;
    $('#list').masonry({
        itemSelector: '.content',
        columnWidth: 200,
        animate: true,
        animationOptions: {
            duration: speed,
            queue: false
        }
    })
})

/*
* required:http://slidesjs.com/
*/
function getFeatures(page) {
    var unInitSlide = []
    var cssArray = ['danger', 'info', 'warning']
    var contentTemplate = ' <div class="bs-callout bs-callout-{css}" id="callout-buttons-state-names"><h4>{author}:<a class="anchorjs-link" href="#"><span class="anchorjs-icon"></span></a></h4><p>{content}</p>'
    /*图片轮播*/
    contentTemplate += '<div id="weiboId{weiboId}" class="banner">{imgs}</div>'
    /*原微博*/
    contentTemplate += '{retweeted_status}'
    /*发布时间行*/
    contentTemplate += '<p class="postTime">@&nbsp{sendTime}</p>'
    /*@内容*/
    contentTemplate += '<p class="comment" title="{comment}">收藏于{commentDate}</p>'
    /*结尾*/
    contentTemplate += '</div>'
    var offset = max*(page-1)
    var url = "/weibo/getFeatures?offset="+offset+"&max="+max;
    $.getJSON(url, function (data) {
        if (data.status == -1) {
            $.globalMessenger().post({
                message: data.msg,
                showCloseButton: true,
                id: "Only-one-message"
            });
        }else {
            initPage("page",data.count,page,max,"getFeatures")

            $("#total").val(data.count)
            var html = ""
            /*随机起始位置*/
            var count = 0 + parseInt(Math.random()*10)
            $.each(data.list, function (index, ele) {
                var h = contentTemplate.replace("{content}", ele.text)
                    .replace("{css}", cssArray[count % 3])
                    .replace("{author}", ele.weiboUserName)
                    .replace("{sendTime}", new Date(ele.weiboCreateDate).format("yyyy/MM/dd hh:mm:ss"))
                    .replace("{commentDate}",new Date(ele.commentDate).format("yyyy/MM/dd hh:mm:ss"))
                    .replace("{comment}",ele.comments);
                if(!ele.reWeiboId&&ele.imgs.length>0){//有图
                    var pics = ele.imgs
                    var picHtml = '<ul class="items">';
                    for(var p = 0;p<pics.length;p++){
                        var picUrl = pics[p].imgUrl
                        picHtml += '<li><a href="'+picUrl+'" target="_blank"><img src="'+picUrl+'"/></a></li>'
                    }
                    picHtml += '</ul>'
                    h = h.replace("{weiboId}",ele.id)
                        .replace("{imgs}",picHtml);
                    unInitSlide.push("weiboId"+ele.id)
                }else{
                    h = h.replace('<div id="weiboId{weiboId}" class="banner">{imgs}</div>','')
                }
                if(ele.reWeiboId){//有原微博
                   var retweetedContent = contentTemplate.replace("{content}", ele.reText)
                        .replace("{css}", '')
                        .replace("{author}", ele.reWeiboUserName)
                        .replace("{sendTime}", new Date(ele.reWeiboCreateDate).format("yyyy/MM/dd hh:mm:ss"))
                        .replace('{retweeted_status}','')
                        .replace('<h4>','<h5>')
                        .replace('</h4>','</h5>')
                        .replace('<p class="comment" title="{comment}">收藏于{commentDate}</p>','');
                    if(ele.imgs.length>0){//有大图
                        var pics = ele.imgs
                        var picHtml = '<ul class="items">';
                        for(var p = 0;p<pics.length;p++){
                            var picUrl = pics[p].imgUrl
                            picHtml += '<li><a href="'+picUrl+'" target="_blank"><img src="'+picUrl+'"/></a></li>'
                        }
                        picHtml += '</ul>'
                        retweetedContent = retweetedContent.replace("{weiboId}",retweeted.id)
                            .replace("{imgs}",picHtml);
                        unInitSlide.push("weiboId"+retweeted.id)
                    }else{
                        retweetedContent = retweetedContent.replace('<div id="weiboId{weiboId}" class="banner">{imgs}</div>','')
                    }
                    h = h.replace('{retweeted_status}',retweetedContent)
                }else{
                    h = h.replace('{retweeted_status}','')
                }
                html += h
                count++
            })
            $("#list").html(html)
            $("#list").removeAttr("style")
            /*init slide*/
            if(unInitSlide){
                for(var i=0;i<unInitSlide.length;i++){

                }
            }
        }
    });
}



Date.prototype.format = function (format) {
    var o = {
        "M+": this.getMonth() + 1, //month
        "d+": this.getDate(), //day
        "h+": this.getHours(), //hour
        "m+": this.getMinutes(), //minute
        "s+": this.getSeconds(), //second
        "q+": Math.floor((this.getMonth() + 3) / 3), //quarter
        "S": this.getMilliseconds() //millisecond
    }

    if (/(y+)/.test(format)) {
        format = format.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    }

    for (var k in o) {
        if (new RegExp("(" + k + ")").test(format)) {
            format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k] : ("00" + o[k]).substr(("" + o[k]).length));
        }
    }
    return format;
}
/**
 * 初始化页面翻页控件
 * @param id
 * @param total  总数
 * @param page  当前第几页
 * @param size  每页大小
 */
function initPage(id,total,page,size,method){
    var template = '<nav><ul class="pager"><li class="previous {predis}" onclick="{preMethod}"><a href="#'+id+'"><span aria-hidden="true">&larr;</span> Older</a></li>'
    template += '<li class="next {nextdis}" onclick="{nextMethod}"><a href="#'+id+'">Newer <span aria-hidden="true">&rarr;</span></a></li>'
    template += '</ul></nav>'
    var isFirst = page==1? true:false
    var isLast = page*size>total? true:false
    var preMethod = isFirst? "":method+"("+(page-1)+")"
    var nextMethod = isLast? "":method+"("+(page+1)+")"
    var html = template.replace("{preMethod}",preMethod)
        .replace("{nextMethod}",nextMethod);
    if(isFirst) html = html.replace("{predis}","disabled")
    if(isLast) html = html.replace("{nextdis}","disabled")
    $("#"+id).html(html);
}