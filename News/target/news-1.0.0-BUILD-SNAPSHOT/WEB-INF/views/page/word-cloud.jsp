<%--
  Created by IntelliJ IDEA.
  User: Bokseongmin
  Date: 2023-08-24
  Time: 오전 9:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div id="word-cloud">

</div>
<script>
    var wordDataList = [];
    var wordCloudUrl = "/news/get/title";

    getWordCloud(wordCloudUrl);

    $("#titleBtn").click(function() {
        wordCloudUrl = "/news/get/title";
        getWordCloud(wordCloudUrl);
    });

    $("#contentBtn").click(function() {
        wordCloudUrl = "/news/get/content";
        getWordCloud(wordCloudUrl);
    });
    function getWordCloud(url) {
        $("#word-cloud").empty();
        $.ajax({
            type: "GET",
            url: url,
            dataType: "json",
            contentType: "application/json; charset=UTF-8",
            success: function (response) {
                console.log(response)
                var wordcloudlist = response.body;
                var rawData = JSON.parse(wordcloudlist);

                // 데이터를 요구한 형식으로 변환하여 배열에 추가
                for (var key in rawData) {
                    if (rawData.hasOwnProperty(key)) {
                        var wordData = {
                            text: key,
                            size: rawData[key]
                        };
                        wordDataList.push(wordData);
                    }
                }

                console.log(wordDataList);

                // Create the SVG element for the word cloud
                const svgWidth = 800;
                const svgHeight = 400;
                const svg = d3.select("#word-cloud").append("svg")
                    .attr("width", svgWidth)
                    .attr("height", svgHeight)
                    .attr("class", "wordcloud")
                    .append("g")
                    .attr("transform", "translate(320,200)");

                // Create the word cloud layout
                const layout = d3.layout.cloud()
                    .size([svgWidth, svgHeight])
                    .words(wordDataList)
                    .padding(5)
                    .rotate(0) // Random rotation
                    .fontSize(d => Math.sqrt(d.size) * 2)
                    .on("end", draw);

                // Start the layout
                layout.start();
                // Draw the word cloud
                function draw(wordDataList) {
                    svg.selectAll("text")
                        .data(wordDataList)
                        .enter().append("text")
                        .style("font-size", d => d.size*10 + "px")
                        .attr("transform", "translate(320,200)")
                        .attr("transform", function(d) {
                            return "translate(" + [d.x, d.y] + ")rotate(" + d.rotate + ")";
                        })
                        .attr("text-anchor", "middle")
                        .text(d => d.text);
                }
            },
            error: function (xhr, status, error) {
                console.error(xhr.responseText);
            }
        });
    }
</script>