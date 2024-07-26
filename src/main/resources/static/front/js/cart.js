// 장바구니 항목을 localStorage에서 가져오기
function getCartItems() {
    const cart = localStorage.getItem('cartItems');
    return cart ? JSON.parse(cart) : {};
}

// 장바구니 항목을 localStorage에 저장하기
function setCartItems(cartItems) {
    localStorage.setItem('cartItems', JSON.stringify(cartItems));
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
                    <td class="text-center" id="price-${productId}" data-price="${product.price}">${product.price.toLocaleString()} 원</td>
                    <td class="text-center" id="total-${productId}">${total.toLocaleString()} 원</td>
                    <td class="text-right">
                        <a onclick="removeProduct(${productId});" class="fa fa-times fa-delete"></a>
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
}

// 제품 정보 가져오기
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
            addProductNotice('제품 장바구니 추가!', '<img src="' + result.image + '" alt="">', '<h3>' + result.name + ' 제품이 <a href="/viewCart">장바구니에 추가되었습니다.</a>!</h3>', 'success');
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

function updateQuantity(productId) {
    var quantityInput = document.getElementById('quantity-' + productId);
    var newQuantity = parseInt(quantityInput.value);

    if (newQuantity <= 0 || isNaN(newQuantity)) {
        alert("수량은 1 이상이어야 합니다.");
        return;
    }

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
}

function removeProduct(productId) {
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
}

// 장바구니 UI를 초기화
$(document).ready(function() {
    updateCartDisplay();
    updateCartSummary();
});

function viewCart() {
    const cartItems = getCartItems();
    const form = $('<form>', { action: '/viewCart', method: 'POST' });

    Object.keys(cartItems).forEach(id => {
        form.append($('<input>', { type: 'hidden', name: 'ids', value: id }));
        form.append($('<input>', { type: 'hidden', name: 'quantities', value: cartItems[id].quantity }));
    });

    $('body').append(form);
    form.submit();
}

function checkoutCart() {
    const cartItems = getCartItems();
    const form = $('<form>', { action: '/checkout', method: 'POST' });

    Object.keys(cartItems).forEach(id => {
        form.append($('<input>', { type: 'hidden', name: 'ids', value: id }));
        form.append($('<input>', { type: 'hidden', name: 'quantities', value: cartItems[id].quantity }));
    });

    $('body').append(form);
    form.submit();
}
