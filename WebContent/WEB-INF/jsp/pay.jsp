<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script src="https://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
<script type="text/javascript">
	wx.config({
	    debug: true, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
	    appId: 'wx7bbd8f488c329408', // 必填，公众号的唯一标识
	    timestamp: new Date().getTime(), // 必填，生成签名的时间戳
	    nonceStr: '6fd4632f-b12c-4593-be6e-b6bcd1eedbca', // 必填，生成签名的随机串
	    signature: '025e385c57494f35173815609e61c5036e03234b',// 必填，签名，见附录1
	    jsApiList: ['chooseWXPay'] // 必填，需要使用的JS接口列表，所有JS接口列表见附录2
	});
	
	wx.ready(function(){
		alert("Config Success!");
		onBridgeReady();
	});
	
	wx.error(function(res){
		alert("Config Fail!");
	});
	
	function abc(){
		wx.chooseWXPay({
		    timestamp: new Date().getTime(), // 支付签名时间戳，注意微信jssdk中的所有使用timestamp字段均为小写。但最新版的支付后台生成签名使用的timeStamp字段名需大写其中的S字符
		    nonceStr: '6fd4632f-b12c-4593-be6e-b6bcd1eedbca', // 支付签名随机串，不长于 32 位
		    package: '', // 统一支付接口返回的prepay_id参数值，提交格式如：prepay_id=***）
		    signType: '025e385c57494f35173815609e61c5036e03234b', // 签名方式，默认为'SHA1'，使用新版支付需传入'MD5'
		    paySign: '025e385c57494f35173815609e61c5-be6e-b6bcd1eedbca', // 支付签名
		    success: function (res) {
		        // 支付成功后的回调函数
		    }
		});
	}

	// 唤醒微信支付
	function onBridgeReady() {
		WeixinJSBridge.invoke('getBrandWCPayRequest', {
			"appId" : "wx7bbd8f488c329408", //公众号名称，由商户传入wx7bbd8f488c329408
			"timeStamp" : new Date().getTime(), //时间戳，自1970年以来的秒数
			"nonceStr" : "e61463f8efa94090b1f366cccfbbb444", //随机串
			"package" : "prepay_id=u802345jgfjsdfgsdg888",
			"signType" : "MD5", //微信签名方式:
			"paySign" : "70EA570631E4BB79628FBCA90534C63FF7FADD89" //微信签名 
		}, function(res) {
			alert(res.err_msg);
			if (res.err_msg == "get_brand_wcpay_request:ok") {
				alert("支付成功");
			}else if(res.err_msg == "get_brand_wcpay_request:cancel"){
				alert("支付过程中用户取消");
			}else if(res.err_msg == "get_brand_wcpay_request:fail"){
				alert("支付失败");
			}else{
				alert("呵呵");
			} // 使用以上方式判断前端返回,微信团队郑重提示：res.err_msg将在用户支付成功后返回    ok，但并不保证它绝对可靠。 
		});
	}

	if (typeof WeixinJSBridge == "undefined") {
		if (document.addEventListener) {
			document.addEventListener('WeixinJSBridgeReady', onBridgeReady,
					false);
		} else if (document.attachEvent) {
			document.attachEvent('WeixinJSBridgeReady', onBridgeReady);
			document.attachEvent('onWeixinJSBridgeReady', onBridgeReady);
		}
	} else {
		onBridgeReady();
	}
</script>
</head>
<body>
<!-- <input type="button" onclick="onBridgeReady()" value="wpay"> -->
</body>
</html>