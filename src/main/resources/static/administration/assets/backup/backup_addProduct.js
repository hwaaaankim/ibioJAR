function createString(i){
	return ('<div class="row noneChangeOption optionSelectForm-' + i + '">'
	+ '<div class="col-lg-5 col-sm-12">'
	+ '<div class="card-body">'
	+ '<div class="hstack gap-1 align-items-start">'
	+ '<div class="flex-grow-1">'
	+ '<input type="text" class="form-control noneChangeOptionName" placeholder="가격 변동이 없는옵션 제목을 입력 해 주세요.">'
	+ '</div></div></div></div>'
	+ '<div class="col-lg-5 col-sm-12">'
	+ '<div class="card-body">'
	+ '<div class="hstack gap-1 align-items-start">'
	+ '<div class="flex-grow-1">'
	+ '<input class="form-control noneChangeOptionValues" data-choices-'
	+ i
	+ ' data-choices-multiple-remove="true" type="text" value="예) 15ML" />'
	+ '</div></div></div></div>'
	+ '<div class="col-lg-2 col-sm-12">'
	+ '<div class="card-body">'
	+ '<div class="hstack gap-1 align-items-start">'
	+ '<div class="flex-grow-1">'
	+ '<button type="button" class="btn btn-warning btn-md" onclick=deleteBtn("none",'+ i +');> <i class="bx bx-minus-circle"></i> 옵션삭제</button>'
	+ '</div></div></div></div></div>')
}

function createChangeString(i){
	return (
	'<div class="row changeOption optionSelectForm-' + i + '">'
	+ '<div class="col-lg-5 col-sm-12">'
	+ '<div class="card-body">'
	+ '<div class="hstack gap-1 align-items-start">'
	+ '<div class="flex-grow-1">'
	+ '<input required type="text" class="form-control changeOptionName" placeholder="가격 변동이 있는 옵션 제목을 입력 해 주세요.">'
	+ '</div></div></div></div>'
	+ '<div class="col-lg-5 col-sm-12">'
	+ '<div class="card-body">'
	+ '<div class="hstack gap-1 align-items-start">'
	+ '<div class="flex-grow-1">'
	+ '<input required class="form-control changeOptionValues" data-choices-'
	+ i
	+ ' data-choices-multiple-remove="true" placeholder="옵션 종류를 입력 해 주세요." type="text" value="예) 15ML:+1500" />'
	+ '</div></div></div></div>'
	+ '<div class="col-lg-2 col-sm-12">'
	+ '<div class="card-body">'
	+ '<div class="hstack gap-1 align-items-start">'
	+ '<div class="flex-grow-1">'
	+ '<button type="button" class="btn btn-warning btn-md" onclick=deleteBtn("changeing",'+ i +');> <i class="bx bx-minus-circle"></i> 옵션삭제</button>'
	+ '</div></div></div></div></div>')
}
// var input = document.getElementById('noneChangeOption-01');
// var inputClone = input.cloneNode(true);

function deleteBtn(code, id){
	$('.optionSelectForm-'+id).remove();
}

$(function() {
	$('#discountSection').css('display', 'none');
	$('#optionSection').css('display', 'none');
	$('#eventSection').css('display', 'none');

	$('#discountRadio').on('change', function() {
		$('#discountSection').slideToggle();
	});

	$('#optionRadio').on('change', function() {
		$('#optionSection').slideToggle();
	});
	$('#optionAddButton').attr('disabled', true);
	$('input[name=priceChange]').on('change', function() {
		$('#optionAddButton').attr('disabled', false);
	});

	$(document).on('click', '#optionAddButton', function() {

		var sign = $('input[name=priceChange]:checked').val();

		if (sign === 'true') {
			var num = $('#optionSelectForm').children().length;
			$('#optionSelectForm').append(createChangeString(num));
			const newChoices = new Choices('[data-choices-'+ num +']',{
				removeItems: true,
    			removeItemButton: true,
			});
		}

		if (sign === 'false') {
			var num = $('#optionSelectForm').children().length;
			$('#optionSelectForm').append(createString(num));
			const newChoices = new Choices('[data-choices-'+ num +']',{
				removeItems: true,
    			removeItemButton: true,
			});
		}
	});
	
	$('#eventRadio').on('change', function() {
		$('#eventSection').slideToggle();
	});
	
	$('#smallPanelBigSort').on('change', function() {
		$.ajax({
			cache: false,
			type: 'POST',
			url: '/admin/searchMiddleSort',
			data: {
				bigId: $(this).val()
			}, success: function(result) {
				$('#smallPanelMiddleSort').find('option').remove();
				$('#smallPanelMiddleSort').append("<option value=''> === 중분류 선택 === </option>");
				for (var i = 0; i < result.length; i++) {
					var option = $("<option value=" + result[i].id + ">" + result[i].name + "</option>");
					$('#smallPanelMiddleSort').append(option);
				}
			}
		});

		$('#smallPanelMiddleSort').on('change', function() {
			$.ajax({
				cache: false,
				type: 'POST',
				url: '/admin/searchSmallSort',
				data: {
					middleId: $(this).val()
				}, success: function(result) {
					$('#smallPanelSmallSort').find('option').remove();
					$('#smallPanelSmallSort').append("<option value=''> === 소분류 선택 === </option>");
					for (var i = 0; i < result.length; i++) {
						var option = $("<option value=" + result[i].id + ">" + result[i].name + "</option>");
						$('#smallPanelSmallSort').append(option);
					}
				}

			});
			$('#smallPanelSmallSortSelect').on('change', function() {
				$('#smallsortDeleteBtn').on('click',function(){
					
					var arr = new Array();
					arr = $('#smallPanelSmallSort').val();
					$.ajax({
						cache: false,
						type: 'POST',
						url: '/admin/smallSortDelete',
						data: {
							text: arr,
						}, error: function(error) {
							alert('해당 분류가 적용된 제품을 삭제 후 시도해 주세요');
						}
	
					}).done(function(fragment) {
						if (fragment != 'fail') {
							alert('삭제 되었습니다.');
							location.reload();
						} else {
							location.reload();
						}
					});
				});
			});
		});
	});
});

var addBtn = document.getElementById('productAddBtn');


addBtn.addEventListener('click', function(){
	let addForm = new FormData();
	
	var bigId = document.getElementById('smallPanelBigSort').value;
	if(bigId === null || bigId === "" || bigId === undefined){
		alert('대분류를 선택 해 주세요.');
		return false;
	}else{
		addForm.append('bigId', bigId);
	}
	
	var middleId = document.getElementById('smallPanelMiddleSort').value;
	if(middleId === null || middleId === "" || middleId === undefined){
		alert('중분류를 선택 해 주세요.');
		return false;
	}else{
		addForm.append('middleId', middleId);
	}
	
	var smallId = document.getElementById('smallPanelSmallSort').value;
	if(smallId === null || smallId === "" || smallId === undefined){
		alert('소분류를 선택 해 주세요.');
		return false;
	}else{
		addForm.append('smallId', smallId);
	}
	
	var brand = document.getElementById('brand').value;
	if(brand === null || brand === "" || brand === undefined){
		addForm.append('brand', "-");
	}else{
		addForm.append('brand', brand);
	}

	var brandImage = document.getElementById('brandImage').files;
	if(brandImage.length < 1){
		addForm.append('brandImage', null);	
	}else{
		addForm.append('brandImage', brandImage[0]);
	}
	
	var title = document.getElementById('title').value;
	if(title === null || title === "" || title === undefined){
		alert('제품명을 입력 해 주세요.');
		return false;
	}else{
		addForm.append('title', title);
	}
	
	var productSpecs = document.getElementById('productSpecs').value;
	if(productSpecs === null || productSpecs === "" || productSpecs === undefined){
		addForm.append('productSpecs', "-");
	}else{
		addForm.append('productSpecs', productSpecs);
	}
	
	var productTags = document.getElementById('productTags').value;
	if(productTags === null || productTags === "" || productTags === undefined){
		addForm.append('productTags', "-");
	}else{
		addForm.append('productTags', productTags);
	}
	
	var description = document.getElementById('description').value;
	if(description === null || description === "" || description === undefined){
		addForm.append('description', "-");
	}else{
		addForm.append('description', description);
	}
	
	var productImage = document.getElementById('productImage').files;
	if(productImage.length < 1){
		alert('제품의 대표 이미지를 선택 해 주세요.');
		return false;
	}else{
		addForm.append('productImage', productImage[0]);
	}
	
	var slideImages = document.getElementById('slideImages').files;
	if(slideImages.length < 1){
		addForm.append('brandImage', null);	
	}else{
		addForm.append('slideImages', slideImages);
	}
	
	var price = document.getElementById('price').value;
	if(price === null || price === "" || price === undefined){
		alert('제품 가격을 숫자로 정확하게 입력 해 주세요.');
		return false;
	}else{
		addForm.append('price', price);
	}
	
	var priceTarget = document.getElementById('target').value;
	addForm.append('priceTarget', priceTarget);
	
	if($('#optionRadio').attr('checked')){
		
		var changeOptions = document.querySelectorAll('.changeOption');
		var changeOptionNames = new Array();
		var changeOptionValues = new Array();
		for(var i=0; i<changeOptions.length;i++){
			var cOptionName = changeOptions[i].querySelector('.changeOptionName').value;
			var cOptionValue = changeOptions[i].querySelector('.changeOptionValues').value;
			if(cOptionName === "" || cOptionName === undefined || cOptionName === null){
				alert('옵션이름을 정확하게 입력 해 주세요.');
				return false;
			}else if(cOptionValue === "" || cOptionValue === undefined || cOptionValue === null){
				alert('옵션값을 정확하게 입력 해 주세요.');
				return false;
			}else{
				changeOptionNames[i] = cOptionName;
				changeOptionValues[i] = cOptionValue;	
			}
		}

		addForm.append('changeOptionNameArray', changeOptionNames);
		addForm.append('changeOptionValueArray', changeOptionValues);
		
		var noneChangeOptions = document.querySelectorAll('.noneChangeOptions');
		var noneChangeOptionsNames = new Array();
		var noneChangeOptionsValues = new Array();
		
		for(var i=0; i<noneChangeOptions.length;i++){
			var nOptionName = noneChangeOptions[i].querySelector('.noneChangeOptionsNames').value;
			var nOptionValue = noneChangeOptions[i].querySelector('.noneChangeOptionsValues').value;
			if(nOptionName === "" || nOptionName === undefined || nOptionName === null){
				alert('옵션이름을 정확하게 입력 해 주세요.');
				return false;
			}else if(nOptionValue === "" || nOptionValue === undefined || nOptionValue === null){
				alert('옵션값을 정확하게 입력 해 주세요.');
				return false;
			}else{
				noneChangeOptionsNames[i] = nOptionName;
				noneChangeOptionsValues[i] = nOptionValue;	
			}
			
		}
		addForm.append('noneChangeOptionNameArray', noneChangeOptionsNames);
		addForm.append('noneChangeOptionValueArray', noneChangeOptionsValues);
	}else{
		addForm.append('changeOptionNameArray', new Array());
		addForm.append('changeOptionValueArray', new Array());
		addForm.append('noneChangeOptionNameArray', new Array());
		addForm.append('noneChangeOptionValueArray', new Array());
	}
	
	if($('#discountRadio').attr('checked')){
		
		var noneDiscount = document.getElementById('noneDiscount').value;
		var memberDiscount = document.getElementById('memberDiscount').value;
		var dealerDiscount = document.getElementById('dealerDiscount').value;
		addForm.append('noneDiscount', noneDiscount);
		addForm.append('memberDiscount', memberDiscount);
		addForm.append('dealerDiscount', dealerDiscount);
		
	}else{
		addForm.append('noneDiscount', "0");
		addForm.append('memberDiscount', "0");
		addForm.append('dealerDiscount', "0");	
	}
	
	if($('#eventRadio').attr('checked')){
		
		var eventName = document.getElementById('eventName').value;
		var eventNoneDiscount = document.getElementById('eventNoneDiscount').value;
		var eventMemberDiscount = document.getElementById('eventMemberDiscount').value;
		var eventDealerDiscount = document.getElementById('eventDealerDiscount').value;
		
		addForm.append('eventSign', true);
		addForm.append('eventName', eventName);
		addForm.append('eventNoneDiscount', eventNoneDiscount);
		addForm.append('eventMemberDiscount', eventMemberDiscount);
		addForm.append('eventDealerDiscount', eventDealerDiscount);		
	}else{
		addForm.append('eventSign', false);
		addForm.append('eventName', "none");
		addForm.append('eventNoneDiscount', "0");
		addForm.append('eventMemberDiscount', "0");
		addForm.append('eventDealerDiscount', "0");		
	}
	
	var specImage = document.getElementById('specImage').files;
	if(specImage.length < 1){
		addForm.append('specImage', null);	
	}else{
		addForm.append('specImage', specImage);
	}
	
	var addedFiles = document.getElementById('addedFiles').files;
	if(addedFiles.length < 1){
		addForm.append('addedFiles', null);	
	}else{
		addForm.append('addedFiles', addedFiles);
	}
	fetch('/admin/productInsert',{
		method:'POST',
		cache: 'no-cache',
		body: addForm,
	})
	.then(response => {
		console.log(response);
	})
	.then((data) => {
		console.log(data);
	})
	.catch(error => {
		console.log(error);
	});
});









































