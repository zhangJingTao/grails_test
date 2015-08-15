<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">
    <title>贴吧内容抓取</title>
    <!-- jQuery -->
    <script src="/main/js/jquery.js"></script>
    <script src="/main/js/bootstrap.min.js"></script>
    <!-- Bootstrap Core CSS -->
    <link href="/main/css/bootstrap.min.css" rel="stylesheet">
    <script type="text/javascript">
        $(function () {
            $('#container').highcharts({
                chart: {
                    type: 'spline'
                },
                title: {
                    text: '当前贴吧爬虫抓取效率'
                },
                subtitle: {
                    text: '以小时为单位'
                },
                xAxis: {
                    type: 'datetime',
                    labels: {
                        overflow: 'justify'
                    }
                },
                yAxis: {
                    title: {
                        text: 'items'
                    },
                    min: 0,
                    minorGridLineWidth: 0,
                    gridLineWidth: 0,
                    alternateGridColor: null,
                    plotBands: [{ // Light air
                        from: 0*60,
                        to: 1*60,
                        color: 'rgba(68, 170, 213, 0.1)',
                        label: {
                            text: 'Sleeping',
                            style: {
                                color: '#606060'
                            }
                        }
                    }, { // Light breeze
                        from: 1*60,
                        to: 20*60,
                        color: 'rgba(0, 0, 0, 0)',
                        label: {
                            text: 'Thread Too Many',
                            style: {
                                color: '#606060'
                            }
                        }
                    }, { // Gentle breeze
                        from: 20*60,
                        to: 80*60,
                        color: 'rgba(68, 170, 213, 0.1)',
                        label: {
                            text: 'Insufficient Memory',
                            style: {
                                color: '#606060'
                            }
                        }
                    }, { // Moderate breeze
                        from: 80*60,
                        to: 200*60,
                        color: 'rgba(0, 0, 0, 0)',
                        label: {
                            text: 'Single Thread',
                            style: {
                                color: '#606060'
                            }
                        }
                    }, { // Fresh breeze
                        from: 200*60,
                        to: 400*60,
                        color: 'rgba(68, 170, 213, 0.1)',
                        label: {
                            text: 'Need GC',
                            style: {
                                color: '#606060'
                            }
                        }
                    }, { // Strong breeze
                        from: 400*60,
                        to: 800*60,
                        color: 'rgba(0, 0, 0, 0)',
                        label: {
                            text: 'Well',
                            style: {
                                color: '#606060'
                            }
                        }
                    }, { // High wind
                        from: 800*60,
                        to: 1000*60,
                        color: 'rgba(68, 170, 213, 0.1)',
                        label: {
                            text: 'Good Enoung',
                            style: {
                                color: '#606060'
                            }
                        }
                    },{ // High wind
                        from: 1000*60,
                        to: 3000*60,
                        color: 'rgba(0, 0, 0, 0)',
                        label: {
                            text: 'Excellent',
                            style: {
                                color: '#606060'
                            }
                        }
                    }]
                },
                tooltip: {
                    valueSuffix: ' pieces/hour'
                },
                plotOptions: {
                    spline: {
                        lineWidth: 4,
                        states: {
                            hover: {
                                lineWidth: 5
                            }
                        },
                        marker: {
                            enabled: false
                        },
                        pointInterval: 3600000, // one Hour
                        pointStart: Date.UTC(2015,new Date().getMonth(),new Date().getDate(), 0, 0, 0)
                    }
                },
                series: [{
                    name: 'Total',
                    data: ${total}

                }, {
                    name: 'Valid',
                    data: ${valid}
                }],
                navigation: {
                    menuItemStyle: {
                        fontSize: '10px'
                    }
                }
            });
        });
    </script>
</head>
<body>
<script src="/js/highcharts/highcharts.js"></script>
<div id="container" style="width: 100%; height: 500px; margin: 0 auto"></div>
</body>
</html>