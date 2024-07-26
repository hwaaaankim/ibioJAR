$(function() {

	$('#bigSort').on('change', function() {
		if($(this).val()!=0){
			$.ajax({
				cache: false,
				type: 'POST',
				url: '/admin/searchMiddleSort',
				data: {
					bigId: $(this).val()
				}, success: function(result) {
					$('#middleSort').find('option').remove();
					$('#middleSort').append("<option value='0'> === 중분류 선택 === </option>");
					for (var i = 0; i < result.length; i++) {
						var option = $("<option value=" + result[i].id + ">" + result[i].name + "</option>");
						$('#middleSort').append(option);
					}
				}
	
			});
		}
	});
	
	$('#middleSort').on('change', function() {
		if($(this).val()!=0){
			$.ajax({
				cache: false,
				type: 'POST',
				url: '/admin/searchSmallSort',
				data: {
					middleId: $(this).val()
				}, success: function(result) {
					$('#smallSort').find('option').remove();
					$('#smallSort').append("<option value='0'> === 소분류 선택 === </option>");
					for (var i = 0; i < result.length; i++) {
						var option = $("<option value=" + result[i].id + ">" + result[i].name + "</option>");
						$('#smallSort').append(option);
					}
				}
	
			});
		}
	});
});


function productDelete(id){
	var result = confirm('해당 제품을 삭제 하시겠습니까?');
	if(result){
		location.href='/admin/productDelete/' + id;
	}
}

document.addEventListener('DOMContentLoaded', function() {
    const productSearchBtn = document.getElementById('productSearchBtn');
    productSearchBtn.addEventListener('click', function() {
        var bigId = $('#bigSort').val() || 0;
        var middleId = $('#middleSort').val() || 0;
        var smallId = $('#smallSort').val() || 0;
        var minCost = $('#minCost').val();
        var maxCost = $('#maxCost').val();
        var productSort = $('input[name="productSort"]:checked').val() || false;
        var productDiscount = $('input[name="productDiscount"]:checked').val() || false;
        var sellingResult = $('input[name="sellingResult"]:checked').val() || false;
        var searchWord = $('#searchWord').val();
        changeList(
            bigId,
            middleId,
            smallId,
            minCost,
            maxCost,
            productSort,
            productDiscount,
            sellingResult,
            searchWord
            
        );
    });
});

function changeList(
    bigId,
    middleId,
    smallId,
    minCost,
    maxCost,
    productSort,
    productDiscount,
    sellingResult,
    searchWord
) {
    const form = document.createElement('form');
    form.method = 'POST';
    form.action = '/admin/ibioProductManager';

    // bigId 입력 필드 생성 및 추가
    const bigIdInput = document.createElement('input');
    bigIdInput.type = 'hidden';
    bigIdInput.name = 'bigId';
    bigIdInput.value = bigId;
    form.appendChild(bigIdInput);

    // middleId 입력 필드 생성 및 추가
    const middleIdInput = document.createElement('input');
    middleIdInput.type = 'hidden';
    middleIdInput.name = 'middleId';
    middleIdInput.value = middleId;
    form.appendChild(middleIdInput);

    // smallId 입력 필드 생성 및 추가
    const smallIdInput = document.createElement('input');
    smallIdInput.type = 'hidden';
    smallIdInput.name = 'smallId';
    smallIdInput.value = smallId;
    form.appendChild(smallIdInput);

    // minCost 입력 필드 생성 및 추가
    const minCostInput = document.createElement('input');
    minCostInput.type = 'hidden';
    minCostInput.name = 'minCost';
    minCostInput.value = minCost;
    form.appendChild(minCostInput);

    // maxCost 입력 필드 생성 및 추가
    const maxCostInput = document.createElement('input');
    maxCostInput.type = 'hidden';
    maxCostInput.name = 'maxCost';
    maxCostInput.value = maxCost;
    form.appendChild(maxCostInput);

    // productSort 입력 필드 생성 및 추가
    const productSortInput = document.createElement('input');
    productSortInput.type = 'hidden';
    productSortInput.name = 'productSort';
    productSortInput.value = productSort;
    form.appendChild(productSortInput);

    // productDiscount 입력 필드 생성 및 추가
    const productDiscountInput = document.createElement('input');
    productDiscountInput.type = 'hidden';
    productDiscountInput.name = 'productDiscount';
    productDiscountInput.value = productDiscount;
    form.appendChild(productDiscountInput);

    // sellingResult 입력 필드 생성 및 추가
    const sellingResultInput = document.createElement('input');
    sellingResultInput.type = 'hidden';
    sellingResultInput.name = 'sellingResult';
    sellingResultInput.value = sellingResult;
    form.appendChild(sellingResultInput);
    
    const searchWordInput = document.createElement('input');
    searchWordInput.type = 'hidden';
    searchWordInput.name = 'searchWord';
    searchWordInput.value = searchWord;
    form.appendChild(searchWordInput)

    document.body.appendChild(form);
    form.submit();
}
















