function createString(i){
	return ('<div class="row noneChangeOption optionSelectForm-' + i + '">'
	+ '<div class="col-lg-3 col-sm-12">'
	+ '<div class="card-body" style="padding-right:0px; padding-left:0px;">'
	+ '<div class="hstack gap-0 align-items-start">'
	+ '<div class="flex-grow-1">'
	+ '<input required type="text" class="form-control" name="noneChangeOptionName" placeholder="옵션 제목 입력">'
	+ '</div></div></div></div>'
	+ '<div class="col-lg-3 col-sm-12">'
	+ '<div class="card-body" style="padding-right:0px; padding-left:0px;">'
	+ '<div class="hstack gap-0 align-items-start">'
	+ '<div class="flex-grow-1">'
	+ '<input required class="form-control" name="noneChangeOptionValues" type="text" placeholder="옵션 이름 입력 예) 빨강색" />'
	+ '</div></div></div></div>'
	+ '<div class="col-lg-3 col-sm-12">'
	+ '<div class="card-body" style="padding-right:0px; padding-left:0px;">'
	+ '<div class="hstack gap-0 align-items-start">'
	+ '<div class="flex-grow-1">'
	+ '<input required class="form-control" name="noneChangeOptionFiles" type="file"/>'
	+ '</div></div></div></div>'
	+ '<div class="col-lg-2 col-sm-12">'
	+ '<div class="card-body" style="padding-right:0px; padding-left:0px;">'
	+ '<div class="hstack gap-0 align-items-start">'
	+ '<div class="flex-grow-1">'
	+ '<button type="button" class="btn btn-warning btn-md" onclick=deleteBtn("none",'+ i +');> <i class="bx bx-minus-circle"></i> 옵션삭제</button>'
	+ '</div></div></div></div></div>')
}

function createChangeString(i){
	return (
	'<div class="row changeOption optionSelectForm-' + i + '">'
	+ '<div class="col-lg-2 col-sm-12">'
	+ '<div class="card-body" style="padding-right:0px; padding-left:0px;">'
	+ '<div class="hstack gap-1 align-items-start">'
	+ '<div class="flex-grow-1">'
	+ '<input required type="text" class="form-control" name="changeOptionName" placeholder="옵션제목 입력">'
	+ '</div></div></div></div>'
	+ '<div class="col-lg-2 col-sm-12">'
	+ '<div class="card-body" style="padding-right:0px; padding-left:0px;">'
	+ '<div class="hstack gap-1 align-items-start">'
	+ '<div class="flex-grow-1">'
	+ '<input required class="form-control" name="changeOptionValues" placeholder="옵션값 입력 예) 1500" type="text" />'
	+ '</div></div></div></div>'
	+ '<div class="col-lg-2 col-sm-12">'
	+ '<div class="card-body" style="padding-right:0px; padding-left:0px;">'
	+ '<div class="hstack gap-1 align-items-start">'
	+ '<div class="flex-grow-1">'
	+ '<input required class="form-control" name="changeOptionUnits" placeholder="옵션단위 입력 예) Ml" type="text" />'
	+ '</div></div></div></div>'
	+ '<div class="col-lg-2 col-sm-12">'
	+ '<div class="card-body" style="padding-right:0px; padding-left:0px;">'
	+ '<div class="hstack gap-1 align-items-start">'
	+ '<div class="flex-grow-1">'
	+ '<input required class="form-control" name="changeOptionPrices" placeholder="옵션 가격변동입력 예) 1500" type="number" />'
	+ '</div></div></div></div>'
	+ '<div class="col-lg-2 col-sm-12">'
	+ '<div class="card-body" style="padding-right:0px; padding-left:0px;">'
	+ '<div class="hstack gap-1 align-items-start">'
	+ '<div class="flex-grow-1">'
	+ '<input required class="form-control" name="changeOptionSigns" placeholder="옵션 가격변동 부호 입력 예) + 또는 -" type="text" />'
	+ '</div></div></div></div>'
	+ '<div class="col-lg-2 col-sm-12">'
	+ '<div class="card-body" style="padding-right:0px; padding-left:0px;">'
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
	}
	
	var middleId = document.getElementById('smallPanelMiddleSort').value;
	if(middleId === null || middleId === "" || middleId === undefined){
		alert('중분류를 선택 해 주세요.');
		return false;
	}
	
	var smallId = document.getElementById('smallPanelSmallSort').value;
	if(smallId === null || smallId === "" || smallId === undefined){
		alert('소분류를 선택 해 주세요.');
		return false;
	}

	var title = document.getElementById('title').value;
	if(title === null || title === "" || title === undefined){
		alert('제품명을 입력 해 주세요.');
		return false;
	}
	
	if($('#optionRadio').attr('checked')){
		
		var changeOptions = document.querySelectorAll('.changeOption');
		for(var i=0; i<changeOptions.length;i++){
			var cOptionName = changeOptions[i].querySelector('.changeOptionName').value;
			var cOptionValue = changeOptions[i].querySelector('.changeOptionValues').value;
			console.log(cOptionName);
			console.log(cOptionValue);
			if(cOptionName === "" || cOptionName === undefined || cOptionName === null){
				alert('옵션이름을 정확하게 입력 해 주세요.');
				return false;
			}else if(cOptionValue === "" || cOptionValue === undefined || cOptionValue === null){
				alert('옵션값을 정확하게 입력 해 주세요.');
				return false;
			}
		}

		var noneChangeOptions = document.querySelectorAll('.noneChangeOptions');
		for(var i=0; i<noneChangeOptions.length;i++){
			
			var nOptionName = noneChangeOptions[i].querySelector('.noneChangeOptionsNames').value;
			var nOptionValue = noneChangeOptions[i].querySelector('.noneChangeOptionsValues').value;
			console.log(nOptionName);
			console.log(nOptionValue);
			if(nOptionName === "" || nOptionName === undefined || nOptionName === null){
				alert('옵션이름을 정확하게 입력 해 주세요.');
				return false;
			}else if(nOptionValue === "" || nOptionValue === undefined || nOptionValue === null){
				alert('옵션값을 정확하게 입력 해 주세요.');
				return false;
			}
			
		}
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
	
	var productImage = document.getElementById('productImage').files;
	if(productImage.length<1){
		alert('제품의 대표 이미지를 선택 해 주세요.');
		return false;
	}
	
	var price = document.getElementById('price').value;
	if(price === null || price === "" || price === undefined){
		alert('제품 가격을 숫자로 정확하게 입력 해 주세요.');
		return false;
	}

	document.body.appendChild(addForm);
	addForm.submit();

});









































