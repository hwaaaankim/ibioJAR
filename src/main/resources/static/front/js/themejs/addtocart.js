
var cart = {
	'add': function(product_id) {
		addProductNotice('제품 장바구니 추가', '<img src="image/demo/shop/product/e11.jpg" alt="">', '<h3><a href="#">Apple Cinema 30"</a> added to <a href="#">shopping cart</a>!</h3>', 'success');
	}
}
var wishlist = {
	'add': function(product_id) {
		addProductNotice('Product added to Wishlist', '<img src="image/demo/shop/product/e11.jpg" alt="">', '<h3>You must <a href="#">login</a>  to save <a href="#">Apple Cinema 30"</a> to your <a href="#">wish list</a>!</h3>', 'success');
	}
}
var compare = {
	'add': function(product_id) {
		addProductNotice('Product added to compare', '<img src="image/demo/shop/product/e11.jpg" alt="">', '<h3>Success: You have added <a href="#">Apple Cinema 30"</a> to your <a href="#">product comparison</a>!</h3>', 'success');
	}
}

/* ---------------------------------------------------
	jGrowl – jQuery alerts and message box
-------------------------------------------------- */
function addProductNotice(title, thumb, text, type) {
	$.jGrowl.defaults.closer = false;
	//Stop jGrowl
	//$.jGrowl.defaults.sticky = true;
	var tpl = thumb + '<h3>' + text + '</h3>';
	$.jGrowl(tpl, {
		life: 4000,
		header: title,
		speed: 'slow',
		theme: type
	});
}

function findProduct(id) {
	var productImage;
	var productName;
	$.ajax({
		type: 'post',           // 타입 (get, post, put 등등)    
		url: '/findProduct',           // 요청할 서버url    
		async: false,            // 비동기화 여부 (default : true)    
		dataType: 'text',       // 데이터 타입 (html, xml, json, text 등등)    
		data: {  // 보낼 데이터 (Object , String, Array)     
			id: id
		}, success: function(result) { // 결과 성공 콜백함수        
			console.log(result);
		},
		error: function(request, status, error) { // 결과 에러 콜백함수       
			console.log(error)
		}
	});
	
}



























