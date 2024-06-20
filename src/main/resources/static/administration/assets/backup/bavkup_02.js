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
	var addForm = document.getElementById('productAddForm');
	
	var bigId = document.getElementById('smallPanelBigSort').value;
	if(bigId === null || bigId === "" || bigId === undefined){
		alert('대분류를 선택 해 주세요.');
		return false;
	}else{
		var bigIdInput = document.createElement("input");
		bigIdInput.setAttribute("type", "hidden");
		bigIdInput.setAttribute("name", "bigId");
		bigIdInput.setAttribute("value", bigId);
		addForm.appendChild(bigIdInput);
	}
	
	var middleId = document.getElementById('smallPanelMiddleSort').value;
	if(middleId === null || middleId === "" || middleId === undefined){
		alert('중분류를 선택 해 주세요.');
		return false;
	}else{
		var middleIdInput = document.createElement("input");
		middleIdInput.setAttribute("type", "hidden");
		middleIdInput.setAttribute("name", "middleId");
		middleIdInput.setAttribute("value", middleId);
		addForm.appendChild(middleIdInput);
	}
	
	var smallId = document.getElementById('smallPanelSmallSort').value;
	if(smallId === null || smallId === "" || smallId === undefined){
		alert('소분류를 선택 해 주세요.');
		return false;
	}else{
		var smallIdInput = document.createElement("input");
		smallIdInput.setAttribute("type", "hidden");
		smallIdInput.setAttribute("name", "smallId");
		smallIdInput.setAttribute("value", smallId);
		addForm.appendChild(smallIdInput);
	}
	
	var brand = document.getElementById('brand').value;
	var brandInput = document.createElement("input");
	brandInput.setAttribute("type", "hidden");
	brandInput.setAttribute("name", "brand");
	brandInput.setAttribute("value", brand);
	addForm.appendChild(brandInput);

	var brandImage = document.getElementById('brandImage').files;
	var brandImageInput = document.createElement("input");
	brandImageInput.setAttribute("type", "file");
	brandImageInput.setAttribute("name", "brandImage");
	brandImageInput.setAttribute("value", brandImage[0]);
	addForm.appendChild(brandImageInput);
	
	var title = document.getElementById('title').value;
	if(title === null || title === "" || title === undefined){
		alert('제품명을 입력 해 주세요.');
		return false;
	}else{
		var titleInput = document.createElement("input");
		titleInput.setAttribute("type", "hidden");
		titleInput.setAttribute("name", "title");
		titleInput.setAttribute("value", title);
		addForm.appendChild(titleInput);
	}
	
	var productSpecs = document.getElementById('productSpecs').value;
	var spec = document.createElement("input");
	spec.setAttribute("type", "hidden");
	spec.setAttribute("name", "productSpecs");
	spec.setAttribute("value", productSpecs);
	addForm.appendChild(spec);
	
	var productTags = document.getElementById('productTags').value;
	var tags = document.createElement("input");
	tags.setAttribute("type", "hidden");
	tags.setAttribute("name", "productTags");
	tags.setAttribute("value", productTags);
	addForm.appendChild(tags);
	
	var description = document.getElementById('description').value;
	var descriptionInput = document.createElement("input");
	descriptionInput.setAttribute("type", "hidden");
	descriptionInput.setAttribute("name", "description");
	descriptionInput.setAttribute("value", description);
	addForm.appendChild(descriptionInput);
	
	var productImage = document.getElementById('productImage').files;
	console.log(productImage[0]);
	if(productImage.length<1){
		alert('제품의 대표 이미지를 선택 해 주세요.');
		return false;
	}else{
		var productImageInput = document.createElement("input");
		productImageInput.setAttribute("type", "file");
		productImageInput.setAttribute("name", "productImage");
		productImageInput.setAttribute("value", productImage[0]);
		addForm.appendChild(productImageInput);
	}
	
	var slideImages = document.getElementById('slideImages').files;
	var slideImagesInput = document.createElement("input");
	slideImagesInput.setAttribute("type", "file");
	slideImagesInput.setAttribute("name", "slideImages");
	slideImagesInput.setAttribute("value", slideImages);
	addForm.appendChild(slideImagesInput);
	
	var price = document.getElementById('price').value;
	if(price === null || price === "" || price === undefined){
		alert('제품 가격을 숫자로 정확하게 입력 해 주세요.');
		return false;
	}else{
		var priceInput = document.createElement("input");
		priceInput.setAttribute("type", "hidden");
		priceInput.setAttribute("name", "price");
		priceInput.setAttribute("value", price);
		addForm.appendChild(priceInput);
	}
	
	var priceTarget = document.getElementById('target').value;
	var target = document.createElement("input");
	target.setAttribute("type", "hidden");
	target.setAttribute("name", "priceTarget");
	target.setAttribute("value", priceTarget);
	addForm.appendChild(target);
	
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
		
		var changeOptionNameArray = document.createElement("input");
		changeOptionNameArray.setAttribute("type", "hidden");
		changeOptionNameArray.setAttribute("name", "changeOptionNameArray");
		changeOptionNameArray.setAttribute("value", changeOptionNames);
		addForm.appendChild(changeOptionNameArray);
		
		var changeOptionValueArray = document.createElement("input");
		changeOptionValueArray.setAttribute("type", "hidden");
		changeOptionValueArray.setAttribute("name", "changeOptionValueArray");
		changeOptionValueArray.setAttribute("value", changeOptionValues);
		addForm.appendChild(changeOptionValueArray);
		
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
		var noneChangeOptionNameArray = document.createElement("input");
		noneChangeOptionNameArray.setAttribute("type", "hidden");
		noneChangeOptionNameArray.setAttribute("name", "noneChangeOptionNameArray");
		noneChangeOptionNameArray.setAttribute("value", noneChangeOptionsNames);
		addForm.appendChild(noneChangeOptionNameArray);
		
		var noneChangeOptionValueArray = document.createElement("input");
		noneChangeOptionValueArray.setAttribute("type", "hidden");
		noneChangeOptionValueArray.setAttribute("name", "noneChangeOptionValueArray");
		noneChangeOptionValueArray.setAttribute("value", noneChangeOptionsValues);
		addForm.appendChild(noneChangeOptionValueArray);
	}
	console.log($('#discountRadio').attr('checked'));
	
	if($('#discountRadio').attr('checked')){
		
		var noneDiscount = document.getElementById('noneDiscount').value;
		var memberDiscount = document.getElementById('memberDiscount').value;
		var dealerDiscount = document.getElementById('dealerDiscount').value;
		
		var noneD = document.createElement("input");
		noneD.setAttribute("type", "hidden");
		noneD.setAttribute("name", "noneD");
		noneD.setAttribute("value", noneDiscount);
		
		var memberD = document.createElement("input");
		memberD.setAttribute("type", "hidden");
		memberD.setAttribute("name", "memberD");
		memberD.setAttribute("value", memberDiscount);
		
		var dealerD = document.createElement("input");
		dealerD.setAttribute("type", "hidden");
		dealerD.setAttribute("name", "dealerD");
		dealerD.setAttribute("value", dealerDiscount);
		addForm.appendChild(noneD);
		addForm.appendChild(memberD);
		addForm.appendChild(dealerD);	
		
	}else{
		var noneD = document.createElement("input");
		noneD.setAttribute("type", "hidden");
		noneD.setAttribute("name", "noneD");
		noneD.setAttribute("value", "0");
		
		var memberD = document.createElement("input");
		memberD.setAttribute("type", "hidden");
		memberD.setAttribute("name", "memberD");
		memberD.setAttribute("value", "0");
		
		var dealerD = document.createElement("input");
		dealerD.setAttribute("type", "hidden");
		dealerD.setAttribute("name", "dealerD");
		dealerD.setAttribute("value", "0");
		addForm.appendChild(noneD);
		addForm.appendChild(memberD);
		addForm.appendChild(dealerD);	
	}
	
	if($('#eventRadio').attr('checked')){
		
		var eventName = document.getElementById('eventName').value;
		var eventNoneDiscount = document.getElementById('eventNoneDiscount').value;
		var eventMemberDiscount = document.getElementById('eventMemberDiscount').value;
		var eventDealerDiscount = document.getElementById('eventDealerDiscount').value;
		
		var eventSign = document.createElement("input");
		eventSign.setAttribute("type", "hidden");
		eventSign.setAttribute("name", "eventSign");
		eventSign.setAttribute("value", true);
		
		var eventN = document.createElement("input");
		eventN.setAttribute("type", "hidden");
		eventN.setAttribute("name", "eventN");
		eventN.setAttribute("value", eventName);
		
		var eventNoneD = document.createElement("input");
		eventNoneD.setAttribute("type", "hidden");
		eventNoneD.setAttribute("name", "eventNoneD");
		eventNoneD.setAttribute("value", eventNoneDiscount);
		
		var eventMemberD = document.createElement("input");
		eventMemberD.setAttribute("type", "hidden");
		eventMemberD.setAttribute("name", "eventMemberD");
		eventMemberD.setAttribute("value", eventMemberDiscount);
		
		var eventDealerD = document.createElement("input");
		eventDealerD.setAttribute("type", "hidden");
		eventDealerD.setAttribute("name", "eventDealerD");
		eventDealerD.setAttribute("value", eventDealerDiscount);
		
		addForm.appendChild(eventSign);
		addForm.appendChild(eventN);
		addForm.appendChild(eventNoneD);
		addForm.appendChild(eventMemberD);
		addForm.appendChild(eventDealerD);
		
	}else{
		
		var eventSign = document.createElement("input");
		eventSign.setAttribute("type", "hidden");
		eventSign.setAttribute("name", "eventSign");
		eventSign.setAttribute("value", false);
		
		var eventN = document.createElement("input");
		eventN.setAttribute("type", "hidden");
		eventN.setAttribute("name", "eventN");
		eventN.setAttribute("value", "none");
		
		var eventNoneD = document.createElement("input");
		eventNoneD.setAttribute("type", "hidden");
		eventNoneD.setAttribute("name", "eventNoneD");
		eventNoneD.setAttribute("value", "0");
		
		var eventMemberD = document.createElement("input");
		eventMemberD.setAttribute("type", "hidden");
		eventMemberD.setAttribute("name", "eventMemberD");
		eventMemberD.setAttribute("value", "0");
		
		var eventDealerD = document.createElement("input");
		eventDealerD.setAttribute("type", "hidden");
		eventDealerD.setAttribute("name", "eventDealerD");
		eventDealerD.setAttribute("value", "0");
		
		addForm.appendChild(eventSign);
		addForm.appendChild(eventN);
		addForm.appendChild(eventNoneD);
		addForm.appendChild(eventMemberD);
		addForm.appendChild(eventDealerD);
	}
	
	document.body.appendChild(addForm);
	addForm.submit();

});









































