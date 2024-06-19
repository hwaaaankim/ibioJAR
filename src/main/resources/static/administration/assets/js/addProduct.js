function createString(i){
	return ('<div class="row noneChangeOption optionSelectForm-' + i + '">'
	+ '<div class="col-lg-5 col-sm-12">'
	+ '<div class="card-body">'
	+ '<div class="hstack gap-1 align-items-start">'
	+ '<div class="flex-grow-1">'
	+ '<input required type="text" class="form-control" name="noneChangeOptionName" placeholder="가격 변동이 없는옵션 제목을 입력 해 주세요.">'
	+ '</div></div></div></div>'
	+ '<div class="col-lg-5 col-sm-12">'
	+ '<div class="card-body">'
	+ '<div class="hstack gap-1 align-items-start">'
	+ '<div class="flex-grow-1">'
	+ '<input required class="form-control" name="noneChangeOptionValues" data-choices-'
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
	+ '<input required type="text" class="form-control" name="changeOptionName" placeholder="가격 변동이 있는 옵션 제목을 입력 해 주세요.">'
	+ '</div></div></div></div>'
	+ '<div class="col-lg-5 col-sm-12">'
	+ '<div class="card-body">'
	+ '<div class="hstack gap-1 align-items-start">'
	+ '<div class="flex-grow-1">'
	+ '<input required class="form-control" name="changeOptionValues" data-choices-'
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
	
	var productSpecs = document.getElementById('productSpecs');
	var productTags = document.getElementById('productTags');
	var target = document.getElementById('target');
	
	var noneDiscount = document.getElementById('noneDiscount');
	var memberDiscount = document.getElementById('memberDiscount');
	var dealerDiscount = document.getElementById('dealerDiscount');
	
	var noneChangeOption = document.querySelectorAll('.noneChangeOption');
	var noneChangeOptionName = document.querySelectorAll('.noneChangeOptionName');
	var noneChangeOptionValues = document.querySelectorAll('.noneChangeOptionValues');
	
	var changeOption = document.querySelectorAll('.changeOption');
	var changeOptionName = document.querySelectorAll('.changeOptionName');
	var changeOptionValues = document.querySelectorAll('.changeOptionValues');
	
	var eventName = document.getElementById('eventName');
	var eventNoneDiscount = document.getElementById('eventNoneDiscount');
	var eventMemberDiscount = document.getElementById('eventMemberDiscount');
	var eventDealerDiscount = document.getElementById('eventDealerDiscount');
	
	console.log(addForm.getAttribute("action"));
});









































