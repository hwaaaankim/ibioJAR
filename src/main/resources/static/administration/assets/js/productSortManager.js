const bigSortInsertBtn = document.getElementById('bigsortInsertBtn');
const bigSortValue = document.getElementById('bigSorts');
bigSortInsertBtn.addEventListener('click', function(event) {
	event.preventDefault();
	var result = confirm("대분류들을 등록 하시겠습니까?");
	if (result) {
		var selectedValues = bigSortValue.value;
		if (selectedValues === null || selectedValues === "" || selectedValues === undefined) {
			alert('대분류를 정확하게 입력 해 주세요.');
		} else {
			var form = document.createElement('form');
			form.setAttribute("charset", "UTF-8");
			form.setAttribute("method", "Post");
			form.setAttribute("action", "/admin/bigsortInsert");

			var sort = document.createElement("input");
			sort.setAttribute("type", "hidden");
			sort.setAttribute("name", "bigSorts");
			sort.setAttribute("value", selectedValues);

			form.appendChild(sort);
			document.body.appendChild(form);
			form.submit();
		}
	}
});

const middleSortInsertBtn = document.getElementById('middlesortInsertBtn');
const middleSortValue = document.getElementById('middleSorts');
const middlePanelBigSortId = document.getElementById('middlePanelBigSort');
middleSortInsertBtn.addEventListener('click', function(event) {
	event.preventDefault();
	var result = confirm("중분류들을 등록 하시겠습니까?");
	if (result) {
		if (middlePanelBigSortId.value === null || middlePanelBigSortId.value === "" || middlePanelBigSortId.value === undefined) {
			alert('대분류를 정확하게 선택 해 주세요.');
		} else if (middleSortValue.value === null || middleSortValue.value === "" || middleSortValue.value === undefined) {
			alert('중분류를 입력 해 주세요.');
		} else {
			var selectedValues = middleSortValue.value;
			var form = document.createElement('form');
			form.setAttribute("charset", "UTF-8");
			form.setAttribute("method", "Post");
			form.setAttribute("action", "/admin/middlesortInsert");

			var sort = document.createElement("input");
			sort.setAttribute("type", "hidden");
			sort.setAttribute("name", "middleSorts");
			sort.setAttribute("value", selectedValues);

			var bigId = document.createElement("input");
			bigId.setAttribute("type", "hidden");
			bigId.setAttribute("name", "bigId");
			bigId.setAttribute("value", middlePanelBigSortId.value);

			form.appendChild(sort);
			form.appendChild(bigId);
			document.body.appendChild(form);
			form.submit();
		}
	}
});

const smallSortInsertBtn = document.getElementById('smallsortInsertBtn');
const smallSortValue = document.getElementById('smallSorts');
const smallPanelBigSortId = document.getElementById('smallPanelBigSort');
const smallPanelMiddleSortId = document.getElementById('smallPanelMiddleSort');
smallSortInsertBtn.addEventListener('click', function(event) {
	event.preventDefault();
	var result = confirm("소분류들을 등록 하시겠습니까?");
	var selectedValues = smallSortValue.value;
	if (result) {
		if (smallPanelBigSortId.value === null || smallPanelBigSortId.value === "" || smallPanelBigSortId.value === undefined) {
			alert('대분류를 정확하게 선택 해 주세요.');
		} else if (smallPanelMiddleSortId.value === null || smallPanelMiddleSortId.value === "" || smallPanelMiddleSortId.value === undefined) {
			alert('중분류를 정확하게 선택 해 주세요.');
		} else if (selectedValues === null || selectedValues === "" || selectedValues === undefined) {
			alert('소분류를 입력 해 주세요.');
		} else {

			var form = document.createElement('form');
			form.setAttribute("charset", "UTF-8");
			form.setAttribute("method", "Post");
			form.setAttribute("action", "/admin/smallsortInsert");

			var sort = document.createElement("input");
			sort.setAttribute("type", "hidden");
			sort.setAttribute("name", "smallSorts");
			sort.setAttribute("value", selectedValues);

			var middleId = document.createElement("input");
			middleId.setAttribute("type", "hidden");
			middleId.setAttribute("name", "middleId");
			middleId.setAttribute("value", smallPanelMiddleSortId.value);

			form.appendChild(sort);
			form.appendChild(middleId);
			document.body.appendChild(form);
			form.submit();
		}
	}
});
$(function() {
	$('#bigsortDeleteBtn').attr('disabled', true);
	$('#bigPanelBigSort').on('change', function() {
		$('#bigsortDeleteBtn').attr('disabled', false);
		$('#bigsortDeleteBtn').on('click', function() {
			var arr = new Array();
			arr = $('#bigPanelBigSort').val();
			$.ajax({
				cache: false,
				type: 'POST',
				url: '/admin/bigSortDelete',
				data: {
					text: arr,
				}, error: function(error) {
					//console.log(error);
					alert('해당 분류가 적용된 중분류를 삭제 후 시도해 주세요');
				}

			}).done(function(fragment) {
				//console.log('frag' + fragment);
				if (fragment != 'fail') {
					$('#bigPanelBigSort').replaceWith(fragment);
					alert('삭제 되었습니다.');
					location.reload();
				} else {
					location.reload();
				}
			});
		});
	});

	$('#middlesortDeleteBtn').attr('disabled', true);
	$('#middlePanelBigSort').on('change', function() {

		$.ajax({
			cache: false,
			type: 'POST',
			url: '/admin/searchMiddleSort',
			data: {
				bigId: $(this).val()
			}, success: function(result) {
				$('#middlePanelMiddleSort').find('option').remove();
				for (var i = 0; i < result.length; i++) {
					var option = $("<option value=" + result[i].id + ">" + result[i].name + "</option>");
					$('#middlePanelMiddleSort').append(option);
				}
			}

		});
		
		$('#middlePanelMiddleSort').on('change', function() {
			$('#middlesortDeleteBtn').attr('disabled', false);
			$('#middlesortDeleteBtn').on('click', function() {
				var arr = new Array();
				arr = $('#middlePanelMiddleSort').val();
				$.ajax({
					cache: false,
					type: 'POST',
					url: '/admin/middleSortDelete',
					data: {
						text: arr,
						bigId: $('#middlePanelBigSort').val()
					}, error: function(error) {
						alert('해당 분류가 적용된 소분류를 삭제 후 시도해 주세요');
					}

				}).done(function(fragment) {
					if (fragment != 'fail') {
						$('#middlePanelMiddleSort').replaceWith(fragment);
						alert('삭제 되었습니다.');
						location.reload();
					} else {
						location.reload();
					}
				});
			});

		});
	});
	
	$('#smallsortDeleteBtn').attr('disabled', true);
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
					for (var i = 0; i < result.length; i++) {
						var option = $("<option value=" + result[i].id + ">" + result[i].name + "</option>");
						$('#smallPanelSmallSort').append(option);
					}
				}

			});
			$('#smallPanelSmallSortSelect').on('change', function() {
				$('#smallsortDeleteBtn').attr('disabled', false);
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





















