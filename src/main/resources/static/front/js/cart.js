function updateQuantity(productId) {
    var quantityInput = document.getElementById('quantity-' + productId);
    var newQuantity = parseInt(quantityInput.value);

    if (newQuantity <= 0 || isNaN(newQuantity)) {
        alert("수량은 1 이상이어야 합니다.");
        return;
    }

    // Update localStorage
    var cart = JSON.parse(localStorage.getItem('cartItems')) || {};
    if (cart[productId]) {
        cart[productId].quantity = newQuantity;
    }
    localStorage.setItem('cartItems', JSON.stringify(cart));

    // Update UI
    var productPrice = cart[productId].price;
    document.getElementById('total-' + productId).innerText = (productPrice * newQuantity).toLocaleString() + ' 원';
    updateCartSummary();
}

function removeProduct(productId) {
    // Update localStorage
    var cart = JSON.parse(localStorage.getItem('cartItems')) || {};
    delete cart[productId];
    localStorage.setItem('cartItems', JSON.stringify(cart));

    // Remove product from UI
    var productRow = document.getElementById('row-' + productId);
    if (productRow) {
        productRow.remove();
    }

    // If cart is empty, show "비어있음" message
    if (Object.keys(cart).length === 0) {
        document.getElementById('nullCart').style.display = '';
        document.getElementById('notNullCart').style.display = 'none';
    }

    updateCartSummary();
}

function updateCartSummary() {
    var cart = JSON.parse(localStorage.getItem('cartItems')) || {};
    var totalPrice = 0;

    Object.keys(cart).forEach(function(productId) {
        var productPrice = cart[productId].price;
        totalPrice += productPrice * cart[productId].quantity;
    });

    var tax = totalPrice * 0.1;
    var finalPrice = totalPrice + tax;

    document.getElementById('totalPrice').innerText = totalPrice.toLocaleString() + ' 원';
    document.getElementById('taxPrice').innerText = '+' + tax.toLocaleString() + ' 원';
    document.getElementById('finalPrice').innerText = finalPrice.toLocaleString() + ' 원';

    // Update cart UI in header
    document.getElementById('cartCount').innerText = Object.keys(cart).reduce((acc, key) => acc + cart[key].quantity, 0);
    document.getElementById('cartPrice').innerText = '(' + totalPrice.toLocaleString() + ' 원)';
}

// 장바구니 UI를 초기화
$(document).ready(function() {
    updateCartDisplay();
    updateCartSummary();
});
