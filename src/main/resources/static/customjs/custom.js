var doc, results;

$(function () {
    $("#searchContext").blur(function () {

        if ($("#searchContext").val().length != '0') {
            lightyear.loading('show');
            $.ajax({
                url: "/searchImage",
                type: "POST",
                data: {
                    q: $("#searchContext").val(),
                    //n: $(".limit").val()--保留输入限制
                },
                dataType: "text",
                success: function (data) {
                    if (null != data) {
                        doc = JSON.parse(data);
                        results = doc.results;
                        //console.log(results);
                        builder_form(0, 5, results, 25);
                        lightyear.loading('hide');
                        $("#searchResult").css("display", "block");
                    } else {
                        lightyear.notify('服务器响应超时，请重试~', 'danger', 100);
                    }
                },

                complete:function(){
                    $(".downimage").on("click", function(){

                        var imagename = $(this).parent()
                            .siblings()
                            .first()
                            .text();
                        $(this).text('ing');
                        $(this).unbind('click');
                        $.post("/pullimage",
                            {
                               imagename:imagename,
                            },
                            function (data,status) {
                                if("success"==status){
                                    lightyear.notify('镜像'
                                        +imagename
                                        +'已创建完成',
                                        'success', 100
                                    );

                                }else{
                                    lightyear.notify('镜像'
                                        +imagename
                                        +'创建失败,请至日志处查看具体信息\n',
                                        'danger', 200);
                                }
                            }
                        )
                    });

                }
            })
        }else{
            lightyear.notify('镜像输入不可为空，请重试~', 'danger', 100);
        }
    })

    function builder_form(start, divide, data, max) {
        $("#result_body").empty();
        con = "";
        start *= divide;
        for (var i = start; i < start + divide && i <= max; i++) {
            con += "<tr><td>" + data[i].name + "</td>";
            con += "<td>" + data[i].description + "</td>";
            con += "<td>" + data[i].star_count + "</td>";
            con += "<td>" + data[i].is_official + "</td>";
            con += "<td>" + data[i].is_trusted + "</td>";
            con += "<td>" +
                  "<div class=\"btn-group downimage\">\n"
                          + "<a class=\"btn btn-xs btn-default\" href=\"#!\" title=\"\" data-toggle=\"tooltip\" data-original-title=\"获取\"><i class=\"mdi mdi-format-vertical-align-bottom\"></i></a>"
                + "</div>"
                + "</td></tr>"
            $("#result_body").html(con);
            //console.log(data[i]);
        }
    }

    /*$("#pullimage").click(function () {
        builder_form(1, 5, results, 25);
    })*/

    $("#result_page li").click(function () {
        var index = this.value - 1;
        builder_form(index, 5, results, 25);
    })
    
});