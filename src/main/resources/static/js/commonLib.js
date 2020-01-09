(function($) {

	// 异步请求方法
	$.getMyJSON = function(url, data, callback) {
		$.ajax({
			"url" : url,
			"context" : this,
			"data" : data,
			"type" : "post",
			"cache" : false,
			"dataType" : "json",
			"success" : function(html) {
				callback.call(this, html);
			}
		});
	};
	
	// 异步请求方法二，增加一个属性值
	$.getMyJSON3 = function(url, data, callback) {
		$.ajax({
			"url" : url,
			"context" : this,
			"data" : data,
			"type" : "post",
			"cache" : false,
			"traditional": true,//这里设置为true,默认的话，traditional为false，即jquery会深度序列化参数对象,traditional 为true阻止深度序列化
			"dataType" : "json",
			"success" : function(html) {
				callback.call(this, html);
			}
		});
	};

	// 同步请求方法
	$.getMyJSON2 = function(url, data, callback) {
		$.ajax({
			"async" : false,
			"url" : url,
			"context" : this,
			"data" : data,
			"type" : "post",
			"cache" : false,
			"dataType" : "json",
			"success" : function(html) {
				callback.call(this, html);
			}
		});
	};

	//异步get请求
	$.getMyJSON5 = function(url, data, callback) {
		$.ajax({
			"url" : url,
			"context" : this,
			"data" : data,
			"type" : "get",
			"cache" : false,
			"dataType" : "json",
			"success" : function(html) {
				callback.call(this, html);
			}
		});
	};
	//同步get请求
	$.getMyJSON6 = function(url, data, callback) {
		$.ajax({
			"async" : false,
			"url" : url,
			"context" : this,
			"data" : data,
			"type" : "get",
			"cache" : false,
			"dataType" : "json",
			"success" : function(html) {
				callback.call(this, html);
			}
		});
	};
	//异步post SpringMVC用RequestBody接收
	$.getMyJSON7 = function(url, data, callback) {
		$.ajax({
			"url" : url,
			"context" : this,
			"data" : JSON.stringify(data),
			"type" : "post",
			"cache" : false,
			"contentType" : "application/json",
			"dataType" : "json",
			"success" : function(html) {
				callback.call(this, html);
			}
		});
	};
	$.aotucompleter = function(target, url, data, callback) {
		$(target).autocomplete({
			serviceUrl : url,
			dataType : 'json',
			params : data,
			onSelect : function(suggestion) {
				callback.call(this, suggestion)
			}
		});
	};

	$.aotucompleterPost = function(target, url, data, callback) {
		$(target).autocomplete({
			serviceUrl : url,
			dataType : 'json',
			params : data,
			type : 'POST',
			isPost : true,
			onSelect : function(suggestion) {
				callback.call(this, suggestion)
			}
		});
	};

	/**
	 * 设置未来(全局)的AJAX请求默认选项 主要设置了AJAX请求遇到Session过期的情况
	 */

	$.ajaxSetup({
		type : 'POST',
		contentType : "application/x-www-form-urlencoded;charset=utf-8",
		complete : function(xhr, status) {
//			var sessionStatus = xhr.getResponseHeader('sessionStatus');
//			if (sessionStatus == 'timeout') {
//				// var top = getTopWinow();
//				// var yes = confirm('由于您长时间没有操作, session已过期, 请重新登录.');
//				// if (yes) {
//				alert("登录超时,请重新登录！");
//				window.location.href = '/shiro-cas';
//				// }
//			}
			if(xhr.readyState == 0 && xhr.status == 0 && xhr.statusText != 'abort'){
				//alert('由于您长时间没有操作, session已过期, 请重新登录.');
				window.location.href = 'http://127.0.0.1:8080/login?service=http://127.0.0.1:8088/shiro-cas';
			}
		}
	});
	
	$(document).on("keydown", "form", function(){
		if(event.keyCode==13){
			return false;
		}
	})

})(jQuery);

/**
 * 在页面中任何嵌套层次的窗口中获取顶层窗口
 * 
 * @return 当前页面的顶层窗口对象
 */

function getTopWinow() {
	var p = window;
	while (p != p.parent) {
		p = p.parent;
	}
	return p;
}
