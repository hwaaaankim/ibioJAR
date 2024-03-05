function createString(i){
	return ('<div class="row optionSelectForm-' + i + '">'
	+ '<div class="col-lg-5 col-sm-12">'
	+ '<div class="card-body">'
	+ '<div class="hstack gap-1 align-items-start">'
	+ '<div class="flex-grow-1">'
	+ '<input type="text" class="form-control" placeholder="옵션 제목을 입력 해 주세요.">'
	+ '</div></div></div></div>'
	+ '<div class="col-lg-5 col-sm-12">'
	+ '<div class="card-body">'
	+ '<div class="hstack gap-1 align-items-start">'
	+ '<div class="flex-grow-1">'
	+ '<input class="form-control" data-choices-'
	+ i
	+ ' data-choices-multiple-remove="true" placeholder="옵션 종류를 입력 해 주세요." type="text" value="예시 : 15ML" />'
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
	'<div class="row optionSelectForm-' + i + '">'
	+ '<div class="col-lg-5 col-sm-12">'
	+ '<div class="card-body">'
	+ '<div class="hstack gap-1 align-items-start">'
	+ '<div class="flex-grow-1">'
	+ '<input type="text" class="form-control" placeholder="옵션 제목을 입력 해 주세요.">'
	+ '</div></div></div></div>'
	+ '<div class="col-lg-5 col-sm-12">'
	+ '<div class="card-body">'
	+ '<div class="hstack gap-1 align-items-start">'
	+ '<div class="flex-grow-1">'
	+ '<input class="form-control" data-choices-'
	+ i
	+ ' data-choices-multiple-remove="true" placeholder="옵션 종류를 입력 해 주세요." type="text" value="15ML:+1500" />'
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
	$('#eventSection').css('display', 'none');
	$('#optionSection').css('display', 'none');
	$('#discountSection').css('display', 'none');

	$('#discountRadio').on('change', function() {
		$('#discountSection').slideToggle();
	});

	$('#optionRadio').on('change', function() {
		$('#optionSection').slideToggle();
	});
	$('#optionAddButton').attr('disabled', true);
	$('input[name=priceChange]').on('change', function() {
		$('#optionSelectForm').empty();
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
});

