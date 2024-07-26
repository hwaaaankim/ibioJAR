var wishlist = {
	'add': function(product_id) {
		addProductNotice('제품 찜 리스트 추가!', '<img src="image/demo/shop/product/e11.jpg" alt="">', '<h3>You must <a href="#">login</a>  to save <a href="#">Apple Cinema 30"</a> to your <a href="#">wish list</a>!</h3>', 'success');
	}
}
var compare = {
	'add': function(product_id) {
		addProductNotice('제품 비교 페이지 추가!', '<img src="image/demo/shop/product/e11.jpg" alt="">', '<h3>Success: You have added <a href="#">Apple Cinema 30"</a> to your <a href="#">product comparison</a>!</h3>', 'success');
	}
}
// 장바구니 항목을 localStorage에서 가져오기
function getCartItems() {
    const cart = localStorage.getItem('cartItems');
    return cart ? JSON.parse(cart) : {};
}

// 장바구니 항목을 localStorage에 저장하기
function setCartItems(cartItems) {
    localStorage.setItem('cartItems', JSON.stringify(cartItems));
}
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
// 장바구니 UI 업데이트
function updateCartDisplay() {
    const cartItems = getCartItems();
    const cartCount = Object.keys(cartItems).reduce((acc, key) => acc + cartItems[key].quantity, 0);
    const cartPrice = Object.keys(cartItems).reduce((acc, key) => acc + (cartItems[key].quantity * cartItems[key].price), 0);

    $('#cartCount').text(cartCount);
    $('#cartPrice').text(`(${cartPrice.toLocaleString()} 원)`);

    if (cartCount === 0) {
        $('#nullCart').show();
        $('#notNullCart').hide();
    } else {
        $('#nullCart').hide();
        $('#notNullCart').show();

        const contentTableBody = $('#contentTable tbody');
        contentTableBody.empty();

        Object.keys(cartItems).forEach(productId => {
            const product = cartItems[productId];
            const quantity = product.quantity;
            const total = quantity * product.price;

            contentTableBody.append(`
                <tr id="row-${productId}">
                    <td class="text-center">
                        <a href="/productDetail/${productId}">
                            <img src="${product.image}" width="70px" alt="${product.name}" title="${product.name}" class="img-thumbnail"/>
                        </a>
                    </td>
                    <td class="text-left">
                        <a href="/productDetail/${productId}">${product.name}</a>
                    </td>
                    <td class="text-center">x${quantity}</td>
                    <td class="text-center">${product.price.toLocaleString()} 원</td>
                    <td class="text-center">${total.toLocaleString()} 원</td>
                    <td class="text-right">
                        <a onclick="cart.remove(${productId});" class="fa fa-times fa-delete"></a>
                    </td>
                </tr>
            `);
        });

        const taxPrice = cartPrice * 0.1;
        const finalPrice = cartPrice + taxPrice;

        $('#totalPrice').text(`${cartPrice.toLocaleString()} 원`);
        $('#taxPrice').text(`+${taxPrice.toLocaleString()} 원`);
        $('#finalPrice').text(`${finalPrice.toLocaleString()} 원`);
    }
}

function findProduct(id) {
    $.ajax({
        type: 'POST',
        url: '/findProduct',
        dataType: 'json',
        contentType: 'application/x-www-form-urlencoded; charset=UTF-8',
        data: { id: id },
        success: function(result) {
            let cartItems = getCartItems();
            if (cartItems[id]) {
                cartItems[id].quantity += 1;
            } else {
                cartItems[id] = {
                    id: result.id,
                    name: result.name,
                    image: result.image,
                    code: result.code,
                    price: parseFloat(result.price.replace(',', '')),
                    quantity: 1
                };
            }
            setCartItems(cartItems);
            updateCartDisplay();
            addProductNotice('제품 장바구니 추가!', '<img src="' + result.image + '" alt="">', '<h3>' + result.name + ' 제품이 <a href="/member/viewCart">장바구니에 추가되었습니다.</a>!</h3>', 'success');
        },
        error: function(request, status, error) {
            console.log(error);
        }
    });
}

var cart = {
    'add': function(product_id) {
        findProduct(product_id);
    },
    'remove': function(product_id) {
        let cartItems = getCartItems();
        if (cartItems[product_id]) {
            cartItems[product_id].quantity -= 1;
            if (cartItems[product_id].quantity === 0) {
                delete cartItems[product_id];
            }
        }
        setCartItems(cartItems);
        updateCartDisplay();
    }
};

// 장바구니 UI를 초기화
$(document).ready(function() {
    updateCartDisplay();
});

function viewCart() {
    const cartItems = getCartItems();
    const ids = Object.keys(cartItems).join(',');
    const quantities = Object.values(cartItems).map(item => item.quantity).join(',');

    window.location.href = `/viewCart?ids=${ids}&quantities=${quantities}`;
}

function checkoutCart() {
    const cartItems = getCartItems();
    const ids = Object.keys(cartItems).join(',');
    const quantities = Object.values(cartItems).map(item => item.quantity).join(',');

    window.location.href = `/checkout?ids=${ids}&quantities=${quantities}`;
}


























