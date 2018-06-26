//绘图公共信息
//定义画布大小
var canvasWidth = 1000;
var canvasHeight = 600;
var colors = ["#2484c1", "#65a620", "#7b6888", "#a05d56", "#961a1a", "#d8d23a", "#e98125", "#d0743c", "#635222", "#6ada6a",
    "#0c6197", "#7d9058", "#207f33", "#44b9b0", "#bca44a", "#e4a14b", "#a3acb2", "#8cc3e9", "#69a6f9", "#5b388f",
    "#546e91", "#8bde95", "#d2ab58", "#273c71", "#98bf6e", "#4daa4b", "#98abc5", "#cc1010", "#31383b", "#006391",
    "#c2643f", "#b0a474", "#a5a39c", "#a9c2bc", "#22af8c", "#7fcecf", "#987ac6", "#3d3b87", "#b77b1c", "#c9c2b6",
    "#807ece", "#8db27c", "#be66a2", "#9ed3c6", "#00644b", "#005064", "#77979f", "#77e079", "#9c73ab", "#1f79a7"];
var color = d3.scaleOrdinal().range(colors);
var  title= [{
    text: "www.guowenke.tk",
    color: "#333333",
    fontSize: 18,
    font: "arial"
}];
margin = { top:50, right: 50, bottom:50, left:50 }

//获取当前窗口宽高
function getWH() {
    var wh = new Object();
    var de = document.documentElement;
    wh.W = (self.innerWidth || (de && de.clientWidth) || document.body.clientWidth) - 0;
    wh.H = (self.innerHeight || (de && de.clientHeight) || document.body.clientHeight) - 0;
    return wh;
}

//取得多维数组最大值
function getMaxdata(arr) {
    var maxdata = 0;
    var len = arr.length;
    for (var i = 0; i < len; i++) {
        maxdata = d3.max([maxdata, d3.max(arr[i])]);
    }
    return maxdata;
}


//取得多维数组最小值
function getMindata(arr) {
    var mindata = 0;
    var len = arr.length;
    for (var i = 0; i < len; i++) {
        mindata = d3.min([mindata, d3.min(arr[i])]);
    }
    return mindata;
}