const autoHyphen = (target) => {
	target.value = target.value
		.replace(/[^0-9]/g, '')
		.replace(/^(\d{0,3})(\d{0,4})(\d{0,4})$/g, "$1-$2-$3").replace(/(\-{1,2})$/g, "");
	}
	
	
$(document).ready(function() {
  $('.file-btn').on('click', function() {
    $(this).prev().trigger('click');
  });

  $('.file').on('change', function() {
    var fileName = $(this)[0].files[0].name;    
    $(this).prev().val(fileName);
  });
});


function fileCheck(target){
	console.log(target.files.length);
	if(target.files.length > 3){
		alert("10개 이내의 파일만 업로드 가능합니다.");
		target.value = "";
	}
}

