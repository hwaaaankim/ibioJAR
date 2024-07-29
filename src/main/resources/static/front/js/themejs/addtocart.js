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
// 장바구니 항목을 localStorage에서 가져오기
function getCartItems() {
    const userId = getUserId();
    if (!userId) return {};
    const cart = localStorage.getItem(`cartItems_${userId}`);
    return cart ? JSON.parse(cart) : {};
}

// 장바구니 항목을 localStorage에 저장하기
function setCartItems(cartItems) {
    const userId = getUserId();
    if (userId) {
        localStorage.setItem(`cartItems_${userId}`, JSON.stringify(cartItems));
    }
}

// 로그인 상태 체크 함수
function isLoggedIn() {
    let loggedIn = false;
    $.ajax({
        type: 'GET',
        url: '/checkLogin',
        async: false,
        success: function(response) {
            loggedIn = response.loggedIn;
            if (loggedIn) {
                // Save user ID to a cookie for later use
                document.cookie = `userId=${response.userId}; path=/; SameSite=Strict`;
            } else {
                document.cookie = 'userId=; Max-Age=0; path=/; SameSite=Strict';
            }
        },
        error: function() {
            loggedIn = false;
        }
    });
    return loggedIn;
}

// 로그인한 사용자 ID 가져오기
function getUserId() {
    const name = 'userId=';
    const decodedCookie = decodeURIComponent(document.cookie);
    const ca = decodedCookie.split(';');
    for (let i = 0; i < ca.length; i++) {
        let c = ca[i];
        while (c.charAt(0) === ' ') {
            c = c.substring(1);
        }
        if (c.indexOf(name) === 0) {
            return c.substring(name.length, c.length);
        }
    }
    return null;
}

// 장바구니 UI 업데이트
function updateCartDisplay() {
    const loggedIn = isLoggedIn();
    if (!loggedIn) {
        $('#nullCart').show();
        $('#notNullCart').hide();
        $('#cart-info').hide();
        $('#nullCart').find('tbody').html('<tr><td class="text-center" colspan="6">로그인 후 이용가능</td></tr>');
        return;
    } else {
        $('#cart-info').show();
    }

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
                    <td class="text-center" id="price-${productId}" data-price="${product.price}">${product.price.toLocaleString()} 원</td>
                    <td class="text-center" id="total-${productId}">${total.toLocaleString()} 원</td>
                    <td class="text-right">
                        <a onclick="confirmRemoveProduct(${productId}, false);" class="fa fa-times fa-delete"></a>
                    </td>
                </tr>
            `);
        });

        updateCartSummary();
    }
}

function updateCartSummary() {
    const cartItems = getCartItems();
    const totalPrice = Object.keys(cartItems).reduce((acc, key) => acc + (cartItems[key].quantity * cartItems[key].price), 0);
    const taxPrice = totalPrice * 0.1;
    const finalPrice = totalPrice + taxPrice;

    $('#totalPrice').text(`${totalPrice.toLocaleString()} 원`);
    $('#taxPrice').text(`+${taxPrice.toLocaleString()} 원`);
    $('#finalPrice').text(`${finalPrice.toLocaleString()} 원`);

    // Update cart UI in header
    $('#cartCount').text(Object.keys(cartItems).reduce((acc, key) => acc + cartItems[key].quantity, 0));
    $('#cartPrice').text(`(${totalPrice.toLocaleString()} 원)`);
}

// 제품 정보 가져오기
function findProduct(id) {
    $.ajax({
        type: 'POST',
        url: '/shopping/findProduct',
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
            addProductNotice('제품 장바구니 추가!', '<img src="' + result.image + '" alt="">', '<h3>' + result.name + ' 제품이 <a href="/shopping/viewCart">장바구니에 추가되었습니다.</a>!</h3>', 'success');
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

function updateQuantity(productId, isCheckoutPage) {
    var quantityInput = document.getElementById('quantity-' + productId);
    var newQuantity = parseInt(quantityInput.value);

    if (newQuantity <= 0 || isNaN(newQuantity)) {
        alert("수량은 1 이상이어야 합니다.");
        return;
    }

    if (confirm('정말 수정하시겠습니까?')) {
        // Update localStorage
        var cartItems = getCartItems();
        if (cartItems[productId]) {
            cartItems[productId].quantity = newQuantity;
        }
        setCartItems(cartItems);

        // Update UI
        var productPrice = cartItems[productId].price;
        document.getElementById('total-' + productId).innerText = (productPrice * newQuantity).toLocaleString() + ' 원';
        updateCartSummary();

        // 서버로 데이터 전송
        if (isCheckoutPage) {
            checkoutCart();
        } else {
            viewCart();
        }
    }
}

function confirmRemoveProduct(productId, isCheckoutPage) {
    if (confirm('정말 삭제하시겠습니까?')) {
        removeProduct(productId, isCheckoutPage);
    }
}

function removeProduct(productId, isCheckoutPage) {
    // Update localStorage
    var cartItems = getCartItems();
    delete cartItems[productId];
    setCartItems(cartItems);

    // Remove product from UI
    var productRow = document.getElementById('row-' + productId);
    if (productRow) {
        productRow.remove();
    }

    // If cart is empty, show "비어있음" message
    if (Object.keys(cartItems).length === 0) {
        document.getElementById('nullCart').style.display = '';
        document.getElementById('notNullCart').style.display = 'none';
    }

    updateCartSummary();

    // 서버로 데이터 전송
    if (isCheckoutPage) {
        checkoutCart();
    } else {
        viewCart();
    }
}

// 장바구니 UI를 초기화
$(document).ready(function() {
    updateCartDisplay();
    updateCartSummary();
    // Checkout 버튼 클릭 이벤트 추가
    $(document).on('click', '#checkoutBtn', function(event) {
        event.preventDefault();
        checkout()
    });
    
    $(document).on('click', '#confirmOrder', function(event) {
        event.preventDefault();
        confirmOrder();
    });
});

function viewCart() {
    const cartItems = getCartItems();
    const form = $('<form>', { action: '/shopping/viewCart', method: 'POST' });

    Object.keys(cartItems).forEach(id => {
        form.append($('<input>', { type: 'hidden', name: 'ids', value: id }));
        form.append($('<input>', { type: 'hidden', name: 'quantities', value: cartItems[id].quantity }));
    });

    $('body').append(form);
    form.submit();
}

function checkout() {
    const cartItems = getCartItems();
    const form = $('<form>', { action: '/shopping/checkOut', method: 'POST' });

    Object.keys(cartItems).forEach(id => {
        form.append($('<input>', { type: 'hidden', name: 'ids', value: id }));
        form.append($('<input>', { type: 'hidden', name: 'quantities', value: cartItems[id].quantity }));
    });

    $('body').append(form);
    form.submit();
}

function checkoutCart() {
    const cartItems = getCartItems();
    const userId = getUserId();
    const form = $('<form>', { action: '/shopping/checkoutProcess', method: 'POST' });

    Object.keys(cartItems).forEach(id => {
        form.append($('<input>', { type: 'hidden', name: 'ids', value: id }));
        form.append($('<input>', { type: 'hidden', name: 'quantities', value: cartItems[id].quantity }));
    });

    $('body').append(form);
    form.submit();

    // Clear cart items in localStorage
    if (userId) {
        localStorage.removeItem(`cartItems_${userId}`);
    }
}

function confirmOrder() {
    const cartItems = getCartItems();
    const productDetails = [];
    let totalPrice = 0;

    Object.keys(cartItems).forEach(id => {
        const item = cartItems[id];
        const itemTotal = item.price * item.quantity;
        totalPrice += itemTotal;
        productDetails.push(`${item.name} x${item.quantity}`);
    });

    const productDetailsText = productDetails.join(', ');
    const confirmMessage = `${productDetailsText}를 구매 하시겠습니까? 최종금액은 ${totalPrice.toLocaleString()}원 입니다.`;

    if (confirm(confirmMessage)) {
        checkoutCart();
    }
}

