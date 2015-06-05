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

    getWeibo()
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
    /*bind onclick*/
    $(".nav>li").bind("click", function () {
        $($(this).parent("ul")).find("li").removeClass("active");
        $(this).addClass("active");
        $("#scope").val(this.id.replace("nav", ""))
        getWeibo();
    })
})

/*
* required:http://slidesjs.com/
*/
function getWeibo() {
    var unInitSlide = []
    var cssArray = ['danger', 'info', 'warning']
    var contentTemplate = ' <div class="bs-callout bs-callout-{css}" id="callout-buttons-state-names"><h4>{author}:<a class="anchorjs-link" href="#" title="{title}"><span class="anchorjs-icon"></span></a></h4><p>{content}</p>'
    /*图片轮播*/
    contentTemplate += '<div id="slides{weiboId}">{imgs}</div>'
    /*原微博*/
    contentTemplate += '{retweeted_status}'
    /*发布时间行*/
    contentTemplate += '<p class="postTime">@&nbsp{sendTime}&nbsp&nbspby&nbsp{channel}</p>'
    /*点赞评论行*/
    contentTemplate += '<p><a href="#" title="转发数"> <span class="badge badge-success">{reposts_count}</span></a>&nbsp<a href="#" title="评论数"><span class="badge badge-warning">{comments_count}</span></a><a href="#" title="点赞数">&nbsp<span class="badge badge-inverse">{attitudes_count}</span></a></p>'
    /*结尾*/
    contentTemplate += '</div>'
    var url = "/weibo/getWeiboLine?feature=" + $("#scope").val();
    $.getJSON(url, function (data) {
        if (data.status == -1) {
            $.globalMessenger().post({
                message: '访问微博服务失败.',
                showCloseButton: true,
                id: "Only-one-message"
            });
        } else if (data.status == 0) {
            $.globalMessenger().post({
                message: '授权不通过，请<a href="/weibo/oauth">重新授权</a>',
                showCloseButton: true,
                id: "Only-one-message"
            });
        } else {
            var html = ""
            /*随机起始位置*/
            var count = 0 + parseInt(Math.random()*10)
            $.each(data.weibos, function (index, ele) {
                var h = contentTemplate.replace("{content}", ele.text)
                    .replace("{css}", cssArray[count % 3])
                    .replace("{author}", ele.user.name)
                    .replace("{title}", ele.user.verified_reason)
                    .replace("{reposts_count}", ele.reposts_count + "")
                    .replace("{comments_count}", ele.comments_count + "")
                    .replace("{attitudes_count}", ele.attitudes_count + "")
                    .replace("{sendTime}", new Date(ele.created_at).format("yyyy/MM/dd hh:mm:ss"))
                    .replace("{channel}", ele.source + "");
                if(ele.original_pic){//有大图
                    h = h.replace("{weiboId}",ele.id)
                        .replace("{imgs}",'<img src="'+ele.original_pic+'">');
                    unInitSlide.push("weiboId"+ele.id)
                }else{
                    h = h.replace('<div id="slides{weiboId}">{imgs}</div>','')
                }
                if(ele.retweeted_status){//有原微博
                    var retweeted = ele.retweeted_status
                    var retweetedContent = contentTemplate.replace("{content}", retweeted.text)
                        .replace("{css}", '')
                        .replace("{author}", retweeted.user.name)
                        .replace("{title}", retweeted.user.verified_reason)
                        .replace("{reposts_count}", retweeted.reposts_count + "")
                        .replace("{comments_count}", retweeted.comments_count + "")
                        .replace("{attitudes_count}", retweeted.attitudes_count + "")
                        .replace("{sendTime}", new Date(retweeted.created_at).format("yyyy/MM/dd hh:mm:ss"))
                        .replace("{channel}", retweeted.source + "")
                        .replace('<div id="slides{weiboId}">{imgs}</div>','')
                        .replace('{retweeted_status}','')
                        .replace('<h4>','<h5>')
                        .replace('</h4>','</h5>');
                    h = h.replace('{retweeted_status}',retweetedContent)
                }else{
                    h = h.replace('{retweeted_status}','')
                }
                html += h
                count++
            })
            $("#list").html(html)
            /*init slide*/
            if(unInitSlide){
                for(var i=0;i<unInitSlide.length;i++){
                    $(unInitSlide[i]).slidesjs({
                        width: 700,
                        height: 528
                    });
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