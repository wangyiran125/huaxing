/**
 * Created by Jason on 15/4/23.
 */
// 计算页面的实际高度，iframe自适应会用到
function calcPageHeight(doc) {
    var cHeight = Math.max(doc.body.clientHeight, doc.documentElement.clientHeight)
    var sHeight = Math.max(doc.body.scrollHeight, doc.documentElement.scrollHeight)
    var height  = Math.max(cHeight, sHeight)
    return height
}
window.onload = function() {
    var doc = document
    var height = calcPageHeight(doc)
    var proxyframe = doc.getElementById('proxyframe');
    if (proxyframe) {
        proxyframe.src = 'http://test1.chinalbs.org:5080/logistics/pages/proxy.html?height=' + height
        // console.log(doc.documentElement.scrollHeight)
    }
};